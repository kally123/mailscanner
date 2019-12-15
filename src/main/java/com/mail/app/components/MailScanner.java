package com.mail.app.components;

import static com.mail.app.components.KeyUtils.CURRENCY_REPLACEMENT_CHARACTERS;
import static com.mail.app.components.KeyUtils.DATE_REPLACEMENT_CHARACTERS;
import static com.mail.app.model.Customer.ADDRESS;
import static com.mail.app.model.Customer.CONTACT_NO;
import static com.mail.app.model.Customer.CUSTOMER_NAME;
import static com.mail.app.model.Customer.DATE;
import static com.mail.app.model.Customer.ORDER_ID;
import static com.mail.app.model.Customer.PACKAGING_CHARGE;
import static com.mail.app.model.Customer.RESTAURANT_PROMO;
import static com.mail.app.model.Customer.ZOMATO_PROMO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.mail.app.model.Customer;
import com.mail.app.model.InvalidCustomer;
import com.mail.app.model.MessagesScanned;
import com.mail.app.model.OrderDetails;
import com.mail.app.repository.InvalidCustomerRepository;
import com.mail.app.repository.MessagesScannedRepository;
import com.mail.app.service.CustomerService;

@Component
@PropertySource("classpath:mailscanner.properties")
public class MailScanner {
	private final int MESSAGE_SLICE_LIMIT = 10;
	private static final int CUSTOMER_NAME_LIMIT = 35;
	private static final String NO_NAME = "NO_NAME_";

	@Autowired
	CustomerService customerService;

	@Autowired
	MessagesScannedRepository messagesScannedRepository;

	@Autowired
	InvalidCustomerRepository invalidCustomerRepository;

	@Autowired
	MailUtility mailUtility;

	@Autowired
	MailConfig mailconfig;

	@Value("${mail.username}")
	private String fromUser;

	public void exportDataToExcel(List<Customer> customers) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet spreadsheet = workbook.createSheet("Woodfire Biryani Zomato Customer List ");

		FileOutputStream out = new FileOutputStream(
				System.getProperty("user.home") + File.separator + "ZomatoCustomerList.xlsx");
		mailUtility.createHeader(workbook, spreadsheet);

		try {
			WriteDataToExcel.getInstance().copyDataToExcel(customers, workbook, spreadsheet, out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("----------------------------------Writing Data----------------------------------");
			workbook.write(out);
			out.flush();
			out.close();
		}
	}

	private void processMessagesToDatabase(List<Message> messages, int messageNumber, MessagesScanned messagesScanned) {
		Customer existingCustomer = null;
		Map<String, Customer> custMap = new HashMap<String, Customer>();
		try {
			for (Message message : messages) {
				if (message.isMimeType("text/html")) {
					Element bodyElement = Jsoup.parse(message.getContent().toString()).body();
					existingCustomer = populateCustomerHtmlMessage(bodyElement, custMap);
				} else if (message.isMimeType("multipart/*")) {
					String text = message.getContent().toString();
					mailUtility.trimSubData(text, 2000);
					logError("INVALID MESSAGE!",
							"INVALID MESSAGE PARSING - FWD message.. This is not a html type message. Please parse manually.."
									+ text);
				}
				if (existingCustomer != null) {
					validateData(existingCustomer);
					custMap.put(existingCustomer.getName(), existingCustomer);
					System.out.print(messageNumber + "..");
				}
				messageNumber++;
			}
			messagesScanned.setMessagesScanned(messageNumber--);
			saveData(custMap, messagesScanned);
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("Error occured for Messagecounter / Customer : " + messageNumber + "/" + existingCustomer);
		}
	}

	@Scheduled(fixedRate = 3600000)
	public void scanEmailMessage() throws MessagingException, IOException {
		Store store = mailconfig.getStore();
		Folder orders = store.getFolder("ZOMATO_ORDERS");
		orders.open(Folder.READ_ONLY);
		System.out.println(fromUser + " GMAIL account is CONNECTED!!");

		MessagesScanned messagesScanned = messagesScannedRepository.findMessagesScannedById(1);
		System.out.println("No of Zomato Orders to be Scanned: "
				+ (orders.getMessages().length - messagesScanned.getMessagesScanned()));
		int messageNumber = messagesScanned.getMessagesScanned();

		int partitionCounter = 1;
		for (List<Message> messages : Lists.partition(
				Arrays.asList(orders.getMessages(messageNumber, orders.getMessageCount())), MESSAGE_SLICE_LIMIT)) {
			processMessagesToDatabase(messages,
					messagesScannedRepository.findMessagesScannedById(1).getMessagesScanned(), messagesScanned);
			System.out.println("Proccessed Up to now : " + partitionCounter * MESSAGE_SLICE_LIMIT + " messages..");
			partitionCounter++;
		}
	}

	private void saveData(Map<String, Customer> custMap, MessagesScanned messagesScanned) {
		try {
			System.out.println();
			System.out.println("Customer Data started saving to Database..");
			List<Customer> existingCustomers = new ArrayList<Customer>();
			for (Customer customer : custMap.values()) {
				if (customer.getId() == null) {
					customerService.save(customer);
				} else {
					existingCustomers.add(customer);
				}
			}
			System.out.println("Segregated existingCustomers list and saved NEW customers alone");
			customerService.flush();

			if (!existingCustomers.isEmpty()) {
				customerService.saveAll(existingCustomers);
				System.out.println("existingCustomers list Data Saved to DB..");
			}

			messagesScannedRepository.save(messagesScanned);
			System.out.println("Data transmitted");
		} catch (Exception e) {
			System.out.println("messagesScanned : " + messagesScanned.getMessagesScanned());
			e.printStackTrace();
		}
	}

	private void validateData(Customer customer) {
		if (customer.getName().length() > CUSTOMER_NAME_LIMIT) {
			customer.setName(mailUtility.trimSubData(customer.getName(), CUSTOMER_NAME_LIMIT));
			logError(customer.getName(),
					"INVALID MESSAGE - Customer name is trimmed to " + CUSTOMER_NAME_LIMIT + " Characters.");
		} else if (StringUtils.isEmpty(customer.getName())) {
			customer.setName(NO_NAME + customer.getMobNumber());
		}

	}

	private void logError(String name, String errorMessage) {
		System.out.println(errorMessage);
		invalidCustomerRepository.save(new InvalidCustomer(name, errorMessage));
	}

	private Customer getCustomer(String customerName, String mobNumber, Map<String, Customer> custMap) {
		List<Customer> dbCustomers = customerService.findByName(customerName);
		Customer serviceCustomer = null;
		if (!dbCustomers.isEmpty()) {
			if (dbCustomers.size() > 1) {
				for (Customer customer : dbCustomers) {
					if (customer.getMobNumber().equals(mobNumber))
						serviceCustomer = customer;
				}
			} else {
				serviceCustomer = dbCustomers.get(0);
			}
		}
		Customer mapCustomer = custMap.get(customerName);
		if (serviceCustomer == null) {
			return mapCustomer; // New customer Scenario
		} else if (serviceCustomer != null && !customerName.equals(serviceCustomer.getName())) {
			return mapCustomer;// New Customer scenario
		} else if (serviceCustomer != null && mapCustomer != null && !mapCustomer.getOrderDetails().isEmpty()) {
			mapCustomer.getOrderDetails().addAll(serviceCustomer.getOrderDetails());
			return mapCustomer;
		}

		return serviceCustomer;
	}

	private Customer populateCustomerHtmlMessage(Element body, Map<String, Customer> custMap) {
		String customerName = body.getElementsContainingOwnText(CUSTOMER_NAME).next().text();
		String mobNumber = body.getElementsContainingOwnText(CONTACT_NO).next().text();
		String orderId = body.getElementsContainingOwnText(ORDER_ID).text().replace(ORDER_ID, "").trim();
		OrderDetails details = new OrderDetails(orderId);

		Customer existingCustomer = getCustomer(customerName, mobNumber, custMap);

		if (existingCustomer == null) {
			existingCustomer = new Customer(customerName, mobNumber,
					body.getElementsContainingOwnText(ADDRESS).next().text().replaceAll("'", ""));
			existingCustomer.getOrderDetails().add(populateOrderDetails(details, body));
		} else if (!isExistingOrder(existingCustomer, orderId)) {
			existingCustomer.getOrderDetails().add(populateOrderDetails(details, body));
		}
		return existingCustomer;
	}

	private boolean isExistingOrder(Customer existingCustomer, String orderId) {
		return !existingCustomer.getOrderDetails().isEmpty()
				&& !existingCustomer.getOrderDetails().stream().filter(orderDetails -> {
					return orderId.equals(orderDetails.getOrderId());
				}).collect(Collectors.toList()).isEmpty();
	}

	private OrderDetails populateOrderDetails(OrderDetails details, Element body) {
		String orderDate = body.getElementsContainingOwnText("Order Summary").next().text().trim();
		if (StringUtils.isEmpty(orderDate)) {
			orderDate = body.getElementsContainingOwnText(DATE).text().trim();
		}
		orderDate = orderDate.replaceAll(DATE_REPLACEMENT_CHARACTERS, "");
		details.setOrderDate(orderDate);
		StringBuffer sb = new StringBuffer();
		Elements elements = body.getElementsByClass("bill-text");
		for (int i = 0; i < elements.size() - 1; i++) {
			String charges = null;
			if (elements.get(i + 1) != null) {
				charges = elements.get(i + 1).text().trim();
			}
			if (elements.get(i).text().contains(ZOMATO_PROMO)) {
				details.setZomatoPromo(
						mailUtility.parseDoubleValue(charges.replaceAll(CURRENCY_REPLACEMENT_CHARACTERS, "")));
			} else if (elements.get(i).text().contains(RESTAURANT_PROMO)) {
				details.setRestaurantPromo(
						mailUtility.parseDoubleValue(charges.replaceAll(CURRENCY_REPLACEMENT_CHARACTERS, "")));
			} else if (elements.get(i).text().contains(PACKAGING_CHARGE)) {
				details.setPackagingCharge(Integer.parseInt(charges.replaceAll(CURRENCY_REPLACEMENT_CHARACTERS, "")));
			} else {
				sb.append(elements.get(i).text() + " ");
			}
		}
		details.setOrderSummary(sb.toString().replaceAll(CURRENCY_REPLACEMENT_CHARACTERS, ""));
		List<Element> paymentElements = body.getElementsByClass("receipt-text");
		details.setPaymentMode(paymentElements.get(0).text());

		details.setBill(
				Double.parseDouble(paymentElements.get(1).text().replaceAll(CURRENCY_REPLACEMENT_CHARACTERS, "")));

		return details;
	}

}
