package com.operation;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.setup.BasePage;

@Component
public class DropDownOperation extends BasePage {

	@Autowired
	private ButtonOperation buttonOperation;

	public void selectElemDropDown(String attribute, String value) {
		try {
			//WebElement element = getWebElement(attribute);
			buttonOperation.click(attribute);
			Thread.sleep(500);
			selectListElement(value);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void selectListElement(String lstElementName) {
		try {
			driverUtil.getDriver().findElement(By.xpath("//div[text()='" + lstElementName + "']")).click();
		} catch (Exception e) {
			reportLogger.logFail("Unable to sleect list element in the dropdown");
			throw e;
		}
	}

}
