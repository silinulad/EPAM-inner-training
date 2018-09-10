package service.action.message;

import static service.action.common.Constants.MESSAGES;
import static service.action.common.Constants.USER_PARAM;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.dao.impl.MessageDAOImpl;
import database.dao.interfaces.MessageDAO;
import exception.DaoException;
import model.Message;
import model.User;
import service.action.common.Action;

/**
 * Created by Silin on 08.2018.
 */

/**
 * ViewMessages action processes user request to see all user messages
 */
public class ViewMessages implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(USER_PARAM);

		MessageDAO messageDao = new MessageDAOImpl();
		Collection<Message> userMessages = messageDao.getMessages(user);

		request.setAttribute("userMessages", userMessages);
		return MESSAGES;
	}

}
