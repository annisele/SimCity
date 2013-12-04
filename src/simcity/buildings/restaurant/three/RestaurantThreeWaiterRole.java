package simcity.buildings.restaurant.three;

import java.util.concurrent.Semaphore;

import simcity.interfaces.restaurant.three.RestaurantThreeWaiter;
public class RestaurantThreeWaiterRole implements RestaurantThreeWaiter{

private Semaphore atDest = new Semaphore(0, true);
	
	public void atDestination() {
		atDest.release();
	}
}




	


