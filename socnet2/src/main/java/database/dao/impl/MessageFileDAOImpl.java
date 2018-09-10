package database.dao.impl;

import static database.dao.utils.DaoUtils.buildFile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import database.connectionpool.ConnectionPool;
import database.dao.interfaces.MessageFileDAO;
import exception.ConnectionPoolException;
import exception.DaoException;
import model.AttachFile;
import model.Message;

/**
 * Created by Silin on 07.2018.
 */

/**
 * Mysql implementation of the MessageFileDAO
 */
public class MessageFileDAOImpl implements MessageFileDAO {

	private ConnectionPool connectionPool;
	private Connection connection;
	private PreparedStatement statement;
	private ResultSet resultSet;

	private static final Logger logger = Logger.getLogger(MessageFileDAOImpl.class);

	private static final String ADD_FILE_TO_MESSAGE_QUERY = "INSERT INTO messagefiles (message_id,file_id) VALUES (?,?)";

	private static final String GET_FILES_FOR_MESSAGE_QUERY = "SELECT * FROM files INNER JOIN messagefiles ON messagefiles.file_id = files.file_id WHERE messagefiles.message_id = ?";

	private static final String GET_FILE_FOR_MESSAGE_QUERY = "SELECT * FROM files INNER JOIN messagefiles ON messagefiles.file_id = files.file_id WHERE messagefiles.message_id = ? AND  files.file_id = ?";

	public MessageFileDAOImpl() throws DaoException {
		try {
			this.connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public boolean addFileToMessage(Message message, AttachFile file) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(ADD_FILE_TO_MESSAGE_QUERY);
			statement.setLong(1, message.getMessageId());
			statement.setLong(2, file.getFileId());
			statement.execute();

			logger.info("Successfully added new file to specified message");
			return true;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public AttachFile getFileForMessage(long messageId, long fileId) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(GET_FILE_FOR_MESSAGE_QUERY);
			statement.setLong(1, messageId);
			statement.setLong(2, fileId);
			resultSet = statement.executeQuery();
			AttachFile file = null;
			if (resultSet.next()) {
				file = buildFile(resultSet);
				logger.info("Successfully got file for specified message");
			}
			return file;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
	}

	@Override
	public Collection<AttachFile> getFilesForMessage(Message message) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			Set<AttachFile> files = new HashSet<>();
			statement = connection.prepareStatement(GET_FILES_FOR_MESSAGE_QUERY);
			statement.setLong(1, message.getMessageId());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				files.add(buildFile(resultSet));
			}
			logger.info("Successfully got all files for specified message");
			return files;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
	}
}
