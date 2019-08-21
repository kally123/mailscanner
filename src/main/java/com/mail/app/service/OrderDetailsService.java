package com.mail.app.service;

import com.mail.app.model.OrderDetails;

public interface OrderDetailsService {
	public OrderDetails findByOrderId(String orderId);

	public int getOrderDetailsCount();
}
