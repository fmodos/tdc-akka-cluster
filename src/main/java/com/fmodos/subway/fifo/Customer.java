package com.fmodos.subway.fifo;

import com.fmodos.subway.NewSandwich;
import com.fmodos.subway.ITEM;
import com.fmodos.subway.fofo.Order;

public class Customer {

	private NewSandwich sandwich;

	int totalOrders;

	public Customer(NewSandwich sandwich) {
		this.sandwich = sandwich;
	}

	public NewSandwich getSandwich() {
		return sandwich;
	}

	public boolean isFinished() {
		return totalOrders == 3;
	}

	public Order nextOrder() {
		System.out.println("Pedido de " + sandwich.getClientName());
		try {
			Thread.sleep(sandwich.getTimeout());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		totalOrders++;
		return new Order(ITEM.LETTUCE);
	}
}
