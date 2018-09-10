package database.dao.interfaces;

import java.util.Collection;

import exception.DaoException;
import model.AttachFile;
import model.Message;

/**
 * Created by Silin on 08.2018.
 */

/**
 * MessageFileDAO interface with operations add an attach file to a message, get
 * all attached files for specified message
 */
public interface MessageFileDAO {

	/**
	 * Add an attach file to a message
	 *
	 * @param message
	 * @param file
	 * @return true if operation successful
	 * @throws DaoException
	 */
	boolean addFileToMessage(final Message message, final AttachFile file) throws DaoException;

	/**
	 * Get an attach file to a message
	 *
	 * @param message
	 * @param file
	 * @return true if operation successful
	 * @throws DaoException
	 */
	AttachFile getFileForMessage(final long messageId, final long fileId) throws DaoException;

	/**
	 * Get all attached files for specified message
	 *
	 * @param message
	 * @return Collection of files
	 * @throws DaoException
	 */
	Collection<AttachFile> getFilesForMessage(final Message message) throws DaoException;
}
