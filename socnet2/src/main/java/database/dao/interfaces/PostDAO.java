package database.dao.interfaces;

import java.util.Collection;

import exception.DaoException;
import model.Post;
import model.User;
/**
 * Created by Silin on 08.2018.
 */

/**
 * PostDAO interface with additional operations: getUserPosts
 */
public interface PostDAO extends GenericDAO<Post>{
	/**
	 * Get all posts for specified user
	 *
	 * @param user
	 * @return Collection of posts
	 * @throws DaoException
	 */
	public Collection<Post> getUserPosts(final User author) throws DaoException;

}
