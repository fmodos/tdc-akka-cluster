package com.fmodos.subway.fofo;

import static akka.actor.SupervisorStrategy.escalate;
import static akka.actor.SupervisorStrategy.restart;

import com.fmodos.subway.EnumOrder;
import com.fmodos.subway.NewSandwich;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.Terminated;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

public class WaiterActor extends AbstractActor {

	@Override
	public Receive createReceive() {
		return receiveBuilder()//
				.match(Order.class, order -> {
					totalOrders++;
					log("Processando pedido "+order.getSalad());
					getSender().tell(EnumOrder.NEXT_ORDER, getSelf());
					printStatus();
				}).match(NewSandwich.class, newSandwich -> {
					activeCustomers++;
					ActorRef customerActor = getContext().actorOf(Props.create(CustomerActor.class, newSandwich),
							newSandwich.getClientName());
					getContext().watch(customerActor);
					customerActor.tell(EnumOrder.NEXT_ORDER, getSelf());
					printStatus();
				}).matchEquals(EnumOrder.FINISH, newSandwich -> {
					activeCustomers--;
					getContext().unwatch(getSender());
					getSender().tell(Terminated.class, getSender());
					printStatus();
				}).build();
	}
	
	int totalOrders;
	int activeCustomers;

	@Override
	public void preStart() throws Exception {
		super.preStart();
		log("Starting Waiter: " + getSelf().path().toString());
	}

	protected void printStatus() {
		log("ActiveCustomer: " + activeCustomers + ", TotalOrder: " + totalOrders);
	}

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return new OneForOneStrategy(10, Duration.create("1 minute"),
				DeciderBuilder.match(RuntimeException.class, e -> restart())//
						.matchAny(o -> escalate()).build());
	}

	private void log(String log) {
		System.out.println(getSelf().path() + " -> " + log);
	}

}
