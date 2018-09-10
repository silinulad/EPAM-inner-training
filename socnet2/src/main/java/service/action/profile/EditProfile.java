package service.action.profile;

import static service.MessageProvider.PROFILE_CHANGE_SUCCESSFUL;
import static service.MessageProvider.USER_EXISTS_ERROR;
import static service.MessageProvider.VALID;
import static service.action.common.Constants.*;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import database.dao.impl.UserDAOImpl;
import database.dao.impl.UserImageDAOImpl;
import database.dao.interfaces.UserDAO;
import database.dao.interfaces.UserImageDAO;
import exception.DaoException;
import model.AttachFile;
import model.User;
import service.InputValidator;
import service.MessageProvider;
import service.action.common.Action;

/**
 * Created by Silin on 08.2018.
 */

/**
 * EditProfile action processes user request to change profile information
 */
public class EditProfile implements Action {

	private static final Logger logger = Logger.getLogger(EditProfile.class);


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {

		HttpSession session = request.getSession();
		UserDAO userDao = new UserDAOImpl();
		UserImageDAO userImageDao = new UserImageDAOImpl();

		User user = (User) session.getAttribute(USER_PARAM);
		String oldEmail = user.getEmail();

		String email = request.getParameter("email");
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String sex = request.getParameter("sex");
		String phone = request.getParameter("phone");
		String birthDate = request.getParameter("birthdate");
		String country = request.getParameter("country");
		String city = request.getParameter("city");

		// validate form input
		MessageProvider validationResult = InputValidator.validateProfileChange(email, firstName, lastName, sex, phone,
				birthDate, country, city);
		if (validationResult != VALID) {
			request.setAttribute(ERROR_PARAM, validationResult);
		} else {
			user.setEmail(email);
			if (!email.equals(oldEmail) && userDao.isExists(user)) {
				user.setEmail(oldEmail);
				request.setAttribute(ERROR_PARAM, USER_EXISTS_ERROR);
			} else {
				// convert a date to avoid a sql error
				DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
				java.util.Date parsedDate = new java.util.Date();
				try {
					parsedDate = format.parse(birthDate);
				} catch (ParseException e) {
					logger.error("Failed to parse date: ", e);
				}
				AttachFile lastUploadedImage = userImageDao.getLastUploadedImage(user);
				String avatar = "";
				if (lastUploadedImage != null) {
					avatar = lastUploadedImage.getPath();
				}
				// update the user in the database and the session
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setGender(sex.charAt(0));
				user.setPhone(phone);
				user.setBirthDate(new Date(parsedDate.getTime()));
				user.setCountry(country);
				user.setCity(city);
				user.setAvatar(avatar);

				userDao.update(user);

				session.setAttribute(USER_PARAM, user);
				request.setAttribute(SUCCESS_PARAM, PROFILE_CHANGE_SUCCESSFUL);
			}
		}
		return SETTINGS;
	}
}
