package com.platform;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.HomePage;
import com.operation.ButtonOperation;
import com.operation.TextBoxOperation;
import com.setup.BasePage;
import com.util.DriverUtil;

@Component
public class BusinessFlow extends BasePage{
	
	@Autowired
	private DriverUtil driverUtil;
	
	@Autowired
	private HomePage homePage;
	
	@Autowired
	private ButtonOperation buttonOperation;
	
	@Autowired
	private TextBoxOperation testBoxOperations;
	
	@Value("${appUrl}")
	private String gtiUrl;

	@Value("${envName}")
	private String envName;

	public void openGtiUrl() {
		try {
			driverUtil.launchURL(gtiUrl);
			driverUtil.getDriver().manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);			
			reportLogger.logPass("Google URL is opened successfully");
		} catch (Exception e) {
			reportLogger.logFail("Unable to open Google URL.");	
			throw new RuntimeException("unable to open Google URL", e);
		}
	}
	
	public void enterData(String text) {		
		try {
			buttonOperation.click(homePage.getTxtSearchBox());
			testBoxOperations.sendKeys(homePage.getTxtSearchBox(), text);
			buttonOperation.click(homePage.getBtnGoogleSearch());		
			reportLogger.logPass("Data Entered successfully");
		}catch (Exception e) {
			reportLogger.logFail("Unable to enter data");	
			throw new RuntimeException("unable to enter data", e);
		}
		
	}

}
