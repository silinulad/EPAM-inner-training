package service.action.friend;

import static service.action.common.Constants.FRIENDS;
import static service.action.common.Constants.USER_PARAM;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.dao.impl.FriendDAOImpl;
import database.dao.interfaces.FriendDAO;
import exception.DaoException;
import model.User;
import service.action.common.Action;

/**
 * Created by Silin on 08.2018.
 */

/**
 * ViewFriends action processes user request to see all user friends
 */
public class ViewFriends implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(USER_PARAM);

		FriendDAO userFriendsDao = new FriendDAOImpl();
		Collection<User> userFriends = userFriendsDao.getFriends(user);

		request.setAttribute("userFriends", userFriends);
		return FRIENDS;
	}
}
