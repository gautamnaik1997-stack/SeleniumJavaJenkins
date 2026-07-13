package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	
private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
public Logger logger;
public Properties p;

public WebDriver getDriver() {
	return driver.get();
}
	
	@Parameters({"OS", "Browser"})
	@BeforeClass(alwaysRun = true)
	public void setup(String os, String br) throws IOException {
		logger = LogManager.getLogger(this.getClass());	
		
		String environment = System.getProperty("environment", "QA");
		String configFile = System.getProperty("user.dir")+ "\\src\\test\\resources\\config\\" + environment.toLowerCase() + ".properties";
		p=new Properties();
		FileReader file = new FileReader(configFile);
		p.load(file);
		
		String executionType = System.getProperty("executionType", p.getProperty("executiontype"));
		String browser = System.getProperty("browser", br);
		if(browser==null || browser.trim().isEmpty()) {
			browser = "Chrome";
		}
		boolean Headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
		
		ChromeOptions chromeOptions = new ChromeOptions();
		EdgeOptions edgeOptions = new EdgeOptions();
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		
		// Chrome
		chromeOptions.addArguments("--disable-gpu");
		chromeOptions.addArguments("--disable-dev-shm-usage");
		chromeOptions.addArguments("--no-sandbox");
		
		// Edge
		edgeOptions.addArguments("--disable-gpu");
		edgeOptions.addArguments("--disable-dev-shm-usage");
		edgeOptions.addArguments("--no-sandbox");
		
		if(Headless==true) {
			
			chromeOptions.addArguments("--headless=new");
			chromeOptions.addArguments("--window-size=1920,1080");
			
			edgeOptions.addArguments("--headless=new");
			edgeOptions.addArguments("--window-size=1920,1080");	
			
			firefoxOptions.addArguments("--headless");
			firefoxOptions.addArguments("--height=1920");
			firefoxOptions.addArguments("--width=1080");
		}
		
		if(executionType.equalsIgnoreCase("remote")) {
			String huburl = "http://localhost:4444";
			switch(browser.toLowerCase()) {
			case "chrome": chromeOptions.setPlatformName(os.toLowerCase());
			driver.set(new RemoteWebDriver(new URL(huburl), chromeOptions));break;
						   
			case "edge": edgeOptions.setPlatformName(os.toLowerCase());
			driver.set(new RemoteWebDriver(new URL(huburl), edgeOptions));break;
			case "firefox": firefoxOptions.setPlatformName(os.toLowerCase());
			driver.set(new RemoteWebDriver(new URL(huburl), firefoxOptions));break;
			default: throw new IllegalArgumentException("Inavlid browser: "+ br);
			}
		}
		
		if(executionType.equalsIgnoreCase("local")) {
			switch(browser.toLowerCase()) {
			case "chrome": driver.set(new ChromeDriver(chromeOptions));break;
			case "edge": driver.set(new EdgeDriver(edgeOptions));break;
			case "firefox": driver.set(new FirefoxDriver(firefoxOptions)); break;
			default: throw new IllegalArgumentException("Inavlid browser: "+ br);
			}
		}
		
		if (getDriver() == null) {
		    throw new RuntimeException(
		        "WebDriver was not initialized. Browser = " + browser +
		        ", ExecutionType = " + executionType
		    );
		}
		
		
		driver.get().manage().deleteAllCookies();
		driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		if(Headless==false) {
			driver.get().manage().window().maximize();
		}
		driver.get().get(p.getProperty("appUrl"));
	}
	
	@AfterClass(alwaysRun = true)
	public void teardown(){
	if(driver.get()!=null) {
		driver.get().quit();
		driver.remove();
		}
	}
	
	public static String captureScreenshot(String cname) throws IOException {
		String timeStamp = new SimpleDateFormat("yyyy.mm.dd.HH.mm.ss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver.get();
		String path = System.getProperty("user.dir")+"//screenshots//" + cname + "_" + timeStamp +".png";
		File sourceFile = ts.getScreenshotAs(OutputType.FILE);
		File targetFile = new File(path);
		FileUtils.copyFile(sourceFile, targetFile);
		return path;
	}

}
