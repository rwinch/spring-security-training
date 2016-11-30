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
public class AccountPage {
	WebDriver driver;
	WebElement balance;

	public AccountPage(WebDriver driver) {
		this.driver = driver;
	}

	public void assertBalance(double balance) {
		assertThat(this.balance.getText()).endsWith(" "+ balance);
	}

	public void assertAt() {
		assertThat(this.driver.getTitle()).contains("Account");
	}

	public TransferForm transferForm() {
		TransferForm form = new TransferForm();
		PageFactory.initElements(driver, form);
		return form;
	}

	public static LoginPage init(WebDriver driver) {
		return PageFactory.initElements(driver, LoginPage.class);
	}

	public class TransferForm {
		WebElement ammount;
		@FindBy(css="input[type=submit]")
		WebElement submit;

		public TransferForm ammount(double ammount) {
			this.ammount.clear();
			this.ammount.sendKeys(ammount+"");
			return this;
		}

		public <T> T submit(Class<T> clazz) {
			submit.click();
			return PageFactory.initElements(driver, clazz);
		}
	}

	public static AccountPage to(WebDriver driver) {
		return to(driver, AccountPage.class);
	}

	public static <T> T to(WebDriver driver, Class<T> clazz) {
		driver.get("http://localhost/account/");
		return PageFactory.initElements(driver, clazz);
	}
}