package com.google;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class HomePage {
	
	private String txtSearchBox = "xpath->//input[@name='q']";
	
	private String btnGoogleSearch = "xpath-->(//input[@value='Google Search'])[2]";

	public String getTxtSearchBox() {
		return txtSearchBox;
	}

	public String getBtnGoogleSearch() {
		return btnGoogleSearch;
	}

}
