package com.util;

import java.lang.reflect.Method;
import java.sql.SQLException;

import org.testng.annotations.DataProvider;

import com.custom.annotations.Parameterize;

public class TestDataProvider {
	public static String GTItestcaseName;

	@DataProvider(name = "ExcelTestData")
	public static Object[][] getExcelTestData(Method m) throws SQLException, Exception {
		String fileName = m.getAnnotation(Parameterize.class).filePath();
		String sheetName = m.getAnnotation(Parameterize.class).sheetName();
		String testCaseName = m.getName();
		ExcelUtil exl = new ExcelUtil();
		exl.setTestCaseName(testCaseName);
		exl.setExcelFile(fileName, sheetName);
		return exl.excelTestData();
	}

	@DataProvider(name = "ExcelTestDataDTO")
	public static Object[][] getExcelTestDataDTO(Method m) throws SQLException, Exception {
		String fileName = m.getAnnotation(Parameterize.class).filePath();
		String sheetName = m.getAnnotation(Parameterize.class).sheetName();
		GTItestcaseName = m.getName();
		ExcelDTO exl = new ExcelDTO();
		if (sheetName.equals("TRUE")) {
			return exl.excelTestData(fileName, GTItestcaseName);
		} else {
			return exl.excelTestData(fileName, sheetName);
		}
	}

}
