package database.dao.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import database.dao.impl.FileDAOImpl;
import database.dao.impl.MessageFileDAOImpl;
import database.dao.interfaces.GenericDAO;
import database.dao.interfaces.MessageFileDAO;
import exception.DaoException;
import model.AttachFile;
import model.Message;
import service.InputValidator;

public class ActionUtil {

	private ActionUtil() {}

	public static List<AttachFile> getFileListFromRequest(HttpServletRequest request, String param)
			throws DaoException {
		List<AttachFile> fileList = new ArrayList<>();
		String[] fileIdArray = request.getParameterValues(param);
		if (fileIdArray != null) {
			GenericDAO<AttachFile> fileDao = new FileDAOImpl();
			for (String fileIdStr : fileIdArray) {
				if (InputValidator.validateId(fileIdStr)) {
					long fileId = Long.parseLong(fileIdStr);
					AttachFile file = fileDao.read(fileId);
					fileList.add(file);
				}
			}
		}
		return fileList;
	}

	public static String getFileIdAsStringFromRequest(HttpServletRequest request, String fromRequestParam,
			String toRequestParam, Message message) throws DaoException {

		StringBuilder builder = new StringBuilder();
		String[] fileIdArray = request.getParameterValues(fromRequestParam);
		if (fileIdArray != null) {
			GenericDAO<AttachFile> fileDao = new FileDAOImpl();
			MessageFileDAO messageFileDao = new MessageFileDAOImpl();
			for (String fileIdStr : fileIdArray) {
				if (InputValidator.validateId(fileIdStr)) {
					long fileId = Long.parseLong(fileIdStr);
					AttachFile file = fileDao.read(fileId);
					messageFileDao.addFileToMessage(message, file);
					if (file != null) {
						builder.append("&");
						builder.append(fromRequestParam);
						builder.append("=");
						builder.append(file.getFileId());
					}
				}
			}
		}
		return builder.toString();
	}

	public static String setFileIdAsStringToRequest(String toRequestParam, List<AttachFile> attachedFileList)
			throws DaoException {
		StringBuilder builder = new StringBuilder();
		for (AttachFile attachFile : attachedFileList) {
			builder.append("&");
			builder.append(toRequestParam);
			builder.append("=");
			builder.append(attachFile.getFileId());
		}
		return builder.toString();
	}

}
