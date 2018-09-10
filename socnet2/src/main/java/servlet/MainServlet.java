package servlet;

import static service.MessageProvider.DATABASE_ERROR;
import static service.MessageProvider.UNKNOWN_ACTION;
import static service.action.common.Constants.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import exception.ActionException;
import exception.DaoException;
import exception.UploadException;
import service.JspViewResolverImpl;
import service.ServletActionHelper;
import service.ViewResolver;
import service.action.common.Action;
import service.action.common.ActionFactory;

/**
 * Created by Silin on 07.2018.
 */

/**
 * MainServlet processes all user "action" commands and returns a page user will
 * load implemented as a FrontController
 */
@WebServlet("/main")
public class MainServlet extends HttpServlet {

	private ViewResolver		jspView				= new JspViewResolverImpl();
	private static final long	serialVersionUID	= 1L;
	private static final Logger	logger				= Logger.getLogger(MainServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String defaultAction = "profile";

		String targetPath = PROFILE;
		String commandName = request.getParameter(ACTION_PARAM);
		Action action = null;
		boolean isRepeatsAdding = false;

		try {
			if (commandName != null) {
				action = ActionFactory.getInstance().getAction(commandName);
				isRepeatsAdding = ActionFactory.getInstance().isRepeatsAdding(commandName);
				targetPath = action.execute(request, response);
			} else {
				action = ActionFactory.getInstance().getAction(defaultAction);
				targetPath = action.execute(request, response);
			}
			
			if (isRepeatsAdding) {
				ServletActionHelper.redirectTo(request, response, targetPath);
			} else {
				String fullPath = jspView.getPathToView(targetPath);
				if (targetPath.startsWith("/main")) {
					fullPath = targetPath;
				}
				ServletActionHelper.forwardTo(request, response, fullPath);
			}

		}
		catch (ActionException e) {
			logger.error("Failed to process user action request:", e);
			request.setAttribute(ERROR_PARAM, UNKNOWN_ACTION);
			targetPath = MAIN;
			String fullPath = jspView.getPathToView(targetPath);
			ServletActionHelper.forwardTo(request, response, fullPath);
		}
		catch (UploadException e) {
			logger.error("Database error:", e);
			request.setAttribute(ERROR_PARAM, e.getMessage());
			targetPath = MAIN;
			String fullPath = jspView.getPathToView(targetPath);
			ServletActionHelper.forwardTo(request, response, fullPath);
		}
		catch (DaoException e) {
			logger.error("Database error:", e);
			request.setAttribute(ERROR_PARAM, DATABASE_ERROR);
			targetPath = MAIN;
			String fullPath = jspView.getPathToView(targetPath);
			ServletActionHelper.forwardTo(request, response, fullPath);
		}
	}
}
