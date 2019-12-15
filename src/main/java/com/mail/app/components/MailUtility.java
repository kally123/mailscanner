package com.mail.app.components;

import static com.mail.app.model.Customer.ADDRESS;
import static com.mail.app.model.Customer.CONTACT_NO;
import static com.mail.app.model.Customer.CUSTOMER_NAME;
import static com.mail.app.model.Customer.ORDER_ID;
import static com.mail.app.model.Customer.ORDER_SUMMARY;
import static com.mail.app.model.Customer.PACKAGING_CHARGE;
import static com.mail.app.model.Customer.PAYMENT_MODE;
import static com.mail.app.model.Customer.RESTAURANT_PROMO;
import static com.mail.app.model.Customer.ZOMATO_PROMO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.mail.app.model.Customer;
import com.mail.app.model.OrderDetails;

@Component
public class MailUtility {

	public String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			result = result + bodyPart.getContent();
		}
		return result;
	}

	public void createHeader(XSSFWorkbook workbook, XSSFSheet spreadsheet) {
		XSSFRow row = spreadsheet.createRow(0);
		Object[] headerObjectArr = new Object[] { ORDER_ID, CUSTOMER_NAME, CONTACT_NO, ADDRESS, ORDER_SUMMARY,
				PACKAGING_CHARGE, PAYMENT_MODE, "Bill", RESTAURANT_PROMO, ZOMATO_PROMO, "Order Date" };
		int headerCellid = 0;

		CellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontName("Arial");
		font.setColor(IndexedColors.BLACK.getIndex());
		cellStyle.setFont(font);
		cellStyle.setBorderBottom(CellStyle.BORDER_THICK);
		cellStyle.setBorderTop(CellStyle.BORDER_THICK);
		cellStyle.setBorderLeft(CellStyle.BORDER_THICK);
		cellStyle.setBorderRight(CellStyle.BORDER_THICK);

		for (Object obj : headerObjectArr) {
			Cell cell = row.createCell(headerCellid++);
			cell.setCellValue((String) obj);
			cell.setCellStyle(cellStyle);
		}
	}

	public MimeMessage draftEmailMessage(Session mailSession) throws AddressException, MessagingException {
		String[] toEmails = { "kalyansomisetty@gmail.com" };
		String emailSubject = "Test email subject";
		String emailBody = "This is an email sent by <b>//howtodoinjava.com</b>.";
		MimeMessage emailMessage = new MimeMessage(mailSession);
		for (int i = 0; i < toEmails.length; i++) {
			emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
		}
		emailMessage.setSubject(emailSubject);
		emailMessage.setContent(emailBody, "text/html");
		return emailMessage;
	}

	public void sendEmail(Session mailSession, String emailHost, String fromUser, String fromUserEmailPassword)
			throws AddressException, MessagingException {
		Transport transport = mailSession.getTransport("smtp");
		transport.connect(emailHost, fromUser, fromUserEmailPassword);
		MimeMessage emailMessage = draftEmailMessage(mailSession);
		transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
		transport.close();
		System.out.println("Email sent successfully.");
	}

	public List<Message> filterMessages(Folder inbox, String filterSubject) throws MessagingException {
		return Arrays.stream(inbox.getMessages(200, 250)).parallel().filter(message -> {
			try {
				return message.getSubject().contains(filterSubject);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			return false;
		}).collect(Collectors.toList());
	}

	public Customer populateCustomerMimeMessage(String emailData) {
		System.out.println("populateCustomerMimeMessage Entered..");
		Customer customer = new Customer();
		String[] message = emailData.split("\r\n");

		int packageChargeIndex = 0;
		for (String data : message) {
			if (data.contains(PACKAGING_CHARGE)) {
				break;
			}
			packageChargeIndex++;
		}

		for (int i = 0; i < message.length; i++) {
			OrderDetails details = new OrderDetails();
			if (message[i].contains(CUSTOMER_NAME)) {
				customer.setName(trimData(message[i], CUSTOMER_NAME));
			} else if (message[i].contains(CONTACT_NO)) {
				customer.setMobNumber(trimData(message[i], CONTACT_NO));
			} else if (message[i].contains(ADDRESS)) {
				customer.setAddress(trimData(message[i], ADDRESS));
			} else if (message[i].contains(ORDER_SUMMARY)) {
				details.setOrderDate(trimData(message[i], ORDER_SUMMARY));
				details.setOrderId(trimData(message[i + 1], ORDER_ID));
				StringBuffer orderSummary = new StringBuffer();
				for (int j = 2; i + j < packageChargeIndex; j++) {
					orderSummary.append(message[i + j]).append(", ");
				}
				details.setOrderSummary(orderSummary.toString());
			} else if (message[i].contains(ZOMATO_PROMO)) {
				details.setZomatoPromo(parseDoubleValue(trimData(message[i], ZOMATO_PROMO)));
			} else if (message[i].contains(RESTAURANT_PROMO)) {
				details.setRestaurantPromo(parseDoubleValue(trimData(message[i], RESTAURANT_PROMO)));
			} else if (message[i].contains(PACKAGING_CHARGE)) {
				details.setPackagingCharge(Integer.parseInt(trimData(message[i], PACKAGING_CHARGE)));
			} else if (message[i].contains(PAYMENT_MODE)) {
				String payment = trimData(message[i], PAYMENT_MODE);

				details.setPaymentMode(payment.substring(0, payment.indexOf('₹')).trim());
				details.setBill(Double.parseDouble(payment.substring(payment.indexOf('₹') + 1, payment.length())));
			}
			if (customer.getOrderDetails() == null) {
				customer.setOrderDetails(new ArrayList<OrderDetails>());
			}
			customer.getOrderDetails().add(details);
		}
		System.out.println(customer.toString());
		return customer;
	}

	public String trimData(String message, String key) {
		message = message.replaceFirst(key, "");
		return message.trim();
	}

	public String trimSubData(String text, int limit) {
		if (text.length() > limit) {
			text = text.substring(0, limit - 1).replaceAll("'", "");
		}

		return text;
	}

	public double parseDoubleValue(String value) {
		double doubleValue = 0;
		try {
			doubleValue = Double.parseDouble(value);
		} catch (Exception e) {
			System.out.println("Cant parse Value : " + value);
			e.printStackTrace();
		}
		return doubleValue;
	}

}
