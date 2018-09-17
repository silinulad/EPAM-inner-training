package com.example.silin.guitarshop.model;

import java.sql.Timestamp;

public class Order {
	private long id;
	private Customer customer;
	private boolean complete;
	private Timestamp time;
	private double totaValue;

	public Order() {
	}

	public Order(long id, Customer customer, boolean complete, Timestamp time, double totaValue) {
		this.id = id;
		this.customer = customer;
		this.complete = complete;
		this.time = time;
		this.totaValue = totaValue;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public double getTotaValue() {
		return totaValue;
	}

	public void setTotaValue(double totaValue) {
		this.totaValue = totaValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Order))
			return false;
		Order other = (Order) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [id=");
		builder.append(id);
		builder.append(", customer=");
		builder.append(customer);
		builder.append(", complete=");
		builder.append(complete);
		builder.append(", time=");
		builder.append(time);
		builder.append(", totaValue=");
		builder.append(totaValue);
		builder.append("]");
		return builder.toString();
	}

}
