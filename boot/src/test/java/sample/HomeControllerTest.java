package sample;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Test
	public void login() throws Exception {
		mockMvc.perform(formLogin())
			.andExpect(authenticated());
	}

	@Test
	public void loginFail() throws Exception {
		mockMvc.perform(formLogin().user("missing"))
			.andExpect(unauthenticated());
	}

	@Test
	@WithMockUser
	public void user() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(content().string(containsString("Hello user")));
	}

	@Test
	@WithMockUser("admin")
	public void admin() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(content().string(containsString("Hello admin")));
	}
}
