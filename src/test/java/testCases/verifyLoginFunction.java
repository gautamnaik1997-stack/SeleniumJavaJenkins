package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class verifyLoginFunction extends BaseClass{

	@Test
	public void validateLogin() {
		try {
		logger.info("********** Starting TC_validateLogin **********");
		
		HomePage hp = new HomePage(getDriver());
		logger.info("********** Clicking Login button from dropdown **********");
		hp.clickMyAccount();
		hp.clickLogin();
		
		LoginPage lp = new LoginPage(getDriver());
		logger.info("********** Setting email and password for login **********");
		lp.setEmail(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLoginBtn();
		
		MyAccountPage map = new MyAccountPage(getDriver());
		boolean actualStatus = map.confirmMsg();
		boolean expectedStatus = true;
		Assert.assertEquals(actualStatus,expectedStatus,"Status Mismatched");
		logger.info("********** Successful Login **********");
		}catch(Exception e) {
			logger.debug("TestExecution failed", e);
			Assert.fail(e.getMessage());
		}finally {
			logger.info("********** Ending TC_validateLogin **********");
		}
	}
	
}
