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
import simcity.buildings.transportation.BusPassengerRole;

public class MockBus implements Bus {
	
    public Semaphore atDestination = new Semaphore(0, true);
	Timer stopTimer = new Timer();
	int busStopCounter = 3;
    List<MyPassenger> passengers = Collections.synchronizedList(new ArrayList<MyPassenger>());
	public BusPassengerRole bp;
    public EventLog log = new EventLog();
    BusEvent event;
    class MyPassenger {
        BusPassengerRole role;
        int startLocation;
        int destination;
        boolean loaded = false;
        MyPassenger(BusPassengerRole r, int s, int d) {
                role = r;                        
                destination = d;
                startLocation = s;
        }
    }
        


	@Override
	public void makeBusMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgWantBus(BusPassenger cp, int s, int d) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Received msgWantBus from BusPassengerRole"));
		
	}

	@Override
	public void msgGettingOn(BusPassenger cp) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Received msgGettingOn from BusPassengerRole"));

		  synchronized(passengers) {
              for (MyPassenger p : passengers) {
                      if (p.role == cp) {
                              p.loaded = true;
                      }
              }
      }
		
		
	}

	@Override
	public void msgGettingOff(BusPassenger cp) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Received msgGettingOn from BusPassengerRole " + cp));

		  synchronized(passengers) {
              for (MyPassenger p : passengers) {
                      if (p.role == cp) {
                              passengers.remove(cp);
                      }
              }
      }
	}

	@Override
	public void msgFinishedLoading() {
		log.add(new LoggedEvent("Received msgFinishedLoading "));
        event = BusEvent.loaded;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgArrived() {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Received msgArrived from busPassenger "));
		event = BusEvent.arrived;
		
	}

	@Override
	public void Drive() {
		// TODO Auto-generated method stub
		  busStopCounter = ((busStopCounter + 1) % 4);
	    	log.add(new LoggedEvent("Received msgArrived from busPassenger "));

	}

	@Override
	public void Stop() {
		// TODO Auto-generated method stub
		  synchronized(passengers) {
              for (MyPassenger p : passengers) {
                      if (p.loaded == false && p.startLocation == busStopCounter) {
                              p.role.msgBusArriving();
                              p.loaded = true;
              }
                              
              }
      }
		
	}

	@Override
	public void DoGoTo(Location l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atDestination() {
		
              //atDestination.release();
	}
	
}