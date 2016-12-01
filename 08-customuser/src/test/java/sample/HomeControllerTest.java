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

import sample.data.MyUserRepository;
import sample.security.WithAdmin;
import sample.security.WithMockMyUserFactory;
import sample.security.WithUser;
import sample.webdriver.pages.HomePage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
public class HomeControllerTest {
	@Autowired
	WebApplicationContext context;
	@Autowired
	MyUserRepository users;

	MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(springSecurity())
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
		mockMvc.perform(formLogin())
			.andExpect(authenticated());
	}

	@Test
	public void loginMissingUser() throws Exception {
		FormLoginRequestBuilder login =
				formLogin().user("missing");
		mockMvc.perform(login)
			.andExpect(unauthenticated());
	}

	@Test
	public void loginInvalidPassword() throws Exception {
		FormLoginRequestBuilder login =
				formLogin().password("secret");
		mockMvc.perform(login)
			.andExpect(unauthenticated());
	}

	@Test
	@WithUser
	public void user() throws Exception {
		String greeting = HomePage.greetingFor(users.findByUsername("user"));
		mockMvc.perform(get("/"))
			.andExpect(content().string(containsString(greeting)));
	}

	@Test
	@WithAdmin
	public void admin() throws Exception {
		String greeting = HomePage.greetingFor(WithMockMyUserFactory.myUser("admin"));
		mockMvc.perform(get("/"))
			.andExpect(content().string(containsString(greeting)));
	}
}
