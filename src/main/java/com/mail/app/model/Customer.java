package com.mail.app.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.util.StringUtils;

@Entity
@Table(name = "customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", unique = true)
	private String name;
	private String mobNumber;
	private String address;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "customer_id")
	private List<OrderDetails> orderDetails = new ArrayList();

	@Transient
	private String concatenatedOrderIds;

	public String getConcatenatedOrderIds() {
		if (StringUtils.isEmpty(concatenatedOrderIds)) {
			return concatenatedOrderIds();
		}
		return concatenatedOrderIds;
	}

	public static final String CUSTOMER_NAME = "Customer Name:";
	public static final String CONTACT_NO = "Contact No.:";
	public static final String ADDRESS = "Customer Address:";
	public static final String ORDER_SUMMARY = "Order Summary";
	public static final String ZOMATO_PROMO = "Zomato Promo";
	public static final String RESTAURANT_PROMO = "Restaurant Promo";
	public static final String PACKAGING_CHARGE = "Packaging Charge";
	public static final String PAYMENT_MODE = "Paid by :";
	public static final String ORDER_ID = "Order ID:";
	public static final String DATE = "Date:";

	public Customer() {

	}

	public Customer(String name, String mobNumber, String address) {
		this.name = name;
		this.mobNumber = mobNumber;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobNumber() {
		return mobNumber;
	}

	public void setMobNumber(String mobNumber) {
		this.mobNumber = mobNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mobNumber == null) ? 0 : mobNumber.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((orderDetails == null) ? 0 : orderDetails.hashCode());
		return result;
	}

	public String concatenatedOrderIds() {
		StringBuffer sb = new StringBuffer();
		for (OrderDetails od : getOrderDetails()) {
			sb.append(od.getOrderId() + ", ");
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mobNumber == null) {
			if (other.mobNumber != null)
				return false;
		} else if (!mobNumber.equals(other.mobNumber))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orderDetails == null) {
			if (other.orderDetails != null)
				return false;
		} else if (!orderDetails.equals(other.orderDetails))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", mobNumber=" + mobNumber + ", address=" + address
				+ ", orderDetails=" + orderDetails + "]";
	}

}