package simcity.test.transportation.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Semaphore;

import simcity.Location;
import simcity.test.mock.EventLog;
import simcity.test.mock.LoggedEvent;
import simcity.interfaces.transportation.Bus;
import simcity.interfaces.transportation.BusPassenger;
import simcity.buildings.transportation.BusAgent.BusEvent;
import simcity.buildings.transportation.BusPassengerRole.PassengerEvent;
import simcity.buildings.transportation.BusPassengerRole.PassengerState;
import simcity.buildings.transportation.BusAgent;

public class MockBusPassenger implements BusPassenger {
	
	public BusAgent bus;
	public int destination;
	public int startingLocation;
	public int xLoc;
	public int yLoc;
	public enum PassengerState {offBus, waitingForBus, onBus};
	public enum PassengerEvent {atBusStop, busArriving, busStopping};
	public PassengerState state = PassengerState.offBus;
	public PassengerEvent event;
    public EventLog log = new EventLog();


	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgBusTo(int s, int d) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Received msgBusTo to the starting location of " + s + " and destination of " + d ));
		event = PassengerEvent.atBusStop;
		
		
	}

	@Override
	public void msgBusArriving() {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Received msgBusArriving from Bus"));
		event = PassengerEvent.busArriving;


		
	}

	@Override
	public void msgWeHaveArrived(int x, int y) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Received msgWeHaveArrived"));
		event = PassengerEvent.busArriving;
		
	}

	@Override
	public void CallBus() {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Method CallBus()"));

		bus.msgWantBus(this, startingLocation, destination);

		
	}

	@Override
	public void GetIn() {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Method GetIn()"));

        bus.msgGettingOn(this);

		
	}

	@Override
	public void GetOut() {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Method GetOut()"));
		bus.msgGettingOff(this);
		
	}

	@Override
	public void setBus(BusAgent b) {
		// TODO Auto-generated method stub
		bus = b;
	}
	
}
