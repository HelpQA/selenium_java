package com.reporting;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.NetworkMode;

/**
 * The Class ReportManager.
 */
@Component
public class ReportManager {

	public ExtentReports extent;
	
	public String reportPath;

	@Value("${envName}")
	private String envName;

	private ConcurrentHashMap<Long, ExtentTest> testCache = new ConcurrentHashMap<Long, ExtentTest>();
	
	public String getReportPath() {
		return this.reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}


	/**
	 * Gets the test.
	 *
	 * @param testName
	 *            the test name
	 * @return the test
	 */
	public ExtentTest getTest(long testId) {
		return testCache.get(testId);
	}

	/**
	 * Gets the extent.
	 *
	 * @return the extent
	 */
	public ExtentReports getExtent() {
		return this.extent;
	}

	/**
	 * Gets the reporter.
	 *
	 * @param filePath-->
	 *            the path of the reports to be generated
	 * @return the reporter
	 */
	public void init(String filePath) {
		this.extent = new ExtentReports(filePath, true, NetworkMode.OFFLINE);
		this.extent.addSystemInfo("Selenium Version", "3.0.0");
		this.extent.addSystemInfo("Env Name", envName);
	}

	/**
	 * Starts test.
	 * 
	 * @param testCaseName
	 */
	/**
	 * @param testCaseName
	 * @return
	 */
	public ExtentTest startTest(String testCaseName) {
		ExtentTest test = extent.startTest(testCaseName);
		test.assignAuthor(System.getProperty("user.name"));
		test.assignCategory("Smoke test - " + envName);
		//ThreadLocalContext.putTestName(testCaseName);
		testCache.put(Thread.currentThread().getId(), test);
		return test;
	}

	/**
	 * End test.
	 *
	 * @param test
	 *            the test
	 */
	public void endTest() {
		this.extent.endTest(testCache.get(Thread.currentThread().getId()));
	}

	/**
	 * Close reports.
	 */
	public void closeReports() {
		this.extent.flush();
		this.extent.close();
	}
}
