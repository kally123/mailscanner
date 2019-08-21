package com.mail.app.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mail.app.dao.OrderDetailsRepository;
import com.mail.app.model.OrderDetails;

public class OrderDetailsServiceImpl implements OrderDetailsService {

	@Autowired
	OrderDetailsRepository orderDetailsrepository;

	@Override
	public OrderDetails findByOrderId(String orderId) {
		return orderDetailsrepository.findByOrderId(orderId);
	}

	@Override
	public int getOrderDetailsCount() {
		return orderDetailsrepository.getOrderDetailsCount();
	}

}
