package simcity.buildings.restaurant.five;

import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.interfaces.restaurant.five.RestaurantFiveCustomer;

public class RestaurantFiveCustomerRole implements RestaurantFiveCustomer {

	private Semaphore atDest = new Semaphore(0, true);
	
	public RestaurantFiveCustomerRole(PersonAgent personAgent) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void atDestination() {
		atDest.release();
	}

}
