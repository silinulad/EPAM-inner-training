package com.example.store.customers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.store.casboxes.CashboxManager;

public class CustomerGenerator implements Runnable {
	private final static Logger LOGGER = LoggerFactory.getLogger(CustomerGenerator.class);

	private CustomerQueue customers;
	private CashboxManager cashboxManager;
	private ExecutorService executor;

	public CustomerGenerator(CustomerQueue customers, CashboxManager cashboxManager, ExecutorService executor) {
		this.customers = customers;
		this.cashboxManager = cashboxManager;
		this.executor = executor;
	}

	@Override
	public void run() {
		LOGGER.debug(this + " started ");
		// MIN... <= < MAX...
		final int MIN_TIMEOUT = 500; // included
		final int MAX_TIMEOUT = 1500; // excluded
		final int MIN_TIME_SERVECE = 5; // included
		final int MAX_TIME_SERVECE = 10; // excluded
		final int MIN_INSOLENCE_DEGREE = 1; // included
		final int MAX_INSOLENCE_DEGREE = 10; // excluded
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(getIntNumber(MIN_TIMEOUT, MAX_TIMEOUT));

				Customer customer = new Customer(getIntNumber(MIN_TIME_SERVECE, MAX_TIME_SERVECE),
						getIntNumber(MIN_INSOLENCE_DEGREE, MAX_INSOLENCE_DEGREE), cashboxManager);

				executor.execute(customer);
				customers.getCustomers().put(customer);

				LOGGER.debug(customer.getClass().getSimpleName() + customer.getId() + " is created, queue size: "
						+ customers.getCustomers().size());
			}
		} catch (InterruptedException e) {
			LOGGER.error(this + " interrupted");
			return;
		}
	}

	private int getIntNumber(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max);
	}

	public CashboxManager getCashboxManager() {
		return cashboxManager;
	}

	public CustomerQueue getCustomers() {
		return customers;
	}

}
