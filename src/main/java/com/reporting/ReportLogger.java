package com.reporting;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import com.db.ConnectionConfiguration;
import com.log.LoggerFactory;
import com.log.MyLogger;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.DateUtil;
import com.util.DriverUtil;
import com.util.GlobalConstant;

/**
 * The Class ReportLogger.
 */
@Component
public class ReportLogger {

	@Autowired
	private DriverUtil driver;

	@Autowired
	private ReportManager reportManager;
	
	private final static MyLogger LOG = LoggerFactory.getLogger(ConnectionConfiguration.class);
	
	private ExtentTest getTest() {
		return reportManager.getTest(Thread.currentThread().getId());
	}

	/**
	 * Log pass.
	 *
	 * @param desc
	 *            the desc
	 */
	public void logPass(String desc) {
		Assert.assertTrue(true, desc);
		getTest().log(LogStatus.PASS, desc);
		LOG.info(desc);
	}

	/**
	 * Log pass.
	 *
	 * @param desc
	 *            the desc
	 * @param scrrenshotReqd-->
	 *            pass boolean as per the scrrenshot required
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws Exception
	 *             the exception
	 */
	public void logPass(String desc, Boolean scrrenshotReqd){
		LOG.info(desc);
		Assert.assertTrue(true, desc);
		getTest().log(LogStatus.PASS, desc);
		if (scrrenshotReqd) {
			getTest().log(LogStatus.PASS, getTest().addScreenCapture(captureScreenshot()));
		}
	}

	/**
	 * Log fail.
	 *
	 * @param desc
	 *            the desc
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws Exception
	 *             the exception
	 */
	public void logFail(String desc) {
		LOG.error(desc);
		getTest().log(LogStatus.FAIL, desc);
		getTest().log(LogStatus.FAIL, getTest().addScreenCapture(captureScreenshot()));
		throw new RuntimeException(desc);
	}

	/**
	 * Log info.
	 *
	 * @param desc
	 *            the desc
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws Exception
	 *             the exception
	 */
	public void logInfo(String desc)  {
		LOG.info(desc);
		getTest().log(LogStatus.INFO, desc);
	}

	/**
	 * Capture screenshot.
	 *
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws Exception
	 *             the exception
	 */
	private String captureScreenshot() {
		String screenshotsPath = GlobalConstant.SCREENSHOTS_PATH + "Screenshot_" + DateUtil.getCurrentTimeStamp()
				+ ".png";
		try {
			// WebDriver driver = DriverClass.getDriver();
			File srcFile = ((TakesScreenshot) driver.getDriver()).getScreenshotAs(OutputType.FILE);
			File targetFile = new File(screenshotsPath);
			FileUtils.copyFile(srcFile, targetFile);
		} catch (Exception e) {
			
		}
		return screenshotsPath;
	}
}
