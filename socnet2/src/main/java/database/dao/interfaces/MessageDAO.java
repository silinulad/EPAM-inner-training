package database.dao.interfaces;

import java.util.Collection;

import exception.DaoException;
import model.Message;
import model.User;

/**
 * Created by Silin on 08.2018.
 */

/**
 * MessageDAO interface with additional operations
 */

public interface MessageDAO extends GenericDAO<Message> {

	/**
	 * Get all messages for specified user
	 *
	 * @param user
	 * @return Collection of messages
	 * @throws DaoException
	 */
	Collection<Message> getMessages(final User user) throws DaoException;

	/**
	 * Get dialog messages for two specified users
	 *
	 * @param tellerFirst
	 * @param tellerSecond
	 * @return Collection of messages
	 * @throws DaoException
	 */
	Collection<Message> getDialog(final User tellerFirst, final User tellerSecond) throws DaoException;

	/**
	 * Delete whole dialog for two specified users
	 *
	 * @param tellerFirst
	 * @param tellerSecond
	 * @return Collection of messages
	 * @throws DaoException
	 */
	boolean deleteDialog(final User tellerFirst, final User tellerSecond) throws DaoException;

}
