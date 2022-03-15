package com.operation;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.springframework.stereotype.Component;
import org.testng.asserts.SoftAssert;

import com.setup.BasePage;

// TODO: Auto-generated Javadoc
/**
 * The Class TableOperation.
 */
@Component
public class TableOperation extends BasePage {

	/** The soft assert. */
	SoftAssert softAssert = new SoftAssert();

	/** The table index. */
	public static int tableIndex = -1;

	/**
	 * Find table by column name.
	 *
	 * @param driver
	 *            the driver
	 * @param columnNames
	 *            the column names
	 * @return the web element
	 */
	public WebElement findTableByColumnName(WebDriver driver, String columnNames) {
		List<WebElement> tables = getTables(driver);
		WebElement table = getTableWithColumnNames(columnNames, tables);
		if (table == null)
			throw new NoSuchElementException("No such table found.");
		return table;
	}

	/**
	 * Gets the table with column names.
	 *
	 * @param expectedColumnNames
	 *            the expected column names
	 * @param tables
	 *            the tables
	 * @return the table with column names
	 */
	public WebElement getTableWithColumnNames(String expectedColumnNames, List<WebElement> tables) {
		WebElement table = null;
		Boolean isMatch = false;
		List<String> expColList = Pattern.compile(";").splitAsStream(expectedColumnNames).collect(Collectors.toList());

		for (int tableCount = 29; tableCount < tables.size(); tableCount++) {
			String actualColumnNames = getColumnNames(tables.get(tableCount));
			if (StringUtils.isNotEmpty(actualColumnNames)) {
				for (String expectedColumnName : expColList) {
					isMatch = isColumnNamesMatch(expectedColumnName, actualColumnNames);
					if (!isMatch) {
						break;
					}
				}
			}
			if (isMatch) {
				tableIndex = tableCount;
				table = tables.get(tableCount);
				break;
			}

		}
		return table;
	}

	/**
	 * Gets the column names.
	 *
	 * @param table
	 *            the table
	 * @return the column names
	 */
	private String getColumnNames(WebElement table) {
		String columnNames = null;
		List<WebElement> columns = table.findElements(By.tagName("td"));

		for (WebElement column : columns) {
			columnNames = columnNames + ";" + column.getText().trim();
		}
		return columnNames;

		/*
		 * for (int columnCount = 0; columnCount < columns.size();
		 * columnCount++) { columnNames = columnNames + ";" +
		 * columns.get(columnCount).getText().trim(); }
		 */
		// return columnNames;
	}

	/**
	 * Checks if is column names match.
	 *
	 * @param expectedColumns
	 *            the expected columns
	 * @param actualColumns
	 *            the actual columns
	 * @return the boolean
	 */
	private Boolean isColumnNamesMatch(String expectedColumns, String actualColumns) {
		Boolean isMatched = false;
		actualColumns = actualColumns.toLowerCase();
		expectedColumns = expectedColumns.toLowerCase();
		if (actualColumns != "" && actualColumns.contains(expectedColumns)) {
			isMatched = true;
		}
		return isMatched;
	}

	/**
	 * Gets the tables.
	 *
	 * @param driver
	 *            the driver
	 * @return the tables
	 */
	// get all tables
	private List<WebElement> getTables(WebDriver driver) {
		return driver.findElements(By.tagName("table"));
	}

	/**
	 * Gets the table column names linked hash map.
	 *
	 * @param table
	 *            the table
	 * @return the table column names linked hash map
	 */
	public LinkedHashMap<String, Integer> getTableColumnNamesLinkedHashMap(WebElement table) {
		try {
			LinkedHashMap<String, Integer> hashMapHeaders = new LinkedHashMap<String, Integer>();
			int intHeaderIndexCtr = 1;
			List<WebElement> columns = table.findElements(By.tagName("td"));

			for (WebElement column : columns) {
				hashMapHeaders.put(column.getText().trim(), intHeaderIndexCtr);
				intHeaderIndexCtr++;
			}
			return hashMapHeaders;
		} catch (Exception e) {
			reportLogger.logFail("Unable to create linkedHashMap of Table header");
			throw new RuntimeException("Unable to create linkedHashMap of Table header", e);
		}
	}

	/**
	 * Validate table value.
	 *
	 * @param driver
	 *            the driver
	 * @param columnNames
	 *            the column names
	 * @param tableIndexValue
	 *            the table index value
	 * @param errorType
	 *            the error type
	 * @param errorMessage
	 *            the error message
	 * @param tranType
	 *            the tran type
	 */
	public void validateTableValue(WebDriver driver, LinkedHashMap<String, Integer> columnNames, int tableIndexValue,
			String errorType, String errorMessage, String tranType) {
		try {
			List<WebElement> tables = driver.findElements(By.tagName("table"));
			for (int tableCount = tableIndexValue + 1; tableCount < tables.size(); tableCount++) {
				String expectedColumnValues = getColumnNames(tables.get(tableCount));
				if (isColumnNamesMatch("Export to Excel", expectedColumnValues)) {
					break;
				}
				int errorMessageIndex = Integer.parseInt(columnNames.get("Error Message").toString());
				int errorTypeIndex = Integer.parseInt(columnNames.get("Error Type").toString());
				int tranTypeIndex = Integer.parseInt(columnNames.get("Tran Type").toString());
				List<String> expColList = Pattern.compile(";").splitAsStream(expectedColumnValues)
						.collect(Collectors.toList());
				String errorMessageValue = expColList.get(errorMessageIndex);
				String errorTypeValue = expColList.get(errorTypeIndex);
				String tranTypeValue = expColList.get(tranTypeIndex);

				if (errorTypeValue.equals(errorType) && errorMessageValue.equals(errorMessage)
						&& tranTypeValue.equals(tranType)) {
					reportLogger.logPass("Correct error message displayed in table");
				} else {
					if (!errorTypeValue.equals(errorType)) {
						reportLogger.logFail("Expected this " + errorType + " coming " + errorTypeValue);
					}
					if (!errorMessageValue.equals(errorMessage)) {
						reportLogger.logFail("Expected this " + errorMessage + " coming " + errorMessageValue);
					}
					if (!tranTypeValue.equals(tranType)) {
						reportLogger.logFail("Expected this : " + tranType + " coming : " + tranTypeValue);
					}
					reportLogger.logFail("Unable to validate table values");
				}
			}
		} catch (NumberFormatException e) {
			reportLogger.logFail("Unable to validate table values");
			throw new RuntimeException("Unable to validate table values", e);
		}
	}

	public void validateExceptionTableValue(WebDriver driver, LinkedHashMap<String, Integer> columnNames,
			int tableIndexValue, String errorType, String errorMessage, String tranType) {
		try {
			String errorMessageValue = "";
			String errorTypeValue = "";
			String tranTypeValue = "";
			List<WebElement> tables = driver.findElements(By.tagName("table"));
			for (int tableCount = tableIndexValue + 1; tableCount < tables.size(); tableCount++) {
				String expectedColumnValues = getColumnNames(tables.get(tableCount));
				if (isColumnNamesMatch("Export to Excel", expectedColumnValues)) {
					reportLogger.logFail("Unable to validate table values");
					if (!errorTypeValue.equals(errorType)) {
						reportLogger.logFail("Expected this " + errorType + " coming " + errorTypeValue);
					}
					if (!errorMessageValue.equals(errorMessage)) {
						reportLogger.logFail("Expected this " + errorMessage + " coming " + errorMessageValue);
					}
					if (!tranTypeValue.equals(tranType)) {
						reportLogger.logFail("Expected this " + tranType + " coming " + tranTypeValue);
					}
					break;
				}
				int errorMessageIndex = Integer.parseInt(columnNames.get("Error Message").toString());
				int errorTypeIndex = Integer.parseInt(columnNames.get("Error Type").toString());
				int tranTypeIndex = Integer.parseInt(columnNames.get("Tran Type").toString());
				List<String> expColList = Pattern.compile(";").splitAsStream(expectedColumnValues)
						.collect(Collectors.toList());
				errorMessageValue = expColList.get(errorMessageIndex);
				errorTypeValue = expColList.get(errorTypeIndex);
				tranTypeValue = expColList.get(tranTypeIndex);

				if (errorTypeValue.equals(errorType) && errorMessageValue.equals(errorMessage)
						&& tranTypeValue.equals(tranType)) {
					reportLogger.logPass("Expected  : " + errorType + "--- Actual : " + errorTypeValue);
					reportLogger.logPass("Expected  : " + errorMessage + "--- Actual : " + errorMessageValue);
					reportLogger.logPass("Expected  : " + tranType + "--- Actual : " + tranTypeValue);
					reportLogger.logPass("Correct error message displayed in table");
					break;
				}
			}
		} catch (NumberFormatException e) {
			reportLogger.logFail("Unable to validate table values");
			throw new RuntimeException("Unable to validate table values", e);
		}

	}

	/**
	 * Validate transaction grid value.
	 *
	 * @param driver
	 *            the driver
	 * @param columnNames
	 *            the column names
	 * @param tableIndexValue
	 *            the table index value
	 * @param processInd
	 *            the process ind
	 */
	public void validateTransactionGridValue(WebDriver driver, LinkedHashMap<String, Integer> columnNames,
			int tableIndexValue, String processInd) {
		try {
			List<WebElement> tables = driver.findElements(By.tagName("table"));
			for (int tableCount = tableIndexValue + 1; tableCount < tables.size(); tableCount++) {
				String expectedColumnValues = getColumnNames(tables.get(tableCount));
				if (isColumnNamesMatch("Export to Excel", expectedColumnValues)) {
					break;
				}
				int errorMessageIndex = Integer.parseInt(columnNames.get("Process Ind").toString());
				List<String> expColList = Pattern.compile(";").splitAsStream(expectedColumnValues)
						.collect(Collectors.toList());
				String errorMessageValue = expColList.get(errorMessageIndex);
				softAssert.assertEquals(errorMessageValue, processInd);
				softAssert.assertAll();
			}
		} catch (NumberFormatException e) {
			reportLogger.logFail("Unable to validate table values");
			throw new RuntimeException("Unable to validate table values", e);
		}
	}

	public void validateValueFromTransactionGrid(WebDriver driver, LinkedHashMap<String, Integer> columnNames,
			int tableIndexValue, String processInd, String columnName) {
		try {
			List<WebElement> tables = driver.findElements(By.tagName("table"));
			for (int tableCount = tableIndexValue + 1; tableCount < tables.size(); tableCount++) {
				String expectedColumnValues = getColumnNames(tables.get(tableCount));
				if (isColumnNamesMatch("Export to Excel", expectedColumnValues)) {
					break;
				}
				int errorMessageIndex = Integer.parseInt(columnNames.get(columnName).toString());
				List<String> expColList = Pattern.compile(";").splitAsStream(expectedColumnValues)
						.collect(Collectors.toList());
				String errorMessageValue = expColList.get(errorMessageIndex);
				if (errorMessageValue.equals(processInd)) {
					reportLogger.logPass("Validated table values successfullyexpected : "+processInd+ " coming : "+errorMessageValue);
					break;
				} else {
					reportLogger.logFail("Unable to validate table values expected : "+processInd+ " coming : "+errorMessageValue);
				}
			}
		} catch (NumberFormatException e) {
			reportLogger.logFail("Unable to validate table values");
			throw new RuntimeException("Unable to validate table values", e);
		}
	}

	/**
	 * Validate transaction grid filtered values.
	 *
	 * @param driver
	 *            the driver
	 * @param xpathofFilteredValue
	 *            the xpathof filtered value
	 * @param tableIndexValue
	 *            the table index value
	 */
	public void validateTransactionGridFilteredValues(WebDriver driver, String xpathofFilteredValue,
			int tableIndexValue) {
		try {
			List<WebElement> tables = driver.findElements(By.tagName("table"));
			for (int tableCount = tableIndexValue + 1; tableCount < tables.size(); tableCount++) {
				String expectedColumnValues = getColumnNames(tables.get(tableCount));
				if (isColumnNamesMatch("Export to Excel", expectedColumnValues)) {
					break;
				}
				WebElement table = tables.get(tableCount);
				List<WebElement> columns = table.findElements(By.xpath(xpathofFilteredValue));
				if (columns.size() < 1) {
					reportLogger.logFail("Filtered Value not match as per our search");
					break;
				}
			}
			reportLogger.logPass("Filter funtionality " + xpathofFilteredValue + " work porperly");
		} catch (NumberFormatException e) {
			reportLogger.logFail("Filter funtionality doest not work porperly");
			throw new RuntimeException("Filter funtionality doest not work porperly", e);
		}
	}

	/**
	 * Count no of transactions.
	 *
	 * @param driver
	 *            the driver
	 * @param tableIndexValue
	 *            the table index value
	 * @return the int
	 */
	public int countNoOfTransactions(WebDriver driver, int tableIndexValue) {
		int noOfTransaction = 0;
		try {
			List<WebElement> tables = driver.findElements(By.tagName("table"));
			for (int tableCount = tableIndexValue + 1; tableCount < tables.size(); tableCount++) {
				String expectedColumnValues = getColumnNames(tables.get(tableCount));
				if (isColumnNamesMatch("Export to Excel", expectedColumnValues)) {
					break;
				}
				noOfTransaction++;
			}
			return noOfTransaction;
		} catch (NumberFormatException e) {
			reportLogger.logFail("Not able to count transactions on GUI");
			throw new RuntimeException("Not able to count transactions on GUI", e);
		}
	}

	/**
	 * Enter position in reorg.
	 *
	 * @param driver
	 *            the driver
	 * @param tableIndexValue
	 *            the table index value
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void enterPositionInReorg(WebDriver driver, int tableIndexValue) throws InterruptedException {
		List<WebElement> tables = driver.findElements(By.tagName("table"));
		WebElement table = tables.get(tableIndexValue + 1);
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		System.out.println("No of rows ::::" + rows.size());
		List<WebElement> cell = rows.get(0).findElements(By.tagName("td"));
		System.out.println("no of cells::" + cell.size());
		// WebElement descell = cell.get(3);
		for (int i = 0; i < cell.size(); i++) {
			System.out.println("i:::" + i);
			WebElement descell = cell.get(i);
			descell.click();
			// descell.sendKeys("100");

			/*
			 * WrapsDriver wrappedElement;
			 * 
			 * wrappedElement = (WrapsDriver) descell; JavascriptExecutor
			 * driver1 = (JavascriptExecutor) wrappedElement.getWrappedDriver();
			 * driver1.executeScript("arguments[0].innerHTML=arguments[1]",
			 * descell, "100"); Thread.sleep(1000);
			 */
			enterDataViakeyStrokes("100");
		}
	}

	/**
	 * Click check box.
	 *
	 * @param driver
	 *            the driver
	 * @param tableIndexValue
	 *            the table index value
	 * @param rowNumber
	 *            the row number
	 */
	public void clickCheckBox(WebDriver driver, int tableIndexValue, int rowNumber) {
		try {
			List<WebElement> tables = driver.findElements(By.tagName("table"));
			WebElement table = tables.get(tableIndexValue + rowNumber);
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			List<WebElement> cells = rows.get(0).findElements(By.tagName("td"));
			WebElement cell = cells.get(0);
			cell.click();
		} catch (Exception e) {
			reportLogger.logFail("Unable to select rows in GTI");
			throw new RuntimeException("unable to select rows in GTI", e);
		}
	}

	/**
	 * Highlight element.
	 *
	 * @param element
	 *            the element
	 */
	public void highlightElement(WebElement element) {
		WrapsDriver wrappedElement;
		for (int i = 0; i < 2; i++) {
			wrappedElement = (WrapsDriver) element;
			JavascriptExecutor driver = (JavascriptExecutor) wrappedElement.getWrappedDriver();
			driver.executeScript("arguments[0].setAttribute('style',arguments[1]);", element,
					"red;border:2px solid red;");
			driver.executeScript("arguments[0].setAttribute('style',arguments[1]);", element, "");

		}
	}

	/**
	 * Highlight element 1.
	 *
	 * @param element1
	 *            the element 1
	 */
	public void highlightElement1(String element1) {
		WebElement element = getWebElement(element1);
		WrapsDriver wrappedElement;
		for (int i = 0; i < 10; i++) {
			wrappedElement = (WrapsDriver) element;
			JavascriptExecutor driver = (JavascriptExecutor) wrappedElement.getWrappedDriver();
			driver.executeScript("arguments[0].setAttribute('style',arguments[1]);", element,
					"red;border:2px solid red;");
			driver.executeScript("arguments[0].setAttribute('style',arguments[1]);", element, "");

		}

	}

	/**
	 * Enter data viakey strokes.
	 *
	 * @param location
	 *            the location
	 * @param enterKey
	 *            the enter key
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void enterDataViakeyStrokes(String location, boolean... enterKey) throws InterruptedException {
		try {
			Robot robot = new Robot();
			Thread.sleep(2000);
			byte[] bytes = location.getBytes();
			for (byte b : bytes) {
				int code = b;
				if (code > 96 && code < 123) {
					code = code - 32;
					robot.delay(40);
					robot.keyPress(code);
					robot.keyRelease(code);
				} else if (code > 64 && code < 91) {
					robot.delay(40);
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(code);
					robot.keyRelease(code);
					robot.keyRelease(KeyEvent.VK_SHIFT);
				} else if (code == 92) {
					robot.delay(40);
					robot.keyPress(KeyEvent.VK_BACK_SLASH);
					robot.keyRelease(KeyEvent.VK_BACK_SLASH);
				} else if (code == 95) {
					robot.delay(40);
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_MINUS);
					robot.keyRelease(KeyEvent.VK_MINUS);
					robot.keyRelease(KeyEvent.VK_SHIFT);
				} else if (code == 58) {
					robot.delay(40);
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_SEMICOLON);
					robot.keyRelease(KeyEvent.VK_SEMICOLON);
					robot.keyRelease(KeyEvent.VK_SHIFT);
				} else if (code == 46) {
					robot.delay(40);
					robot.keyPress(KeyEvent.VK_PERIOD);
					robot.keyRelease(KeyEvent.VK_PERIOD);
				} else {
					robot.delay(40);
					robot.keyPress(code);
					robot.keyRelease(code);
				}
			}
			Thread.sleep(2000);
			if (enterKey.length > 0) {
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
			}

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
		} catch (AWTException e) {
			reportLogger.logFail("Unable to Send Key Strokes.");
			e.printStackTrace();
		}
	}

}
