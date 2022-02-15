package com.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.reporting.ReportLogger;

@Component
@Scope("prototype")
public class ExcelDTO {

	@Autowired
	private ReportLogger reportLogger;

	/**
	 * Gets the fillo conection.
	 *
	 * @param excelPath
	 *            the excel path
	 * @return the fillo conection
	 */
	public Connection getFilloConection(String excelPath) {
		Connection con = null;
		try {
			Fillo fillo = new Fillo();
			con = fillo.getConnection(excelPath);
			return con;
		} catch (Exception e) {
			reportLogger.logFail("Unable to make connection with Excel");
			throw new RuntimeException("Unable to make connection with Excel", e);
		}
	}

	/**
	 * Gets the record set.
	 *
	 * @param con
	 *            the con
	 * @param query
	 *            the query
	 * @return the record set
	 */
	public Recordset getRecordSet(Connection con, String query) {
		Recordset rsExcelData = null;
		try {
			rsExcelData = con.executeQuery(query);
			return rsExcelData;
		} catch (Exception e) {
			reportLogger.logFail("Unable to get record from excel workbook");
			throw new RuntimeException("Unable to get record from excel workbook", e);
		}
	}

	/**
	 * Insert update data inexcel.
	 *
	 * @param con
	 *            the con
	 * @param query
	 *            the query
	 */
	public void insertUpdateDataInexcel(Connection con, String query) {
		try {
			con.executeUpdate(query);
		} catch (Exception e) {
			reportLogger.logFail("Unable to insert/update data in excel workbook");
			throw new RuntimeException("Unable to insert/update data in excel workbook", e);
		}
	}

	/**
	 * Close excel connection.
	 *
	 * @param con
	 *            the con
	 */
	public void closeExcelConnection(Connection con) {
		try {
			con.close();
		} catch (Exception e) {
			reportLogger.logFail("Unable to close clonnection with excel");
			throw new RuntimeException("Unable to close clonnection with excel", e);
		}
	}

	/**
	 * Excel test data.
	 *
	 * @param sPath
	 *            the s path
	 * @param sSheetName
	 *            the s sheet name
	 * @return the object[][]
	 * @throws FilloException
	 *             the fillo exception
	 */
	public Object[][] excelTestData(String sPath, String sSheetName) throws FilloException {
		HashMap<String, String> testDataMap = null;
		Connection con = getFilloConection(sPath);
		int iRows;
		int row = 0;
		String sQuery = "Select * from " + sSheetName + " where RUN='Y'";
		Recordset rs = con.executeQuery(sQuery);
		iRows = rs.getCount();
		Object[][] data = new Object[iRows][1];
		List<String> lstFieldNames = rs.getFieldNames();
		while (rs.next()) {
			testDataMap = new HashMap<String, String>();
			for (String filedName : lstFieldNames) {
				// System.out.println(filedName+" "+rs.getField(filedName));
				testDataMap.put(filedName, rs.getField(filedName));
			}
			data[row][0] = testDataMap;
			row++;
		}
		return data;
	}
	
	public Object[][] getExcelData(String sPath, String sSheetName) throws FilloException {
		HashMap<String, String> testDataMap = null;
		Connection con = getFilloConection(sPath);
		int iRows;
		int row = 0;
		String sQuery = "Select * from " + sSheetName ;
		Recordset rs = con.executeQuery(sQuery);
		iRows = rs.getCount();
		Object[][] data = new Object[iRows][1];
		List<String> lstFieldNames = rs.getFieldNames();
		while (rs.next()) {
			testDataMap = new HashMap<String, String>();
			for (String filedName : lstFieldNames) {
				testDataMap.put(filedName.toUpperCase().replace(" ","_").replace("?", ""), rs.getField(filedName));
			}
			data[row][0] = testDataMap;
			row++;
		}
		return data;
	}

	/**
	 * Delete file.
	 *
	 * @param filePath the file path
	 */
	public void deleteFile(String filePath) {
		try {
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			reportLogger.logFail("Unable to delete file on Path : " + filePath);
			throw new RuntimeException("Unable to delete file on Path : " + filePath, e);
		}
	}
	
	/**
	 * Count excel rows.
	 *
	 * @param filePath the file path
	 * @return the int
	 */
	public int countExcelRows(String filePath)
	{
		try {
			int iRows = 0;
			Connection con = getFilloConection(filePath);
			String sQuery = "Select * from Transactions";
			Recordset rs = con.executeQuery(sQuery);
			iRows = rs.getCount();
			return iRows;
		} catch (FilloException e) {
			reportLogger.logFail("Unable to counts rows in excel : " + filePath);
			throw new RuntimeException("Unable to counts rows in excel : " + filePath, e);
		}
	}

}
