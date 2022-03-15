package com.operation;

import java.io.IOException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.setup.BasePage;

/**
 * The Class TabOperation.
 */
@Component
@Scope("prototype")
public class TabOperation extends BasePage {

	/**
	 * Click transaction tab.
	 *
	 * @return the transaction
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws Exception
	 *             the exception
	 */
	public TabOperation clickTransactionTab(String element) {
		try {
			click(element);
			return this;
		} catch (Exception e) {
			reportLogger.logFail("Unable to click Transaction tab on Transaction page.");
			e.printStackTrace();
			throw new RuntimeException("Unable to click Transaction tab on Transaction page.", e);
		}
	}

}
