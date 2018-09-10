package database.dao.impl;

import static database.dao.utils.DaoUtils.buildUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import database.connectionpool.ConnectionPool;
import database.dao.interfaces.FriendDAO;
import exception.ConnectionPoolException;
import exception.DaoException;
import model.User;

/**
 * Created by Silin on 07.2018.
 */

/**
 * Mysql implementation of the UserFriendsDao
 */
public class FriendDAOImpl implements FriendDAO {

	private ConnectionPool connectionPool;

	private Connection connection;

	private PreparedStatement statement;

	private ResultSet resultSet;

	private static final Logger logger = Logger.getLogger(FriendDAOImpl.class);

	private static final String	ADD_FRIEND_QUERY		= "INSERT INTO userfriends (user_id,friend_id) VALUES (?,?)";

	private static final String	DELETE_FRIEND_QUERY		= "DELETE FROM userfriends WHERE user_id = ? AND friend_id = ?";

	private static final String	GET_USER_FRIENDS_QUERY	= "SELECT * FROM users INNER JOIN userfriends ON userfriends.friend_id = users.user_id WHERE userfriends.user_id = ?";

	public FriendDAOImpl() throws DaoException {
		try {
			this.connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public boolean addFriend(User user, User friend) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(ADD_FRIEND_QUERY);
			statement.setLong(1, user.getUserId());
			statement.setLong(2, friend.getUserId());
			statement.execute();

			logger.info("Successfully added new Friend");
			return true;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public boolean deleteFriend(User user, User friend) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(DELETE_FRIEND_QUERY);
			statement.setLong(1, user.getUserId());
			statement.setLong(2, friend.getUserId());
			statement.execute();

			logger.info("Successfully deleted Friend");
			return true;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public Collection<User> getFriends(final User user) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			Set<User> users = new HashSet<>();
			statement = connection.prepareStatement(GET_USER_FRIENDS_QUERY);
			statement.setLong(1, user.getUserId());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				users.add(buildUser(resultSet));
			}
			logger.info("Successfully got all Friends for specified User");
			return users;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
	}
}
