package service.action.image;

import static service.MessageProvider.*;
import static service.action.common.Constants.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dao.impl.GroupDAOImpl;
import database.dao.interfaces.GenericDAO;
import exception.DaoException;
import exception.UploadException;
import model.Group;
import service.InputValidator;
import service.MessageProvider;
import service.action.common.Action;

/**
 * Created by Silin on 08.2018.
 */

/**
 * ChangeImage command processes the user request to change an image
 */
public class ChangeImage implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws DaoException, ServletException, IOException, UploadException {
		
		GenericDAO<Group> groupDao = new GroupDAOImpl();

		String idParameter = request.getParameter("id");
		String name = request.getParameter("name");
		String description = request.getParameter("description");

		// validate id from request
		if (InputValidator.validateId(idParameter)) {
			long groupId = Long.parseLong(idParameter);
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
		return MAIN;
	}

}
