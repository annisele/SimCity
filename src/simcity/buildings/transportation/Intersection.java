package simcity.buildings.transportation;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Intersection {
	
	private List<Object> vehicles = Collections.synchronizedList(new ArrayList<Object>());
	private Semaphore stoplight = new Semaphore(1, true);
	private int x;
	private int y;
	private Rectangle box;
	
	public static final int intersectionWidth = 100;
	public static final int intersectionHeight = 100;
	
	public Intersection(int x, int y) {
		this.x = x;
		this.y = y;
		box = new Rectangle(x, y, intersectionWidth, intersectionHeight);
	}
	
	public void startIntersection() {
		while(true) {
			if(vehicles.isEmpty()) {

			}
			else {
				if (vehicles.get(0) instanceof BusAgent) {
					//((BusAgent)vehicles.get(0)).allowToGo();
				}
				else if (vehicles.get(0) instanceof CarAgent) {
					//((CarAgent)vehicles.get(0)).allowToGo();
				}
			}
		}
	}
	
	public void vehicleArrived(Object vehicle) {
		vehicles.add(vehicle);
	}
	
}