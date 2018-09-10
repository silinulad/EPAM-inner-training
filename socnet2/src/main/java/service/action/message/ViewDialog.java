package service.action.message;

import static service.action.common.Constants.DIALOG;
import static service.action.common.Constants.MAIN;
import static service.action.common.Constants.USER_PARAM;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.dao.impl.MessageDAOImpl;
import database.dao.impl.MessageFileDAOImpl;
import database.dao.impl.UserDAOImpl;
import database.dao.interfaces.GenericDAO;
import database.dao.interfaces.MessageDAO;
import database.dao.interfaces.MessageFileDAO;
import database.dao.utils.ActionUtil;
import exception.DaoException;
import model.AttachFile;
import model.Message;
import model.User;
import service.InputValidator;
import service.action.common.Action;

/**
 * Created by Silin on 08.2018.
 */

/**
 * ViewDialog action processes user request to see dialog between tow users
 */
public class ViewDialog implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
		HttpSession session = request.getSession();
		User userSender = (User) session.getAttribute(USER_PARAM);

		MessageDAO messageDao = new MessageDAOImpl();
		GenericDAO<User> userDao = new UserDAOImpl();
		MessageFileDAO messageFileDao = new MessageFileDAOImpl();

		String idRecipientParam = request.getParameter("id");

		// validate id from request
		if (InputValidator.validateId(idRecipientParam)) {
			long recipientId = Long.parseLong(idRecipientParam);

			User userRecipient = userDao.read(recipientId);

			List<AttachFile> fileList = ActionUtil.getFileListFromRequest(request, "attachFile");

			Collection<Message> dialog = messageDao.getDialog(userSender, userRecipient);
			for (Message message : dialog) {
				Collection<AttachFile> attachedFiles = messageFileDao.getFilesForMessage(message);
				message.setFiles(attachedFiles);
			}
			request.setAttribute("dialog", dialog);
			request.setAttribute("recipientId", recipientId);
			request.setAttribute("fileList", fileList);

			return DIALOG;
		}
		return MAIN;
	}

}
