package simcity.buildings.transportation;

import java.util.ArrayList;
import java.util.Collections;
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
import simcity.interfaces.transportation.BusPassenger;


//Make Changes to bus stop counter
//
public class BusAgent extends Agent implements simcity.interfaces.transportation.Bus {
	
	Timer stopTimer = new Timer();
	Directory dir;

	public class MyPassenger {
		BusPassenger role;
		int startLocation;
		int destination;
		public boolean loaded = false;
		MyPassenger(BusPassenger r, int s, int d) {
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
		if (name == "Buster") {
			Location stop1 = new Location(68, 67);
			Location stop2 = new Location(370, 67);
			Location stop3 = new Location(370, 366);
			Location stop4 = new Location(68, 366);
			busStops.put(0, stop1);
			busStops.put(1, stop2);
			busStops.put(2, stop3);
			busStops.put(3, stop4);
			busStopCounter = 3;
		}
		else if (name == "Busta") {
			Location stop1 = new Location(15, 108);
			Location stop2 = new Location(336, 108);
			Location stop3 = new Location(336, 405);
			Location stop4 = new Location(15, 405);
			busStops.put(0, stop1);
			busStops.put(1, stop2);
			busStops.put(2, stop3);
			busStops.put(3, stop4);
			busStopCounter = 3;
		}
		else if (name == "Bismarck") {
			Location stop1 = new Location(15, 30);
			Location stop2 = new Location(190, 30);
			Location stop3 = new Location(360, 30);
			Location stop4 = new Location(360, 168);
			Location stop5 = new Location(360, 280);
			Location stop6 = new Location(168, 280);
			busStops.put(0, stop1);
			busStops.put(1, stop2);
			busStops.put(2, stop3);
			busStops.put(3, stop4);
			busStops.put(4, stop5);
			busStops.put(5, stop6);
			busStopCounter = 5;
		}
		
	}
		
	public String name;
	public BusGui gui;
	public List<MyPassenger> passengers = Collections.synchronizedList(new ArrayList<MyPassenger>());
	
	public static final int NUM_BUSSTOPS = 4;
	//List<Location> busStops;
	public Map<Integer,Location> busStops = new HashMap<Integer,Location>(NUM_BUSSTOPS);
	public Semaphore atDestination = new Semaphore(0, true);
	
	public int busStopCounter;
	public enum BusState {none, stopped, driving};
	public enum BusEvent {none, arrived, loaded};
	
	public BusState state = BusState.stopped;
	public BusEvent event = BusEvent.none;
	
	public void makeBusMove() {		// HACKHACKHACK
		event = BusEvent.loaded;
		stateChanged();
	}
	
	public void msgWantBus(BusPassenger cp, int s, int d) {
		MyPassenger temp = new MyPassenger(cp, s, d);
		temp.loaded = false;
		passengers.add(temp);
		
		stateChanged();
	}
	
	public void msgGettingOn(BusPassenger cp) {
		//passengers.get(cp).loaded();
		synchronized(passengers) {
			for (MyPassenger p : passengers) {
				if (p.role == cp) {
					p.loaded();
				}
			}
		}
		stateChanged();
	}
	
	public void msgGettingOff(BusPassenger cp) {
		synchronized(passengers) {
			for (MyPassenger p : passengers) {
				if (p.role == cp) {
					passengers.remove(cp);
				}
			}
		}
		stateChanged();
	}

	public void msgFinishedLoading() {
		event = BusEvent.loaded;
		stateChanged();
	}
	
	// from animation
	public void msgArrived() {
		event = BusEvent.arrived;
		stateChanged();
	}


	
	// Scheduler
	public boolean pickAndExecuteAnAction() {
		if (state == BusState.stopped && event == BusEvent.loaded){
			state = BusState.driving;
			Drive();
			return true;
		}
		if (state == BusState.driving && event == BusEvent.arrived){
			state = BusState.stopped;
			Stop();
			return true;
		}
		if (state == BusState.stopped && event == BusEvent.arrived) {
			state = BusState.driving;
			Drive();
			return true;
		}
		
		return false;
	}
	
	// Actions
	public void Drive() {
		if (this.getName().equals("Busta") || this.getName().equals("Buster")) {
		busStopCounter = ((busStopCounter + 1) % 4);
		}
		else { busStopCounter = ((busStopCounter + 1) % 6);}
			
		DoGoTo(busStops.get(busStopCounter));
		try {
			atDestination.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		//busStopCounter = ((busStopCounter + 1) % 4);
		msgArrived();
	}

	public void Stop() {
		synchronized(passengers) {
			for (MyPassenger p : passengers) {
				if (p.loaded == false && p.startLocation == busStopCounter) {
					p.role.msgBusArriving();
					p.loaded = true;
		        }
				if (p.loaded == true && p.destination == busStopCounter) {
					p.role.msgWeHaveArrived(dir.getBusStop(busStopCounter).getX(), dir.getBusStop(busStopCounter).getY());
					p.loaded = false;
					//passengers.remove(p);
				}		
			}
		}
		msgFinishedLoading();
			//event = BusEvent.loading; 	
	}
	
	
	public void DoGoTo(Location l) {
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
		//atDestination.release();
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




