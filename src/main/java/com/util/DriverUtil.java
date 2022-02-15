package com.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The Class DriverClass.
 */
@Component
public class DriverUtil {

	@Value("${browserName}")
	private String browserName;

	private WebDriver driver;

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Gets the driver.
	 *
	 * @return the driver
	 */
	public WebDriver getDriver() {
		return this.driver;
	}


	/**
	 * Launch browser.
	 *
	 * @param browserType the browser type
	 * @return the web driver
	 */
	public WebDriver launchBrowser() {
		switch (browserName.trim().toUpperCase()) {
		case "FF":
			this.driver = new FirefoxDriver();
			break;
		case "IE":
			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir") + "\\Drivers\\IEDriverServer.exe");
			DesiredCapabilities capabilitiesIE = DesiredCapabilities.internetExplorer();
			capabilitiesIE.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			this.driver = new InternetExplorerDriver(capabilitiesIE);
			this.driver.manage().window().maximize();
			break;
		case "CHROME":
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\Drivers\\chromedriver.exe");
			ChromeOptions chrome = new ChromeOptions();
			chrome.addArguments("test-type");
			chrome.addArguments("start-maximized");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, chrome);
			this.driver = new ChromeDriver(capabilities);
			break;
		}
		return this.driver;
	}
	
	public void launchURL(String url) {
		this.driver.get(url);
	}


}
