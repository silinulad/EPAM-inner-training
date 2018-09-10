package servlet;

import static service.MessageProvider.DATABASE_ERROR;
import static service.MessageProvider.USER_INCORRECT_ERROR;
import static service.MessageProvider.VALID;
import static utils.Encryption.checkPassword;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import database.dao.impl.UserDAOImpl;
import database.dao.interfaces.UserDAO;
import exception.DaoException;
import model.User;
import service.*;

/**
 * Created by Silin on 07.2018.
 */

/**
 * LoginServlet handles user login requests and checks request parameters. If
 * the login is unsuccessful it provides the reason to the user
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private ViewResolver jspView = new JspViewResolverImpl();
	private static final long	serialVersionUID	= 1L;
	private final static Logger	logger				= Logger.getLogger(LoginServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Secure login page from logged users
		HttpSession session = request.getSession(false);
		ServletActionHelper.checkSession(session, request, response);
		ServletActionHelper.forwardTo(request, response, jspView.getPathToView("login"));
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);

		// Secure login page from logged users
		ServletActionHelper.checkSession(session, request, response);

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// Validate form parameters
		MessageProvider validationResult = InputValidator.validateLogin(email, password);
		if (validationResult != VALID) {
			printError(validationResult, request, response);
			return;
		}

		// Check if the user exists and his password is correct
		try {
			UserDAO userDao = new UserDAOImpl();
			User user = userDao.read(email);
			if (user != null && checkPassword(password, user.getHash())) {

				session.setAttribute("user", user);
				logger.info("User logged in: {" + user.getEmail() + "," + user.getFirstName() + "," + user.getLastName()
						+ "}");
				ServletActionHelper.redirectTo(request, response, "/main");
			} else {
				printError(USER_INCORRECT_ERROR, request, response);
			}
		}
		catch (DaoException e) {
			logger.error("Database error: " + e);
			printError(DATABASE_ERROR, request, response);
		}
	}

	/**
	 * Print the error message to the user
	 *
	 * @param errorMsg
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void printError(MessageProvider errorMsg, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("errorMsg", errorMsg);
		request.setAttribute("email", request.getParameter("email"));
		ServletActionHelper.forwardTo(request, response, "/login.jsp");
	}
}
