package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Silin on 07.2018.
 */

/**
 * LocaleServlet allows changing localization using the user request
 */
@WebServlet("/locale")
public class LocaleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String RU_CODE = "ru";
	private static final String EN_CODE = "en";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setLocale(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setLocale(request, response);
	}

	/**
	 * Set the locale into the session
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void setLocale(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final String LANGUAGE_ATTR = "language";
		HttpSession session = request.getSession(true);
		String lang = request.getParameter("lang");

		if (session != null && lang != null) {
			if (RU_CODE.equals(lang)) {
				session.setAttribute(LANGUAGE_ATTR, RU_CODE);
			} else {
				// English by default
				session.setAttribute(LANGUAGE_ATTR, EN_CODE);
			}
		}

		// return back to the page from where request came
		response.sendRedirect(request.getHeader("Referer"));
	}
}
