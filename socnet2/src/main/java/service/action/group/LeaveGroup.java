package service.action.group;

import static service.MessageProvider.*;
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
 * LeaveGroup action processes a user request to leave specified Group
 */
public class LeaveGroup implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(USER_PARAM);

		GenericDAO<Group> groupDao = new GroupDAOImpl();
		UserGroupsDAO userGroupsDao = new UserGroupsDAOImpl();

		String idParameter = request.getParameter("id");

		// validate id parameter
		if (InputValidator.validateId(idParameter)) {
			long groupId = Long.parseLong(idParameter);
			Group group = groupDao.read(groupId);

			if (group != null) {
				Collection<Group> userGroups = userGroupsDao.getGroups(user);
				if (userGroups.contains(group)) {
					userGroupsDao.leftGroup(user, group);
					request.setAttribute(SUCCESS_PARAM, GROUP_LEFT_SUCCESS);
				} else {
					request.setAttribute(ERROR_PARAM, GROUP_NOT_JOINED_ERROR);
				}
				return "/main?action=group&id=" + groupId;
			} else {
				request.setAttribute(ERROR_PARAM, GROUP_NOT_FOUND_ERROR);
			}
		} else {
			request.setAttribute(ERROR_PARAM, GROUP_ID_ERROR);
		}
		return MAIN;
	}
}
