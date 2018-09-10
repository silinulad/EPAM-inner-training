package database.dao.interfaces;

import exception.DaoException;
import model.User;

/**
 * Created by Silin on 08.2018.
 */

/**
 * UserDao interface with additional operations: read by email, isExist
 */
public interface UserDAO extends GenericDAO<User> {
	/**
	 * Get user from database using provided email
	 *
	 * @param email
	 * @return User
	 * @throws DaoException
	 */
	public User read(String email) throws DaoException;

	/**
	 * Check if user exists using his email address
	 *
	 * @param user
	 * @return true if exists
	 * @throws DaoException
	 */
	public boolean isExists(final User user) throws DaoException;
}
