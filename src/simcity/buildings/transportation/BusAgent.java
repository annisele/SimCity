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
			Location stop1 = new Location(110, 110);
			Location stop2 = new Location(500, 110);
			Location stop3 = new Location(940, 110);
			Location stop4 = new Location(1370, 110);
			Location stop5 = new Location(1370, 470);
			Location stop6 = new Location(1370, 800);
			Location stop7 = new Location(940,  800 );
			Location stop8 = new Location(500 , 800  );
			Location stop9 = new Location(110 , 800);
			Location stop10 = new Location(110, 470);
			//Location stop11 = new Location(940, 840);
			//Location stop12 = new Location(1370, 840);
			//Location stop9 = new Location(   );
			//Location stop10 = new Location(   );
			//Location stop11 = new Location(   );
			//Location stop12 = new Location(   );
			busStops.put(0, stop1);
			busStops.put(1, stop2);
			busStops.put(2, stop3);
			busStops.put(3, stop4);
			busStops.put(4, stop5);
			busStops.put(5, stop6);
			busStops.put(6, stop7);
			busStops.put(7, stop8);
			busStops.put(8, stop9);
			busStops.put(9, stop10);
			//busStops.put(10, stop11);
			//busStops.put(11, stop12);
			
			
			
			busStopCounter = 9;
		}
		else if (name == "Kipling") {
			Location stop1 = new Location(1370, 800);
			Location stop2 = new Location(1370, 470);
			Location stop3 = new Location(1370, 110);
			Location stop4 = new Location(940, 110);
			Location stop5 = new Location(500, 110);
			Location stop6 = new Location(110, 110);
			Location stop7 = new Location(110,  470 );
			Location stop8 = new Location(110, 800);
			Location stop9 = new Location(500 , 800);
			Location stop10 = new Location(940, 800);
			busStops.put(0, stop1);
			busStops.put(1, stop2);
			busStops.put(2, stop3);
			busStops.put(3, stop4);
			busStops.put(4, stop5);
			busStops.put(5, stop6);
			busStops.put(6, stop7);
			busStops.put(7, stop8);
			busStops.put(8, stop9);
			busStops.put(9, stop10);
			busStopCounter = 9;
		}
		
	}
		
	public String name;
	public BusGui gui;
	public List<MyPassenger> passengers = Collections.synchronizedList(new ArrayList<MyPassenger>());
	
	public static final int NUM_BUSSTOPS = 10;
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
		else { busStopCounter = ((busStopCounter + 1) % 10);}
			
		DoGoTo(busStops.get(busStopCounter));
		try {
			atDestination.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
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




