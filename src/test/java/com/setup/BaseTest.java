package com.setup;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestResult;

import com.log.LoggerFactory;
import com.log.MyLogger;
import com.reporting.ReportManager;
import com.util.DriverUtil;
import com.util.ExcelUtil;
import com.util.MailUtil;

@ContextConfiguration(locations = { "classpath:config.xml" })
public class BaseTest extends AbstractTestNGSpringContextTests {

	@Autowired
	public DriverUtil driverUtil;
	
	@Autowired
	private MailUtil mailUtil;

	@Autowired
	protected ExcelUtil excelUtil;

	@Value("${sendEmail}")
	private String sendEmail;

	@Autowired
	protected ReportManager reportManager;
	
	boolean testSuiteSuccessFlag = true;
	
	private final static MyLogger LOG = LoggerFactory.getLogger(BaseTest.class);

	public void initReport(String reportPath) {
		reportManager.init(reportPath);
		reportManager.setReportPath(reportPath);
	}

	public void afterSuite() {
		String testSuiteExecutionStatus;
		reportManager.closeReports();
		if (testSuiteSuccessFlag) {
			testSuiteExecutionStatus = "SUCCESS";
		} else {
			testSuiteExecutionStatus = "FAILURE";
		}
		if ( sendEmail.equalsIgnoreCase("Yes") || sendEmail.equalsIgnoreCase("Y") ) {
			mailUtil.sendEmail(testSuiteExecutionStatus);
		}
	}

	public void setUp(Method methodInstance) {
		reportManager.startTest(methodInstance.getName());
		driverUtil.launchBrowser();
	}

	public void tearDown(Method methodInstance, ITestResult result) {
		if (result.getStatus() == 2) {
			testSuiteSuccessFlag = false;
		}
		reportManager.endTest();
		driverUtil.getDriver().quit();
		driverUtil.getDriver().close();
	}

}
