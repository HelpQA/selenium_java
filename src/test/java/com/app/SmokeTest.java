/*
 * 
 */
package com.app;

import java.lang.reflect.Method;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.custom.annotations.Parameterize;
import com.platform.BusinessFlow;
import com.setup.BaseTest;
import com.util.DateUtil;
import com.util.TestDataProvider;



public class SmokeTest extends BaseTest {

	@Value("${APP_ENV}")
	public String envName;
	
	@Autowired
	private BusinessFlow businessFlow;
	
	@PostConstruct
	public void init() {
		System.out.println("init method");
		//String reportPath = "Report_" + DateUtil.getCurrentTimeStamp() + ".html";
		String reportPath = System.getProperty("user.dir") + "\\src\\test\\resources\\reports\\" + "_Report_"
				+ DateUtil.getCurrentTimeStamp() + ".html";
		super.initReport(reportPath);
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		System.out.println("after  suite method");
		super.afterSuite();
	}

	@BeforeMethod(alwaysRun = true)
	public void setUp(Method m) {
		System.out.println("setup method");
		super.setUp(m);
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(Method m, ITestResult result) {
		super.tearDown(m, result);
	}

	@Parameterize(filePath = "src//test//resources//Input_Data.xlsx", sheetName = "SQLResults")
	@Test(enabled = true, dataProvider = "ExcelTestDataDTO", dataProviderClass = TestDataProvider.class, description = "GEN-5025:Add Sell Transaction :Check Specified location account is invalid for subaccount")
	public void searchSeleniumGoogle(HashMap<String, String> testData){
		System.out.println("test started sahil chaudhary");
		System.out.println("url ---------" + System.getProperty("user.dir"));
		businessFlow.openGtiUrl();
		//driverUtil.launchURL(url);
		//driverUtil.launchURL(url);
	}

}
