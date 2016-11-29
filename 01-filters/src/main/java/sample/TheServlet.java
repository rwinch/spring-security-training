package sample;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class TheServlet extends HttpServlet {
	private static final String ATTR = Filter1.class.getName().concat(".ATTR");

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute(ATTR, Boolean.TRUE);
	}

	public static boolean invoked(HttpServletRequest request) {
		return request.getAttribute(ATTR) != null;
	}
}
