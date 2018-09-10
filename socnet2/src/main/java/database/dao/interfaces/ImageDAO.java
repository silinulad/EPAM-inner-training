package database.dao.interfaces;
/**
 * Created by Silin on 08.2018.
 */

import exception.DaoException;

/**
 * ImageDAO interface with basic operation to add image to the group or the gift
 */

public interface ImageDAO<T> {
	/**
	 * Add an image to the T object
	 * 
	 * @param object
	 * @return true if operation successful
	 * @throws DaoException
	 */
	boolean addImage(final T object) throws DaoException;

}
