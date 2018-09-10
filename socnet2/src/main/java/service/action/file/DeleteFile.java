package service.action.file;

import static service.MessageProvider.GROUP_DELETED_SUCCESS;
import static service.MessageProvider.GROUP_ID_ERROR;
import static service.MessageProvider.GROUP_NOT_FOUND_ERROR;
import static service.action.common.Constants.ERROR_PARAM;
import static service.action.common.Constants.MAIN;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dao.impl.FileDAOImpl;
import database.dao.impl.UserDAOImpl;
import database.dao.interfaces.GenericDAO;
import database.dao.utils.ActionUtil;
import exception.DaoException;
import model.AttachFile;
import model.User;
import service.InputValidator;
import service.action.common.Action;
import utils.UploadPath;

public class DeleteFile implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException, IOException {

		GenericDAO<AttachFile> fileDao = new FileDAOImpl();
		GenericDAO<User> userDao = new UserDAOImpl();

		String idFileParam = request.getParameter("delattach");
		String idUserParam = request.getParameter("idTo");

		if (InputValidator.validateId(idFileParam) && InputValidator.validateId(idUserParam)) {
			long delFileId = Long.parseLong(idFileParam);
			long recipientId = Long.parseLong(idUserParam);

			User recipient = userDao.read(recipientId);
			AttachFile delFile = fileDao.read(delFileId);

			List<AttachFile> attachedFileList = ActionUtil.getFileListFromRequest(request, "attach");

			if (delFile != null && recipient != null) {
				fileDao.delete(delFile);

				if (attachedFileList.contains(delFile)) {
					attachedFileList.remove(delFile);
				}

				String fileCatalog = UploadPath.getPathFileUpload();
				Files.delete(Paths.get(fileCatalog + delFile.getUploadedName()));

				request.setAttribute("successMsg", GROUP_DELETED_SUCCESS);
				request.setAttribute("fileList", attachedFileList);
				String partOfGetRequest = ActionUtil.setFileIdAsStringToRequest("attachFile", attachedFileList);
				return "/main?action=dialog&id=" + recipientId + partOfGetRequest;
			} else {
				request.setAttribute(ERROR_PARAM, GROUP_NOT_FOUND_ERROR);
			}
		} else {
			request.setAttribute(ERROR_PARAM, GROUP_ID_ERROR);
		}
		return MAIN;
	}
}
