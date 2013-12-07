package simcity.test.restaurantthree.mock;

import simcity.SimSystem;
import simcity.buildings.restaurant.three.RestaurantThreeWaiterRole;
import simcity.interfaces.*;
import simcity.interfaces.restaurant.three.RestaurantThreeCashier;
import simcity.interfaces.restaurant.three.RestaurantThreeCook;
import simcity.interfaces.restaurant.three.RestaurantThreeWaiter;
import simcity.interfaces.restaurant.three.RestaurantThreeHost;
import simcity.test.mock.*;

public class MockRestaurantThreeWaiter extends Mock implements RestaurantThreeWaiter {
	

	public MockRestaurantThreeWaiter(String name) {
		super(name);
	}
	public EventLog log = new EventLog();
	
	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
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