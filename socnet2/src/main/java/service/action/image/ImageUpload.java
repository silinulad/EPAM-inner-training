package service.action.image;

import static service.MessageProvider.GROUP_ID_ERROR;
import static service.MessageProvider.GROUP_NOT_FOUND_ERROR;
import static service.action.common.Constants.ERROR_PARAM;
import static service.action.common.Constants.USER_PARAM;

import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import database.dao.impl.FileDAOImpl;
import database.dao.impl.GiftDAOImpl;
import database.dao.impl.GroupDAOImpl;
import database.dao.impl.UserImageDAOImpl;
import database.dao.interfaces.GenericDAO;
import database.dao.interfaces.ImageDAO;
import database.dao.interfaces.UserImageDAO;
import exception.DaoException;
import exception.UploadException;
import model.AttachFile;
import model.Gift;
import model.Group;
import model.User;
import service.InputValidator;
import service.action.common.Action;
import utils.UploadPath;

public class ImageUpload implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws DaoException, ServletException, IOException, UploadException {

		UserImageDAO userImageDao = null;
		GenericDAO<Gift> giftDao = null;
		GenericDAO<Group> groupDao = null;
		ImageDAO<Group> imageGroupDao = null;
		ImageDAO<Gift> imageGiftDao = null;

		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute(USER_PARAM);
		
		String path = request.getHeader("Referer");
		String idGiftParam = request.getParameter("idGift");
		String idGroupParam = request.getParameter("id");

		AttachFile file = uploadFile(request);

		if (idGiftParam != null) {
			if (InputValidator.validateId(idGiftParam)) {
				giftDao = new GiftDAOImpl();
				imageGroupDao = new GroupDAOImpl();
				Gift gift = giftDao.read(Long.parseLong(idGiftParam));

				if (gift != null) {
					gift.setImage(file.getPath());
					imageGiftDao = new GiftDAOImpl();
					imageGiftDao.addImage(gift);
					return path;
				} else {
					request.setAttribute(ERROR_PARAM, GROUP_NOT_FOUND_ERROR);
				}
			} else {
				request.setAttribute(ERROR_PARAM, GROUP_ID_ERROR);
			}

		} else if (idGroupParam != null) {
			if (InputValidator.validateId(idGroupParam)) {
				groupDao = new GroupDAOImpl();
				imageGroupDao = new GroupDAOImpl();
				Group group = groupDao.read(Long.parseLong(idGroupParam));

				if (group != null) {
					group.setImage(file.getPath());
					imageGroupDao = new GroupDAOImpl();
					imageGroupDao.addImage(group);
					return path;
				} else {
					request.setAttribute(ERROR_PARAM, GROUP_NOT_FOUND_ERROR);
				}
			} else {
				request.setAttribute(ERROR_PARAM, GROUP_ID_ERROR);
			}
		} else {
			userImageDao = new UserImageDAOImpl();
			userImageDao.addImage(sessionUser, file);
		}
		return path;
	}

	private AttachFile uploadFile(HttpServletRequest request)
			throws IOException, ServletException, UploadException, DaoException {

		final String INPUT_NAME = "file";
		Part filePart = request.getPart(INPUT_NAME);

		String originFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		if (originFileName.isEmpty()) {
			throw new UploadException("File is absent");
		}
		GenericDAO<AttachFile> fileDao = new FileDAOImpl();
		String fileCatalog = UploadPath.getPathImageUpload();
		UploadPath.creatDirectory(fileCatalog);

		String uploadFileName = UploadPath.getUploadName(originFileName);
		String serverFilePath = fileCatalog + uploadFileName;
		filePart.write(serverFilePath);
		String relativePath = UploadPath.getRelativePath(uploadFileName);

		AttachFile file = new AttachFile.Builder().setOriginName(originFileName).setUploadedName(uploadFileName)
				.setPath(relativePath).build();

		fileDao.create(file);
		long fileId = fileDao.getId(uploadFileName);
		AttachFile attachFile = fileDao.read(fileId);

		return attachFile;
	}
}
