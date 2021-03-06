package simcity.test.restaurantfour.mock;

import java.util.List;

import simcity.PersonAgent;
import simcity.SimSystem;
import simcity.buildings.restaurant.four.RestaurantFourMenu;
import simcity.buildings.restaurant.four.RestaurantFourMenu.foodType;
import simcity.buildings.restaurant.four.RestaurantFourSystem;
import simcity.buildings.restaurant.four.RestaurantFourWaiterRole.MyCustomer;
import simcity.buildings.restaurant.four.RestaurantFourWaiterRole.Status;
import simcity.interfaces.restaurant.four.RestaurantFourCustomer;
import simcity.interfaces.restaurant.four.RestaurantFourWaiter;
import simcity.test.mock.EventLog;
import simcity.test.mock.LoggedEvent;
import simcity.test.mock.Mock;

public class MockRestaurantFourWaiter extends Mock implements RestaurantFourWaiter {

	public MockRestaurantFourWaiter(String name) {
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
	public void msgStartWorking() {
		log.add(new LoggedEvent("Received msgStartWorking from host"));
		
	}

	@Override
	public List<MyCustomer> getCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestaurantFourMenu getMenu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMenu(RestaurantFourMenu menu) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgSeatCustomerAtTable(RestaurantFourCustomer customer,
			int tableNumber, RestaurantFourMenu menu) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgImReadyToOrder(RestaurantFourCustomer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgThisIsMyOrder(RestaurantFourCustomer customer,
			foodType foodChoice) {
		// TODO Auto-generated method stub
		
	}

}
