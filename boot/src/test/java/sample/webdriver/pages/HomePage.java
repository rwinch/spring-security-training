package sample.webdriver.pages;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * @author Rob Winch
 *
 */
public class HomePage {
	WebDriver driver;
	WebElement logout;
	WebElement greeting;

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	public LoginPage logout() {
		logout.click();
		return LoginPage.init(driver);
	}

	public void assertGreetingFor(String username) {
		assertThat(greeting.getText()).isEqualTo("Hello " + username);
	}

	public void assertAt() {
		assertThat(this.driver.getTitle()).contains("Home");
	}

	public static HomePage to(WebDriver driver) {
		driver.get("/");
		return to(driver, HomePage.class);
	}

	public static <T> T to(WebDriver driver, Class<T> clazz) {
		driver.get("/");
		return PageFactory.initElements(driver, clazz);
	}
}