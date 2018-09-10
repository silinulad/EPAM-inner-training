package service.action.file;

import static service.action.common.Constants.MAIN;
import static service.action.common.Constants.USER_PARAM;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import database.dao.impl.FileDAOImpl;
import database.dao.impl.UserFileDAOImpl;
import database.dao.interfaces.GenericDAO;
import database.dao.interfaces.UserFileDAO;
import exception.DaoException;
import exception.UploadException;
import model.AttachFile;
import model.User;
import service.InputValidator;
import service.action.common.Action;
import utils.UploadPath;

public class FileUpload implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws DaoException, ServletException, IOException, UploadException {
		final String INPUT_NAME = "file";

		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute(USER_PARAM);
	
		String idParam = request.getParameter("idTo");

		GenericDAO<AttachFile> fileDao = new FileDAOImpl();
		UserFileDAO userFileDao = new UserFileDAOImpl();

		// validate id from request
		if (InputValidator.validateId(idParam)) {
			long recipientId = Long.parseLong(idParam);

			List<Part> fileParts = request.getParts()
									.stream()
									.filter(part -> INPUT_NAME.equals(part.getName()))
									.collect(Collectors.toList());

			String fileCatalog = UploadPath.getPathFileUpload();
			UploadPath.creatDirectory(fileCatalog);

			StringBuilder builder = new StringBuilder();

			for (Part filePart : fileParts) {
				String originFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
				if (originFileName.isEmpty()) {
					throw new UploadException("File is absent");
				}

				String uploadFileName = UploadPath.getUploadName(originFileName);
				String serverFilePath = fileCatalog + uploadFileName;

				filePart.write(serverFilePath);

				AttachFile file = new AttachFile.Builder()
						.setOriginName(originFileName)
						.setUploadedName(uploadFileName)
						.setPath(serverFilePath)
						.build();

				fileDao.create(file);
				long fileId = fileDao.getId(uploadFileName);
				AttachFile attachFile = fileDao.read(fileId);
				userFileDao.addFile(sessionUser, attachFile);
				builder.append("&attachFile=");
				builder.append(attachFile.getFileId());
			}
			return request.getContextPath() + "/main?action=dialog&id=" + recipientId + builder.toString();
		}
		return MAIN;
	}
}
