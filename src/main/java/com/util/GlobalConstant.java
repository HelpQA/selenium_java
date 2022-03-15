package com.util;

public interface GlobalConstant {
	
	String FS = System.getProperty("file.separator");
	
	public static final String TEST_RESOURCES_DIR = "src" + FS + "test" + FS + "resources" + FS;
	
	public static final String BASE_DIR = System.getProperty("user.dir") + FS;
	
	public static final String PROPERTIES_FILEPATH = TEST_RESOURCES_DIR + "genweb-qa.local.properties";
	
	public static final String SMOKE_TEST_REPORTS_PATH = "\\\\file4\\develop\\wmc_apps\\Geneva\\" + "Reports" + FS ;
	
	//public static final String SCREENSHOTS_PATH = "\\\\file4\\develop\\wmc_apps\\Geneva\\" + "Reports" + FS + "Screenshots" + FS;
	public static final String SCREENSHOTS_PATH = "c:\\Screenshots";
	
	public static final String TEST_FLOW_EXCEL_INCLUDE_INDICATOR = "Yes";

	public  String SMOKETESTDATA_FILEPATH =  "src//test//resources//SmokeTest_Data.xlsx";
	
	public  String REFDATAREGRESSION_FILEPATH =  "src//test//resources//Genweb_TestDataSheets//RefDataReqRegressionTest.xlsx";
	
	public  String GTISMOKETESTDATA_FILEPATH = "src//test//resources//GTISmokeTest_Data.xlsx";

	public  String TESTDATA_SHEETNAME = "SQLResults";

	public static final String PENDING = "PENDING";
	
	public static final String INPROGRESS = "IN PROGRESS";

	public static final String STATUS = "STATUS";

	public static final String PROCESSING = "PROCESSING";

	public static final String EMAIL_SIGNATURE = "<br><font>Thanks and Regards</font><br><font>Akash Chugh</font><br>";

	public static final String MAIL_INIT_DATA = "<font face=verdana>Hi All,<font><br><br><font>Please find the execution details of testcases :<font><br><br>";

	public static final String EMAIL_SUBJECT = ": Test Case Execution Report for Cycle :";

	public static final String HARPSMOKETESTDATA_FILEPATH = "src//test//resources//HarpSmokeTest_Data.xlsx";

	public static final String ERROR_MSG = "You have not provided a valid";

	public static final String REFDATAFUNCTIONAL_FILEPATH = "src//test//resources//Genweb_TestDataSheets//RefDataReqFunctionalTest.xlsx";

	public static final String SECOVERRIDE_FILEPATH = "src//test//resources//Genweb_TestDataSheets//SecOverrideInputData.xlsx";

}
