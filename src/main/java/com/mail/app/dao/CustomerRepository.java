package com.mail.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mail.app.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	@Query(nativeQuery = true, value = "select * from customer where name= ?1 and mob_number = ?2")
	public Customer findByNameAndMobNumber(String name, String mobNumber);

	@Query(nativeQuery = true, value = "select * from customer where name= ?1")
	public Customer findByNameIs(String name);

	@Query(nativeQuery = true, value = "select * from customer where name= ?1")
	public List<Customer> findByName(String name);

	public Customer findByMobNumber(String mobNumber);

	@Query(nativeQuery = true, value = "select * from customer c, orderdetails od where c.id = od.customer_id and od.order_id= ?1")
	public Customer findCustomerWithOrderId(String orderId);

	@Query(nativeQuery = true, value = "select * from customer order by id desc")
	public List<Customer> getCustomers();

	@Query(nativeQuery = true, value = "select * from customer c having c.order_details >2 order by id desc")
	public List<Customer> findCustomersMultiOrders();

}
