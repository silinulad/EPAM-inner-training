package service.action.group;

import static service.MessageProvider.GROUP_CREATED_SUCCESS;
import static service.MessageProvider.VALID;
import static service.action.common.Constants.ERROR_PARAM;
import static service.action.common.Constants.USER_PARAM;

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
import service.MessageProvider;
import service.action.common.Action;

/**
 * Created by Silin on 08.2017.
 */

/**
 * AddGroup action processes request to create new group
 */
public class AddGroup implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(USER_PARAM);

		GenericDAO<Group> groupDao = new GroupDAOImpl();
		UserGroupsDAO userGroupsDao = new UserGroupsDAOImpl();

		String name = request.getParameter("name");
		String description = request.getParameter("description");

		// validate input parameters
		MessageProvider validationResult = InputValidator.validateGroup(name, description);
		if (validationResult != VALID) {
			request.setAttribute("name", name);
			request.setAttribute("description", description);
			request.setAttribute("errorMsg", validationResult);
		} else {
			Group group = new Group.Builder().setName(name).setDescription(description).setOwner(user).build();

			groupDao.create(group);
			long groupId = groupDao.getId(group.getName());
			group.setGroupId(groupId);
			userGroupsDao.joinGroup(user, group);

			request.setAttribute(ERROR_PARAM, GROUP_CREATED_SUCCESS);
		}
		return "/main?action=groups";
	}
}
