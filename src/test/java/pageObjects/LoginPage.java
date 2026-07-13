package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage{

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	By email_txt = By.xpath("//input[@id='input-email']");
	By password_txt = By.xpath("//input[@id='input-password']");
	By btn_Login = By.xpath("//input[@value='Login']");
	
	public void setEmail(String email) {
		driver.findElement(email_txt).sendKeys(email);
	}
	
	public void setPassword(String password) {
		driver.findElement(password_txt).sendKeys(password);
	}
	public void clickLoginBtn() {
		driver.findElement(btn_Login).click();
	}

}
