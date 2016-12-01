package sample;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import sample.security.WithAdmin;
import sample.security.WithUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
public class HomeControllerTest {
	@Autowired
	WebApplicationContext context;

	MockMvc mockMvc;

	@Before
	public void setup() {
		// FIXME apply Spring Security
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.build();
	}

	@Test
	public void loginRequired() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(redirectedUrl("http://localhost/login"));
	}

	@Test
	public void loginSuccess() throws Exception {
		// default user / password
		// FIXME use formLogin() to log in with user / password
		mockMvc.perform(null)
		// FIXME verify that the user is authenticated()
			.andExpect(null);
	}

	@Test
	public void loginMissingUser() throws Exception {
		// FIXME customize login to use username of "missing"
		FormLoginRequestBuilder login =
				formLogin();
		mockMvc.perform(login)
		// FIXME verify the unauthenticated()
			.andExpect(null);
	}

	@Test
	public void loginInvalidPassword() throws Exception {
		// FIXME customize login to use password of "secret"
		FormLoginRequestBuilder login =
				formLogin().password("secret");
		mockMvc.perform(login)
		// FIXME verify the unauthenticated()
			.andExpect(unauthenticated());
	}

	@Test
	public void user() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(content().string(containsString("Hello user")));
	}

	@Test
	public void admin() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(content().string(containsString("Hello admin")));
	}
}
