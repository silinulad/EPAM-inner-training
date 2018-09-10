package filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Created by Silin on __.07.2018.
 */

/**
 * TokenFilter provides protection from CSRF attack by setting a token in a
 * session and validating it later with the token in forms Spring CSRF
 * protection:
 * http://docs.spring.io/spring-security/site/docs/current/reference/html/csrf.html
 */
@WebFilter(filterName = "TokenFilter", urlPatterns = { "/login", "/registration", "/logout", "/main" })
@MultipartConfig
public class TokenFilter implements Filter {

	private final static Logger logger = Logger.getLogger(TokenFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final String TOKEN = "token";
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(true);

		String newToken = UUID.randomUUID().toString();
		String oldToken = (String) session.getAttribute(TOKEN);
		String requestToken = httpRequest.getParameter(TOKEN);
		// set token for newly created session
		if (oldToken == null) {
			session.setAttribute(TOKEN, newToken);
			chain.doFilter(httpRequest, httpResponse);
			return;
		}
		// All post requests without the token in the form will be blocked
		if (httpRequest.getMethod().equalsIgnoreCase("POST")) {
			if (requestToken != null && oldToken.equals(requestToken)) {
				chain.doFilter(httpRequest, httpResponse);
			} else {
				logger.info("Blocking request with incorrect token from " + httpRequest.getRequestURI());
				request.getRequestDispatcher("/").forward(request, response);
			}
		} else {
			chain.doFilter(httpRequest, httpResponse);
		}
	}
	
	@Override
	public void destroy() {

	}
}
