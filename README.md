# tdc-akka-cluster

This is the coding demo that was used for the presentation at TDC Florianópolis, slides of the talk:
https://pt.slideshare.net/fmodos/akka-brincando-com-atores

* FifoMain -> Example of a locking FIFO.
* FofoMain -> Example of a WaiterActor that process orders of 2 Customers.
* RouterMain -> Example using a HostessActor that routes messages for WaiterActor using RoundRobinRouter.
* ClusterMain -> Example of an Akka cluster where the HostessActor and the WaiterActor run in different JVM. 

Some of this coding was based in the akka examples: https://github.com/akka/akka-samples
