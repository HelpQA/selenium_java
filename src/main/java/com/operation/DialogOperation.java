package com.operation;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.setup.BasePage;

// TODO: Auto-generated Javadoc
/**
 * The Class PopUpOperations.
 */
@Component
@Scope("prototype")
public class DialogOperation extends BasePage {

	private String headerTextSuccess = "xpath-->//span[@class='x-window-header-text' and text()='Success']";

	private String headerTextAlert = "xpath-->//span[@class='x-window-header-text' and text()='Alert']";

	private String headerTextDelete = "xpath-->//span[@class='x-window-header-text' and text()='Delete']";
	
	private String headerTextError = "xpath-->//span[@class='x-window-header-text' and text()='Import Errors']";

	private String validateMessage = "xpath-->//span[contains(text(),'Set Validated Successfully')]";

	private String validateSuccessMessage = "xpath-->//span[text()='Set Validated Successfully: 0 errors detected']";

	private String txtTransSavedSuccess = "classname-->ext-mb-text";

	private String submitMessage = "xpath-->//span[text()='Successfully Submitted, You will be redirected to the search page now.']";

	private String approveMessage = "xpath-->//span[text()='Set has been submitted to Geneva successfully.']";

	private String setModificationMsg = "xpath-->//span[text()='These transactions have already been Geneva Loaded.  Would you like to modify them?']";

	private String setPendingApprovalModificatioMsg = "xpath-->//span[text()='These transactions have already been Approval Pending.  Would you like to modify them?']";

	private String setModifiedMessage = "xpath-->//span[text()='Successfully updated transaction']";

	private String excelUploadedMessage = "xpath-->//span[text()='Excel file uploaded successfully']";

	private String excelUploadErrorMessage = "xpath-->//span[text()='Excel file has errors, please correct the errors and upload again']";
	
	private String editAlertMessage = "xpath-->//span[text()='Transfer transactions are not eligible for Edit All or Edit selected via multi-edit. Can only edit individually.']";
	
	private String forwardFXMessage = "xpath-->//span[text()='Forward FX Transaction will be posted (Yes/No).']";
	
	public String getHeaderTextError() {
		return headerTextError;
	}

	public String getExcelUploadErrorMessage() {
		return excelUploadErrorMessage;
	}

	public String getExcelUploadedMessage() {
		return excelUploadedMessage;
	}
	
	public String getEditAlertMessage() {
		return editAlertMessage;
	}
	
	public String getforwardFXMessage()
	{
		return forwardFXMessage;
	}
	
	/**
	 * Check success msg.
	 *
	 * @return the dialog operation
	 */
	public DialogOperation checkSuccessMsg() {
		try {
			waitForElementToBeVisible(headerTextSuccess);
			if (getWebElement(headerTextSuccess).isDisplayed()) {
				System.out.println("Getting " + getWebElement(headerTextSuccess).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
				reportLogger.logPass("Transaction saved successfully");
			} else {
				System.out.println("Getting " + getWebElement(headerTextSuccess).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
				reportLogger.logFail("Unable to save the transaction successfully. Getting "
						+ getWebElement(headerTextSuccess).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
			}
			return this;
		} catch (Exception e) {
			reportLogger.logFail("Unable to check successful msg after adding expense type on Transaction page.");
			throw new RuntimeException("Unable to check successful msg after adding expense type on Transaction page.",
					e);
		}
	}

	public DialogOperation checkHeaderAlertMsg() {
		try {
			waitForElementToBeVisible(headerTextAlert);
			if (getWebElement(headerTextAlert).isDisplayed()) {
				reportLogger.logPass("Alert message coming successfully");
			} else {
				reportLogger.logFail("Alert message not coming. Getting " + getWebElement(headerTextAlert).getText()
						+ " message = " + getWebElement(txtTransSavedSuccess).getText());
			}
			return this;
		} catch (Exception e) {
			reportLogger.logFail("Unable to check Alert msg");
			throw new RuntimeException("Unable to check Alert msg", e);
		}
	}

	public DialogOperation checkDeleteMsg() {
		try {
			waitForElementToBeVisible(headerTextDelete);
			if (getWebElement(headerTextDelete).isDisplayed()) {
				reportLogger.logPass("Proceed to Delete Transaction successfully");
			} else {
				reportLogger.logFail("Unable to Delete transaction successfully. Getting "
						+ getWebElement(headerTextDelete).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
			}
			return this;
		} catch (Exception e) {
			reportLogger.logFail("Unable to dete transaction from GTI");
			throw new RuntimeException("Unable to dete transaction from GTI", e);
		}
	}

	/**
	 * Check success msg.
	 *
	 * @return the dialog operation
	 */
	public DialogOperation checkValidateMsg() {
		try {
			waitForElementToBeVisible(validateMessage);
			if (getWebElement(validateMessage).isDisplayed()) {
				System.out.println("Getting " + getWebElement(validateMessage).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
				reportLogger.logPass("Transaction validated");
			} else {
				System.out.println("Getting " + getWebElement(validateMessage).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
				reportLogger.logFail("Unable to save the transaction successfully. Getting "
						+ getWebElement(validateMessage).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
			}
			return this;
		} catch (Exception e) {
			reportLogger.logFail("Unable to check successful msg after adding expense type on Transaction page.");
			throw new RuntimeException("Unable to check successful msg after adding expense type on Transaction page.",
					e);
		}
	}

	/**
	 * Check success msg.
	 *
	 * @return the dialog operation
	 */
	public DialogOperation validateSetSeccessfully() {
		try {
			waitForElementToBeVisible(validateSuccessMessage);
			if (getWebElement(validateSuccessMessage).isDisplayed()) {
				System.out.println("Getting " + getWebElement(validateSuccessMessage).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
				reportLogger.logPass("Transaction validated successfully");
			} else {
				System.out.println("Getting " + getWebElement(validateSuccessMessage).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
				reportLogger.logFail("Unable to save the transaction successfully. Getting "
						+ getWebElement(validateMessage).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
			}
			return this;
		} catch (Exception e) {
			reportLogger.logFail("Unable to check successful msg after adding expense type on Transaction page.");
			throw new RuntimeException("Unable to check successful msg after adding expense type on Transaction page.",
					e);
		}
	}

	public DialogOperation checkSubmitMsg() {
		try {
			waitForElementToBeVisible(submitMessage);
			if (getWebElement(submitMessage).isDisplayed()) {
				System.out.println("Getting " + getWebElement(submitMessage).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
				reportLogger.logPass("Transaction submitted successfully");
			} else {
				System.out.println("Getting " + getWebElement(submitMessage).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
				reportLogger.logFail(
						"Unable to subit transaction successfully. Getting " + getWebElement(submitMessage).getText()
								+ " message = " + getWebElement(txtTransSavedSuccess).getText());
			}
			return this;
		} catch (Exception e) {
			reportLogger.logFail("Unable to submit transactions");
			throw new RuntimeException("Unable to submit transactions", e);
		}
	}

	public DialogOperation checkApproveMsg() {
		try {
			waitForElementToBeVisible(approveMessage);
			if (getWebElement(approveMessage).isDisplayed()) {
				System.out.println("Getting " + getWebElement(approveMessage).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
				reportLogger.logPass("Transaction approve successfully");
			} else {
				System.out.println("Getting " + getWebElement(approveMessage).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
				reportLogger.logFail(
						"Unable to approve transaction successfully. Getting " + getWebElement(approveMessage).getText()
								+ " message = " + getWebElement(txtTransSavedSuccess).getText());
			}
			return this;
		} catch (Exception e) {
			reportLogger.logFail("Unable to approve transactions");
			throw new RuntimeException("Unable to approve transactions", e);
		}
	}

	public DialogOperation checkSetModifyMsg() {
		try {
			waitForElementToBeVisible(setModificationMsg);
			if (getWebElement(setModificationMsg).isDisplayed()) {
				System.out.println("Getting " + getWebElement(setModificationMsg).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
				reportLogger.logPass("Transaction modified successfully");
			} else {
				System.out.println("Getting " + getWebElement(setModificationMsg).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
				reportLogger.logFail("Unable to modify transaction successfully. Getting "
						+ getWebElement(setModificationMsg).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
			}
			return this;
		} catch (Exception e) {
			reportLogger.logFail("Unable to modify transactions");
			throw new RuntimeException("Unable to modify transactions", e);
		}
	}

	public DialogOperation checkPendingApprovalSetModifyMsg() {
		try {
			waitForElementToBeVisible(setPendingApprovalModificatioMsg);
			if (getWebElement(setPendingApprovalModificatioMsg).isDisplayed()) {
				System.out.println("Getting " + getWebElement(setPendingApprovalModificatioMsg).getText()
						+ " message = " + getWebElement(txtTransSavedSuccess).getText());
				reportLogger.logPass("Transaction modified successfully");
			} else {
				System.out.println("Getting " + getWebElement(setPendingApprovalModificatioMsg).getText()
						+ " message = " + getWebElement(txtTransSavedSuccess).getText());
				reportLogger.logFail("Unable to modify transaction successfully. Getting "
						+ getWebElement(setPendingApprovalModificatioMsg).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
			}
			return this;
		} catch (Exception e) {
			reportLogger.logFail("Unable to modify transactions");
			throw new RuntimeException("Unable to modify transactions", e);
		}
	}

	public DialogOperation setModifiedMsg() {
		try {
			waitForElementToBeVisible(setModifiedMessage);
			if (getWebElement(setModifiedMessage).isDisplayed()) {
				System.out.println("Getting " + getWebElement(setModifiedMessage).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
				reportLogger.logPass("Transaction modified successfully");
			} else {
				System.out.println("Getting " + getWebElement(setModifiedMessage).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
				reportLogger.logFail("Unable to modify transaction successfully. Getting "
						+ getWebElement(setModifiedMessage).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
			}
			return this;
		} catch (Exception e) {
			reportLogger.logFail("Unable to modify transactions");
			throw new RuntimeException("Unable to modify transactions", e);
		}
	}

	public DialogOperation checkMessage(String webElement) {
		try {
			waitForElementToBeVisible(webElement);
			if (getWebElement(webElement).isDisplayed()) {
				System.out.println("Getting " + getWebElement(webElement).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
				reportLogger.logPass("Dialog message " + webElement + " coming as expected.");
			} else {
				System.out.println("Getting " + getWebElement(webElement).getText() + " message = "
						+ getWebElement(txtTransSavedSuccess).getText());
				reportLogger.logFail("Unable to get Expected message. Getting " + getWebElement(webElement).getText()
						+ " message = " + getWebElement(txtTransSavedSuccess).getText());
			}
			return this;
		} catch (Exception e) {
			reportLogger.logFail("Unable to get Expected message.");
			throw new RuntimeException("Unable to get Expected message.", e);
		}
	}
	
	public DialogOperation checkHeaderMessage(String webElement) {
		try {
			waitForElementToBeVisible(webElement);
			if (getWebElement(webElement).isDisplayed()) {
				System.out.println("Getting " + getWebElement(webElement).getText());
				reportLogger.logPass("Dialog message " + webElement + " coming as expected.");
			} else {
				System.out.println("Getting " + getWebElement(webElement).getText());
				reportLogger.logFail("Unable to get Expected message. Getting " + getWebElement(webElement).getText());
			}
			return this;
		} catch (Exception e) {
			reportLogger.logFail("Unable to get Expected message.");
			throw new RuntimeException("Unable to get Expected message.", e);
		}
	}

}
