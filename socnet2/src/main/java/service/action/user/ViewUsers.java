package service.action.user;

import static service.action.common.Constants.USERS;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dao.impl.UserDAOImpl;
import database.dao.interfaces.GenericDAO;
import exception.DaoException;
import model.User;
import service.action.common.Action;

/**
 * Created by Silin on 07.2018.
 */

/**
 * ViewUsers action processes a user request to see all users on the site
 */
public class ViewUsers implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
		GenericDAO<User> userDao = new UserDAOImpl();
		Collection<User> users = userDao.getAll();
		request.setAttribute("users", users);
		return USERS;
	}
}