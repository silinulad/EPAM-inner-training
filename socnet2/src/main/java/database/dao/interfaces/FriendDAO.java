package database.dao.interfaces;

import java.util.Collection;

import exception.DaoException;
import model.User;

/**
 * Created by Silin on 08.2018.
 */

/**
 * UserFriendsDao interface with basic operations add friend, delete friend and
 * get all user friends
 */
public interface FriendDAO {

	/**
	 * Add user to friends list of another user
	 *
	 * @param user
	 * @param friend
	 * @return true if operation successful
	 * @throws DaoException
	 */
	boolean addFriend(final User user, final User friend) throws DaoException;

	/**
	 * Delete user from friends list of another user
	 *
	 * @param user
	 * @param friend
	 * @return true if operation successful
	 * @throws DaoException
	 */
	boolean deleteFriend(final User user, final User friend) throws DaoException;

	/**
	 * Get all friends for specified user
	 *
	 * @param user
	 * @return Colection of users
	 * @throws DaoException
	 */
	Collection<User> getFriends(final User user) throws DaoException;
}
