package service.action.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.DaoException;
import exception.UploadException;

/**
 * Created by Silin on 07.2018.
 */

/**
 * Interface Action with the execute method which processes a user request.
 * Implementation of the Strategy pattern
 */
public interface Action {

	/**
	 * Execute an action and return a page where a user should be forwarded
	 *
	 * @param request
	 * @param response
	 * @return page
	 * @throws DaoException
	 * @throws UploadException
	 */
	String execute(HttpServletRequest request, HttpServletResponse response)
			throws DaoException, ServletException, IOException, UploadException;
}