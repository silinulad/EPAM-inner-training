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
import database.dao.interfaces.GenericDAO;
import exception.ConnectionPoolException;
import exception.DaoException;
import model.AttachFile;


public class FileDAOImpl implements GenericDAO<AttachFile> {

	private ConnectionPool		connectionPool;
	private Connection			connection;
	private PreparedStatement	statement;
	private ResultSet			resultSet;
	
	private static final Logger logger = Logger.getLogger(FileDAOImpl.class);
	
	private static final String CREATE_QUERY = "INSERT INTO files (name_or, name_up, path) VALUES (?,?,?)";

	private static final String READ_QUERY = "SELECT * FROM files WHERE file_id = ?";

	private static final String UPDATE_QUERY = "UPDATE files SET file_id = ?, name_or = ?, name_up = ?, path = ?";

	private static final String DELETE_QUERY = "DELETE FROM files WHERE file_id = ?";

	private static final String GET_ID_QUERY = "SELECT file_id FROM files WHERE name_up = ?";

	private static final String GET_ALL_QUERY = "SELECT * FROM files";

	public FileDAOImpl() throws DaoException {
		try {
			this.connectionPool = ConnectionPool.getInstance();
		}
		catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public boolean create(final AttachFile file) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(CREATE_QUERY);

			statement.setString(1, file.getOriginName());
			statement.setString(2, file.getUploadedName());
			statement.setString(3, file.getPath());
			statement.execute();

			logger.info("Successfully added new File");
			return true;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public AttachFile read(long id) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(READ_QUERY);

			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				AttachFile file = buildFile(resultSet);
				logger.info("Successfully read File");
				return file;
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
		return null;
	}

	@Override
	public boolean update(AttachFile file) throws DaoException {
		long fileId = file.getFileId();
		if (fileId == 0) {
			fileId = getId(file.getUploadedName());
		}

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(UPDATE_QUERY);

			statement.setLong(1, fileId);
			statement.setString(2, file.getOriginName());
			statement.setString(3,file.getUploadedName());
		
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
	public boolean delete(AttachFile file) throws DaoException {
		long fileId = file.getFileId();
		if (fileId == 0) {
			fileId = getId(file.getUploadedName());
		}

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(DELETE_QUERY);
			statement.setLong(1, fileId);

			boolean result = statement.executeUpdate() > 0;
			logger.info("Successfully deleted File");
			return result;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement);
		}
	}

	@Override
	public long getId(String oploadedName) throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(GET_ID_QUERY);

			statement.setString(1, oploadedName);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				long result = resultSet.getLong(1);
				logger.info("Successfully got File id");
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
	public Collection<AttachFile> getAll() throws DaoException {
		try {
			connection = connectionPool.takeConnection();
			Set<AttachFile> files = new HashSet<>();
			statement = connection.prepareStatement(GET_ALL_QUERY);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				files.add(buildFile(resultSet));
			}
			logger.info("Successfully got all Files");
			return files;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException(e);
		} finally {
			connectionPool.closeConnection(connection, statement, resultSet);
		}
	}
}
