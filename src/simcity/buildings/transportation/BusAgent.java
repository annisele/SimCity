package simcity.buildings.transportation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import agent.Agent;
import simcity.Location;
import simcity.gui.transportation.BusGui;


//Make Changes to bus stop counter
//
public class BusAgent extends Agent implements simcity.interfaces.transportation.Bus {

	class MyPassenger {
		BusPassengerRole role;
		Location startLocation;
		Location destination;
		boolean loaded = false;
		MyPassenger(BusPassengerRole r, Location d) {
			role = r;
			
			destination = d;
		}
		
		void loaded() {
			loaded = true;
		}
	
	    }
	
	public BusAgent(String busname) {
		this.name = busname; 
		Location stop1 = new Location(55, 67);
		Location stop2 = new Location(365, 67);
		Location stop3 = new Location(365, 370);
		Location stop4 = new Location(55, 370);
		busStops.put(0, stop1);
		busStops.put(1, stop2);
		busStops.put(2, stop3);
		busStops.put(3, stop4);
		busStopCounter = 0;
		
	}
		
	private String name;
	private BusGui gui;
	List<MyPassenger> passengers;
	
	public static final int NUM_BUSSTOPS = 4;
	//List<Location> busStops;
	Map<Integer,Location> busStops = new HashMap<Integer,Location>(NUM_BUSSTOPS);
	public Semaphore atDestination = new Semaphore(0, true);
	
	int busStopCounter;
	public enum BusState {stopped, driving};
	public enum BusEvent {loading, arrived};
	
	BusState state;
	BusEvent event;
	
	public void makeBusMove() {		// HACKHACKHACK
		stateChanged();
	}
	
	public void msgWantBus(BusPassengerRole cp, Location l) {
		passengers.add(new MyPassenger(cp, l));
	}
	
	public void msgGettingOn(BusPassengerRole cp, Location l) {
		//passengers.get(cp).loaded();
		for (MyPassenger p : passengers) {
			if (p.role == cp) {
				p.loaded();
			}
		}
	}
	
	public void msgGettingOff(BusPassengerRole cp) {
		passengers.remove(cp);
	}

	// from animation
	public void msgArrived() {
		event = BusEvent.arrived;
	}

	boolean FullyLoaded() {
		for (MyPassenger p : passengers) {
			if (p.startLocation == busStops.get(busStopCounter)) {
				if (p.loaded == false)
					return false;
			}
		}
		return true;
	}
	
	// Scheduler
	public boolean pickAndExecuteAnAction() {
		Drive();
		
		return false;
	}
	
	// Actions
	private void Drive() {
		
		DoGoTo(busStops.get(busStopCounter));
		try {
			atDestination.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		busStopCounter = ((busStopCounter + 1) % 4);
		
		makeBusMove();
	}

	private void Stop() {
		/*for (MyPassenger p : passengers) {
			if (FullyLoaded() == true && p.destination == busStops.get(busStopCounter)) {
				p.role.msgWeHaveArrived(busStops.get(busStopCounter).getX(),
	                              busStops.get(busStopCounter).getY());
			}
			else if (p.loaded == false && p.startLocation == busStops.get(busStopCounter)) {
				p.role.msgBusArriving();
	                      }
			event = BusEvent.loading; */
		
		
		}
	
	
	private void DoGoTo(Location l) {
		//Animation
		//Wait a few Seconds
		gui.DoGoToStop(l.getX(), l.getY());
	}

	public void atDestination() {
		atDestination.release();
	}

	public void setGui(BusGui gui) {
		this.gui = gui;
	}

}




