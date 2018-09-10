package database.dao.impl;

import static database.dao.utils.DaoUtils.buildUserSettings;

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
import exception.ConnectionPoolException;
import exception.DaoException;
import model.UserSettings;

public class UserSettingDAOImpl implements GenericDAO<UserSettings> {

	private ConnectionPool connectionPool;
	private Connection connection;
	private PreparedStatement statement;
	private ResultSet resultSet;

	private static final Logger logger = Logger.getLogger(UserSettingDAOImpl.class);

	private static final String CREATE_QUERY = "INSERT INTO usersettings (user_id) VALUES (?)";

	private static final String READ_QUERY = "SELECT * FROM usersettings WHERE user_id = ?";

	private static final String UPDATE_QUERY = "UPDATE usersettings SET visible_only_friends = ?, hide_friends = ?, hide_groups = ?, hide_presents = ?, show_date = ?, hide_country = ?, hide_city = ?, hide_sex = ?, hide_phone = ? WHERE user_id = ?";

	private static final String GET_ALL_QUERY = "SELECT * FROM usersettings";

	public UserSettingDAOImpl() throws DaoException {
		try {
			this.connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public boolean create(UserSettings userSettings) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(CREATE_QUERY);

			statement.setLong(1, userSettings.getUserId());
			statement.execute();

			logger.info("Successfully added settings for the new user");
			return true;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public UserSettings read(long id) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(READ_QUERY);

			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				UserSettings userSettings = buildUserSettings(resultSet);
				logger.info("Successfully read user settings");
				return userSettings;
			}
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
		return null;
	}

	@Override
	public boolean update(UserSettings userSettings) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(UPDATE_QUERY);

			statement.setBoolean(1, userSettings.isVisiblePageOnlyFriends());
			statement.setBoolean(2, userSettings.isHideFriends());
			statement.setBoolean(3, userSettings.isHideGroups());
			statement.setBoolean(4, userSettings.isHideGifts());
			statement.setString(5, userSettings.getShowDate());
			statement.setBoolean(6, userSettings.isHideCountry());
			statement.setBoolean(7, userSettings.isHideCitiy());
			statement.setBoolean(8, userSettings.isHideSex());
			statement.setBoolean(9, userSettings.isHidePhone());
			statement.setLong(10, userSettings.getUserId());

			boolean result = statement.executeUpdate() > 0;
			logger.info("Successfully updated user settings");
			return result;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}

	}

	@Override
	public boolean delete(UserSettings object) throws DaoException {
		return delete(object);
	}

	@Override
	public long getId(String field) throws DaoException {
		return getId(field);
	}

	@Override
	public Collection<UserSettings> getAll() throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			Set<UserSettings> userSettings = new HashSet<>();
			statement = connection.prepareStatement(GET_ALL_QUERY);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				userSettings.add(buildUserSettings(resultSet));
			}

			logger.info("Successfully got all Groups");
			return userSettings;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
	}

}
