package service.action.setting;

import static service.MessageProvider.OLD_PASSWORD_INVALID_ERROR;
import static service.MessageProvider.PASSWORD_CHANGE_SUCCESSFUL;
import static service.MessageProvider.VALID;
import static service.action.common.Constants.*;
import static utils.Encryption.checkPassword;
import static utils.Encryption.encryptPassword;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.dao.impl.UserDAOImpl;
import database.dao.interfaces.GenericDAO;
import exception.DaoException;
import model.User;
import service.InputValidator;
import service.MessageProvider;
import service.action.common.Action;

/**
 * Created by Silin on 08.2018.
 */

/**
 * ChangePassword command processes user password change request
 */
public class ChangePassword implements Action {


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {


		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(USER_PARAM);

		GenericDAO<User> userDao = new UserDAOImpl();

		String oldPassword = request.getParameter("old_password");
		String newPassword = request.getParameter("new_password");
		String repeatPassword = request.getParameter("repeat_password");

		String oldHash = user.getHash();
		String newHash;

		// validate form input
		MessageProvider validationResult = InputValidator.validatePasswordChange(oldPassword, newPassword,
				repeatPassword);
		if (validationResult != VALID) {
			request.setAttribute(USER_PARAM, validationResult);
			return SETTINGS;
		}

		// validate old password is correct
		if (!checkPassword(oldPassword, oldHash)) {
			request.setAttribute(ERROR_PARAM, OLD_PASSWORD_INVALID_ERROR);
		} else {
			// change the password and update the user in the session
			newHash = encryptPassword(newPassword);
			user.setHash(newHash);
			userDao.update(user);
			session.setAttribute(USER_PARAM, user);

			request.setAttribute(SUCCESS_PARAM, PASSWORD_CHANGE_SUCCESSFUL);
		}
		return SETTINGS;
	}
}
