package com.fmodos.subway.fifo;

import com.fmodos.subway.NewSandwich;

public class FifoMain {

	public static void main(String[] args) {
		Waiter waiter = new Waiter();
		int delayToChoose = 1000;
		waiter.receiveOrder(new NewSandwich("Slow Client", delayToChoose));
		waiter.receiveOrder(new NewSandwich("Fast Client", 0));
	}

}
