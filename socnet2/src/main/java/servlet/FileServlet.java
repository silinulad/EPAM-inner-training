package servlet;

import static service.MessageProvider.DATABASE_ERROR;
import static service.MessageProvider.UNKNOWN_ACTION;
import static service.action.common.Constants.*;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import exception.ActionException;
import exception.DaoException;
import exception.UploadException;
import service.JspViewResolverImpl;
import service.ServletActionHelper;
import service.ViewResolver;
import service.action.common.Action;
import service.action.common.ActionFactory;
import utils.UploadPath;

/**
 * Servlet implementation class FileServlet
 */
@WebServlet("/file")
@MultipartConfig(	fileSizeThreshold = 1024 * 1024 * 30, // 30 MB
					maxFileSize = 1024 * 1024 * 50, // 50 MB
					maxRequestSize = 1024 * 1024 * 100) // 100 MB

public class FileServlet extends HttpServlet {

	private ViewResolver		jspView				= new JspViewResolverImpl();
	private static final long	serialVersionUID	= 1L;
	private static boolean		isInitPathsUpload;
	private static final Logger	logger				= Logger.getLogger(MainServlet.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		final String PROJECT_ROOT = "/";
		String realPath = getServletContext().getRealPath(PROJECT_ROOT);
		if (!isInitPathsUpload) {
			UploadPath.initialize(realPath);
			isInitPathsUpload = true;
		}
	}

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

		String TOKEN = "token";
		
		String targetPath = PROFILE;
		String commandName = request.getParameter(ACTION_PARAM);
		Action action = null;

		HttpSession session = request.getSession();
		
		String oldToken = (String) session.getAttribute(TOKEN);
		String requestToken = request.getParameter(TOKEN);
		if (requestToken == null && !oldToken.equals(requestToken)) {
			logger.info("Blocking request with incorrect token from " + request.getRequestURI());
			ServletActionHelper.redirectTo(request, response, MAIN);
		}

		try {
			action = ActionFactory.getInstance().getAction(commandName);
			targetPath = action.execute(request, response);
			response.sendRedirect(targetPath);

		}
		catch (ActionException e) {
			logger.error("Failed to process user action request:", e);
			request.setAttribute(ERROR_PARAM, UNKNOWN_ACTION);
			String fullPath = jspView.getPathToView(targetPath);
			ServletActionHelper.forwardTo(request, response, fullPath);
		}
		catch (UploadException e) {
			request.setAttribute(ERROR_PARAM, e.getMessage());
			String fullPath = jspView.getPathToView(targetPath);
			ServletActionHelper.forwardTo(request, response, fullPath);
		}
		catch (DaoException e) {
			logger.error("Database error:", e);
			request.setAttribute(ERROR_PARAM, DATABASE_ERROR);
			String fullPath = jspView.getPathToView(targetPath);
			ServletActionHelper.forwardTo(request, response, fullPath);
		}
	}
}
