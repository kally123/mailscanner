package com.mail.app.service;

import java.util.Collection;
import java.util.List;

import com.mail.app.model.Customer;

public interface CustomerService {

	public Customer findByNameAndMobNumber(String name, String mobNumber);

	public Customer findByMobNumber(String mobNumber);

	public Customer findByNameIs(String name);

	public List<Customer> findByName(String name);

	public Customer findCustomerWithOrderId(String orderId);

	public List<Customer> getCustomers();

	public List<Customer> findCustomersMultiOrders();

	public void save(Customer customer);

	public void saveAll(Collection<Customer> customers);

	public void flush();
}
