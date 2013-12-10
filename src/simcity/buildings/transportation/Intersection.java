package simcity.buildings.transportation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

public class Intersection {

	private int number;
	private Semaphore stoplight = new Semaphore(1, true);
	private Timer timer = new Timer();
	private List<Object> vehicles = Collections.synchronizedList(new ArrayList<Object>());
	
	// Constructor
	public Intersection(int number) {
		this.number = number;
	}
	
	// Accessors
	public int getIntersectionNumber() {
		return number;
	}
	
	public void setIntersectionNumber(int number) {
		this.number = number;
	}
	
	// Functions
	public void vehicleWantsToCross(Object vehicle) {
		vehicles.add(vehicle);
		try {
    		stoplight.acquire();
    	} catch (InterruptedException e) {
    		
    	}
		
		timer.schedule(new TimerTask() {
			public void run() {
				vehicleHasCrossed();
			}
		}, 1500);
	}
	
	public void vehicleCrossing() {
		
	}
	
	public void vehicleHasCrossed() {
		stoplight.release();
		Object tempVehicle = vehicles.get(0);
		if (tempVehicle instanceof CarPassengerRole) {
			((CarPassengerRole) tempVehicle).atDestination();
		}
		vehicles.remove(tempVehicle);
	}
	
}
