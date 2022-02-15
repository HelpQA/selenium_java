package com.setup;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.reporting.ReportLogger;
import com.util.DriverUtil;

@Component
public class BasePage {

	@Autowired
	protected DriverUtil driverUtil;

	@Autowired
	protected ReportLogger reportLogger;

	@Value("${largeWaitSec}")
	private int largeWaitSec;

	public WebElement getWebElement(String attribute) {
		try {
			waitForElementToBeVisible(getLocator(attribute)); 
		} catch (Exception e) {
			reportLogger.logFail("Failed to return webelement for object--> " + attribute);
			throw new RuntimeException("Failed to return webelement for object--> " + attribute, e);
		}
		return driverUtil.getDriver().findElement(getLocator(attribute));
	}

	public By getLocator(String attribute) {
		try {
			String strLocator = attribute.split("-->")[0].trim();
			String strLocatorValue = attribute.split("-->")[1].trim();
			if (strLocator.toLowerCase().equals("id"))
				return By.id(strLocatorValue);
			else if (strLocator.toLowerCase().equals("xpath"))
				return By.xpath(strLocatorValue);
			else if (strLocator.toLowerCase().equals("name"))
				return By.name(strLocatorValue);
			else if (strLocator.toLowerCase().equals("classname"))
				return By.className(strLocatorValue);
			else if (strLocator.toLowerCase().equals("linktext"))
				return By.linkText(strLocatorValue);
			else
				throw new RuntimeException("Failed to load Driver");
		} catch (Exception e) {
			reportLogger.logFail("Failed to return locator for object--> " + attribute);
			throw new RuntimeException("Failed to return locator for object--> " + attribute, e);
		}
	}

	public boolean waitForElementToBeVisible(By by) {
		boolean visibleFlag = false;
		try {
			WebDriverWait wait = new WebDriverWait(driverUtil.getDriver(), largeWaitSec);
			wait.ignoring(InvalidElementStateException.class).ignoring(StaleElementReferenceException.class)
					.ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			if (!driverUtil.getDriver().findElement(by).isDisplayed()) {
				String strElementName = driverUtil.getDriver().findElement(by).toString().split("->")[1];
				reportLogger.logFail(" Webelement : " + strElementName + "is not visible yet.");
			}
			visibleFlag = true;
		} catch (Exception e) {
			reportLogger.logFail("Getting an Exception while checking if the element is visible");
			//throw new RuntimeException("Getting an Exception while checking if the element is visible", e);
		}
		return visibleFlag;
	}

	public void verifyElementIsDisable(String attribute) {
		WebElement webElement = getWebElement(attribute);
		if (webElement.isEnabled() && webElement.isDisplayed()) {
			reportLogger.logPass("Element" + attribute + " is enable");
		} else {
			reportLogger.logFail("Element" + attribute + " is not enable");
		}
	}

	public boolean isClickable(String attribute) {
		try {
			By by = getLocator(attribute);
			WebElement webElement = driverUtil.getDriver().findElement(by);
			System.out.println("Test:::::" + webElement.isEnabled());
			WebDriverWait wait = new WebDriverWait(driverUtil.getDriver(), 5);
			wait.until(ExpectedConditions.elementToBeClickable(webElement));
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	public boolean waitForElementToBeVisible(String attribute) {
		return waitForElementToBeVisible(getWebElement(attribute));
	}

	public boolean waitForElementToBeVisible(WebElement element) {
		boolean visblityFlag = true;
		try {
			WebDriverWait wait = new WebDriverWait(driverUtil.getDriver(), largeWaitSec);
			wait.ignoring(InvalidElementStateException.class).ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.visibilityOf(element));
			if (!element.isDisplayed()) {
				String strElementName = element.toString().split("->")[1];
				reportLogger.logFail(" Webelement : " + strElementName + "is not visible yet.");
				visblityFlag = false;
			}
		} catch (Exception e) {
			reportLogger.logFail(
					"Getting an Exception while checking if the element: " + element.toString() + " is visible");
			//throw new RuntimeException(
				//	"Getting an Exception while checking if the element:  " + element.toString() + " is visible", e);
		}
		return visblityFlag;
	}

	public void waitForElementToEnable(WebElement element) {
		try {
			waitForElementToBeVisible(element);
			for (int intCounter = 0; intCounter < 10; intCounter++) {
				if ((element.isEnabled())) {
					break;
				} else {
					Thread.sleep(2000);
				}
			}
			if (!(element.isEnabled())) {
				reportLogger.logFail(element.toString() + "is not enabled yet.");
			}
		} catch (Exception e) {
			reportLogger.logFail(element.toString() + "is not enabled yet.");
			throw new RuntimeException(element.toString() + "is not enabled yet.", e);
		}
	}

	public void sendKeys(String attribute, String txtValue) {
		try {
			WebElement element = getWebElement(attribute);
			element.clear();
			element.sendKeys(txtValue);
			element.sendKeys(Keys.TAB);
		} catch (Exception e) {
			reportLogger.logFail("Unable to set data on the text box");
			throw new RuntimeException("Unable to set data on the text box", e);
		}
	}

	public void clickByJS(String attribute) {
		try {
			WebElement element = getWebElement(attribute);
			JavascriptExecutor executor = (JavascriptExecutor) driverUtil.getDriver();
			executor.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			reportLogger.logFail("Unable to click on object : " + attribute);
			throw new RuntimeException("Unable to click on object : " + attribute, e);
		}
	}

	public void click(String attribute) {
		try {
			WebElement element = getWebElement(attribute);
			element.click();
		} catch (Exception e) {
			reportLogger.logFail("Unable to click on object : " + attribute);
			throw new RuntimeException("Unable to click on object : " + attribute, e);
		}
	}

	public void doubleClick(String attribute) {
		try {
			WebElement element = getWebElement(attribute);
			Actions action = new Actions(driverUtil.getDriver());
			action.doubleClick(element).build().perform();
		} catch (Exception e) {
			reportLogger.logFail("Unable to double click on object : " + attribute);
			throw new RuntimeException("Unable to double click on object : " + attribute, e);
		}
	}

	public void selectListElement(String lstElementName) {
		try {
			String xpathListElementName = "//div[contains(@class,'x-combo-list-item') and contains(text(),'"
					+ lstElementName + "')]";
			clickByJS("xpath-->" + xpathListElementName);
			/*
			 * driverUtil.getDriver().findElement(By .xpath(
			 * "//div[contains(@class,'x-combo-list-item') and contains(text(),'"
			 * + lstElementName + "')]")) .click();
			 */
		} catch (Exception e) {
			reportLogger.logFail("Unable to select list element in the dropdown");
			throw new RuntimeException("Unable to select list element in the dropdown", e);
		}
	}

	public void selectListElement(String attribute, String lstElementName) {
		try {
			if (!lstElementName.trim().isEmpty()) {
				WebElement element = getWebElement(attribute);
				Select dropDown = new Select(element);
				dropDown.selectByVisibleText(lstElementName);
			}
		} catch (Exception e) {
			reportLogger.logFail("Unable to select list element in the dropdown");
			throw new RuntimeException("Unable to select list element in the dropdown", e);
		}
	}

	public void waitElementToVisibleByXpath(String xpathElement) {
		try {
			WebDriverWait wait = new WebDriverWait(driverUtil.getDriver(), largeWaitSec);
			wait.ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class)
					.ignoring(InvalidElementStateException.class)
					.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathElement)));
		} catch (Exception e) {
			reportLogger.logFail("Object : " + xpathElement + " is not visible yet.");
			throw new RuntimeException("Object : " + xpathElement + " is not visible yet.", e);
		}
	}

	public void waitForElementsToVisibleByXpath(String xpathElement) {
		try {
			WebDriverWait wait = new WebDriverWait(driverUtil.getDriver(), largeWaitSec);
			wait.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class)
					.ignoring(InvalidElementStateException.class)
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathElement)));
		} catch (Exception e) {
			reportLogger.logFail("Objects for xpath : " + xpathElement + " is not visible yet.");
			throw new RuntimeException("Objects for xapth : " + xpathElement + " is not visible yet.", e);
		}
	}

	public boolean isPageDisplayed(String attribute) {
		try {
			boolean blnPageDisplayedFlag = false;
			WebElement element = getWebElement(attribute);
			if (element.isDisplayed()) {
				blnPageDisplayedFlag = true;
			}
			return blnPageDisplayedFlag;
		} catch (Exception e) {
			reportLogger.logFail("Page is not dispalyed yet.");
			throw new RuntimeException("Page is not dispalyed yet.");
		}
	}

	public void setCheckBox(String attribute, boolean blnChkboxValue) {
		try {
			WebElement element = getWebElement(attribute);
			boolean blnActualChkboxStatus = element.isSelected();
			if (blnActualChkboxStatus != blnChkboxValue) {
				element.click();
			}
		} catch (Exception e) {
			reportLogger.logFail("Unable to set checkbox for object : " + attribute);
			throw new RuntimeException("Unable to set checkbox for object : " + attribute);
		}
	}

	public void selectElemDropDown(String attribute, String value) {
		try {
			if (!value.trim().isEmpty()) {
				clickByJS(attribute);
				Thread.sleep(500);
				selectListElement(value);
			}
		} catch (Exception e) {
			reportLogger.logFail("Unable to select element in the drop down");
			throw new RuntimeException("Unable to select element in the drop down");
		}
	}

	public HashMap<String, Integer> storeHeaderIndicesHashMap(String xpathHeader) {

		// Declare the hash map
		HashMap<String, Integer> hashMapHeaders = new HashMap<String, Integer>();

		try {
			String strHeaderName = "";
			// Fetch all the header elements in list
			waitForElementsToVisibleByXpath(xpathHeader);
			waitElementToVisibleByXpath(xpathHeader);
			List<WebElement> elements = driverUtil.getDriver().findElements(By.xpath(xpathHeader));
			int intHeaderIndexCtr = 1;

			// Store all the headers and its indices in hash map
			for (WebElement elem : elements) {
				if (elem.getText().isEmpty() && (intHeaderIndexCtr != 1)) {
					strHeaderName = "BLANK";
				} else if (intHeaderIndexCtr == 3) {
					strHeaderName = elem.getText().trim().toUpperCase().replace(" ", "_").replace("?", "");
				} else {
					strHeaderName = elem.getText().trim().toUpperCase().replace(" ", "_").replace("?", "");
				}
				hashMapHeaders.put(strHeaderName, intHeaderIndexCtr);
				intHeaderIndexCtr = intHeaderIndexCtr + 1;
			}
		} catch (Exception e) {
			reportLogger.logFail(" Unable to store header indices in the hashmap");
			throw new RuntimeException(" Unable to store header indices in the hashmap", e);
		}
		return hashMapHeaders;
	}
	
	public int getTableRowCount( String xpathIdValue) {
		int rowCount = 0;
		String xpathTableRows = "//div[@id='" + xpathIdValue + "']//div[@class='x-grid3-scroller']/div/div";
		try {
			List<WebElement> rows = driverUtil.getDriver().findElements(By.xpath(xpathTableRows));
			rowCount = rows.size();
		} catch (Exception e) {
			reportLogger.logFail(" Unable to get row count of the table");
			throw new RuntimeException(" Unable to get row count of the table", e);
		}
		return rowCount;
	}

	public String fetchDataFromTable(String strTableHeaderName, int intRowNumber, String xpathIdValue) {
		try {
			// Fetches the header and its indices and store them in the
			// dictionary map
			String xpathDataValue = null;
			int intHeaderIndex;
			String xpathHeaderIndices = null;
			Thread.sleep(2000);
			// Fetch the xpath used for header indices
			xpathHeaderIndices = "//div[@id='" + xpathIdValue + "']//div[@class='x-grid3-header']//td";
			HashMap<String, Integer> Dictionary = storeHeaderIndicesHashMap(xpathHeaderIndices);

			// Fetch the xpath used to fetch the data table
			intHeaderIndex = Dictionary.get(strTableHeaderName);
			xpathDataValue = "//div[@id='" + xpathIdValue + "']//div[@class='x-grid3-scroller']/div/div[" + intRowNumber
					+ "]//td[" + intHeaderIndex + "]//div";
			return getWebElement("xpath-->" + xpathDataValue).getText();
		} catch (Exception e) {
			reportLogger.logFail(" Unable to fetch data from table ");
			throw new RuntimeException(" Unable to fetch data from table", e);
		}
	}

	public void clickHeaderTableMenu(String strTableHeaderName, String xpathIdValue) {
		try {
			// Fetches the header and its indices and store them in the
			// dictionary map
			String xpathDataValue = null;
			int intHeaderIndex;
			String xpathHeaderIndices = null;
			Thread.sleep(2000);
			// Fetch the xpath used for header indices
			xpathHeaderIndices = "//div[@id='" + xpathIdValue + "']//div[@class='x-grid3-header']//td";
			HashMap<String, Integer> Dictionary = storeHeaderIndicesHashMap(xpathHeaderIndices);

			// Fetch the xpath used to fetch the data table
			intHeaderIndex = Dictionary.get(strTableHeaderName);
			xpathDataValue = "//div[@id='" + xpathIdValue
					+ "']//div[@class='x-grid3-header']//div[@class='x-grid3-header-offset']//tr[@class='x-grid3-hd-row']//td["
					+ intHeaderIndex + "]/div/a[@class='x-grid3-hd-btn']";
			click("xpath-->" + xpathDataValue);
		} catch (Exception e) {
			reportLogger.logFail(" Unable to fetch data from table ");
			throw new RuntimeException(" Unable to fetch data from table", e);
		}
	}

	public int getRowCountAngJsTable(Integer intRowNumber, String xpathHeaderTagValue) throws InterruptedException {
		try {
			// Fetch the xpath used to fetch the data table
			String xpathDataValue = "//div[@data-ng-grid='" + xpathHeaderTagValue + "']//div[@class='ngCanvas']/div";
			List<WebElement> rowsList = driverUtil.getDriver().findElements(By.xpath(xpathDataValue));
			return rowsList.size();
		} catch (Exception e) {
			reportLogger.logFail(" Unable to fetch data from table ");
			throw new RuntimeException(" Unable to fetch data from table", e);
		}
	}

	public String fetchDataFromAngJsTable(String strTableHeaderName, Integer intRowNumber, String xpathHeaderTagValue)
			throws InterruptedException {
		try {
			// Fetches the header and its indices and store them in the
			// dictionary map
			String xpathDataValue = null;
			int intHeaderIndex;
			String xpathHeaderIndices = null;

			// Fetch the xpath used for header indices
			xpathHeaderIndices = "//div[@data-ng-grid='" + xpathHeaderTagValue
					+ "']//div[@class='ngHeaderContainer']//div[@class='ngHeaderSortColumn ']/div[1]";
			HashMap<String, Integer> Dictionary = storeHeaderIndicesHashMap(xpathHeaderIndices);

			// Fetch the xpath used to fetch the data table
			intHeaderIndex = Dictionary.get(strTableHeaderName)-1;
			String className = "col" + intHeaderIndex + " colt" + intHeaderIndex;
			xpathDataValue = "//div[@data-ng-grid='" + xpathHeaderTagValue + "']//div[@class='ngCanvas']/div["
					+ intRowNumber + "]//div[contains(@class,'" + className + "')]/span";
			return getWebElement("xpath-->" + xpathDataValue).getText();
		} catch (Exception e) {
			reportLogger.logFail(" Unable to fetch data from table ");
			throw new RuntimeException(" Unable to fetch data from table", e);
		}
	}

	public void clickDataInTable(String strTableHeaderName, Integer intRowNumber, String xpathIdValue) {
		try {
			// Fetches the header and its indices and store them in the
			// dictionary map
			String xpathDataValue = null;
			int intHeaderIndex;
			String xpathHeaderIndices = null;

			// Fetch the xpath used for header indices
			xpathHeaderIndices = "//div[@id='" + xpathIdValue + "']//div[@class='x-grid3-header']//td";
			HashMap<String, Integer> Dictionary = storeHeaderIndicesHashMap(xpathHeaderIndices);

			// Fetch the xpath used to fetch the data table
			intHeaderIndex = Dictionary.get(strTableHeaderName);
			xpathDataValue = "//div[@id='" + xpathIdValue + "']//div[@class='x-grid3-scroller']/div/div[" + intRowNumber
					+ "]//td[" + intHeaderIndex + "]//div";
			clickByJS("xpath-->" + xpathDataValue);
		} catch (Exception e) {
			reportLogger.logFail(" Unable to fetch data from table ");
			throw new RuntimeException(" Unable to fetch data from table", e);
		}
	}

	public void clickDataInAngJsTable(String strTableHeaderName, Integer intRowNumber, String xpathHeaderTagValue) {
		try {
			// Fetches the header and its indices and store them in the
			// dictionary map
			String xpathDataValue = null;
			int intHeaderIndex;
			String xpathHeaderIndices = null;

			// Fetch the xpath used for header indices
			xpathHeaderIndices = "//div[@data-ng-grid='" + xpathHeaderTagValue
					+ "']//div[@class='ngHeaderContainer']//div[@class='ngHeaderSortColumn ']/div[1]";
			HashMap<String, Integer> Dictionary = storeHeaderIndicesHashMap(xpathHeaderIndices);

			// Fetch the xpath used to fetch the data table
			intHeaderIndex = Dictionary.get(strTableHeaderName) - 1;
			String className = "ngCellText ng-scope col" + intHeaderIndex + " colt" + intHeaderIndex;
			xpathDataValue = "//div[@data-ng-grid='" + xpathHeaderTagValue + "']//div[@class='ngCanvas']/div["
					+ intRowNumber + "]//div[contains(@class,'" + className + "')]/span";
			clickByJS("xpath-->" + xpathDataValue);
		} catch (Exception e) {
			reportLogger.logFail(" Unable to click data in table ");
			throw new RuntimeException(" Unable to click data in table", e);
		}
	}

	public void dblClickDataInAngJsTable(String strTableHeaderName, Integer intRowNumber, String xpathHeaderTagValue)
			throws InterruptedException {
		try {
			// Fetches the header and its indices and store them in the
			// dictionary map
			String xpathDataValue = null;
			int intHeaderIndex;
			String xpathHeaderIndices = null;

			// Fetch the xpath used for header indices
			xpathHeaderIndices = "//div[@data-ng-grid='" + xpathHeaderTagValue
					+ "']//div[@class='ngHeaderContainer']//div[@class='ngHeaderSortColumn ']/div[1]";
			HashMap<String, Integer> Dictionary = storeHeaderIndicesHashMap(xpathHeaderIndices);

			// Fetch the xpath used to fetch the data table
			intHeaderIndex = Dictionary.get(strTableHeaderName) - 1;
			String className = "ngCellText ng-scope col" + intHeaderIndex + " colt" + intHeaderIndex;
			xpathDataValue = "//div[@data-ng-grid='" + xpathHeaderTagValue + "']//div[@class='ngCanvas']/div["
					+ intRowNumber + "]//div[contains(@class,'" + className + "')]/span";
			doubleClick("xpath-->" + xpathDataValue);
		} catch (Exception e) {
			reportLogger.logFail(" Unable to double click on data in table ");
			throw new RuntimeException(" Unable to double click on data in table", e);
		}
	}

	public void dblClickDataInTable(String strTableHeaderName, Integer intRowNumber, String xpathIdValue) {
		try {
			// Fetches the header and its indices and store them in the
			// dictionary map
			String xpathDataValue = null;
			int intHeaderIndex;
			String xpathHeaderIndices = null;

			// Fetch the xpath used for header indices
			xpathHeaderIndices = "//div[@id='" + xpathIdValue + "']//div[@class='x-grid3-header']//td";
			HashMap<String, Integer> Dictionary = storeHeaderIndicesHashMap(xpathHeaderIndices);

			// Fetch the xpath used to fetch the data table
			intHeaderIndex = Dictionary.get(strTableHeaderName);
			xpathDataValue = "//div[@id='" + xpathIdValue + "']//div[@class='x-grid3-scroller']/div/div[" + intRowNumber
					+ "]//td[" + intHeaderIndex + "]//div";
			doubleClick("xpath-->" + xpathDataValue);
		} catch (Exception e) {
			reportLogger.logFail(" Unable to fetch data from table ");
			throw new RuntimeException(" Unable to fetch data from table", e);
		}
	}

	public void clickCheckboxInTable(Integer intRowNumber, String xpathIdValue) {
		try {
			String xpathDataValue = "//div[@id='" + xpathIdValue + "']//div[@class='x-grid3-scroller']/div/div["
					+ intRowNumber + "]//td[1]//div";
			if (isObjectPresent(xpathDataValue)) {
				click("xpath-->" + xpathDataValue);
			} else {
				reportLogger.logFail("Row to click checkbox is not present in the table.");
				throw new RuntimeException(" Row to click checkbox is not present in the table");
			}
		} catch (Exception e) {
			reportLogger.logFail(" Unable to click checkbox in table ");
			throw new RuntimeException(" Unable to click checkbox in table", e);
		}
	}
	
	public boolean isObjectPresent(String xpathObject) {
		boolean objectPresentFlag = false;
		try {
			driverUtil.getDriver().findElement(By.xpath(xpathObject));
				objectPresentFlag = true;
		} catch(NoSuchElementException e) {
				objectPresentFlag = false;
		}
		return objectPresentFlag;
	}

	public void clickCheckboxInAngJsTable(Integer intRowNumber, String xpathHeaderTagValue) {
		try {
			String xpathDataValue = "//div[@data-ng-grid='" + xpathHeaderTagValue + "']//div[@class='ngCanvas']/div["
					+ intRowNumber + "]//div[contains(@class,'ngSelectionCell ng-scope')]//input";
			click("xpath-->" + xpathDataValue);
		} catch (Exception e) {
			reportLogger.logFail(" Unable to click data in table ");
			throw new RuntimeException(" Unable to click data in table", e);
		}
	}

	public void dblClickTableHeader(String strTableheaderName, String xpathHeaderTagValue) {
		try {
			// fetches the header name and store it indices in the below hashmap
			HashMap<String, Integer> mapHeaderIndex = storeHeaderIndicesHashMap(xpathHeaderTagValue);
			int intHeaderIndex = mapHeaderIndex.get(strTableheaderName) - 1;
			driverUtil.getDriver()
					.findElement(By.xpath("//div[@class='x-grid3-header-offset']//tr[@class='x-grid3-hd-row']//td["
							+ intHeaderIndex + "]/div"))
					.click();
			driverUtil.getDriver()
					.findElement(By.xpath("//div[@class='x-grid3-header-offset']//tr[@class='x-grid3-hd-row']//td["
							+ intHeaderIndex + "]/div/img"))
					.click();
		} catch (Exception e) {
			reportLogger.logFail(" Unable to doble click on the table header on Process account page");
			throw new RuntimeException(" Unable to doble click on the table header on Process account page", e);
		}
	}

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
	
		/**
	 * Highlight element.
	 *
	 * @param element
	 *            the element
	 */
	public void highlightElement(WebElement element) {
		WrapsDriver wrappedElement;
		for (int i = 0; i < 10; i++) {
			wrappedElement = (WrapsDriver) element;
			JavascriptExecutor driver = (JavascriptExecutor) wrappedElement.getWrappedDriver();
			driver.executeScript("arguments[0].setAttribute('style',arguments[1]);", element,
					"red;border:2px solid red;");
			driver.executeScript("arguments[0].setAttribute('style',arguments[1]);", element, "");

		}
	}
	
	public boolean waitForElementToBeClickable(By by, int timeOut) {
		try {
			WebDriverWait wait = new WebDriverWait(driverUtil.getDriver(), timeOut);
			wait.ignoring(InvalidElementStateException.class).ignoring(StaleElementReferenceException.class)
					.ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.elementToBeClickable(by));
			return true;
		} catch (Exception e) {
			return false;
		}
	}


}
