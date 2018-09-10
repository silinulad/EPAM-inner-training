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
import database.dao.interfaces.UserImageDAO;
import exception.ConnectionPoolException;
import exception.DaoException;
import model.AttachFile;
import model.User;

/**
 * Mysql implementation of the UserImageDAOImpl
 */
public class UserImageDAOImpl implements UserImageDAO {

	private ConnectionPool connectionPool;
	private Connection connection;
	private PreparedStatement statement;
	private ResultSet resultSet;

	private static final Logger logger = Logger.getLogger(UserImageDAOImpl.class);

	private static final String ADD_IMAGE_QUERY = "INSERT INTO userimages (user_id, file_id) VALUES (?,?)";

	private static final String DELETE_IMAGE_QUERY = "DELETE FROM userimages WHERE user_id = ? AND file_id = ?";

	private static final String GET_USER_IMAGES_QUERY = "SELECT * FROM userimages JOIN files ON files.file_id = userimages.file_id WHERE user_id = ?";

	private static final String GET_USER_IMAGE_QUERY = "SELECT * FROM userimages JOIN files ON files.file_id = userimages.file_id WHERE user_id = ? ORDER BY userimages.file_id DESC LIMIT 1";

	public UserImageDAOImpl() throws DaoException {
		try {
			this.connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public boolean addImage(User user, AttachFile file) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(ADD_IMAGE_QUERY);
			statement.setLong(1, user.getUserId());
			statement.setLong(2, file.getFileId());
			statement.execute();

			logger.info("Successfully added new Image");
			return true;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public boolean deleteImage(User user, AttachFile file) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(DELETE_IMAGE_QUERY);
			statement.setLong(1, user.getUserId());
			statement.setLong(2, file.getFileId());
			statement.execute();

			logger.info("Successfully deleted Image");
			return true;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public Collection<AttachFile> getImages(User user) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			Set<AttachFile> files = new HashSet<>();
			statement = connection.prepareStatement(GET_USER_IMAGES_QUERY);
			statement.setLong(1, user.getUserId());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				files.add(buildFile(resultSet));
			}
			logger.info("Successfully got all Images for specified User");
			return files;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
	}

	@Override
	public AttachFile getLastUploadedImage(User user) throws DaoException {
		try {
			connection = connectionPool.takeConnection();

			statement = connection.prepareStatement(GET_USER_IMAGE_QUERY);
			statement.setLong(1, user.getUserId());
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				AttachFile file = buildFile(resultSet);
				return file;
			}
			logger.info("Successfully got the last image uploaded by specified User");
			return null;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
	}

}
