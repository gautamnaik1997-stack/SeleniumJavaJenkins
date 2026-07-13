package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	
	@FindBy(xpath="//span[normalize-space()='My Account']") WebElement lnktext_MyAccount;
	@FindBy(how = How.XPATH, using ="//a[normalize-space()='Login']") WebElement lnktext_Login;
	
	public void clickMyAccount() {
		lnktext_MyAccount.click();
	}
	
	public void clickLogin() {
		lnktext_Login.click();
	}
}	
