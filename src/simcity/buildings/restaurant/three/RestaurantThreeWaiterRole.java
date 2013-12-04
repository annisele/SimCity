package simcity.buildings.restaurant.three;

import java.util.concurrent.Semaphore;

import simcity.interfaces.restaurant.three.RestaurantThreeCashier;
import simcity.interfaces.restaurant.three.RestaurantThreeCook;
import simcity.interfaces.restaurant.three.RestaurantThreeHost;
import simcity.interfaces.restaurant.three.RestaurantThreeWaiter;
public class RestaurantThreeWaiterRole implements RestaurantThreeWaiter{

private Semaphore atDest = new Semaphore(0, true);
	
	public void atDestination() {
		atDest.release();
	}

	@Override
	public void setHost(RestaurantThreeHost host) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCook(RestaurantThreeCook cook) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCashier(RestaurantThreeCashier cashier) {
		// TODO Auto-generated method stub
		
	}
}




	


