package sample;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import sample.security.WithAdmin;
import sample.security.WithRob;
import sample.security.WithUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
public class InterceptUrlTest {

	public static final String ROB = "rob";
	public static final String ODD = "a";
	public static final String EVEN = "ab";
	public static final String OTHER = "/other";
	public static final String SECRET = "/secret";
	public static final String ADMIN_FOO = "/admin/foo";
	public static final String PUBLIC = "/public/";

	@Autowired
	WebApplicationContext context;

	MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	@Test
	public void permitAll() throws Exception {
		mockMvc.perform(get(PUBLIC))
			.andExpect(status().isOk());
	}

	@Test
	public void adminUnAuthenticated() throws Exception {
		mockMvc.perform(get(ADMIN_FOO))
			.andExpect(status().isUnauthorized());
	}

	@Test
	@WithUser
	public void adminWithUser() throws Exception {
		mockMvc.perform(get(ADMIN_FOO))
			.andExpect(status().isForbidden());
	}

	@Test
	@WithAdmin
	public void adminWithAdmin() throws Exception {
		mockMvc.perform(get(ADMIN_FOO))
			.andExpect(status().isOk());
	}

	@Test
	@WithAdmin
	public void secretWhenAdminThenOk() throws Exception {
		mockMvc.perform(get(SECRET))
			.andExpect(content().string("secret"))
			.andExpect(status().isOk());
	}

	@Test
	@WithAdmin
	public void secretWhenAdminAndWithSlashThenOk() throws Exception {
		mockMvc.perform(get(SECRET + "/"))
			.andExpect(content().string("secret"))
			.andExpect(status().isOk());
	}

	@Test
	@WithAdmin
	public void secretWhenAdminAndWithSuffixThenOk() throws Exception {
		mockMvc.perform(get(SECRET + ".html"))
			.andExpect(content().string("secret"))
			.andExpect(status().isOk());
	}

	@Test
	@WithUser
	public void secretWhenUserThenForbidden() throws Exception {
		mockMvc.perform(get(SECRET))
			.andExpect(status().isForbidden());
	}

	@Test
	@WithUser
	public void secretWhenUserAndWithSlashThenForbidden() throws Exception {
		mockMvc.perform(get(SECRET + "/"))
			.andExpect(status().isForbidden());
	}

	@Test
	@WithUser
	public void secretWhenUserAndWithSuffixThenForbidden() throws Exception {
		mockMvc.perform(get(SECRET + ".html"))
			.andExpect(status().isForbidden());
	}

	@Test
	public void robUnAuthenticatedUser() throws Exception {
		mockMvc.perform(get(emails(ROB)))
			.andExpect(status().isUnauthorized());
	}

	@Test
	@WithUser
	public void robWithUser() throws Exception {
		mockMvc.perform(get(emails(ROB)))
			.andExpect(status().isForbidden());
	}

	@Test
	@WithAdmin
	public void robWithAdmin() throws Exception {
		mockMvc.perform(get(emails(ROB)))
			.andExpect(status().isForbidden());
	}

	@Test
	@WithRob
	public void robWithRob() throws Exception {
		mockMvc.perform(get(emails(ROB)))
			.andExpect(status().isOk());
	}

	@Test
	public void phonesWhenOddAndNotAuthenticatedThenUnauthorized() throws Exception {
		mockMvc.perform(get(phones(ODD)))
			.andExpect(status().isOk());
	}

	@Test
	@WithUser
	public void phonesWhenOddAndWithUserThenOk() throws Exception {
		mockMvc.perform(get(phones(ODD)))
			.andExpect(status().isOk());
	}

	@Test
	@WithAdmin
	public void phonesWhenEvenAndWithUserThenUnauthorized() throws Exception {
		mockMvc.perform(get(phones(EVEN)))
			.andExpect(status().isForbidden());
	}

	private static String emails(String username) {
		return "/users/"+username+"/emails";
	}

	private static String phones(String username) {
		return "/users/"+username+"/phones";
	}
}
