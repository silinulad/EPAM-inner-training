package service.action.profile;

import static service.MessageProvider.USER_ID_ERROR;
import static service.MessageProvider.USER_NOT_FOUND_ERROR;
import static service.action.common.Constants.MAIN;
import static service.action.common.Constants.PROFILE;
import static service.action.common.Constants.USER_PARAM;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.dao.impl.*;
import database.dao.interfaces.*;
import exception.DaoException;
import model.*;
import service.InputValidator;
import service.action.common.Action;

/**
 * Created by Silin on 08.2018.
 */

/**
 * ViewProfile action processes user request to see user profile
 */
public class ViewProfile implements Action {

	private User loggedUser;
	private UserGroupsDAO userGroupDao;
	private FriendDAO userFriendsDao;
	private PostDAO userPostDao;
	private GenericDAO<Gift> giftDao;
	private PresentDAO presentDao;
	private GenericDAO<UserSettings> userOptionsDao;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
		HttpSession session = request.getSession();
		loggedUser = (User) session.getAttribute(USER_PARAM);

		GenericDAO<User> userDao = new UserDAOImpl();
		userGroupDao = new UserGroupsDAOImpl();
		userFriendsDao = new FriendDAOImpl();
		userPostDao = new PostDAOImpl();
		giftDao = new GiftDAOImpl();
		presentDao = new PresentDAOImpl();
		userOptionsDao = new UserSettingDAOImpl();

		String idParameter = request.getParameter("id");

		// print current user profile if no id specified
		if (idParameter == null) {
			return setInfo(request, loggedUser);
		}

		if (InputValidator.validateId(idParameter)) {
			User user = userDao.read(Long.parseLong(idParameter));

			if (user != null) {
				return setInfo(request, user);
			} else {
				request.setAttribute("errorMsg", USER_NOT_FOUND_ERROR);
			}
		} else {
			request.setAttribute("errorMsg", USER_ID_ERROR);
		}
		return MAIN;
	}

	/**
	 * Sets an information to the request and return the profile page where
	 * information should be printed
	 *
	 * @param request
	 * @param user
	 * @return profile page
	 * @throws DaoException
	 */
	private String setInfo(HttpServletRequest request, User user) throws DaoException {
		request.setAttribute("userProfile", user);
		Collection<Group> userGroups = userGroupDao.getGroups(user);
		request.setAttribute("userGroups", userGroups);

		Collection<User> userFriends = userFriendsDao.getFriends(user);
		request.setAttribute("userFriends", userFriends);

		Collection<Post> userPosts = userPostDao.getUserPosts(user);
		request.setAttribute("userPosts", userPosts);

		Collection<Gift> gifts = giftDao.getAll();
		request.setAttribute("gifts", gifts);

		Collection<Present> userPresents = presentDao.getPresentsGivenTo(user);
		request.setAttribute("userPresents", userPresents);
		
		UserSettings userOptions = userOptionsDao.read(user.getUserId());
		request.setAttribute("userOptions", userOptions);

		// check that current user is our friend and show remove button
		if (!user.equals(loggedUser)) {
			Collection<User> loggedUserFriends = userFriendsDao.getFriends(loggedUser);
			if (loggedUserFriends.contains(user)) {
				request.setAttribute("isFriend", "true");
			}
		}
		return PROFILE;
	}
}
