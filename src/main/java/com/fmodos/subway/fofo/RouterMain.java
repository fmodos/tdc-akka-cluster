package com.fmodos.subway.fofo;

import com.fmodos.subway.NewSandwich;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class RouterMain {

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("SubwaySystem");
		ActorRef hostess = system.actorOf(Props.create(HostessActor.class, false), "hostess");

		hostess.tell(new NewSandwich("Desmaiar", 1000, true), null);
		hostess.tell(new NewSandwich("Modos", 1000), null);
	}

}
