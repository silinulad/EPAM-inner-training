package database.dao.impl;

import static database.dao.utils.DaoUtils.buildGroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import database.connectionpool.ConnectionPool;
import database.dao.interfaces.GenericDAO;
import database.dao.interfaces.ImageDAO;
import exception.ConnectionPoolException;
import exception.DaoException;
import model.Group;

/**
 * Created by Silin on 08.2018.
 */

/**
 * Mysql implementation of the GenericDao for a Group
 */
public class GroupDAOImpl implements GenericDAO<Group>, ImageDAO<Group> {

	private ConnectionPool		connectionPool;
	private Connection			connection;
	private PreparedStatement	statement;
	private ResultSet			resultSet;

	private static final Logger	logger			= Logger.getLogger(GroupDAOImpl.class);

	private static final String	CREATE_QUERY	= "INSERT INTO groups (name, owner_id, description) VALUES (?,?,?)";

	private static final String	READ_QUERY		= "SELECT * FROM groups JOIN users ON groups.owner_id = users.user_id WHERE group_id = ?";

	private static final String	UPDATE_QUERY	= "UPDATE groups SET name = ?, owner_id = ?, description = ? WHERE group_id = ?";

	private static final String	DELETE_QUERY	= "DELETE FROM groups WHERE group_id = ?";

	private static final String	GET_ID_QUERY	= "SELECT group_id FROM groups WHERE name = ? ORDER BY group_id DESC LIMIT 1";

	private static final String	GET_ALL_QUERY	= "SELECT * FROM groups JOIN users ON groups.owner_id = users.user_id";

	private static final String	ADD_IMAGE_QUERY	= "UPDATE groups SET image = ? WHERE group_id = ?";


	public GroupDAOImpl() throws DaoException {
		try {
			this.connectionPool = ConnectionPool.getInstance();
		}
		catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public boolean create(final Group group) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(CREATE_QUERY);

			statement.setString(1, group.getName());
			statement.setLong(2, group.getOwner().getUserId());
			statement.setString(3, group.getDescription());
			statement.execute();
			logger.info("Successfully added new Group");
			return true;
		}
		catch (SQLException | ConnectionPoolException e) {
			throw new DaoException(e);
		}
		finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public Group read(long id) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(READ_QUERY);

			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				Group group = buildGroup(resultSet);
				logger.info("Successfully read Group");
				return group;
			}
		}
		catch (SQLException | ConnectionPoolException e) {
			throw new DaoException(e);
		}
		finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
		return null;
	}

	@Override
	public boolean update(final Group group) throws DaoException {
		long groupId = group.getGroupId();
		if (groupId == 0) {
			groupId = getId(group.getName());
		}

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(UPDATE_QUERY);

			statement.setString(1, group.getName());
			statement.setLong(2, group.getOwner().getUserId());
			statement.setString(3, group.getDescription());
			statement.setLong(4, groupId);

			boolean result = statement.executeUpdate() > 0;
			logger.info("Successfully updated Group");
			return result;
		}
		catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		}
		finally {
			connectionPool.closeConnection(connection, statement);
		}

	}

	@Override
	public boolean delete(final Group group) throws DaoException {
		long groupId = group.getGroupId();
		if (groupId == 0) {
			groupId = getId(group.getName());
		}

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(DELETE_QUERY);
			statement.setLong(1, groupId);

			boolean result = statement.executeUpdate() > 0;
			logger.info("Successfully deleted Group");
			return result;
		}
		catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		}
		finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public long getId(final String name) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(GET_ID_QUERY);

			statement.setString(1, name);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				long result = resultSet.getLong(1);
				logger.info("Successfully got Group id");
				return result;
			}
		}
		catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		}
		finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
		return 0;
	}

	@Override
	public Collection<Group> getAll() throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			Set<Group> groups = new HashSet<>();
			statement = connection.prepareStatement(GET_ALL_QUERY);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				groups.add(buildGroup(resultSet));
			}

			logger.info("Successfully got all Groups");
			return groups;
		}
		catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		}
		finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
	}

	@Override
	public boolean addImage(final Group group) throws DaoException {
		long groupId = group.getGroupId();
		if (groupId == 0) {
			groupId = getId(group.getName());
		}

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(ADD_IMAGE_QUERY);

			statement.setString(1, group.getImage());
			statement.setLong(2, groupId);

			boolean result = statement.executeUpdate() > 0;
			logger.info("Successfully added the image to the Group");
			return result;
		}
		catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		}
		finally {
			connectionPool.closeConnection(connection, statement);
		}
	}
}
