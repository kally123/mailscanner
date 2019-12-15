package com.mail.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mail.app.model.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, String> {

	@Query(nativeQuery = true, value = "select * from orderdetails where order_id= \"1?\"")
	public OrderDetails findByOrderId(String orderId);

	@Query(nativeQuery = true, value = "select count(*) from orderdetails")
	public int getOrderDetailsCount();

	public Long countByOrderDate(String orderDate);

}
