package com.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ExcelUtil {
	
	public static HashMap<String, String> mapWriteColNameColVal = new HashMap<String, String>();

	private HashMap<String, Integer> mapHeaderNameColNum = new HashMap<String, Integer>();
	
	private int rowNumDataWrite;
	
	private static List<Integer> excelrow = new ArrayList<Integer>();

	private XSSFSheet sheet;

	private XSSFWorkbook workBook;

	private XSSFCell cell;

	private XSSFRow row;

	private String testCaseName;
	
	private FileInputStream inputStream;

	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	public void setExcelFile(String path, String sheetName) {
		try {
			inputStream = new FileInputStream(path);
			this.workBook = new XSSFWorkbook(inputStream);
			this.sheet = workBook.getSheet(sheetName);
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			
		} catch (Exception e) {
			
		}
	}
	
	public static void renameSheet(String path,String oldSheetName,String newSheetName) {
		FileInputStream inputStream;
		HSSFWorkbook workBook;
		try {
			inputStream = new FileInputStream(path);
			System.out.println();
			workBook = new HSSFWorkbook(inputStream);
			workBook.setSheetName( workBook.getSheetIndex(oldSheetName),newSheetName);
			inputStream.close();
			FileOutputStream outputStream = new FileOutputStream(path);
			workBook.write(outputStream);
			workBook.close();
			
		} catch (Exception e) {
			throw new RuntimeException("Getting an exception while renaming the excel sheet. ", e);
		}
	}
	
	public String getCellData(int rowNum, int columnNum) {
		String cellData = null;
		row = sheet.getRow(rowNum);
		try {
			if (row != null) {
				this.cell = sheet.getRow(rowNum).getCell(columnNum);
				cellData = this.cell.getStringCellValue();
			}
		} catch (Exception e) {
			
		}
		return cellData;
	}
	
	public void setTestDataColValue(String path,int rowNumber) {
		String colName = "";
		String colValue = "";
		for (Map.Entry<String, String> writeColPair : mapWriteColNameColVal.entrySet()) {
			colName = writeColPair.getKey().trim();
			colValue = writeColPair.getValue().trim();
			setCellData(path,colValue,rowNumDataWrite,mapHeaderNameColNum.get(colName));
		}
	}

	public void setCellData(String path, String cellData, int rowNum, int columnNum) {
		try {
			this.row = sheet.getRow(rowNum);
			this.cell = this.row.getCell(columnNum, Row.RETURN_BLANK_AS_NULL);
			if (this.cell == null) {
				this.cell = row.createCell(columnNum);
			}
			cell.setCellValue(cellData);
			inputStream.close();
			FileOutputStream fileOut = new FileOutputStream(path);
			workBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			
		}
	}

	public int getRowCount() {
		return this.sheet.getPhysicalNumberOfRows();
	}

	public int getColumnCount(int row) {
		return this.sheet.getRow(row).getPhysicalNumberOfCells();
	}

	public int getHeaderCount(int row) {
		int colCount = sheet.getRow(row).getPhysicalNumberOfCells();
		int intCounter;
		for (intCounter = 0; intCounter < colCount; intCounter++) {
			if (getCellData(row, intCounter) == null) {
				break;
			}
		}
		return intCounter;
	}
	
	public Object[][] excelTestData() {

		if (excelrow.size() != 0) {
			excelrow.removeAll(excelrow);
		}
		try {
			int startRowNumber = 1;
			int col = getHeaderCount(startRowNumber -1) - 1;

			// Loop to add the header names and its column index in the hashmap
			for (int intCounter = 1; intCounter <= col; intCounter++) {
				mapHeaderNameColNum.put(getCellData(0, intCounter), intCounter);
			}

			// check for the rows having yes as run mode and test case name same
			// as the current test case which we are running
			while (getCellData(startRowNumber, 0) != null) {
				if (com.util.GlobalConstant.TEST_FLOW_EXCEL_INCLUDE_INDICATOR
						.equalsIgnoreCase(getCellData(startRowNumber, 0))) {
					if (this.testCaseName.equalsIgnoreCase(getCellData(startRowNumber, 1))) {
						// this will add the row number to the below list
						excelrow.add(startRowNumber);
					}
				}
				startRowNumber++;
			}

			// column size for the excel to get the run status and the test case name
			System.out.println("Fetched row size" + excelrow.size());
			if (col > 1 && startRowNumber >= 1) {
				Map<String, String> mapColumnNameData;// create an instance of the map to
											// store the value of column and its data

				Object[][] testData = new Object[excelrow.size()][1];
				String colName = null;
				String colDataValue = null;
				// Loop to fetch the input values from the excel and store in 
				// an array list
				for (int rowCount = 0; rowCount < excelrow.size(); rowCount++) {
					mapColumnNameData = new HashMap<String, String>();
					for (int colCounter = 0; colCounter < col;colCounter++) {
						colName = getCellData(0, colCounter + 1);
						colDataValue= getCellData(excelrow.get(rowCount), colCounter + 1);
						System.out.println(colName + "==" + colDataValue);
						mapColumnNameData.put(colName, colDataValue);
					}
					testData[rowCount][0] = mapColumnNameData;
				}
				
				System.out.println("Fetched the test data from the excel and stored in the array list exlData");
				return testData;
			} else {
				System.out.println("Data not present in excel sheet, no of columns are =" + col);
			}
		} catch (Exception e) {
			
		}
		return null;// return first element of a string object if
								// nothing is fetched from the input sheet
	}
}
