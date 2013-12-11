package simcity.buildings.transportation;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import simcity.Directory;
import simcity.Location;
import simcity.gui.transportation.BusGui;
import simcity.interfaces.transportation.BusPassenger;
import agent.Agent;

public class BusAgent extends Agent implements simcity.interfaces.transportation.Bus {
	
	// Data ////////////////////////////////////////////////////////////////////////////////////////////
	
	Directory dir;
	public String name;
	private BusGui gui;
		
	private List<MyPassenger> passengers = Collections.synchronizedList(new ArrayList<MyPassenger>());
	
	private Map<Integer, Location> busRoute = new HashMap<Integer, Location>();
	private Map<Integer, Location> busStops = new HashMap<Integer,Location>();
	private Map<Integer, Intersection> stoplights = new HashMap<Integer, Intersection>();
	
	private int busRouteCounter;
	private int busStopCounter;
	
	public static final int NUM_BUSSTOPS = 10;
	public enum BusState {none, stopped, driving};
	public enum BusEvent {none, arrived, arrivedAtBusStop, loaded};
	private BusState state = BusState.stopped;
	private BusEvent event = BusEvent.none;
	
	private Timer stopTimer = new Timer();
	private Semaphore atDestination = new Semaphore(0, true);
	
	// Contructor ///////////////////////////////////////////////////////////////////////////////////////
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
		else if (name == "clockwise") {
			List<Location> tempList = Directory.defineClockwiseBusRoute();
			for(int i=1; i<=15; i++) {
				Location tempLoc = tempList.get(0);
				busRoute.put(i, tempLoc);
				tempList.remove(tempLoc);
			}
			List<Intersection> tempInts = Directory.defineClockwiseBusStopIntersections();
			for (int i=1; i<=10; i++) {
				Intersection tempInt = tempInts.get(0);
				stoplights.put(i, tempInt);
				tempInts.remove(tempInt);
			}
			busRouteCounter = 0;
			busStopCounter = 0;
		}
		else if (name == "counterclockwise") {
			List<Location> tempList = Directory.defineCounterClockwiseBusRoute();
			for(int i=1; i<=10; i++) {
				Location tempLoc = tempList.get(0);
				busRoute.put(i, tempLoc);
				tempList.remove(tempLoc);
			}
			List<Intersection> tempInts = Directory.defineCounterClockwiseBusStopIntersections();
			for (int i=1; i<=10; i++) {
				Intersection tempInt = tempInts.get(0);
				stoplights.put(i, tempInt);
				tempInts.remove(tempInt);
			}
			busRouteCounter = 0;
		}
	}
	
	// Accesors //////////////////////////////////////////////////////////////////////////////////////////////////
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
	
	// Messages ///////////////////////////////////////////////////////////////////////////////////////////////////
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
	
	public void msgArrivedAtBusStop() {
		event = BusEvent.arrivedAtBusStop;
		stateChanged();
	}
	
	// Scheduler //////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean pickAndExecuteAnAction() {
		if (state == BusState.driving && event == BusEvent.arrivedAtBusStop){
			state = BusState.stopped;
			Load();
			return true;
		}
		if (state == BusState.driving && event == BusEvent.arrived){
			state = BusState.stopped;
			Stop();
			return true;
		}
		
		if (state == BusState.stopped && event == BusEvent.loaded){
			state = BusState.driving;
			Drive();
			return true;
		}
		
		if (state == BusState.stopped && event == BusEvent.arrived) {
			state = BusState.driving;
			Drive();
			return true;
		}
		
		return false;
	}
	
	// Actions //////////////////////////////////////////////////////////////////////////////////////////////////////
	public void Drive() {
		/*
		if (this.getName().equals("Busta") || this.getName().equals("Buster")) {
		busStopCounter = ((busStopCounter + 1) % 4);
		}
		else {busStopCounter = ((busStopCounter + 1) % 10);}
			
		DoGoTo(busStops.get(busStopCounter));
		try {
			atDestination.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		} 
		//busStopCounter = ((busStopCounter + 1) % 4);
		msgArrived();
		*/
		busRouteCounter = ((busRouteCounter) % 15);
		DoGoTo(busRoute.get(busRouteCounter + 1));
		try {
			atDestination.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		if (busRouteCounter == 2 || busRouteCounter == 5 || busRouteCounter == 7 || busRouteCounter == 12 || busRouteCounter == 14 ) {
			msgArrivedAtBusStop();
			busRouteCounter++;
			return;
		}
		else {
			stoplights.get(busRouteCounter+1).vehicleWantsToCross(this);
			try {
				atDestination.acquire();
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
			msgArrived();
			busRouteCounter++;
		}
	}

	public void Load() {
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
	
	public void Stop() {
		msgFinishedLoading();
	}
	
	// Animation DoXYZ /////////////////////////////////////////////////////////////////////////////////////////////
	public void DoGoTo(Location l) {
		//Animation
		//Wait a few Seconds
		gui.DoGoToStop(l.getX(), l.getY());
	}

	// Utility functions ////////////////////////////////////////////////////////////////////////////////////////////
	public void atDestination() {
		
		/*
		stopTimer.schedule(new TimerTask() {
			public void run() {
				atDestination.release();
			}
		}, 300);*/
		atDestination.release();
	}
	
	//check to see if a point is within the rectangle
	public boolean inside (int x, int y, Rectangle rect) {
		return (x > rect.x && x < rect.x+rect.width) && (y > rect.y && y < rect.y + rect.height);
	}
	//adding this to check for hit check
	public boolean hitCheck(int x, int y, Rectangle rect) {
		return (x >= rect.x && x <= rect.x + rect.width) && (y >= rect.y && y <= rect.x + rect.height);
	}
	
	//adding this to check for collision
	public boolean collisionCheck(Rectangle rect1, Rectangle rect2) {
		return hitCheck(rect1.x, rect1.y, rect2) || hitCheck(rect1.x + rect1.width, rect1.y, rect2) ||
				hitCheck(rect1.x, rect1.y + rect1.height, rect2) || hitCheck(rect1.x + rect1.width, rect1.y + rect1.height, rect2);
	}

	// Utility classes ////////////////////////////////////////////////////////////////////////////////////////////
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

}




