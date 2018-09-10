package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Silin on 07.2018.
 */

/**
 * The ServletHelper uses a number of methods to wrap some actions within
 * servlets and implementations of the Action interface.
 */
public final class ServletActionHelper {

	private ServletActionHelper() {	
	}

	/**
	 * Check a session and a user in the session
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void checkSession(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		final String SESSION_USER_ATTR = "user";
		final String MAIN_PATH = "/main";
		if (session != null && session.getAttribute(SESSION_USER_ATTR) != null) {
			redirectTo(request, response, MAIN_PATH);
            return;
        }
	}

	/**
	 * Forward a user request to the specified page
	 *
	 * @param request
	 * @param response
	 * @param path
	 *            page
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void forwardTo(HttpServletRequest request, HttpServletResponse response, String path)
			throws ServletException, IOException {
		request.getRequestDispatcher(path).forward(request, response);
	}

	/**
	 * Redirect the user to the page
	 *
	 * @param request
	 * @param response
	 * @param path
	 *            page
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void redirectTo(HttpServletRequest request, HttpServletResponse response, String path)
			throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + path);
	}

}
