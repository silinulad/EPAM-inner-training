package database.dao.interfaces;

import java.util.Collection;

import exception.DaoException;
import model.Present;
/**
 * Created by Silin on 08.2018.
 */
import model.User;

/**
 * PresentDAO interface with additional operations: getUserPresents
 */
public interface PresentDAO extends GenericDAO<Present> {

	/**
	 * Get all presents to specified user
	 *
	 * @param user
	 * @return Collection of presents
	 * @throws DaoException
	 */
	public Collection<Present> getPresentsGivenTo(final User sender) throws DaoException;

	/**
	 * Send a present to specified user
	 * 
	 * @param present
	 * @param recipient
	 * @return true if operation successful
	 * @throws DaoException
	 */
	public boolean sendPresentTo(Present present, User recipient) throws DaoException;

}
