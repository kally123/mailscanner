package com.mail.app.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mail.app.components.MailScanner;
import com.mail.app.components.MailUtility;
import com.mail.app.components.SoundAnalytics;
import com.mail.app.dao.OrderDetailsRepository;
import com.mail.app.model.Customer;
import com.mail.app.model.RepeatedCustomer;
import com.mail.app.model.RepeatedCustomerClazz;
import com.mail.app.service.CustomerService;

@Controller
@PropertySource("classpath:mailscanner.properties")
public class RestuarantController {

	@Autowired
	MailScanner mailScanner;

	@Autowired
	MailUtility mailUtility;

	@Autowired
	CustomerService customerService;

	@Autowired
	OrderDetailsRepository orderDetailsrepository;
	// OrderDetailsService orderDetailsService;

	// inject via application.properties
	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@Value("${mail.username}")
	private String fromUser;

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);
		return "welcome";
	}

	@RequestMapping("/connectAccount")
	public String connectAccount(Map<String, Object> model) throws MessagingException {
		Store store = mailUtility.establishConnectionWithKey();
		Folder orders = store.getFolder("ZOMATO_ORDERS");
		orders.open(Folder.READ_ONLY);
		model.put("accountStatus", "GMAIL account is CONNECTED!!");
		System.out.println("GMAIL account is CONNECTED!!");
		return "accountStatus";
	}

	@RequestMapping("/searchby")
	public String searchby(Map<String, Object> model) {
		return "searchby";
	}

	@RequestMapping("/mailscanner")
	public String scanMails(Map<String, Object> model) {

		try {
			mailScanner.scanEmailMessage();
			SoundAnalytics.playSound("analog-watch-alarm_daniel-simion.wav");
		} catch (Exception e) {
			e.printStackTrace();
			SoundAnalytics.playSound("Smoke-Alarm-SoundBible.com-1551222038.wav");
		}
		return "mailscanstatus";
	}

	@RequestMapping("/customers")
	public String getCustomers(Map<String, Object> model)
			throws JsonGenerationException, JsonMappingException, IOException {
		List<Customer> customers = customerService.getCustomers();
		String custData = new ObjectMapper().writeValueAsString(customers);
		model.put("customers", custData);
		model.put("totalCustomers", customers.size());

		return "customers";
	}

	@RequestMapping("/findCustomersMultiOrders")
	public String findCustomersMultiOrders(Map<String, Object> model)
			throws JsonGenerationException, JsonMappingException, IOException {
		List<Customer> customers = customerService.getCustomers().stream().filter(customer -> {
			return customer.getOrderDetails().size() > 1;
		}).collect(Collectors.toList());
		String custData = new ObjectMapper().writeValueAsString(customers);
		model.put("customers", custData);
		model.put("totalCustomers", customers.size());

		return "customers";
	}

	@RequestMapping("/exportDataToExcel")
	public String exportDataToExcel(Map<String, Object> model) throws IOException {
		mailScanner.exportDataToExcel();
		return "welcome";
	}

	@RequestMapping("/findByName")
	public String findByName(@RequestParam("name") String name, Model map) {
		map.addAttribute("customer", customerService.findByNameIs(name));
		return "customer";
	}

	@RequestMapping("/findByMobNumber")
	public String findByMobNumber(@RequestParam("mobNumber") String mobNumber, Model map) {
		map.addAttribute("customer", customerService.findByMobNumber(mobNumber));
		return "customer";
	}

	@RequestMapping("/findCustomerWithOrderId")
	public String findCustomerWithOrderId(@RequestParam("orderId") String orderId, Model map) {
		map.addAttribute("customer", customerService.findCustomerWithOrderId(orderId));
		return "customer";
	}

	@RequestMapping("/findByOrderId")
	public String findByOrderId(@RequestParam("orderId") String orderId, Model map) {
		map.addAttribute("orderdetails", orderDetailsrepository.findByOrderId(orderId));
		return "customer";
	}

	@RequestMapping("/dashboard")
	public String dashboard(Model map) throws JsonGenerationException, JsonMappingException, IOException {
		map.addAttribute("totalcustomers", customerService.getCustomerCount());
		map.addAttribute("totalorders", orderDetailsrepository.getOrderDetailsCount());

		List<RepeatedCustomerClazz> rep = new ArrayList<RepeatedCustomerClazz>();
		for (RepeatedCustomer rep1 : customerService.getRepeatedCustomers()) {
			rep.add(new RepeatedCustomerClazz(rep1.getName(), rep1.getCount(), rep1.getTotal()));
		}
		map.addAttribute("repeatedcustomers", rep);
		String custData = new ObjectMapper().writeValueAsString(rep);
		map.addAttribute("repeatedcustomersStr", custData);

		DateFormat df = new SimpleDateFormat("dd MMM yyyy");
		df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		map.addAttribute("todaysorders", orderDetailsrepository.countByOrderDate(df.format(new Date())) + "");

		Date lastWeekDate = new Date();
		lastWeekDate.setDate(lastWeekDate.getDate() - 7);
		map.addAttribute("lastweekorders", orderDetailsrepository.countByOrderDate("14th Aug 2019") + "");
		map.addAttribute("lastWeekDate", df.format(lastWeekDate));

		return "dashboard";
	}

}
