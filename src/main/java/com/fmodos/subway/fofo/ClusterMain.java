package com.fmodos.subway.fofo;

import com.fmodos.subway.NewSandwich;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ClusterMain {

	public static void main(String[] args) throws Exception {
		// startup(new String[] { "2552"});
		if (args.length == 0) {
			startHostess(args);
		} else {
			startup(args);
		}
	}

	public static void mainAll(String[] args) throws Exception {
		if (args.length == 0) {
			startup(new String[] { "2551", "2552", "0" });
			startHostess(args);
		} else {
			startup(args);
		}
	}

	public static void startHostess(String[] ports) throws InterruptedException {
		ActorSystem system = ActorSystem.create("ClusterSystem", ConfigFactory.load("stats1"));
		ActorRef hostess = system.actorOf(Props.create(HostessActor.class, true), "hostess");
		Thread.sleep(10000);
		for (int i = 0; i < 100; i++) {
			hostess.tell(new NewSandwich("Modos" + i, 0), null);
			hostess.tell(new NewSandwich("Fabiano" + i, 1000), null);
		}
	}

	public static void startup(String[] ports) {
		for (String port : ports) {
			Config config = ConfigFactory
					.parseString(
							"akka.remote.netty.tcp.port=" + port + "\n" + "akka.remote.artery.canonical.port=" + port)
					.withFallback(ConfigFactory.parseString("akka.cluster.roles = [compute]"))
					.withFallback(ConfigFactory.load("stats1"));

			ActorSystem.create("ClusterSystem", config);			
		}

	}

}
