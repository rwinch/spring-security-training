package sample;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import sample.mvc.UserCredentials;
import sample.security.WithAdmin;
import sample.security.WithUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
public class HomeControllerTest {
	@Autowired
	WebApplicationContext context;
	ObjectMapper mapper = new ObjectMapper();

	MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(springSecurity())
				.defaultRequest(get("/").contentType(MediaType.APPLICATION_JSON))
				.build();
	}

	@Test
	public void loginRequired() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().isUnauthorized());
	}

	@Test
	public void loginSuccess() throws Exception {
		// default user / password
		UserCredentials credentials = new UserCredentials("user","password");
		mockMvc.perform(login(credentials))
			.andExpect(status().isOk())
			.andExpect(authenticated());
	}

	@Test
	public void loginMissingUser() throws Exception {
		UserCredentials credentials = new UserCredentials("missing","password");
		mockMvc.perform(login(credentials))
			.andExpect(unauthenticated());
	}

	@Test
	public void loginInvalidPassword() throws Exception {
		UserCredentials credentials = new UserCredentials("user","invalidt");
		mockMvc.perform(login(credentials))
			.andExpect(unauthenticated());
	}

	@Test
	@WithUser
	public void user() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(content().string(containsString("Hello user")));
	}

	@Test
	@WithAdmin
	public void admin() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(content().string(containsString("Hello admin")));
	}

	private MockHttpServletRequestBuilder login(UserCredentials credentials) throws Exception {
		String body = mapper.writeValueAsString(credentials);
		return post("/login").with(csrf()).content(body);
	}
}
