package com.example.store.casboxes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.store.customers.CustomerQueue;

public class CashboxManager extends JPanel implements Runnable {
	private final static Logger LOGGER = LoggerFactory.getLogger(CashboxManager.class);

	private static final long serialVersionUID = 3438976249070625827L;

	private ExecutorService executor;
	private CustomerQueue customreQueue;
	private Queue<Cashbox> workingCashboxes = new PriorityQueue<>();
	private Queue<Cashbox> idleCashboxes = new LinkedList<>();
	private int adjustmentPeriod;

	public CashboxManager(ExecutorService executor, CustomerQueue customreQueue, int initialCashboxNumber) {
		this.executor = executor;
		this.customreQueue = customreQueue;
		this.adjustmentPeriod = ThreadLocalRandom.current().nextInt(500, 1500); // into seconds
		initiCashbox(initialCashboxNumber);
	}

	private void adjustCasboxNumber() throws InterruptedException {
		if (customreQueue.getCustomers().size() / workingCashboxes.size() > 10) {
			LOGGER.debug("customer queue size:" + customreQueue.getCustomers().size() + ", working cashboxes size: "
					+ workingCashboxes.size());
			if (idleCashboxes.size() > 0) {
				Cashbox cashbox = idleCashboxes.remove();
				cashbox.serviceCustomerQueue();
				workingCashboxes.offer(cashbox);
				LOGGER.debug("it was taken idle " + cashbox);
				return;
			}
			Cashbox cashbox = new Cashbox(customreQueue);
			executor.execute(cashbox);
			workingCashboxes.add(cashbox);
			LOGGER.debug("it was created new " + cashbox);
			return;

		}
		if (workingCashboxes.size() > 2 && customreQueue.getCustomers().size() / workingCashboxes.size() < 5) {
			LOGGER.debug(workingCashboxes.peek() + " goes to stash");
			doOtherWork();
		}
		if (customreQueue.getCustomers().size() == 0) {

			while (workingCashboxes.size() > 2) {
				LOGGER.debug("while the workingCashbox size >2 go to stash");
				doOtherWork();
			}
		}
	}

	private void doOtherWork() {
		Cashbox cashbox = workingCashboxes.poll();
		cashbox.doSomethingElse();
		boolean isAdded = idleCashboxes.offer(cashbox);
		if (isAdded) {
			LOGGER.debug(cashbox.getClass().getSimpleName() + cashbox.getId() + " was added to the idleCashboxes");
		}
	}

	private void initiCashbox(int initialCashboxNumber) {
		for (int i = 0; i < initialCashboxNumber; i++) {
			Cashbox cashbox = new Cashbox(customreQueue);
			executor.execute(cashbox);
			workingCashboxes.add(cashbox);
		}
	}

	public CustomerQueue getCustomreQueue() {
		return customreQueue;
	}

	public Queue<Cashbox> getWorkingCashboxes() {
		return workingCashboxes;
	}

	public Queue<Cashbox> getIdleCashboxes() {
		return idleCashboxes;
	}

	@Override
	public void run() {
		LOGGER.debug(this + " started ");
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(adjustmentPeriod);
				adjustCasboxNumber();
				System.out.print("\n{\n");
				for (Cashbox cashbox : workingCashboxes) {
					LOGGER.info("working " + cashbox);
				}
				for (Cashbox cashbox : idleCashboxes) {
					LOGGER.info("idle " + cashbox);
				}
				System.out.println("}\n");
			}
		} catch (InterruptedException e) {
			LOGGER.error(this + " interrupted");
			return;
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // clears background
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.DARK_GRAY);
		StringBuilder builder = new StringBuilder();
		g2d.drawString("It is a very minimalistic GUI", getWidth() / 2 - 100, 20);
		for (Cashbox cashbox : getWorkingCashboxes()) {
			builder.append(cashbox);
			g2d.drawString(builder.toString(), 10, 50);
		}

	}

	public void drawShopping() throws InterruptedException {
		while (!Thread.currentThread().isInterrupted()) {
			repaint();
		}
	}
}
