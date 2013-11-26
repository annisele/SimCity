package simcity.buildings.transportation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import agent.Agent;
import simcity.Directory;
import simcity.Location;
import simcity.gui.transportation.BusGui;


//Make Changes to bus stop counter
//
public class BusAgent extends Agent implements simcity.interfaces.transportation.Bus {
	
	Timer stopTimer = new Timer();
	Directory dir;

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
		
		void loaded() {
			loaded = true;
		}
	
	    }
	
	public BusAgent(String busname) {
		this.name = busname; 
		Location stop1 = new Location(68, 67);
		Location stop2 = new Location(370, 67);
		Location stop3 = new Location(370, 366);
		Location stop4 = new Location(68, 366);
		busStops.put(0, stop1);
		busStops.put(1, stop2);
		busStops.put(2, stop3);
		busStops.put(3, stop4);
		busStopCounter = 0;
		
	}
		
	private String name;
	private BusGui gui;
	List<MyPassenger> passengers = new ArrayList<MyPassenger>();
	
	public static final int NUM_BUSSTOPS = 4;
	//List<Location> busStops;
	Map<Integer,Location> busStops = new HashMap<Integer,Location>(NUM_BUSSTOPS);
	public Semaphore atDestination = new Semaphore(0, true);
	
	int busStopCounter;
	public enum BusState {stopped, driving};
	public enum BusEvent {loading, arrived};
	
	BusState state = BusState.stopped;
	BusEvent event;
	
	public void makeBusMove() {		// HACKHACKHACK
		//stateChanged();
		Drive();
	}
	
	public void msgWantBus(BusPassengerRole cp, int s, int d) {
		MyPassenger temp = new MyPassenger(cp, s, d);
		temp.loaded = false;
		passengers.add(temp);
		
		System.out.println("In msgWantBus in BusAgent");
		stateChanged();
	}
	
	public void msgGettingOn(BusPassengerRole cp) {
		//passengers.get(cp).loaded();
		for (MyPassenger p : passengers) {
			if (p.role == cp) {
				p.loaded();
			}
		}
		stateChanged();
	}
	
	public void msgGettingOff(BusPassengerRole cp) {
		passengers.remove(cp);
		stateChanged();
	}

	// from animation
	public void msgArrived() {
		//event = BusEvent.arrived;
		stateChanged();
	}

	boolean FullyLoaded() {
		for (MyPassenger p : passengers) {
			if (p.startLocation == (busStopCounter)) {
				if (p.loaded == false)
					return false;
			}
		}
		return true;
	}
	
	// Scheduler
	public boolean pickAndExecuteAnAction() {
		if (passengers.size() == 0) {
		if (state == BusState.stopped){
					Drive();
					state = BusState.driving;
					return true;
			}
		else if (state == BusState.driving){
			return false;
		}
		}
		else if (passengers.size() > 0) {
			if (state == BusState.stopped) {	
				state = BusState.driving;
				Stop();
				return true;
			}
			else if (state == BusState.driving) {
				return false;
			}


		
		
	}
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
		for (MyPassenger p : passengers) {
			if (p.loaded == true && p.destination == busStopCounter) {
				p.role.msgWeHaveArrived(dir.getBusStop(busStopCounter).getX(), dir.getBusStop(busStopCounter).getY());
				passengers.remove(p);
			}
			else if (p.loaded == false && p.startLocation == busStopCounter) {
				p.role.msgBusArriving();
				p.loaded = true;
	            }
		}
			//event = BusEvent.loading; 
			
			
		
		}
	
	
	private void DoGoTo(Location l) {
		//Animation
		//Wait a few Seconds
		gui.DoGoToStop(l.getX(), l.getY());
	}

	public void atDestination() {
		  
		  stopTimer.schedule(new TimerTask() {
              public void run() {
                      atDestination.release();
                      
              }
      },
     300);
	}

	public void setGui(BusGui gui) {
		this.gui = gui;
	}
	
	
	public void clear() {
		stopTimer.cancel();
		stopTimer.purge();
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setDirectory(Directory dir) {
		this.dir = dir;
	}

}




