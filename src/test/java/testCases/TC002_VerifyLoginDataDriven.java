package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utils.DataProviders;

public class TC002_VerifyLoginDataDriven extends BaseClass{
	
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class)
	public void validateLoginDataDriven(String username, String password, String expectedResult) {
		try {
		logger.info("********** Starting TC_validateLoginDataDriven **********");
		
		HomePage hp = new HomePage(getDriver());
		logger.info("********** Clicking Login button from dropdown **********");
		hp.clickMyAccount();
		hp.clickLogin();
		
		LoginPage lp = new LoginPage(getDriver());
		logger.info("********** Setting email and password for login **********");
		lp.setEmail(username);
		lp.setPassword(password);
		lp.clickLoginBtn();
		Thread.sleep(3000);
		
		MyAccountPage map = new MyAccountPage(getDriver());
		boolean actualStatus = map.confirmMsg();
		boolean expectedStatus = true;
		if(expectedResult.equalsIgnoreCase("valid")) {
			if(actualStatus==expectedStatus) {
				map.clickLogout();
				Assert.assertTrue(true);
			}
			else
				Assert.assertTrue(false);
		}
		
		if(expectedResult.equalsIgnoreCase("invalid")) {
			if(actualStatus==expectedStatus) {
				map.clickLogout();
				Assert.assertTrue(false);
			}
			else
				Assert.assertTrue(true);
		}
		}catch(Exception e) {
			logger.debug("TestExecution failed", e);
			Assert.fail(e.getMessage());
		}finally {
			logger.info("********** Ending TC_validateLoginDataDriven **********");
		}
	}

}
