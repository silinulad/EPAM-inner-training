package service.action.gift;

import static service.action.common.Constants.GIFTS;
import static service.action.common.Constants.USER_PARAM;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.dao.impl.GiftDAOImpl;
import database.dao.impl.PresentDAOImpl;
import database.dao.interfaces.GenericDAO;
import database.dao.interfaces.PresentDAO;
import exception.DaoException;
import exception.UploadException;
import model.Gift;
import model.Present;
import model.User;
import service.action.common.Action;

/**
 * Created by Silin on 08.2018.
 */

/**
 * ViewGifts action processes a user request to see all gifts information
 */
public class ViewGifts implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws DaoException, ServletException, IOException, UploadException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(USER_PARAM);

		GenericDAO<Gift> giftsDao = new GiftDAOImpl();
		PresentDAO presantDao = new PresentDAOImpl();

		Collection<Gift> gifts = giftsDao.getAll();
		request.setAttribute("gifts", gifts);

		Collection<Present> userPresents = presantDao.getPresentsGivenTo(user);
		request.setAttribute("userPresents", userPresents);
		return GIFTS;
	}

}
