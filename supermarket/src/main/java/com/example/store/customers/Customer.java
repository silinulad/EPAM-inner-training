package com.example.store.customers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.store.casboxes.Cashbox;
import com.example.store.casboxes.CashboxManager;

public class Customer implements Runnable, Comparable<Customer> {
	private final static Logger LOGGER = LoggerFactory.getLogger(Customer.class);

	private static int counter = 0;
	private final int id = counter++;
	private final int serviceTime;
	private final int insolenceDegree;
	private volatile CashboxManager cashboxManager;
	private Cashbox currentCashbox;
	private volatile boolean isServiced;

	public Customer(int serviceTime, int insolenceDegree, CashboxManager cashboxManager) {
		this.serviceTime = serviceTime;
		this.insolenceDegree = insolenceDegree;
		this.cashboxManager = cashboxManager;
	}

	public int getServiceTime() {
		return serviceTime;
	}

	public int getInsolenceDegree() {
		return insolenceDegree;
	}

	public static int getCounter() {
		return counter;
	}

	public int getId() {
		return id;
	}

	public boolean isServeced() {
		return isServiced;
	}

	public void setServeced(boolean isServeced) {
		this.isServiced = isServeced;
	}

	public CashboxManager getCashboxManager() {
		return cashboxManager;
	}

	@Override
	public void run() {
		LOGGER.debug(this + " started ");

		currentCashbox = findShortestQueue(cashboxManager);
		currentCashbox.enqueueCustomer(this);

		while (!isServiced) {
			// if true tries to change a cashbox where a customer queue is shorter
			if (this != currentCashbox.getLocalCustomerQueue().peek()) {
				Cashbox oldCasbox = null;
				synchronized (this) {
					Cashbox bufferedCashbox = cashboxManager.getWorkingCashboxes().peek();
					if (currentCashbox.getId() != bufferedCashbox.getId()) {
						oldCasbox = currentCashbox;
						currentCashbox = bufferedCashbox;
					}
				}
				if (oldCasbox != null) {
					oldCasbox.dequeueCustomer(this);
					currentCashbox.enqueueCustomer(this);
					LOGGER.debug(this + " changed " + oldCasbox + " to " + currentCashbox);
				}
			}
		}
		LOGGER.debug(this + " was serviced by " + currentCashbox);
	}

	private Cashbox findShortestQueue(CashboxManager cashboxManager) {
		int shortestQueueLength = Integer.MAX_VALUE;
		Cashbox currentCashbox = null;
		for (Cashbox cashbox : cashboxManager.getWorkingCashboxes()) {
			int currentQueueLength = cashbox.getLocalCustomerQueue().size();
			if (currentQueueLength == 0) {
				shortestQueueLength = currentQueueLength;
				currentCashbox = cashbox;
				break;
			} else {
				if (currentQueueLength < shortestQueueLength) {
					shortestQueueLength = currentQueueLength;
					currentCashbox = cashbox;
				}
			}
		}
		return currentCashbox;
	}

	@Override
	public synchronized int compareTo(Customer other) {
		return other.insolenceDegree - insolenceDegree;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + insolenceDegree;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Customer))
			return false;
		Customer other = (Customer) obj;
		if (insolenceDegree != other.insolenceDegree)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName());
		builder.append(id);
		builder.append(" [serviceTime=");
		builder.append(serviceTime);
		builder.append(", insolenceDegree=");
		builder.append(insolenceDegree);
		builder.append("]");
		return builder.toString();
	}

}
