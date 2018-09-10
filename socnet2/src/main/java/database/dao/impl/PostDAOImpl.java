package database.dao.impl;

import static database.dao.utils.DaoUtils.buildPost;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import database.connectionpool.ConnectionPool;
import database.dao.interfaces.PostDAO;
import exception.ConnectionPoolException;
import exception.DaoException;
import model.Post;
import model.User;
/**
 * Created by Silin on 08.2018.
 */

/**
 * Mysql implementation of the PostDAO
 */

public class PostDAOImpl implements PostDAO {
	
	private ConnectionPool connectionPool;
	private Connection connection;
	private PreparedStatement statement;
	private ResultSet resultSet;

	private static final Logger logger = Logger.getLogger(PostDAOImpl.class);
	
	private static final String CREATE_QUERY = "INSERT INTO posts (id_author,body) VALUES (?,?)";
	
	private static final String READ_QUERY = "SELECT * FROM posts JOIN users ON user_id = id_author WHERE post_id = ?";
	
	private static final String UPDATE_QUERY = "UPDATE posts SET id_author = ?, body = ? WHERE post_id = ?";
	
	private static final String DELETE_QUERY = "DELETE FROM posts WHERE post_id = ?";
	
	private static final String GET_ID_QUERY = "SELECT post_id FROM posts WHERE body = ? ORDER BY post_id DESC LIMIT 1";
	
	private static final String GET_ALL_QUERY = "SELECT * FROM posts JOIN users ON user_id = id_author";
	
	private static final String GET_POSTS_BY_USER_QUERY = "SELECT * FROM posts JOIN users ON user_id = id_author WHERE user_id = ? ORDER BY time DESC";
													

	public PostDAOImpl() throws DaoException {
		try {
			this.connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}
	@Override
	public boolean create(Post post) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(CREATE_QUERY);

			statement.setLong(1, post.getAuthor().getUserId());
			statement.setString(2, post.getBody());
			statement.execute();

			logger.info("Successfully added new Post");
			return true;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public Post read(long id) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(READ_QUERY);

			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			Post post = null;
			if (resultSet.next()) {
				post = buildPost(resultSet);
				logger.info("Successfully read Post");
			}
			return post;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
	}

	@Override
	public boolean update(Post post) throws DaoException {
		long postId = post.getPostId();
		if (postId == 0) {
			postId = getId(post.getBody());
		}

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(UPDATE_QUERY);

			statement.setLong(1, post.getAuthor().getUserId());
			statement.setString(2, post.getBody());
			statement.setLong(3, postId);

			boolean result = statement.executeUpdate() > 0;
			logger.info("Successfully updated Post");
			return result;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public boolean delete(Post post) throws DaoException {
		long postId = post.getPostId();
		if (postId == 0) {
			postId = getId(post.getBody());
		}

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(DELETE_QUERY);
			statement.setLong(1, postId);

			boolean result = statement.executeUpdate() > 0;
			logger.info("Successfully deleted Post");
			return result;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public long getId(String body) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(GET_ID_QUERY);

			statement.setString(1, body);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				long result = resultSet.getLong(1);
				logger.info("Successfully got Post id");
				return result;
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
		return 0;
	}

	@Override
	public Collection<Post> getAll() throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			List<Post> messages = new ArrayList<>();
			statement = connection.prepareStatement(GET_ALL_QUERY);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				messages.add(buildPost(resultSet));
			}
			logger.info("Successfully got all Posts");
			return messages;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
	}
	@Override
	public Collection<Post> getUserPosts(User author) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			List<Post> messages = new ArrayList<>();
			statement = connection.prepareStatement(GET_POSTS_BY_USER_QUERY);
			statement.setLong(1, author.getUserId());
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				messages.add(buildPost(resultSet));
			}
			logger.info("Successfully got all posts for specified User");
			return messages;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
	}

}
