package servlet;

import static service.MessageProvider.DATABASE_ERROR;
import static service.MessageProvider.USER_EXISTS_ERROR;
import static service.MessageProvider.VALID;
import static utils.Encryption.encryptPassword;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import database.dao.impl.UserDAOImpl;
import database.dao.impl.UserSettingDAOImpl;
import database.dao.interfaces.GenericDAO;
import database.dao.interfaces.UserDAO;
import exception.DaoException;
import model.User;
import model.UserSettings;
import service.*;

/**
 * Created by Silin on 07.2018.
 */

/**
 * RegistrationServlet handles user registration requests and checks request
 * parameters. If registration is unsuccessful provides a reason to the user
 */
@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
	private ViewResolver jspView = new JspViewResolverImpl();
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(RegistrationServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Secure registration page from logged users
		HttpSession session = request.getSession(false);
		ServletActionHelper.checkSession(session, request, response);
		ServletActionHelper.forwardTo(request, response, "/login.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		// Secure registration page from logged users
		ServletActionHelper.checkSession(session, request, response);

		String email = request.getParameter("email");
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String password = request.getParameter("password");
		String repeatPassword = request.getParameter("repeat_password");

		// Validate form parameters
		MessageProvider validationResult = InputValidator.validateRegistration(email, firstName, lastName, password,
				repeatPassword);
		if (validationResult != VALID) {
			printError(validationResult, request, response);
			return;
		}

		User user = new User.Builder()
				.setEmail(email)
				.setFirstName(firstName)
				.setLastName(lastName)
				.setHash(encryptPassword(password))
				.build();

		// Register the user if he doesn't exist in the database
		try {
			UserDAO userDao = new UserDAOImpl();
			GenericDAO<UserSettings> userSettingsDao = new UserSettingDAOImpl();
			if (userDao.isExists(user)) {
				printError(USER_EXISTS_ERROR, request, response);
			} else {
				userDao.create(user);

				// update id and registration date
				user = userDao.read(email);
				
				//set user setting into the session
				UserSettings userOptions = new UserSettings.Builder().setUserId(user.getUserId()).build();
				userSettingsDao.create(userOptions);

				session.setAttribute("user", user);
				logger.info("Successfully registered new user: {" + user.getEmail() + "," + user.getFirstName() + ","
						+ user.getLastName() + "}");
				ServletActionHelper.redirectTo(request, response, "/main");
			}
		} catch (DaoException e) {
			logger.error("Database error: " + e);
			printError(DATABASE_ERROR, request, response);
		}
	}

	private void printError(MessageProvider errorMsg, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("errorMsg", errorMsg);
		// marker to open registration tab
		request.setAttribute("isRegister", "true");

		// Save form parameters
		Enumeration<?> params = request.getParameterNames();
		String param;
		while (params.hasMoreElements()) {
			param = (String) params.nextElement();
			// do not save token
			if (!"token".equals(param)) {
				request.setAttribute(param, request.getParameter(param));
			}
		}
		ServletActionHelper.forwardTo(request, response, jspView.getPathToView("login"));
	}
}
