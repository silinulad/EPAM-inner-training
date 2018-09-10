package service.action.setting;

import static service.action.common.Constants.SETTINGS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.DaoException;
import service.action.common.Action;

/**
 * Created by Silin on 07.2018.
 */

/**
 * ViewSettings action processes an user request to see the user settings page
 */
public class ViewSettings implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
		return SETTINGS;
	}
}
