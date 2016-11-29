package sample.webdriver.pages;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author Rob Winch
 *
 */
public class LoginPage {
	private final WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public void assertAt() {
		assertThat(this.driver.getTitle()).contains("Login Page");
	}

	public LoginForm loginForm() {
		LoginForm form = new LoginForm();
		PageFactory.initElements(driver, form);
		return form;
	}

	public static LoginPage init(WebDriver driver) {
		return PageFactory.initElements(driver, LoginPage.class);
	}

	public class LoginForm {
		WebElement username;
		WebElement password;
		@FindBy(css="input[type=submit]")
		WebElement submit;

		public LoginForm user(String user) {
			username.clear();
			username.sendKeys(user);
			return this;
		}

		public LoginForm password(String password) {
			this.password.clear();
			this.password.sendKeys(password);
			return this;
		}

		public <T> T submit(Class<T> clazz) {
			submit.click();
			return PageFactory.initElements(driver, clazz);
		}
	}
}