package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Created by Silin on __.07.2018.
 */

/**
 * Charset Filter sets charset to utf-8 for every request and response
 */
@WebFilter("/*")
public class CharsetFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final String CHAR_ENCODING = "UTF-8";
		request.setCharacterEncoding(CHAR_ENCODING);
		response.setCharacterEncoding(CHAR_ENCODING);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
}
