package com.example.store.customers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class CustomerQueue {

	private static int counter = 0;
	private final int id = counter++;

	private BlockingQueue<Customer> customers;

	public CustomerQueue() {
		customers = new PriorityBlockingQueue<>();
	}

	public BlockingQueue<Customer> getCustomers() {
		return customers;
	}

	@Override
	public String toString() {
		if (getCustomers().isEmpty()) {
			return "customer's queue is empty";
		}
		StringBuilder result = new StringBuilder();
		for (Customer customer : customers) {
			result.append(customer);
		}
		return getClass().getSimpleName() + id + " size: " + getCustomers().size() + " - " + result.toString();
	}

}
