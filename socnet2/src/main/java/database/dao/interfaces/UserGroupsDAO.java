package database.dao.interfaces;

import java.util.Collection;

import exception.DaoException;
import model.Group;
import model.User;

/**
 * Created by Silin on 08.2018.
 */

/**
 * UserGroupsDao interface with basic operations: join, left, get Groups
 */
public interface UserGroupsDAO {
	
	/**
	 * Join specified Group
	 *
	 * @param user
	 * @param group
	 * @return true if operation successful
	 * @throws DaoException
	 */
	boolean joinGroup(final User user, final Group group) throws DaoException;

	/**
	 * Leave specified Group
	 *
	 * @param user
	 * @param group
	 * @return true if operation successful
	 * @throws DaoException
	 */
	boolean leftGroup(final User user, final Group group) throws DaoException;

	/**
	 * Get all Groups for specified User
	 *
	 * @param user
	 * @return Collection of Groups
	 * @throws DaoException
	 */
	Collection<Group> getGroups(final User user) throws DaoException;

	/**
	 * Get all Users who joined specified Group
	 *
	 * @param group
	 * @return Collection of Users
	 * @throws DaoException
	 */
	Collection<User> getUsers(final Group group) throws DaoException;
}
