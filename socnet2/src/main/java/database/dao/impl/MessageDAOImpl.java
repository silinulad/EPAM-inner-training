package database.dao.impl;

import static database.dao.utils.DaoUtils.buildMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import database.connectionpool.ConnectionPool;
import database.dao.interfaces.MessageDAO;
import exception.ConnectionPoolException;
import exception.DaoException;
import model.Message;
import model.User;

/**
 * Created by Silin on 08.2018.
 */

/**
 * Mysql implementation of the MessageDao
 */
public class MessageDAOImpl implements MessageDAO {

	private ConnectionPool connectionPool;
	private Connection connection;
	private PreparedStatement statement;
	private ResultSet resultSet;

	private static final Logger logger = Logger.getLogger(MessageDAOImpl.class);
	
	private static final String CREATE_QUERY = "INSERT INTO messages (id_from,id_to,body) VALUES (?,?,?)";
	
	private static final String READ_QUERY = "SELECT * FROM messages "
													+ "JOIN users AS u1 ON u1.user_id = id_from "
													+ "JOIN users AS u2 ON u2.user_id = id_to "
													+ "WHERE message_id = ?";
	
	private static final String UPDATE_QUERY = "UPDATE messages SET id_from = ?,id_to = ?,body = ? WHERE message_id = ?";
	
	private static final String DELETE_QUERY = "DELETE FROM messages WHERE message_id = ?";
	
	private static final String GET_ID_QUERY = "SELECT message_id FROM messages WHERE body = ?";
	
	private static final String GET_ALL_QUERY = "SELECT * FROM messages "
														+ "JOIN users AS u1 ON u1.user_id = id_from "
														+ "JOIN users AS u2 ON u2.user_id = id_to";
	
	private static final String GET_MESSAGES_BY_USER_QUERY = "SELECT m1.*, u1.*, u2.* "
																+ "FROM messages AS m1 "
																+ "JOIN users AS u1 ON u1.user_id = m1.id_from "
																+ "JOIN users AS u2 ON u2.user_id = m1.id_to "
																+ "WHERE m1.message_id IN ("
																+ 	"SELECT MAX(m2.message_id) "
																+ 	"FROM messages AS m2 "
																+ "WHERE m2.id_from = ? OR m2.id_to = ? "
																+ 	"GROUP BY "
																+ 	"CASE "
																+ "WHEN m2.id_to = ? THEN m2.id_from " + "WHEN m2.id_from = ? THEN m2.id_to " + "ELSE ? "
																+ 	"END) "
																+ "ORDER BY m1.time DESC";
	
	private static final String GET_USER_DIALOG_QUERY = "SELECT * FROM messages "
															+ "JOIN users AS u1 ON u1.user_id = id_from "
															+ "JOIN users AS u2 ON u2.user_id = id_to "
															+ "WHERE id_from = ? AND id_to = ? OR id_from = ? AND id_to = ? ORDER BY time DESC";

	private static final String DELETE_DIALOG_QUERY = "DELETE FROM messages WHERE id_from=? AND id_to=? OR id_from=? AND id_to=?";

	public MessageDAOImpl() throws DaoException {
		try {
			this.connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public boolean create(final Message message) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(CREATE_QUERY);

			statement.setLong(1, message.getSender().getUserId());
			statement.setLong(2, message.getRecipient().getUserId());
			statement.setString(3, message.getBody());
			statement.execute();

			logger.info("Successfully added new Message");
			return true;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public Message read(long id) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(READ_QUERY);

			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				Message message = buildMessage(resultSet);
				logger.info("Successfully read Message");
				return message;
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
		return null;
	}

	@Override
	public boolean update(final Message message) throws DaoException {
		long messageId = message.getMessageId();
		if (messageId == 0) {
			messageId = getId(message.getBody());
		}

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(UPDATE_QUERY);

			statement.setLong(1, message.getSender().getUserId());
			statement.setLong(2, message.getRecipient().getUserId());
			statement.setString(3, message.getBody());
			statement.setLong(4, messageId);

			boolean result = statement.executeUpdate() > 0;
			logger.info("Successfully updated Message");
			return result;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public boolean delete(final Message message) throws DaoException {
		long messageId = message.getMessageId();
		if (messageId == 0) {
			messageId = getId(message.getBody());
		}

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(DELETE_QUERY);
			statement.setLong(1, messageId);

			boolean result = statement.executeUpdate() > 0;
			logger.info("Successfully deleted Message");
			return result;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public long getId(final String body) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(GET_ID_QUERY);

			statement.setString(1, body);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				long result = resultSet.getLong(1);
				logger.info("Successfully got Message id");
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
	public Collection<Message> getAll() throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			List<Message> messages = new ArrayList<>();
			statement = connection.prepareStatement(GET_ALL_QUERY);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				messages.add(buildMessage(resultSet));
			}
			logger.info("Successfully got all Messages");
			return messages;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
	}

	@Override
	public Collection<Message> getMessages(User user) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			List<Message> messages = new ArrayList<>();
			statement = connection.prepareStatement(GET_MESSAGES_BY_USER_QUERY);
			statement.setLong(1, user.getUserId());
			statement.setLong(2, user.getUserId());
			statement.setLong(3, user.getUserId());
			statement.setLong(4, user.getUserId());
			statement.setLong(5, user.getUserId());
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				messages.add(buildMessage(resultSet));
			}
			logger.info("Successfully got all messages for specified User");
			return messages;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
	}

	@Override
	public Collection<Message> getDialog(User tellerFirst, User tellerSecond) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			List<Message> messages = new ArrayList<>();
			statement = connection.prepareStatement(GET_USER_DIALOG_QUERY);
			statement.setLong(1, tellerFirst.getUserId());
			statement.setLong(2, tellerSecond.getUserId());
			statement.setLong(3, tellerSecond.getUserId());
			statement.setLong(4, tellerFirst.getUserId());

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				messages.add(buildMessage(resultSet));
			}
			logger.info("Successfully got dialog for specified users");
			return messages;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
	}

	@Override
	public boolean deleteDialog(User tellerFirst, User tellerSecond) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(DELETE_DIALOG_QUERY);
			statement.setLong(1, tellerFirst.getUserId());
			statement.setLong(2, tellerSecond.getUserId());
			statement.setLong(3, tellerSecond.getUserId());
			statement.setLong(4, tellerFirst.getUserId());

			boolean result = statement.executeUpdate() > 0;
			logger.info("Successfully deleted Dialog");
			return result;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}
}
