package simcity.buildings.transportation;

import java.util.concurrent.Semaphore;

import simcity.gui.transportation.IntersectionGui;

public class Intersection {

	private IntersectionGui gui;
	private int x;
	private int y;
	private Semaphore stoplight = new Semaphore(1, true);
	
	// Constructor
	public Intersection(int x, int y) {
		this.gui = new IntersectionGui(x, y);
		this.x = x;
		this.y = y;
	}
	
	// Accessors
	public IntersectionGui getGui() {
		return gui;
	}
	
	public void setGui(IntersectionGui gui) {
		this.gui = gui;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	// Functions
	public void vehicleWantsToCross(Object vehicle) {
		try {
    		stoplight.acquire();
    	} catch (InterruptedException e) {
    		
    	}
		
		if (vehicle instanceof CarAgent) {
			
		}
		else if (vehicle instanceof BusAgent) {
			((BusAgent) vehicle).atDestination();
		}
	}
	
	public void vehicleHasCrossed() {
		stoplight.release();
	}
	
}
