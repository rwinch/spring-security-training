package sample.webdriver;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import sample.SpringConfig;
import sample.data.MyUserRepository;
import sample.security.WithAdmin;
import sample.security.WithMockMyUserFactory;
import sample.security.WithUser;
import sample.webdriver.pages.HomePage;
import sample.webdriver.pages.LoginPage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
public class WebDriverTest {
	@Autowired
	WebApplicationContext context;
	@Autowired
	MyUserRepository users;

	WebDriver driver;

	@Before
	public void setup() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
		driver = MockMvcHtmlUnitDriverBuilder
				.mockMvcSetup(mockMvc)
				.build();
	}

	@Test
	public void loginRequired() throws Exception {
		LoginPage login = HomePage.to(driver, LoginPage.class);
		login.assertAt();
	}

	@Test
	public void loginSuccess() throws Exception {
		LoginPage login = HomePage.to(driver, LoginPage.class);
		HomePage home = login
			.loginForm()
			.user("user")
			.password("password")
			.submit(HomePage.class);
		home.assertAt();
	}

	@Test
	public void loginMissingUser() throws Exception {
		LoginPage login = HomePage.to(driver, LoginPage.class);
		login = login
			.loginForm()
			.user("missing")
			.password("password")
			.submit(LoginPage.class);
		login.assertAt();
	}

	@Test
	public void loginInvalidPassword() throws Exception {
		LoginPage login = HomePage.to(driver, LoginPage.class);
		login = login
			.loginForm()
			.user("user")
			.password("invalid")
			.submit(LoginPage.class);
		login.assertAt();
	}

	@Test
	@WithUser
	public void indexWhenAccessWithUser() throws Exception {
		HomePage home = HomePage.to(driver);
		home.assertGreetingFor(users.findByUsername("user"));
	}

	@Test
	@WithAdmin
	public void indexWhenAccessWithAdmin() throws Exception {
		HomePage home = HomePage.to(driver);
		home.assertGreetingFor(WithMockMyUserFactory.myUser("admin"));
	}

	@Test
	@WithUser
	public void homePageWhenClickLogOutThenLoginPageDisplayed() {
		HomePage home = HomePage.to(driver);

		LoginPage loginPage = home.logout();
		loginPage.assertAt();
	}
}
