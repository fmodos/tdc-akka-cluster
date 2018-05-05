package com.fmodos.subway.fofo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.fmodos.subway.NewSandwich;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.cluster.routing.ClusterRouterPool;
import akka.cluster.routing.ClusterRouterPoolSettings;
import akka.routing.RoundRobinPool;

public class Hostess extends AbstractActor {

	// private ActorRef waiters = getContext().actorOf(new
	// RoundRobinPool(2).props(Props.create(Waiter.class)), "waiter");

	int totalInstances = 2;
	int maxInstancesPerNode = 1;
	boolean allowLocalRoutees = false;
	Set<String> useRoles = new HashSet<>(Arrays.asList("compute"));
	ActorRef waiters = getContext()
			.actorOf(
					new ClusterRouterPool(new RoundRobinPool(2), new ClusterRouterPoolSettings(totalInstances,
							maxInstancesPerNode, allowLocalRoutees, useRoles)).props(Props.create(WaiterActor.class)),
					"waiterRouter");

	@Override
	public void preStart() throws Exception {
		super.preStart();
		log("Starting " + getSelf().path());
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(NewSandwich.class, s -> {
			waiters.tell(s, getSelf());
		}).build();
	}

	private void log(String log) {
		System.out.println(getSelf().path() + " -> " + log);
	}

}
