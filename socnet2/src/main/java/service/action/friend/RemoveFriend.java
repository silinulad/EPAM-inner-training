package service.action.friend;

import static service.MessageProvider.*;
import static service.action.common.Constants.*;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.dao.impl.FriendDAOImpl;
import database.dao.impl.UserDAOImpl;
import database.dao.interfaces.FriendDAO;
import database.dao.interfaces.GenericDAO;
import exception.DaoException;
import model.User;
import service.InputValidator;
import service.action.common.Action;

/**
 * Created by Silin on 07.2018.
 */

/**
 * RemoveFriend action processes user request to remove user from friend list
 */
public class RemoveFriend implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
	
		String idAttrName = "id";

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(USER_PARAM);

		FriendDAO userFriendsDao = new FriendDAOImpl();
		GenericDAO<User> userDao = new UserDAOImpl();

		String idParameter = request.getParameter(idAttrName);

		// validate id parameter
		if (InputValidator.validateId(idParameter)) {
			long friendId = Long.parseLong(idParameter);
			User friend = userDao.read(friendId);
			Collection<User> friends = userFriendsDao.getFriends(user);

			if (friend != null && user.getUserId() != friendId) {
				if (friends.contains(friend)) {
					userFriendsDao.deleteFriend(user, friend);
					userFriendsDao.deleteFriend(friend, user);
					request.setAttribute(SUCCESS_PARAM, USER_DEL_FRIEND_SUCCESS);
				} else {
					request.setAttribute(ERROR_PARAM, USER_NOT_FRIEND_ERROR);
				}

				return "/main?action=profile&id=" + friendId;
			} else {
				request.setAttribute(ERROR_PARAM, USER_NOT_FOUND_ERROR);
			}
		} else {
			request.setAttribute(ERROR_PARAM, USER_ID_ERROR);
		}
		return MAIN;
	}
}
