package sample.webdriver.pages;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import sample.data.MyUser;

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

	public void assertGreetingFor(MyUser myUser) {
		String greeting = greetingFor(myUser);
		assertThat(this.greeting.getText()).isEqualTo(greeting);
	}
	public void assertAt() {
		assertThat(this.driver.getTitle()).contains("Home");
	}

	public static String greetingFor(MyUser myUser) {
		return "Hello " + myUser.getFirstName() + " " + myUser.getLastName();
	}

	public static HomePage to(WebDriver driver) {
		return to(driver, HomePage.class);
	}

	public static <T> T to(WebDriver driver, Class<T> clazz) {
		driver.get("http://localhost/");
		return PageFactory.initElements(driver, clazz);
	}
}