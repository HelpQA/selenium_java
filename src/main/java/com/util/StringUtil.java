package com.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reporting.ReportLogger;

@Component
public class StringUtil {
	
	@Autowired
	private ReportLogger reportLogger;

	/**
	 * performs the concat operation for Object Varargs
	 * @param objects
	 * @return concatenated String of objects
	 */
	public static String concatenate(final Object... objects) {
		String concatenatedString = null;
		if (null != objects) {
			StringBuilder concatenatedStringBuilder = null;
			for (final Object object : objects) {
				if (null == concatenatedStringBuilder) {
					concatenatedStringBuilder = new StringBuilder();
				}
				if (null != object) {
					concatenatedStringBuilder.append(object);
				}
			}
			concatenatedString = concatenatedStringBuilder.toString();
		}
		return concatenatedString;
	}

	/**
	 * performs the concat operation for String Varargs
	 * @param objects
	 * @return concatenated String of strings
	 */
	public static String concatenate(final String... strings) {
		String concatenatedString = null;
		if (null != strings) {
			StringBuilder concatenatedStringBuilder = null;
			for (final String string : strings) {
				if (null == concatenatedStringBuilder) {
					concatenatedStringBuilder = new StringBuilder();
				}
				if (null != string) {
					concatenatedStringBuilder.append(string);
				}
			}
			concatenatedString = concatenatedStringBuilder.toString();
		}
		return concatenatedString;
	}
	
	public boolean verifyData(String sActual, String sExpected) {
		boolean blnVerificationFlag = false;
		if (sActual.trim().toUpperCase().equals(sExpected.trim().toUpperCase())) {
			reportLogger.logPass("Verify that Actual value= " + sActual + " matches with expected value = " + sExpected);
			blnVerificationFlag = true;
		} else {
			reportLogger.logFail("Actual value= " + sActual + "doesnot match with the expected value = " + sExpected);
		}
		return blnVerificationFlag;
	}

	public boolean verifyDataContains(String sActual, String sExpected) {
		boolean blnVerificationFlag = false;
		if (sActual.trim().toUpperCase().contains(sExpected.trim().toUpperCase())) {
			reportLogger.logPass("Verify that Actual value= " + sActual + " matches with expected value = " + sExpected);
			blnVerificationFlag = true;
		} else {
			reportLogger.logFail("Actual value= " + sActual + " doesnot match with the expected value = " + sExpected);
		}
		return blnVerificationFlag;
	}
}
