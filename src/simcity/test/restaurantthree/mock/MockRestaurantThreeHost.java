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
	public PersonAgent getPerson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPerson(PersonAgent person) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RestaurantThreeSystem getSystem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSystem(RestaurantThreeSystem restSystem) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public List getWaitersList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void msgGotToWork() {
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
	public void atDestination() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int numWaitingCustomers() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void msgIWantFood(RestaurantThreeCustomer restaurantThreeCustomerRole) {
		// TODO Auto-generated method stub
		
	}

}
