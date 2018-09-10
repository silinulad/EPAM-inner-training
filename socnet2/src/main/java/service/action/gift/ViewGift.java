package service.action.gift;

import static service.MessageProvider.GROUP_ID_ERROR;
import static service.MessageProvider.GROUP_NOT_FOUND_ERROR;
import static service.action.common.Constants.ERROR_PARAM;
import static service.action.common.Constants.GIFT;
import static service.action.common.Constants.MAIN;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dao.impl.GiftDAOImpl;
import database.dao.interfaces.GenericDAO;
import exception.DaoException;
import model.Gift;
import service.InputValidator;
import service.action.common.Action;

/**
 * Created by Silin on 08.2018.
 */

/**
 * ViewGift action processes a user request to see information about the gift
 */
public class ViewGift implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {

		GenericDAO<Gift> giftDao = new GiftDAOImpl();

		String idParameter = request.getParameter("id");

		if (InputValidator.validateId(idParameter)) {
			Gift gift = giftDao.read(Long.parseLong(idParameter));

			if (gift != null) {
				request.setAttribute("giftInfo", gift);
				return GIFT;
			} else {
				request.setAttribute(ERROR_PARAM, GROUP_NOT_FOUND_ERROR);
			}
		} else {
			request.setAttribute(ERROR_PARAM, GROUP_ID_ERROR);
		}
		return MAIN;
	}
}
