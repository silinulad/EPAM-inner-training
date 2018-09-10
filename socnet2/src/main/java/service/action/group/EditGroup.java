package service.action.group;

import static service.MessageProvider.*;
import static service.action.common.Constants.ERROR_MODAL_PARAM;
import static service.action.common.Constants.ERROR_PARAM;
import static service.action.common.Constants.SUCCESS_PARAM;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dao.impl.GroupDAOImpl;
import database.dao.interfaces.GenericDAO;
import exception.DaoException;
import model.Group;
import service.InputValidator;
import service.MessageProvider;
import service.action.common.Action;

/**
 * Created by Silin on 08.2018.
 */


/**
 * EditGroup action processes the group owner request to change the group
 * information
 */
public class EditGroup implements Action {
	private long groupId;
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
		GenericDAO<Group> groupDao = new GroupDAOImpl();

		String idParameter = request.getParameter("id");
		String name = request.getParameter("name");
		String description = request.getParameter("description");

		// validate id from request
		if (InputValidator.validateId(idParameter)) {
			groupId = Long.parseLong(idParameter);
			Group group = groupDao.read(groupId);

			if (group != null) {
				// validate input parameters
				MessageProvider validationResult = InputValidator.validateGroup(name, description);
				if (validationResult != VALID) {
					request.setAttribute(ERROR_MODAL_PARAM, validationResult);
				} else {
					group.setName(name);
					group.setDescription(description);
					groupDao.update(group);
					request.setAttribute(SUCCESS_PARAM, GROUP_UPDATED_SUCCESS);
				}
				return "/main?action=group&id=" + groupId;
			} else {
				request.setAttribute(ERROR_PARAM, GROUP_NOT_FOUND_ERROR);
			}
		} else {
			request.setAttribute(ERROR_PARAM, GROUP_ID_ERROR);
		}
		return "/main?action=group&id=" + groupId;
	}
}
