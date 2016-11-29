package sample;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class Filter2 extends OncePerRequestFilter {
	private static final String INVOKED_ATTR = Filter2.class.getName().concat(".INVOKED_ATTR");
	private static final String DO_NOT_CONTINUE_ATTR = Filter2.class.getName().concat(".DISABLED_ATTR");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		request.setAttribute(INVOKED_ATTR, Boolean.TRUE);

		if(shouldContinue(request)) {
			return;
		}

		filterChain.doFilter(request, response);
	}

	private static boolean shouldContinue(HttpServletRequest request) {
		return request.getAttribute(DO_NOT_CONTINUE_ATTR) != null;
	}

	public static void doNotContinue(HttpServletRequest request) {
		request.setAttribute(DO_NOT_CONTINUE_ATTR, Boolean.TRUE);
	}

	public static boolean invoked(HttpServletRequest request) {
		return request.getAttribute(INVOKED_ATTR) != null;
	}
}
