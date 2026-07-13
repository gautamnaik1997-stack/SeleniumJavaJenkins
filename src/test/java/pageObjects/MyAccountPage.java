package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {

	public MyAccountPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//h2[normalize-space()='My Account']") WebElement label_MyAccount; 
	@FindBy(xpath="//a[@class='list-group-item'][normalize-space()='Logout']") WebElement lnk_LogOut; 
	
	public boolean confirmMsg() {
		boolean status = label_MyAccount.isDisplayed();
		return status;
	}
	public void clickLogout() {
		lnk_LogOut.click();
	}

}
