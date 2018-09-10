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
import database.dao.interfaces.GenericDAO;
import database.dao.interfaces.UserDAO;
import exception.ConnectionPoolException;
import exception.DaoException;
import model.User;

/**
 * Created by Silin on 08.2018.
 */

/**
 * Mysql implementation of the UserDao
 */
public class UserDAOImpl implements GenericDAO<User>, UserDAO {

	private ConnectionPool connectionPool;

	private Connection connection;

	private PreparedStatement statement;

	private ResultSet resultSet;

	private static final Logger logger = Logger.getLogger(UserDAOImpl.class);

	private static final String CREATE_QUERY = "INSERT INTO users (email, hash, firstname, lastname, gender, birthdate, country, city, phone, avatar) VALUES (?,?,?,?,?,?,?,?,?,?)";

	private static final String READ_QUERY = "SELECT * FROM users WHERE user_id = ?";

	private static final String READ_BY_EMAIL_QUERY = "SELECT * FROM users WHERE email = ?";

	private static final String UPDATE_QUERY = "UPDATE users SET email = ?, hash = ?, firstname= ?, lastname = ?, gender = ?, birthdate = ?, country = ?, city = ?, phone = ?, avatar = ? WHERE user_id = ?";

	private static final String DELETE_QUERY = "DELETE FROM users WHERE user_id = ?";

	private static final String GET_ID_QUERY = "SELECT user_id FROM users WHERE email = ?";

	private static final String GET_ALL_QUERY = "SELECT * FROM users";

	public UserDAOImpl() throws DaoException {
		try {
			this.connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public boolean create(final User user) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(CREATE_QUERY);

			statement.setString(1, user.getEmail());
			statement.setString(2, user.getHash());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getLastName());
			statement.setString(5, String.valueOf(user.getGender()));
			statement.setDate(6, user.getBirthDate());
			statement.setString(7, user.getCountry());
			statement.setString(8, user.getCity());
			statement.setString(9, user.getPhone());
			statement.setString(10, user.getAvatar());
			statement.execute();

			logger.info("Successfully added new User");
			return true;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public User read(long id) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(READ_QUERY);

			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				User user = buildUser(resultSet);
				logger.info("Successfully read User");
				return user;
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
		return null;
	}

	@Override
	public boolean update(final User user) throws DaoException {
		long userId = user.getUserId();
		if (userId == 0) {
			userId = getId(user.getEmail());
		}

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(UPDATE_QUERY);

			statement.setString(1, user.getEmail());
			statement.setString(2, user.getHash());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getLastName());
			statement.setString(5, String.valueOf(user.getGender()));
			statement.setDate(6, user.getBirthDate());
			statement.setString(7, user.getCountry());
			statement.setString(8, user.getCity());
			statement.setString(9, user.getPhone());
			statement.setString(10, user.getAvatar());
			statement.setLong(11, userId);

			boolean result = statement.executeUpdate() > 0;
			logger.info("Successfully updated User");
			return result;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public boolean delete(final User user) throws DaoException {
		long userId = user.getUserId();
		if (userId == 0) {
			userId = getId(user.getEmail());
		}

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(DELETE_QUERY);
			statement.setLong(1, userId);

			boolean result = statement.executeUpdate() > 0;
			logger.info("Successfully deleted User");
			return result;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public long getId(String email) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(GET_ID_QUERY);

			statement.setString(1, email);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				long result = resultSet.getLong(1);
				logger.info("Successfully got User id");
				return result;
			}
		} catch (ConnectionPoolException | SQLException e) {
			e.printStackTrace();
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
		return 0;
	}

	@Override
	public Collection<User> getAll() throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			Set<User> users = new HashSet<>();
			statement = connection.prepareStatement(GET_ALL_QUERY);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				users.add(buildUser(resultSet));
			}
			logger.info("Successfully got all Users");
			return users;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
	}

	/**
	 * Get user from database using provided email
	 *
	 * @param email
	 * @return User
	 * @throws DaoException
	 */
	@Override
	public User read(String email) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(READ_BY_EMAIL_QUERY);

			statement.setString(1, email);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				User user = buildUser(resultSet);
				logger.info("Successfully read User by email");
				return user;
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
		return null;
	}

	/**
	 * Check if user exists using his email address
	 *
	 * @param user
	 * @return true if exists
	 * @throws DaoException
	 */
	@Override
	public boolean isExists(final User user) throws DaoException {
		return getId(user.getEmail()) != 0;
	}
}
