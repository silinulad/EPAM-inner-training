package database.dao.impl;

import static database.dao.utils.DaoUtils.buildPresent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import database.connectionpool.ConnectionPool;
import database.dao.interfaces.PresentDAO;
import exception.ConnectionPoolException;
import exception.DaoException;
import model.Present;
import model.User;

/**
 * Created by Silin on 08.2018.
 */

/**
 * Mysql implementation of the GenericDao for a Present
 */
public class PresentDAOImpl implements PresentDAO {
	private ConnectionPool connectionPool;
	private Connection connection;
	private PreparedStatement statement;
	private ResultSet resultSet;

	private static final Logger logger = Logger.getLogger(PresentDAOImpl.class);

	private static final String CREATE_QUERY = "INSERT INTO presents (gift_id, sender_id) VALUES (?,?)";

	private static final String READ_QUERY = "SELECT * FROM presents "
												+ "JOIN users ON presents.sender_id = users.user_id "
												+ "JOIN gifts ON presents.gift_id = gifts.gift_id"
												+ " WHERE present_id = ?";

	private static final String UPDATE_QUERY = "UPDATE presents SET gift_id = ?, sender_id = ?  WHERE present_id = ?";

	private static final String DELETE_QUERY = "DELETE FROM presents WHERE present_id = ?";

	private static final String GET_ID_QUERY = "SELECT present_id, name FROM presents, gifts WHERE gifts.name = ? ORDER BY present_id DESC LIMIT 1";

	private static final String GET_ALL_QUERY = "SELECT * FROM presents "
												+ "JOIN users ON presents.sender_id = users.user_id "
												+ "JOIN gifts ON presents.gift_id = gifts.gift_id";

	private static final String SEND_PRESENT_QUERY = "INSERT INTO userpresents (present_id, user_id) VALUES (?,?)";

	private static final String GET_USER_PRESENTS_QUERY = "SELECT * FROM userpresents "
															+ "JOIN presents ON presents.present_id = userpresents.present_id "
															+ "JOIN users ON users.user_id = presents.sender_id "
															+ "JOIN gifts ON gifts.gift_id = presents.gift_id "
															+ "WHERE userpresents.user_id = ?";

	public PresentDAOImpl() throws DaoException {
		try {
			this.connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}
	@Override
	public boolean create(Present present) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(CREATE_QUERY);

			statement.setLong(1, present.getGift().getGiftId());
			statement.setLong(2, present.getSender().getUserId());
			statement.execute();
			logger.info("Successfully added new Present");
			return true;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public Present read(long presentId) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(READ_QUERY);

			statement.setLong(1, presentId);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				Present present = buildPresent(resultSet);
				logger.info("Successfully read Present");
				return present;
			}
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
		return null;
	}

	@Override
	public boolean update(Present present) throws DaoException {
		long presentId = present.getPresentId();
		if (presentId == 0) {
			presentId = getId(present.getGift().getName());
		}

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(UPDATE_QUERY);

			statement.setLong(1, present.getGift().getGiftId());
			statement.setLong(2, present.getSender().getUserId());
			statement.setLong(3, presentId);

			boolean result = statement.executeUpdate() > 0;
			logger.info("Successfully updated Present");
			return result;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public boolean delete(Present present) throws DaoException {
		long presentId = present.getPresentId();
		if (presentId == 0) {
			presentId = getId(present.getGift().getName());
		}

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(DELETE_QUERY);
			statement.setLong(1, presentId);

			boolean result = statement.executeUpdate() > 0;
			logger.info("Successfully deleted Present");
			return result;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public long getId(String giftName) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(GET_ID_QUERY);

			statement.setString(1, giftName);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				long result = resultSet.getLong(1);
				logger.info("Successfully got Present id");
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
	public Collection<Present> getAll() throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			List<Present> presents = new ArrayList<>();
			statement = connection.prepareStatement(GET_ALL_QUERY);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				presents.add(buildPresent(resultSet));
			}
			logger.info("Successfully got all Presents");
			return presents;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
	}

	@Override
	public Collection<Present> getPresentsGivenTo(User sender) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			List<Present> presents = new ArrayList<>();
			statement = connection.prepareStatement(GET_USER_PRESENTS_QUERY);
			statement.setLong(1, sender.getUserId());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				presents.add(buildPresent(resultSet));
			}
			logger.info("Successfully got all Presents for specified User");
			return presents;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
	}
	@Override
	public boolean sendPresentTo(Present present, User recipient) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(SEND_PRESENT_QUERY);
			statement.setLong(1, present.getPresentId());
			statement.setLong(2, recipient.getUserId());
			statement.execute();

			logger.info("The Gift presented successfully");
			return true;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

}
