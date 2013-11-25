package simcity.buildings.restaurant.five;

import java.util.concurrent.Semaphore;

import simcity.interfaces.restaurant.five.RestaurantFiveHost;

public class RestaurantFiveHostRole implements RestaurantFiveHost{

	private Semaphore atDest = new Semaphore(0, true);

	
	@Override
	public void atDestination() {
		atDest.release();
	}

}
