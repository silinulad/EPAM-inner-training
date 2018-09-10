package service.action.group;

import static service.MessageProvider.*;
import static service.action.common.Constants.ERROR_PARAM;
import static service.action.common.Constants.SUCCESS_PARAM;
import static service.action.common.Constants.USER_PARAM;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.dao.impl.GroupDAOImpl;
import database.dao.interfaces.GenericDAO;
import exception.DaoException;
import model.Group;
import model.User;
import service.InputValidator;
import service.action.common.Action;

/**
 * Created by Silin on 08.2018.
 */

/**
 * DeleteGroup action processes group owner request to delete group
 */
public class DeleteGroup implements Action {

	private long groupId;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(USER_PARAM);

		GenericDAO<Group> groupDao = new GroupDAOImpl();

		String idParameter = request.getParameter("id");

		if (InputValidator.validateId(idParameter)) {
			groupId = Long.parseLong(idParameter);
			Group group = groupDao.read(groupId);

			if (group != null) {
				// check that user is Group Owner
				if ((group.isOwner(user))) {
					groupDao.delete(group);
					request.setAttribute(SUCCESS_PARAM, GROUP_DELETED_SUCCESS);
				} else {
					request.setAttribute(ERROR_PARAM, INSUFFICIENT_RIGHTS_ERROR);
					return "/main?action=group&id=" + groupId;
				}
			} else {
				request.setAttribute(ERROR_PARAM, GROUP_NOT_FOUND_ERROR);
			}
		} else {
			request.setAttribute(ERROR_PARAM, GROUP_ID_ERROR);
		}
		return "/main?action=group&id=" + groupId;
	}
}
