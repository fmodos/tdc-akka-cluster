package com.fmodos.subway.fifo;

import com.fmodos.subway.NewSandwich;

public class Waiter {

	int totalOrders;
	private int activeCustomers;

	public void receiveOrder(NewSandwich order) {
		Customer customer = new Customer(order);
		activeCustomers++;
		printStatus();
		while (!customer.isFinished()) {
			totalOrders++;
			customer.nextOrder();
			printStatus();
		}
		activeCustomers--;
		printStatus();
	}

	protected void printStatus() {
		System.out.println("TotalOrder: " + totalOrders);
		System.out.println("ActiveCustomer: " + activeCustomers);
	}

}
