package com.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.setup.BasePage;
import com.operation.TextBoxOperation;
import com.reporting.ReportLogger;

@Component
public class TextBoxOperation extends BasePage {

	@Autowired
	private ReportLogger reportLogger;

	/**
	 * Sets the value.
	 *
	 * @param element
	 *            the element
	 * @param value
	 *            the value
	 * @return the text box operation
	 */
	public TextBoxOperation setValue(String element, String value) {
		try {
			if (value != "") {
				sendKeys(element, value);
			}

			return this;
		} catch (Exception e) {
			reportLogger.logFail("Unable to set sub account id on Transaction page.");
			e.printStackTrace();
			throw new RuntimeException("Unable to set " + element + " with value " + value, e);
		}
	}
}
