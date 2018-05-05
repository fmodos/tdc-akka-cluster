package com.fmodos.subway.fofo;

import com.fmodos.subway.ITEM;

public class Order {

	private final ITEM salad;

	public Order(ITEM salad) {
		this.salad = salad;
	}

	public ITEM getSalad() {
		return salad;
	}

}
