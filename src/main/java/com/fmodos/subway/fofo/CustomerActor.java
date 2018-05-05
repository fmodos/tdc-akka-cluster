package com.fmodos.subway.fofo;

import java.util.Optional;

import com.fmodos.subway.EnumOrder;
import com.fmodos.subway.NewSandwich;
import com.fmodos.subway.ITEM;

import akka.actor.AbstractActor;

public class CustomerActor extends AbstractActor {

	@Override
	public Receive createReceive() {
		return receiveBuilder().//
				matchEquals(EnumOrder.NEXT_ORDER, salad -> {
					doOrder();
				}).build();
	}
	
	private NewSandwich sandwich;
	
	private int totalOrders;
	
	public CustomerActor(NewSandwich sandwich) {
		this.sandwich = sandwich;
	}

	private void doOrder() throws InterruptedException {
		Thread.sleep(sandwich.getTimeout());
		handleFaint();
		totalOrders++;
		if (totalOrders == 3) {
			log("Encerro pedido " + sandwich.getClientName());
			getSender().tell(EnumOrder.FINISH, getSelf());
		} else {
			log("Pedido de " + sandwich.getClientName());
			getSender().tell(new Order(ITEM.LETTUCE), getSelf());
		}
	}

	private void handleFaint() {
		if (totalOrders == 1 && sandwich.isFaint()) {
			log("Desmaiou " + sandwich.getClientName());
			throw new RuntimeException("Problem here");
		}
	}

	@Override
	public void preStart() throws Exception {
		super.preStart();
		System.out.println("Starting Customer: " + getSelf().path().toString());
	}

	@Override
	public void preRestart(Throwable reason, Optional<Object> message) throws Exception {
		super.preRestart(reason, message);
		log("Reiniciando : " + totalOrders);
		sandwich.setFaint(false);
		doOrder();
	}

	private void log(String log) {
		System.out.println(getSelf().path() + " -> " + log);
	}

}
