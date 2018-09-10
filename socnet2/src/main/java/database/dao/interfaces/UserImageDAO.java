package database.dao.interfaces;

import java.util.Collection;

import exception.DaoException;
import model.AttachFile;
import model.User;

/**
 * Created by Silin on 08.2018.
 */

/**
 * UserImageDAO interface with basic operations
 */
public interface UserImageDAO {
	
	/**
	 * Add an image to the userimages table
	 *
	 * @param user
	 * @param file
	 * @return true if operation successful
	 * @throws DaoException
	 */
	boolean addImage(final User user, final AttachFile file) throws DaoException;

	/**
	 * Delete an image to the userimages table
	 *
	 * @param user
	 * @param file
	 * @return true if operation successful
	 * @throws DaoException
	 */
	boolean deleteImage(final User user, final AttachFile file) throws DaoException;

	/**
	 * Get all images uploaded by specified user
	 *
	 * @param user
	 * @return Collection of user images
	 * @throws DaoException
	 */
	Collection<AttachFile> getImages(final User user) throws DaoException;
	
	/**
	 * Get the last file uploaded by specified user
	 *
	 * @param user
	 * @return last uploaded image
	 * @throws DaoException
	 */
	AttachFile getLastUploadedImage(User user) throws DaoException;

}
