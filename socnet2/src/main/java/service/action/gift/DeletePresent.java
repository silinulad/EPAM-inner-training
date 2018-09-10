package service.action.gift;

import static service.MessageProvider.*;
import static service.action.common.Constants.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.dao.impl.PresentDAOImpl;
import database.dao.interfaces.GenericDAO;
import exception.DaoException;
import exception.UploadException;
import model.Present;
import model.User;
import service.InputValidator;
import service.action.common.Action;

public class DeletePresent implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws DaoException, ServletException, IOException, UploadException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(USER_PARAM);

		GenericDAO<Present> presentDao = new PresentDAOImpl();

		String idPresentParam = request.getParameter("id");

		if (InputValidator.validateId(idPresentParam)) {
			long presentId = Long.parseLong(idPresentParam);
			Present present = presentDao.read(presentId);

			if (present != null) {
				// check that user is the recipient this present
				if (!(present.getSender().equals(user))) {
					presentDao.delete(present);
					request.setAttribute(SUCCESS_PARAM, GROUP_DELETED_SUCCESS);
					return "/main?action=profile";
				} else {
					request.setAttribute(ERROR_PARAM, INSUFFICIENT_RIGHTS_ERROR);
				}
			} else {
				request.setAttribute(ERROR_PARAM, GROUP_NOT_FOUND_ERROR);
			}

		} else {
			request.setAttribute(ERROR_PARAM, GROUP_ID_ERROR);
		}
		return MAIN;
	}

}
