package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Created by Silin on 07.2018.
 */

/**
 * Login filter provides functionality for blocking unauthorized requests to
 * server pages and resources. Grants access if user is logged in or paths of
 * requests are allowed
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

	private static final String[] ALLOWED_PATHS = { "/login", "/registration", "/error", "/locale", "/css", "/fonts",
			"/js", "/images", "/upload" };

	private static final Logger logger = Logger.getLogger(LoginFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);

		String context = httpRequest.getContextPath();
		String uri = httpRequest.getRequestURI();

		boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
		boolean isAllowedRequest = checkPath(uri.substring(context.length()));

		if (isLoggedIn || isAllowedRequest) {
			chain.doFilter(httpRequest, httpResponse);
		} else {
			logger.info("Blocking unauthorized access to " + uri);
			httpResponse.sendRedirect(context + "/login");
		}
	}

	/**
	 * Checks if the requested URI is in allowed paths
	 *
	 * @param uri
	 * @return
	 */
	private boolean checkPath(String uri) {
		for (String path : ALLOWED_PATHS) {
			if ("/".equals(uri) || path.equals(uri) || uri.startsWith(path)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void destroy() {
	}
}
