package com.mail.app.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orderdetails")
public class OrderDetails {

	@Id
	private String orderId;
	private String orderDate;
	private Double bill;
	private String orderSummary;
	private Double restaurantPromo;
	private Integer packagingCharge;
	private Double zomatoPromo;
	private String paymentMode;

	public OrderDetails() {

	}

	public OrderDetails(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public Double getBill() {
		return bill;
	}

	public void setBill(Double bill) {
		this.bill = bill;
	}

	public String getOrderSummary() {
		return orderSummary;
	}

	public void setOrderSummary(String orderSummary) {
		this.orderSummary = orderSummary;
	}

	public Double getRestaurantPromo() {
		return restaurantPromo;
	}

	public void setRestaurantPromo(Double restaurantPromo) {
		this.restaurantPromo = restaurantPromo;
	}

	public Integer getPackagingCharge() {
		return packagingCharge;
	}

	public void setPackagingCharge(Integer packagingCharge) {
		this.packagingCharge = packagingCharge;
	}

	public Double getZomatoPromo() {
		return zomatoPromo;
	}

	public void setZomatoPromo(Double zomatoPromo) {
		this.zomatoPromo = zomatoPromo;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bill == null) ? 0 : bill.hashCode());
		result = prime * result + ((orderDate == null) ? 0 : orderDate.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((orderSummary == null) ? 0 : orderSummary.hashCode());
		result = prime * result + ((packagingCharge == null) ? 0 : packagingCharge.hashCode());
		result = prime * result + ((paymentMode == null) ? 0 : paymentMode.hashCode());
		result = prime * result + ((restaurantPromo == null) ? 0 : restaurantPromo.hashCode());
		result = prime * result + ((zomatoPromo == null) ? 0 : zomatoPromo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDetails other = (OrderDetails) obj;
		if (bill == null) {
			if (other.bill != null)
				return false;
		} else if (!bill.equals(other.bill))
			return false;
		if (orderDate == null) {
			if (other.orderDate != null)
				return false;
		} else if (!orderDate.equals(other.orderDate))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (orderSummary == null) {
			if (other.orderSummary != null)
				return false;
		} else if (!orderSummary.equals(other.orderSummary))
			return false;
		if (packagingCharge == null) {
			if (other.packagingCharge != null)
				return false;
		} else if (!packagingCharge.equals(other.packagingCharge))
			return false;
		if (paymentMode == null) {
			if (other.paymentMode != null)
				return false;
		} else if (!paymentMode.equals(other.paymentMode))
			return false;
		if (restaurantPromo == null) {
			if (other.restaurantPromo != null)
				return false;
		} else if (!restaurantPromo.equals(other.restaurantPromo))
			return false;
		if (zomatoPromo == null) {
			if (other.zomatoPromo != null)
				return false;
		} else if (!zomatoPromo.equals(other.zomatoPromo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "orderId=" + orderId + ", orderDate=" + orderDate + ", bill=" + bill + ", orderSummary=" + orderSummary
				+ ", restaurantPromo=" + restaurantPromo + ", packagingCharge=" + packagingCharge + ", zomatoPromo="
				+ zomatoPromo + ", paymentMode=" + paymentMode;
	}

}
