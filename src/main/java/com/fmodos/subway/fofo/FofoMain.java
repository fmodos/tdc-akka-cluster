package com.fmodos.subway.fofo;

import com.fmodos.subway.NewSandwich;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class FofoMain {

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("SubwaySystem");
		ActorRef waiter = system.actorOf(Props.create(WaiterActor.class), "waiter");

		int delayToChoose = 10000;
		waiter.tell(new NewSandwich("SlowClient", delayToChoose), ActorRef.noSender());
		waiter.tell(new NewSandwich("FastClient", 0, true), ActorRef.noSender());

	}

}
