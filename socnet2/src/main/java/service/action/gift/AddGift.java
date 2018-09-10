package service.action.gift;

import static service.MessageProvider.GROUP_CREATED_SUCCESS;
import static service.MessageProvider.VALID;
import static service.action.common.Constants.ERROR_PARAM;
import static service.action.common.Constants.SUCCESS_PARAM;

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
 * Created by Silin on 08.2017.
 */

/**
 * AddGift action processes request to create new gift
 */
public class AddGift implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws DaoException, ServletException, IOException, UploadException {

		GenericDAO<Gift> giftDao = new GiftDAOImpl();
		String name = request.getParameter("name");
		String description = request.getParameter("description");


		// validate input parameters
		MessageProvider validationResult = InputValidator.validateGift(name, description);
		if (validationResult != VALID) {
			request.setAttribute("name", name);
			request.setAttribute("description", description);
			request.setAttribute(ERROR_PARAM, validationResult);
		} else {
			Gift gift = new Gift.Builder()
							.setName(name)
							.setDescription(description)
							.build();
			
			giftDao.create(gift);
			request.setAttribute(SUCCESS_PARAM, GROUP_CREATED_SUCCESS);
		}
		return "/main?action=gifts";
	}

}
