package com.mail.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mail.app.model.Customer;
import com.mail.app.model.RepeatedCustomer;

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

	// @Query(value = "select c from customer c where size(c.orderDetails) > 1")
	// public List<Customer> findCustomersMultiOrders();

	@Query(nativeQuery = true, value = "select count(*) from customer")
	public int getCustomerCount();

	@Query(nativeQuery = true, value = "select c.name, count(od.customer_id) count, sum(bill) as total from customer c, orderdetails od where c.id=od.customer_id group by c.name having count(od.customer_id) >1 order by total desc")
	public List<RepeatedCustomer> getRepeatedCustomers();
}
