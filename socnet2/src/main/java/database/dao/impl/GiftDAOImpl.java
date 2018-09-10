package database.dao.impl;

import static database.dao.utils.DaoUtils.buildGift;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import database.connectionpool.ConnectionPool;
import database.dao.interfaces.GenericDAO;
import database.dao.interfaces.ImageDAO;
import exception.ConnectionPoolException;
import exception.DaoException;
import model.Gift;

/**
 * Created by Silin on 08.2018.
 */

/**
 * Mysql implementation of the GenericDao for a Gift
 */
public class GiftDAOImpl implements GenericDAO<Gift>, ImageDAO<Gift> {

	private ConnectionPool connectionPool;
	private Connection connection;
	private PreparedStatement statement;
	private ResultSet resultSet;

	private static final Logger logger = Logger.getLogger(GiftDAOImpl.class);

	private static final String CREATE_QUERY = "INSERT INTO gifts (name, description) VALUES (?,?)";

	private static final String READ_QUERY = "SELECT * FROM gifts WHERE gift_id = ?";

	private static final String UPDATE_QUERY = "UPDATE gifts SET name = ?, description = ? WHERE gift_id = ?";

	private static final String DELETE_QUERY = "DELETE FROM gifts WHERE gift_id = ?";

	private static final String GET_ID_QUERY = "SELECT gift_id FROM gifts WHERE name = ? ORDER BY gift_id DESC LIMIT 1";

	private static final String GET_ALL_QUERY = "SELECT * FROM gifts";

	private static final String ADD_IMAGE_QUERY = "UPDATE gifts SET image = ? WHERE gift_id = ?";

	public GiftDAOImpl() throws DaoException {
		try {
			this.connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public boolean create(Gift gift) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(CREATE_QUERY);

			statement.setString(1, gift.getName());
			statement.setString(2, gift.getDescription());
			statement.execute();
			logger.info("Successfully added new Gift");
			return true;
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public Gift read(long id) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(READ_QUERY);

			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				Gift gift = buildGift(resultSet);
				logger.info("Successfully read Gift");
				return gift;
			}
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
		return null;
	}

	@Override
	public boolean update(Gift gift) throws DaoException {
		long giftId = gift.getGiftId();
		if (giftId == 0) {
			giftId = getId(gift.getName());
		}

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(UPDATE_QUERY);

			statement.setString(1, gift.getName());
			statement.setString(2, gift.getDescription());
			statement.setLong(3, giftId);

			boolean result = statement.executeUpdate() > 0;
			logger.info("Successfully updated Gift");
			return result;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public boolean delete(Gift gift) throws DaoException {
		long giftId = gift.getGiftId();
		if (giftId == 0) {
			giftId = getId(gift.getName());
		}

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(DELETE_QUERY);
			statement.setLong(1, giftId);

			boolean result = statement.executeUpdate() > 0;
			logger.info("Successfully deleted Gift");
			return result;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public long getId(String name) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(GET_ID_QUERY);

			statement.setString(1, name);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				long result = resultSet.getLong(1);
				logger.info("Successfully got Gift id");
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
	public Collection<Gift> getAll() throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			List<Gift> gifts = new ArrayList<>();
			statement = connection.prepareStatement(GET_ALL_QUERY);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				gifts.add(buildGift(resultSet));
			}

			logger.info("Successfully got all Gifts");
			return gifts;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
	}

	@Override
	public boolean addImage(Gift gift) throws DaoException {
		long groupId = gift.getGiftId();
		if (groupId == 0) {
			groupId = getId(gift.getName());
		}

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(ADD_IMAGE_QUERY);

			statement.setString(1, gift.getImage());
			statement.setLong(2, groupId);

			boolean result = statement.executeUpdate() > 0;
			logger.info("Successfully added the image to the Gift");
			return result;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}
}
