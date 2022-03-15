package com.operation;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.setup.BasePage;

/**
 * The Class ButtonOperation.
 */
@Component
@Scope("prototype")
public class ButtonOperation extends BasePage {

	private String btnOk = "xpath-->//button[contains(text(),'OK')]";

	private String btnYes = "xpath-->//button[contains(text(),'Yes')]";

	private String elemCloseTrans = "xpath-->//div[@class='x-tool x-tool-close']";

	public String getBtnOk() {
		return btnOk;
	}

	public String getelemCloseTrans() {
		return elemCloseTrans;
	}

	public String getBtnYes() {
		return btnYes;
	}

	/**
	 * Expand trans type.
	 *
	 * @param element
	 *            the element
	 * @return the button operation
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	/*
	 * public ButtonOperation expandTransType(String element) throws
	 * InterruptedException { try { if (getWebElement(element).isDisplayed()) {
	 * getWebElement(element).click(); Thread.sleep(2000); } return this; }
	 * catch (Exception e) { reportLogger.logFail(
	 * "Unable to expand transaction type on Transaction page.");
	 * e.printStackTrace(); throw new RuntimeException(
	 * "Unable to expand transaction type on Transaction page.", e); } }
	 */
	public ButtonOperation expandTransType(String element) {
		try {
			if (driverUtil.getDriver().findElements(getLocator(element)).size() > 0) {
				getWebElement(element).click();
				Thread.sleep(2000);
			}
			return this;
		} catch (Exception e) {
			reportLogger.logFail("Unable to expand transaction type on Transaction page.");
			e.printStackTrace();
			throw new RuntimeException("Unable to expand transaction type on Transaction page.", e);
		}
	}

	public ButtonOperation expandTransTypeUsingJS(String element) throws InterruptedException {
		try {
			if (getWebElement(element).isDisplayed()) {
				clickByJS(element);
				Thread.sleep(2000);
			}
			return this;
		} catch (Exception e) {
			reportLogger.logFail("Unable to expand transaction type on Transaction page.");
			e.printStackTrace();
			throw new RuntimeException("Unable to expand transaction type on Transaction page.", e);
		}
	}

	/**
	 * Click transaction type.
	 *
	 * @param transType
	 *            the trans type
	 * @return the button operation
	 */
	public ButtonOperation clickTransactionType(String transType) {
		try {
			String xpathTransType = "//div[@id='trans-tree-panel']//span[text()='<transType>']/parent::a";
			xpathTransType = xpathTransType.replace("<transType>", transType);
			waitForElementToBeVisible(driverUtil.getDriver().findElement(By.xpath(xpathTransType)));
			if (driverUtil.getDriver().findElement(By.xpath(xpathTransType)).isDisplayed()) {
				driverUtil.getDriver().findElement(By.xpath(xpathTransType)).click();
			} else {
				reportLogger.logFail("Transaction Type : " + transType + " is not displayed.");
			}
			return this;
		} catch (Exception e) {
			reportLogger.logFail("Unable to click transaction type on Transaction page.");
			e.printStackTrace();
			throw new RuntimeException("Unable to click transaction type on Transaction page.", e);
		}
	}

	/**
	 * Click transaction type.
	 *
	 * @param transType
	 *            the trans type
	 * @return the button operation
	 */
	public ButtonOperation clickAssetServicingTransactionType(String transType) {
		try {
			String xpathTransType = "xpath-->//div[@id='asset-tree-panel']//span[text()='<transType>']/parent::a";
			xpathTransType = xpathTransType.replace("<transType>", transType);
			// waitForElementToBeVisible(driverUtil.getDriver().findElement(By.xpath(xpathTransType)));
			if (getWebElement(xpathTransType).isDisplayed()) {
				// driverUtil.getDriver().findElement(By.xpath(xpathTransType)).click();
				clickByJS(xpathTransType);
			} else {
				reportLogger.logFail("Transaction Type : " + transType + " is not displayed.");
			}
			return this;
		} catch (Exception e) {
			reportLogger.logFail("Unable to click transaction type on Transaction page.");
			e.printStackTrace();
			throw new RuntimeException("Unable to click transaction type on Transaction page.", e);
		}
	}
	
	public ButtonOperation clickMultiTransactionTemplate(String transType) {
		try {
			String xpathTransType = "xpath-->//div[@id='multi-trans-tree-panel']//span[text()='<transType>']/parent::a";
			xpathTransType = xpathTransType.replace("<transType>", transType);
			if (getWebElement(xpathTransType).isDisplayed()) {
				 clickButton(xpathTransType);
			} else {
				reportLogger.logFail("Transaction Type : " + transType + " is not displayed.");
			}
			return this;
		} catch (Exception e) {
			reportLogger.logFail("Unable to click transaction type on Transaction page.");
			e.printStackTrace();
			throw new RuntimeException("Unable to click transaction type on Transaction page.", e);
		}
	}

	/**
	 * Click save transaction.
	 *
	 * @param element
	 *            the element
	 * @return the button operation
	 */
	public ButtonOperation clickSaveTransaction(String element) {
		try {
			waitForElementToEnable(getWebElement(element));
			click(element);
			return this;
		} catch (Exception e) {
			reportLogger.logFail("Unable to click save transaction button on Transaction page.");
			e.printStackTrace();
			throw new RuntimeException("Unable to click save transaction button on Transaction page.", e);
		}
	}

	/**
	 * Click button.
	 *
	 * @param element
	 *            the element
	 * @return the button operation
	 */
	public ButtonOperation clickButton(String element) {
		try {
			waitForElementToEnable(getWebElement(element));
			click(element);
			return this;
		} catch (Exception e) {
			reportLogger.logFail("Unable to click button " + element);
			e.printStackTrace();
			throw new RuntimeException("Unable to click button " + element, e);
		}
	}

	public ButtonOperation mouseHover(String element) {
		try {
			waitForElementToEnable(getWebElement(element));
			
			WebElement element1 = getWebElement(element);
			Actions action = new Actions(driverUtil.getDriver());
			 
		     action.moveToElement(element1).build().perform();
			return this;
		} catch (Exception e) {
			reportLogger.logFail("Unable to hover mouse " + element);
			e.printStackTrace();
			throw new RuntimeException("Unable to hover mouse " + element, e);

		}
	}
	
	public void clickByJS(String attribute) {
		try {
			By by = getLocator(attribute);
			WebElement element = driverUtil.getDriver().findElement(by);
			JavascriptExecutor executor = (JavascriptExecutor) driverUtil.getDriver();
			executor.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			reportLogger.logFail("Unable to click element " + attribute);
			e.printStackTrace();
			throw new RuntimeException("Unable to click element " + attribute, e);
		}
	}
}
