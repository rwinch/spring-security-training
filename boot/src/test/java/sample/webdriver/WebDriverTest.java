package sample.webdriver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sample.webdriver.pages.HomePage;
import sample.webdriver.pages.LoginPage;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WebDriverTest {
	@Autowired
	WebDriver driver;

	@Test
	public void login() throws Exception {
		LoginPage login = HomePage.to(driver, LoginPage.class);
		HomePage home = login
			.loginForm()
			.user("user")
			.password("password")
			.submit(HomePage.class);
		home.assertAt();
	}

	@Test
	public void loginFail() throws Exception {
		LoginPage login = HomePage.to(driver, LoginPage.class);
		login = login
			.loginForm()
			.user("missing")
			.password("password")
			.submit(LoginPage.class);
		login.assertAt();
	}

	@Test
	@WithMockUser
	public void user() throws Exception {
		HomePage home = HomePage.to(driver);
		home.assertGreetingFor("user");
	}

	@Test
	@WithMockUser("admin")
	public void admin() throws Exception {
		HomePage home = HomePage.to(driver);
		home.assertGreetingFor("admin");
	}

	@Test
	@WithMockUser
	public void homePageWhenClickLogOutThenLoginPageDisplayed() {
		HomePage home = HomePage.to(driver);

		LoginPage loginPage = home.logout();
		loginPage.assertAt();
	}
}
