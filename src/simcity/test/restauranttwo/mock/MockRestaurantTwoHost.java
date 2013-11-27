package simcity.test.restauranttwo.mock;

import simcity.SimSystem;
import simcity.buildings.bank.BankCustomerRole;
import simcity.buildings.bank.BankTellerRole;
import simcity.interfaces.bank.BankCustomer;
import simcity.interfaces.bank.BankHost;
import simcity.interfaces.bank.BankTeller;
import simcity.interfaces.restaurant.two.RestaurantTwoCustomer;
import simcity.interfaces.restaurant.two.RestaurantTwoHost;
import simcity.interfaces.restaurant.two.RestaurantTwoWaiter;
import simcity.test.mock.EventLog;
import simcity.test.mock.LoggedEvent;
import simcity.test.mock.Mock;

public class MockRestaurantTwoHost extends Mock implements RestaurantTwoHost {

	public MockRestaurantTwoHost(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	public EventLog log = new EventLog();
	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void enterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void msgLeavingTable(RestaurantTwoCustomer restaurantTwoCustomer) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void msgAskToBreak(RestaurantTwoWaiter restaurantTwoWaiterRole) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void msgRestate(RestaurantTwoWaiter restaurantTwoWaiterRole) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addWaiter(RestaurantTwoWaiter role) {
		// TODO Auto-generated method stub
		
	}

	
	
}