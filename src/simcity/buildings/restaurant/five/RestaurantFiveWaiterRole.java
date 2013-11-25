package simcity.buildings.restaurant.five;

import java.util.concurrent.Semaphore;

import simcity.interfaces.restaurant.five.RestaurantFiveWaiter;

public class RestaurantFiveWaiterRole implements RestaurantFiveWaiter {

	private Semaphore atDest = new Semaphore(0, true);
	
	@Override
	public void atDestination() {
		atDest.release();
	}

}
