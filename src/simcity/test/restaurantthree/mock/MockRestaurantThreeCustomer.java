package simcity.test.restaurantthree.mock;

import simcity.SimSystem;
import simcity.buildings.restaurant.three.RestaurantThreeCustomerRole;
import simcity.interfaces.*;
import simcity.interfaces.restaurant.three.RestaurantThreeCustomer;
import simcity.interfaces.restaurant.three.RestaurantThreeHost;
import simcity.test.mock.*;

public class MockRestaurantThreeCustomer extends Mock implements RestaurantThreeCustomer {
	

	public MockRestaurantThreeCustomer(String name) {
		super(name);
	}
	public EventLog log = new EventLog();
	
	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RestaurantThreeHost getRestaurantThreeHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRestaurantThreeHost(RestaurantThreeHost bh) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgRestaurantFull() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}