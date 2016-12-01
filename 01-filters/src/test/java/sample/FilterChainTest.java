package sample;

import static org.assertj.core.api.Assertions.assertThat;

import javax.servlet.FilterChain;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class FilterChainTest {
	FilterChain chain;
	MockHttpServletResponse response;
	MockHttpServletRequest request;

	@Before
	public void setupRequestResponse() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}

	@Before
	public void setupChain() {
		// FIXME implement FilterChain (do not use existing implementation)
		// Should use Filter1, Filter2, and TheServlet (in
		// that order)
	}

	@Test
	public void allInvoked() throws Exception {
		chain.doFilter(request, response);

		assertThat(Filter1.invoked(request)).isTrue();
		assertThat(Filter2.invoked(request)).isTrue();
		assertThat(TheServlet.invoked(request)).isTrue();
	}

	@Test
	public void filter2DoNotContinue() throws Exception {
		Filter2.doNotContinue(request);

		chain.doFilter(request, response);

		assertThat(Filter1.invoked(request)).isTrue();
		assertThat(Filter2.invoked(request)).isTrue();
		assertThat(TheServlet.invoked(request)).isFalse();
	}

	@Test
	public void filter1DoNotContinue() throws Exception {
		Filter1.doNotContinue(request);

		chain.doFilter(request, response);

		assertThat(Filter1.invoked(request)).isTrue();
		assertThat(Filter2.invoked(request)).isFalse();
		assertThat(TheServlet.invoked(request)).isFalse();
	}
}
