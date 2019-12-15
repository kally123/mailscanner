package com.mail.app.service;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mail.app.model.Customer;
import com.mail.app.model.RepeatedCustomer;
import com.mail.app.repository.CustomerRepository;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository repository;

	@Override
	public Customer findByNameAndMobNumber(String name, String mobNumber) {
		return repository.findByNameAndMobNumber(name, mobNumber);
	}

	@Override
	public Customer findByNameIs(String name) {
		return repository.findByNameIs(name);
	}

	@Override
	public List<Customer> findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public Customer findByMobNumber(String mobNumber) {
		return repository.findByMobNumber(mobNumber);
	}

	@Override
	public Customer findCustomerWithOrderId(String orderId) {
		return repository.findCustomerWithOrderId(orderId);
	}

	@Override
	public List<Customer> getCustomers() {
		return repository.getCustomers();
	}

	@Override
	public void save(Customer customer) {
		repository.save(customer);
	}

	@Override
	public void saveAll(Collection<Customer> customers) {
		repository.saveAll(customers);
		repository.flush();
	}

	@Override
	public void flush() {
		repository.flush();

	}

	@Override
	public List<Customer> findCustomersMultiOrders() {
		return repository.getCustomers();
	}

	@Override
	public int getCustomerCount() {
		return repository.getCustomerCount();
	}

	@Override
	public List<RepeatedCustomer> getRepeatedCustomers() {
		return repository.getRepeatedCustomers();
	}

}
