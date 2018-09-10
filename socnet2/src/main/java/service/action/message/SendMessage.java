package service.action.message;

import static service.action.common.Constants.MAIN;
import static service.action.common.Constants.USER_PARAM;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.dao.impl.MessageDAOImpl;
import database.dao.impl.UserDAOImpl;
import database.dao.interfaces.GenericDAO;
import database.dao.utils.ActionUtil;
import exception.DaoException;
import model.Message;
import model.User;
import service.InputValidator;
import service.action.common.Action;

/**
 * Created by Silin on 07.2018.
 */

/**
 * SendMessage action processes the user request to send a message to any users
 */

public class SendMessage implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
		HttpSession session = request.getSession();
		User userSender = (User) session.getAttribute(USER_PARAM);

		GenericDAO<Message> messageDao = new MessageDAOImpl();
		GenericDAO<User> userDao = new UserDAOImpl();

		String idParam = request.getParameter("idTo");
		String body = request.getParameter("body");

		// validate id from request
		if (InputValidator.validateId(idParam)) {
			long recipientId = Long.parseLong(idParam);
			User userRecipient = userDao.read(recipientId);

			Message message = new Message.Builder()
								.setRecipient(userRecipient)
								.setSender(userSender)
								.setBody(body)
								.build();
						
			messageDao.create(message);
			long idMessage = messageDao.getId(message.getBody());
			message.setMessageId(idMessage);

			String partOfGetRequest = ActionUtil.getFileIdAsStringFromRequest(request, "attach", "userFile", message);

			return "/main?action=dialog&id=" + recipientId + "&idMessage=" + idMessage + partOfGetRequest;
		}
		return MAIN;

	}
}
