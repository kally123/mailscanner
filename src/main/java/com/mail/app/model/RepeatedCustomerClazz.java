package com.mail.app.model;

public class RepeatedCustomerClazz {
	private String name;
	private int count;
	private double total;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public RepeatedCustomerClazz(String name, int count, double total) {
		this.name = name;
		this.count = count;
		this.total = total;
	}
}
