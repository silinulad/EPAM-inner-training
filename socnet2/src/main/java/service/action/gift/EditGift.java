package service.action.gift;

import static service.MessageProvider.*;
import static service.action.common.Constants.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dao.impl.GiftDAOImpl;
import database.dao.interfaces.GenericDAO;
import exception.DaoException;
import exception.UploadException;
import model.Gift;
import service.InputValidator;
import service.MessageProvider;
import service.action.common.Action;

/**
 * Created by Silin on 08.2018.
 */

/**
 * EditGift action processes the gift creator request to change the gift information
 */
public class EditGift implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws DaoException, ServletException, IOException, UploadException {
		
		GenericDAO<Gift> giftDao = new GiftDAOImpl();

		String idParameter = request.getParameter("id");
		String name = request.getParameter("name");
		String description = request.getParameter("description");

		// validate id from request
		if (InputValidator.validateId(idParameter)) {
			long giftId = Long.parseLong(idParameter);
			Gift gift = giftDao.read(giftId);

			if (gift != null) {
				// validate input parameters
				MessageProvider validationResult = InputValidator.validateGift(name, description);
				if (validationResult != VALID) {
					request.setAttribute(ERROR_MODAL_PARAM, validationResult);
				} else {
					gift.setName(name);
					gift.setDescription(description);

					giftDao.update(gift);
					request.setAttribute(SUCCESS_PARAM, GROUP_UPDATED_SUCCESS);
				}
				return "/main?action=gift&id=" + giftId;
			} else {
				request.setAttribute(ERROR_PARAM, GROUP_NOT_FOUND_ERROR);
			}
		} else {
			request.setAttribute(ERROR_PARAM, GROUP_ID_ERROR);
		}
		return MAIN;
	}


}
