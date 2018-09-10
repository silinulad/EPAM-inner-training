package servlet;

import static service.MessageProvider.STATUS_403_ERROR;
import static service.MessageProvider.STATUS_404_ERROR;
import static service.MessageProvider.STATUS_500_ERROR;
import static service.MessageProvider.STATUS_UNKNOWN_ERROR;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import service.MessageProvider;

/**
 * Created by Silin on 07.2018.
 */

/**
 * ErrorHandler handles all web application exceptions and errors Redirects a
 * user to the valid error page explaining the error TODO: possible abuse
 */
@WebServlet("/error")
public class ErrorHandlerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ErrorHandlerServlet.class);

	Integer statusCode = null;
	String servletName = null;
	String requestUri = null;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processError(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processError(request, response);
	}

	private void processError(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
		requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");

		checkAttributes(statusCode, servletName, requestUri);
		MessageProvider errorMsg = getErrorMessage(statusCode);

		logger.error(String.format("Error/Exception information: {Servlet Name: %s, Status code: %d, Request URI: %s",
				servletName, statusCode, requestUri), throwable);

		request.setAttribute("errorMsg", errorMsg);
		request.setAttribute("statusCode", statusCode);
		request.getRequestDispatcher("/WEB-INF/error/errorpage.jsp").forward(request, response);
	}

	private void checkAttributes(Integer statusCode, String servletName, String requestUri) {
		final String UNKNOWN_ERROR = "Unknown";
		final int EMPTY_STATUS = 0;
		if (statusCode == null) {
			statusCode = EMPTY_STATUS;
		}
		if (servletName == null) {
			servletName = UNKNOWN_ERROR;
		}
		if (requestUri == null) {
			requestUri = UNKNOWN_ERROR;
		}
	}

	private MessageProvider getErrorMessage(Integer statusCode) {
		MessageProvider errorMsg = STATUS_UNKNOWN_ERROR;
		switch (statusCode) {
		case 403:
			errorMsg = STATUS_403_ERROR;
			break;
		case 404:
			errorMsg = STATUS_404_ERROR;
			break;
		case 500:
			errorMsg = STATUS_500_ERROR;
			break;
		}
		return errorMsg;
	}
}
