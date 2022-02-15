package com.platform;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.setup.BasePage;
import com.util.DriverUtil;

@Component
public class BusinessFlow extends BasePage{
	
	@Autowired
	private DriverUtil driverUtil;
	
	@Value("${appUrl}")
	private String gtiUrl;

	@Value("${envName}")
	private String envName;

	/**
	 * Open gti url.
	 */
	public void openGtiUrl() {
		try {
			driverUtil.launchURL(gtiUrl);
			driverUtil.getDriver().manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			reportLogger.logPass("GTI URL is opened successfully");
		} catch (Exception e) {
			reportLogger.logFail("Unable to open GTI URL.");	
			throw new RuntimeException("unable to open GTI URL", e);
		}
	}

}
