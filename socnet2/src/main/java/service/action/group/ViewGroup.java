package service.action.group;

import static service.MessageProvider.GROUP_ID_ERROR;
import static service.MessageProvider.GROUP_NOT_FOUND_ERROR;
import static service.action.common.Constants.*;

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
import service.InputValidator;
import service.action.common.Action;

/**
 * Created by Silin on 08.2018.
 */

/**
 * ViewGroup action processes a user request to see information about the group
 */
public class ViewGroup implements Action {

	private UserGroupsDAO userGroupsDao;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
		GenericDAO<Group> groupDao = new GroupDAOImpl();
		userGroupsDao = new UserGroupsDAOImpl();

		String idParameter = request.getParameter("id");

		if (InputValidator.validateId(idParameter)) {
			Group group = groupDao.read(Long.parseLong(idParameter));

			if (group != null) {
				return setInfo(request, group);
			} else {
				request.setAttribute(ERROR_PARAM, GROUP_NOT_FOUND_ERROR);
			}
		} else {
			request.setAttribute(ERROR_PARAM, GROUP_ID_ERROR);
		}
		return MAIN;
	}

	/**
	 * Set information about the group that will be printed in JSP
	 *
	 * @param request
	 * @param group
	 * @return group page
	 * @throws DaoException
	 */
	private String setInfo(HttpServletRequest request, Group group) throws DaoException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(USER_PARAM);

		request.setAttribute("groupInfo", group);
		Collection<User> groupMembers = userGroupsDao.getUsers(group);
		request.setAttribute("groupMembers", groupMembers);

		// Check that user is a group member and show "left group" button
		if (groupMembers.contains(user)) {
			request.setAttribute("isMember", "true");
		}

		return GROUP;
	}
}
