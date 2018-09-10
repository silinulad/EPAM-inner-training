package service.action.message;

import static service.MessageProvider.GROUP_DELETED_SUCCESS;
import static service.MessageProvider.GROUP_ID_ERROR;
import static service.MessageProvider.GROUP_NOT_FOUND_ERROR;
import static service.action.common.Constants.ERROR_PARAM;
import static service.action.common.Constants.SUCCESS_PARAM;
import static service.action.common.Constants.USER_PARAM;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.dao.impl.MessageDAOImpl;
import database.dao.impl.UserDAOImpl;
import database.dao.interfaces.GenericDAO;
import database.dao.interfaces.MessageDAO;
import exception.DaoException;
import model.User;
import service.InputValidator;
import service.action.common.Action;

public class DeleteDialog implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute(USER_PARAM);

		MessageDAO messageDao = new MessageDAOImpl();
		GenericDAO<User> userDAO = new UserDAOImpl();

		String idParameter = request.getParameter("id");
		if (InputValidator.validateId(idParameter)) {
			long interlocutorId = Long.parseLong(idParameter);
			User interlocutor = userDAO.read(interlocutorId);

			if (interlocutor != null) {
				messageDao.deleteDialog(sessionUser, interlocutor);
				request.setAttribute(SUCCESS_PARAM, GROUP_DELETED_SUCCESS);
			} else {
				request.setAttribute(ERROR_PARAM, GROUP_NOT_FOUND_ERROR);
			}
		} else {
			request.setAttribute(ERROR_PARAM, GROUP_ID_ERROR);
		}
		return "/main?action=messages";
	}
}