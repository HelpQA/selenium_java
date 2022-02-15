package com.util;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.reporting.ReportManager;


@Component
public class MailUtil {
	
	@Autowired
	private ReportManager reportManager;

	@Value("${toEmail}")
	private String toEmail;

	@Value("${ccEmail}")
	private String ccEmail;

	@Value("${fromEmail}")
	private String fromEmail;

	@Value("${emailSmtpServer}")
	private String emailSmtpServer;

	@Value("${emailSmtpPort}")
	private int emailSmtpPort;

	private String refCycleName = "";
	
	@Value("${APP_ENV}")
	public String envName;

	@Value("${userName}")
	private String userName;

	@Value("${password}")
	private String password;

	private static final Log LOG = LogFactory.getLog(MailUtil.class);

	public void attachFiles(HtmlEmail email, String filePath) {
		try {
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(filePath);
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			// attachment.setDisposition(EmailAttachment.INLINE);
			//attachment.setDescription(desc);
			String[] arrFileName = filePath.split("Reports");
			String fileName = arrFileName[arrFileName.length-1].substring(1);
			attachment.setName(fileName);
			email.attach(attachment);
		} catch (Exception e) {
			LOG.error("Failed in attachFiles method.", e);
			throw new RuntimeException("Failed in attachFiles method  ", e);
		}

	}

	public void addSubj(HtmlEmail email, String subj) {
		email.setSubject(subj);
	}

	public void sendEmail(String status) {
		try {
			StringBuffer htmlTestCaseMsg = new StringBuffer("");
			HtmlEmail email = new HtmlEmail();
			sendMailSetup(email);
			addSubj(email, status + " : " + envName.toUpperCase() + GlobalConstant.EMAIL_SUBJECT + refCycleName);
			mailInitializationData(htmlTestCaseMsg);
			if (status.equalsIgnoreCase("SUCCESS")) {
				htmlTestCaseMsg.append("<br><font>" + refCycleName + " Result : </font><font><b>PASS</b></font><br>");
			} else {
				htmlTestCaseMsg.append("<br><font>" + refCycleName + " Result : </font><font><b>FAIL</b></font><br>");
			}
			appendTestSuiteName(htmlTestCaseMsg);
			appendReportPath(htmlTestCaseMsg);
			appendSignature(htmlTestCaseMsg);
			email.setMsg(htmlTestCaseMsg.toString());
			//attachFiles(email,reportManager.getReportPath());
			email.send();
		} catch (Exception e) {
			LOG.error("Failed in sendEmail method.", e);
			throw new RuntimeException("Failed in sendEmail method  ", e);
		}
	}
	
	public void mailInitializationData(StringBuffer htmlTestCaseMsg) {
		htmlTestCaseMsg.append(GlobalConstant.MAIL_INIT_DATA);
	}

	private void appendReportPath(StringBuffer htmlTestCaseMsg) {
		String reportPath = reportManager.getReportPath();
		htmlTestCaseMsg.append("<b><a href=\"" + reportPath + "\">Validation Report</a></b><br><br>");
	}

	private void appendTestSuiteName(StringBuffer htmlTestCaseMsg) {
		htmlTestCaseMsg.append("<br><font><b>Result Link: </b></font>");
	}

	private void sendMailSetup(HtmlEmail email) throws EmailException {
		try {
			this.setToRecipients(email, toEmail);
			this.setCcRecipients(email, ccEmail);
			email.setHostName(emailSmtpServer);
			email.setFrom(fromEmail);
			email.setSmtpPort(emailSmtpPort);
			email.setAuthentication(userName, PwdEncrypterUtil.decrypt(password));
		} catch (Exception e) {
			LOG.error("Failed in sendMailSetup method.", e);
			throw new RuntimeException("Failed in sendMailSetup method  ", e);
		}
	}

	private void appendSignature(StringBuffer htmlTestCaseMsg) {
		htmlTestCaseMsg.append(GlobalConstant.EMAIL_SIGNATURE);
	}

	public void setToRecipients(HtmlEmail email, String recipients) throws EmailException {
		try {
			InternetAddress address;
			List<InternetAddress> listTo = new ArrayList<>();
			String recipientsArray[] = null;
			if (recipients != null) {
				recipientsArray = recipients.split(",");
			}
			for (int recipientCounter = 0; recipientCounter < recipientsArray.length; recipientCounter++) {
				address = new InternetAddress();
				String recipient = recipientsArray[recipientCounter];
				address.setAddress(recipient);
				listTo.add(address);
			}
			email.setTo(listTo);
		} catch (Exception e) {
			LOG.error("Failed in setToRecipients method.", e);
			throw new RuntimeException("Failed in setToRecipients method  ", e);
		}	
	}

	public void setCcRecipients(HtmlEmail email, String recipients) throws EmailException {
		try {
			InternetAddress address;
			List<InternetAddress> listCc = new ArrayList<>();
			String recipientsArray[] = null;
			if (recipients != null) {
				recipientsArray = recipients.split(",");
			}
			for (int recipientCounter = 0; recipientCounter < recipientsArray.length; recipientCounter++) {
				address = new InternetAddress();
				String recipient = recipientsArray[recipientCounter];
				address.setAddress(recipient);
				listCc.add(address);
			}
			email.setCc(listCc);
		} catch (Exception e) {
			LOG.error("Failed in setCcRecipients method.", e);
			throw new RuntimeException("Failed in setCcRecipients method  ", e);
		}
	}

}
