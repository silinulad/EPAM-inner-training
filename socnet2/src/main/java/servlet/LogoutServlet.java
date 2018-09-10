package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import model.User;

/**
 * Created by Silin on 07.2018.
 */

/**
 * LogoutServlet handles a simple logout request
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(LogoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logoutUser(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logoutUser(request, response);
    }

    /**
	 * Invalidate the user session and log his logout event
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
    private void logoutUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String UNKNOWN_LABEL = "unknown";
		String userEmail = UNKNOWN_LABEL;
		String userFirstName = UNKNOWN_LABEL;
		String userLastName = UNKNOWN_LABEL;

        HttpSession session = request.getSession();
        if (session != null) {
            User user = (User) session.getAttribute("user");

            if (user != null) {
                userEmail = user.getEmail();
				userFirstName = user.getFirstName();
                userLastName = user.getLastName();
            }

            session.invalidate();
        }

        logger.info("User logged out: {" + userEmail + "," + userFirstName + "," + userLastName + "}");
        response.sendRedirect("./login");
    }
}
