package simcity.buildings.restaurant.five;

import java.util.Map;
import java.util.concurrent.Semaphore;

import simcity.interfaces.restaurant.five.RestaurantFiveCook;

public class RestaurantFiveCookRole implements RestaurantFiveCook {

	private Semaphore atDest = new Semaphore(0, true);
	
	@Override
	public void atDestination() {
		atDest.release();
	}

	@Override
	public void msgHereAreItems(Map<String, Integer> itemsToDeliver,
			double change) {
		// TODO Auto-generated method stub
		
	}

}
