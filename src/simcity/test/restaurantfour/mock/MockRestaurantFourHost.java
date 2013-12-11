package simcity.test.restaurantfour.mock;

import java.util.List;

import simcity.PersonAgent;
import simcity.SimSystem;
import simcity.buildings.restaurant.four.RestaurantFourHostRole.Status;
import simcity.buildings.restaurant.four.RestaurantFourSystem;
import simcity.interfaces.restaurant.four.RestaurantFourCook;
import simcity.interfaces.restaurant.four.RestaurantFourCustomer;
import simcity.interfaces.restaurant.four.RestaurantFourHost;
import simcity.interfaces.restaurant.four.RestaurantFourWaiter;
import simcity.test.mock.EventLog;
import simcity.test.mock.LoggedEvent;
import simcity.test.mock.Mock;

public class MockRestaurantFourHost extends Mock implements RestaurantFourHost {

	public MockRestaurantFourHost(String name) {
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
	public RestaurantFourSystem getSystem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSystem(RestaurantFourSystem restaurantFourSystem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Status getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStatus(Status status) {
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
	public void msgWaiterReadyForWork(RestaurantFourWaiter waiter) {
		log.add(new LoggedEvent("Received msgWaiterReadyForWork from waiter " + waiter));
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
	public List getCustomersList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void msgImHungry(RestaurantFourCustomer customer) {
		log.add(new LoggedEvent("Received msgImHungry from customer " + customer));
	}

	@Override
	public RestaurantFourCook getCook() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCook(RestaurantFourCook cook) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgCookReadyForWork(RestaurantFourCook cook) {
		// TODO Auto-generated method stub
		
	}

}
