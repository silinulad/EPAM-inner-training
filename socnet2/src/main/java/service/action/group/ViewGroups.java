package service.action.group;

import static service.action.common.Constants.GROUPS;
import static service.action.common.Constants.USER_PARAM;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.dao.impl.GroupDAOImpl;
import database.dao.impl.UserGroupsDAOImpl;
import database.dao.interfaces.GenericDAO;
import database.dao.interfaces.UserGroupsDAO;
import exception.DaoException;
import model.Group;
import model.User;
import service.action.common.Action;

/**
 * Created by Silin on 07.2018.
 */

/**
 * ViewGroups action processes a user request to see all groups information
 */
public class ViewGroups implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(USER_PARAM);

		GenericDAO<Group> groupDao = new GroupDAOImpl();
		UserGroupsDAO userGroupsDao = new UserGroupsDAOImpl();

		Collection<Group> groups = groupDao.getAll();
		request.setAttribute("groups", groups);

		Collection<Group> userGroups = userGroupsDao.getGroups(user);
		request.setAttribute("userGroups", userGroups);
		return GROUPS;
	}
}
