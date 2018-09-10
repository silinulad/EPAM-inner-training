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
import database.dao.interfaces.UserFileDAO;
import exception.ConnectionPoolException;
import exception.DaoException;
import model.AttachFile;
import model.User;

/**
 * Created by Silin on 08.2018.
 */

/**
 * Mysql implementation of the UserFileDao
 */
public class UserFileDAOImpl implements UserFileDAO {

	private ConnectionPool		connectionPool;
	private Connection			connection;
	private PreparedStatement	statement;
	private ResultSet			resultSet;

	private static final Logger	logger					= Logger.getLogger(UserFileDAOImpl.class);

	private static final String	ADD_FILE_QUERY			= "INSERT INTO userfiles (user_id, file_id) VALUES (?,?)";

	private static final String	DELETE_FILE_QUERY		= "DELETE FROM userfiles WHERE user_id = ? AND file_id = ?";

	private static final String	GET_USER_FILES_QUERY	= "SELECT * FROM userfiles WHERE user_id = ?";

	private static final String	GET_USER_FILE_QUERY		= "SELECT * FROM userfiles WHERE user_id = ? ORDER BY file_id DESC LIMIT 1";

	public UserFileDAOImpl() throws DaoException {
		try {
			this.connectionPool = ConnectionPool.getInstance();
		}
		catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public boolean addFile(User user, AttachFile file) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(ADD_FILE_QUERY);
			statement.setLong(1, user.getUserId());
			statement.setLong(2, file.getFileId());
			statement.execute();

			logger.info("Successfully added new File");
			return true;
		}
		catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		}
		finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public boolean deleteFile(User user, AttachFile file) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(DELETE_FILE_QUERY);
			statement.setLong(1, user.getUserId());
			statement.setLong(2, file.getFileId());
			statement.execute();

			logger.info("Successfully deleted File");
			return true;
		}
		catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		}
		finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public Collection<AttachFile> getFiles(User user) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			Set<AttachFile> files = new HashSet<>();
			statement = connection.prepareStatement(GET_USER_FILES_QUERY);
			statement.setLong(1, user.getUserId());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				files.add(buildFile(resultSet));
			}
			logger.info("Successfully got all Files for specified User");
			return files;
		}
		catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		}
		finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
	}

	@Override
	public AttachFile getLastUploadedFile(User user) throws DaoException {
		try {
			connection = connectionPool.takeConnection();

			statement = connection.prepareStatement(GET_USER_FILE_QUERY);
			statement.setLong(1, user.getUserId());
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				AttachFile file = buildFile(resultSet);
				return file;
			}
			logger.info("Successfully got the last file uploaded by specified User");
			return null;
		}
		catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		}
		finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
	}
}
