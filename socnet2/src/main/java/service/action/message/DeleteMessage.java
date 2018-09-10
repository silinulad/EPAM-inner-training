package service.action.message;

import static service.MessageProvider.*;
import static service.action.common.Constants.ERROR_PARAM;
import static service.action.common.Constants.MAIN;
import static service.action.common.Constants.USER_PARAM;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.dao.impl.MessageDAOImpl;
import database.dao.interfaces.GenericDAO;
import exception.DaoException;
import model.Message;
import model.User;
import service.InputValidator;
import service.action.common.Action;

public class DeleteMessage implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(USER_PARAM);

		GenericDAO<Message> messageDao = new MessageDAOImpl();

		String idParameter = request.getParameter("id");

		if (InputValidator.validateId(idParameter)) {
			long messageId = Long.parseLong(idParameter);
			Message message = messageDao.read(messageId);
			if (message != null) {
				// check that the user is the sender
				if (message.getSender().equals(user)) {
					messageDao.delete(message);
					request.setAttribute("successMsg", GROUP_DELETED_SUCCESS);
					return "/main?action=dialog&id=" + message.getRecipient().getUserId();
				} else {
					request.setAttribute(ERROR_PARAM, INSUFFICIENT_RIGHTS_ERROR);
					return "/main?action=dialog&id=" + message.getRecipient().getUserId();
				}
			} else {
				request.setAttribute(ERROR_PARAM, GROUP_NOT_FOUND_ERROR);
			}
		} else {
			request.setAttribute(ERROR_PARAM, GROUP_ID_ERROR);
		}
		return MAIN;
	}

}
