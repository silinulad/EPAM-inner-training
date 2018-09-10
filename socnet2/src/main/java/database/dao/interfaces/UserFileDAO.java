package database.dao.interfaces;

import java.util.Collection;

import exception.DaoException;
import model.AttachFile;
import model.User;

/**
 * Created by Silin on 08.2018.
 */

/**
 * UserFileDao interface with basic operations
 */
public interface UserFileDAO  {
	/**
	 * Add a file to the userfiles table
	 *
	 * @param user
	 * @param file
	 * @return true if operation successful
	 * @throws DaoException
	 */
	boolean addFile(final User user, final AttachFile file) throws DaoException;

	/**
	 * Delete a file from the userfiles table
	 *
	 * @param user
	 * @param file
	 * @return true if operation successful
	 * @throws DaoException
	 */
	boolean deleteFile(final User user, final AttachFile file) throws DaoException;

	/**
	 * Get all files uploaded by specified user
	 *
	 * @param user
	 * @return Collection of user files
	 * @throws DaoException
	 */
	Collection<AttachFile> getFiles(final User user) throws DaoException;
	
	/**
	 * Get the last file uploaded by specified user
	 *
	 * @param user
	 * @return last uploaded file
	 * @throws DaoException
	 */
	AttachFile getLastUploadedFile(User user) throws DaoException;

}
