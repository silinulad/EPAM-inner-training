package database.dao.impl;

import static database.dao.utils.DaoUtils.buildGroup;
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
import database.dao.interfaces.UserGroupsDAO;
import exception.ConnectionPoolException;
import exception.DaoException;
import model.Group;
import model.User;

/**
 * Created by Silin on 08.2018.
 */

/**
 * Mysql implementation of the UserGroupsDao
 */
public class UserGroupsDAOImpl implements UserGroupsDAO {

	private ConnectionPool connectionPool;
	private Connection connection;
	private PreparedStatement statement;
	private ResultSet resultSet;

	private static final Logger logger = Logger.getLogger(UserGroupsDAOImpl.class);

	private static final String JOIN_GROUP_QUERY = "INSERT INTO usergroups (group_id,user_id) VALUES (?,?)";

	private static final String LEFT_GROUP_QUERY = "DELETE FROM usergroups WHERE group_id = ? AND user_id = ?";

	private static final String GET_USER_GROUPS_QUERY = "SELECT * FROM groups INNER JOIN users on groups.owner_id = users.user_id INNER JOIN usergroups ON usergroups.group_id = groups.group_id WHERE usergroups.user_id = ?";

	private static final String GET_ALL_USERS_QUERY = "SELECT * FROM usergroups JOIN users ON usergroups.user_id = users.user_id WHERE group_id = ?";

	public UserGroupsDAOImpl() throws DaoException {
		try {
			this.connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public boolean joinGroup(final User user, final Group group) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(JOIN_GROUP_QUERY);
			statement.setLong(1, group.getGroupId());
			statement.setLong(2, user.getUserId());
			statement.execute();

			logger.info("User successfully joined Group");
			return true;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public boolean leftGroup(final User user, final Group group) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(LEFT_GROUP_QUERY);
			statement.setLong(1, group.getGroupId());
			statement.setLong(2, user.getUserId());
			statement.execute();

			logger.info("User successfully left Group");
			return true;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public Collection<Group> getGroups(final User user) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(GET_USER_GROUPS_QUERY);
			statement.setLong(1, user.getUserId());
			resultSet = statement.executeQuery();

			Set<Group> groups = new HashSet<>();
			while (resultSet.next()) {
				groups.add(buildGroup(resultSet));
			}
			logger.info("Successfully got all Groups for specified User");
			return groups;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
	}

	@Override
	public Collection<User> getUsers(final Group group) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(GET_ALL_USERS_QUERY);
			statement.setLong(1, group.getGroupId());
			resultSet = statement.executeQuery();

			Set<User> users = new HashSet<>();
			while (resultSet.next()) {
				users.add(buildUser(resultSet));
			}

			logger.info("Successfully got all Users for specified Group");
			return users;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
	}
}
