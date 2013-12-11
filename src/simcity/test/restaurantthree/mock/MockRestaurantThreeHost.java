package simcity.test.restaurantthree.mock;

import java.util.List;

import simcity.PersonAgent;
import simcity.SimSystem;
import simcity.buildings.restaurant.three.RestaurantThreeSystem;
import simcity.interfaces.restaurant.three.RestaurantThreeCustomer;
import simcity.interfaces.restaurant.three.RestaurantThreeHost;
import simcity.interfaces.restaurant.three.RestaurantThreeWaiter;
import simcity.test.mock.EventLog;
import simcity.test.mock.LoggedEvent;
import simcity.test.mock.Mock;

public class MockRestaurantThreeHost extends Mock implements RestaurantThreeHost {

	public MockRestaurantThreeHost(String name) {
		super(name);
	}
	public EventLog log = new EventLog();
	
	

	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgIWantFood(RestaurantThreeCustomer restaurantThreeCustomerRole) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgAddWaiter(RestaurantThreeWaiter role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getWaitingCustomers() {
		// TODO Auto-generated method stub
		return 0;
	}

}
