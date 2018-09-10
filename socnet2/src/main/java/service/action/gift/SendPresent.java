package service.action.gift;

import static service.action.common.Constants.MAIN;
import static service.action.common.Constants.USER_PARAM;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.dao.impl.GiftDAOImpl;
import database.dao.impl.PresentDAOImpl;
import database.dao.impl.UserDAOImpl;
import database.dao.interfaces.GenericDAO;
import database.dao.interfaces.PresentDAO;
import exception.DaoException;
import exception.UploadException;
import model.Gift;
import model.Present;
import model.User;
import service.InputValidator;
import service.action.common.Action;

public class SendPresent implements Action {

	private static Object sendPresentLock = new Object();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws DaoException, ServletException, IOException, UploadException {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute(USER_PARAM);

		PresentDAO presentDao = new PresentDAOImpl();
		GenericDAO<User> userDao = new UserDAOImpl();
		GenericDAO<Gift> giftDao = new GiftDAOImpl();

		String idParam = request.getParameter("idTo");
		String giftIdParam = request.getParameter("giftId");

		// validate id from request
		if (InputValidator.validateId(idParam) && InputValidator.validateId(giftIdParam)) {
			long recipientId = Long.parseLong(idParam);
			long giftId = Long.parseLong(giftIdParam);
			User userRecipient = userDao.read(recipientId);
			Gift gift = giftDao.read(giftId);
			Present present = new Present.Builder().setGift(gift).setSender(sessionUser).build();
			synchronized (sendPresentLock) {
				presentDao.create(present);
				long presentId = presentDao.getId(gift.getName());
				present = presentDao.read(presentId);
			}

			presentDao.sendPresentTo(present, userRecipient);
			return "/main?action=profile&id=" + recipientId;
		}
		return MAIN;
	}

}
