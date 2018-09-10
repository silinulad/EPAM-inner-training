package com.example.store.casboxes;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.store.customers.Customer;
import com.example.store.customers.CustomerQueue;

public class Cashbox implements Runnable, Comparable<Cashbox> {
	private final static Logger LOGGER = LoggerFactory.getLogger(Cashbox.class);

	private static int counter = 0;
	private final int id = counter++;
	private int countServicedCustomers = 0;
	private CustomerQueue commonCustomerQueue;
	private Queue<Customer> localCustomerQueue = new PriorityQueue<>();
	private boolean canServiseCustomers = true;

	public Cashbox(CustomerQueue customers) {
		this.commonCustomerQueue = customers;
	}

	@Override
	public void run() {
		LOGGER.debug(this + " started ");
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					while (localCustomerQueue.size() == 0) {
						LOGGER.debug(this + " started wait while the local customer queue will be not empty");
						wait();
					}
				}

				Customer servicingCustomer;
				synchronized (servicingCustomer = localCustomerQueue.peek()) {
					LOGGER.debug(this + " took from local customer queue : " + servicingCustomer);

					TimeUnit.SECONDS.sleep(servicingCustomer.getServiceTime());
					servicingCustomer.setServeced(true);
					servicingCustomer.notifyAll();
				}

				synchronized (this) {
					localCustomerQueue.poll();
					commonCustomerQueue.getCustomers().remove(servicingCustomer);
					LOGGER.debug(this + " waits a job");
					notifyAll();
				}

				synchronized (this) {
					countServicedCustomers++;
					while (!canServiseCustomers) {
						LOGGER.debug(this + " waits a job");
						wait();
					}
				}
			}
		} catch (InterruptedException e) {
			LOGGER.error(this + " interrupted");
			return;
		}
	}

	public synchronized void doSomethingElse() {
		canServiseCustomers = false;
		commonCustomerQueue.getCustomers().addAll(localCustomerQueue);
		localCustomerQueue.clear();
		LOGGER.debug(this + " is idle and gives away its elements to the " + commonCustomerQueue);
	}

	public synchronized void serviceCustomerQueue() {
		assert !canServiseCustomers : "it's alreaady servecing: " + this;
		canServiseCustomers = true;
		notifyAll();

		LOGGER.debug(getClass().getSimpleName() + " servicing customers");
	}

	public synchronized void enqueueCustomer(Customer customer) {
		localCustomerQueue.offer(customer);
		notifyAll();
	}

	public synchronized void dequeueCustomer(Customer customer) {
		localCustomerQueue.remove(customer);
		notifyAll();
	}

	public Queue<Customer> getLocalCustomerQueue() {
		return localCustomerQueue;
	}

	public int getId() {
		return id;
	}

	public static int getCounter() {
		return counter;
	}

	public int getCountServicedCustomers() {
		return countServicedCustomers;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "-" + id + " serviced " + countServicedCustomers
				+ " customers, local queue size: " + localCustomerQueue.size() + " - " + localCustomerQueue;
	}

	@Override
	public synchronized int compareTo(Cashbox other) {
		return localCustomerQueue.size() - other.localCustomerQueue.size();
	}

}
