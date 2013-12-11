package simcity.test.restaurantfour.mock;

import simcity.PersonAgent;
import simcity.SimSystem;
import simcity.buildings.restaurant.four.RestaurantFourCustomerRole.Event;
import simcity.buildings.restaurant.four.RestaurantFourCustomerRole.State;
import simcity.buildings.restaurant.four.RestaurantFourMenu;
import simcity.buildings.restaurant.four.RestaurantFourSystem;
import simcity.interfaces.restaurant.four.RestaurantFourCustomer;
import simcity.interfaces.restaurant.four.RestaurantFourWaiter;
import simcity.test.mock.EventLog;
import simcity.test.mock.Mock;

public class MockRestaurantFourCustomer extends Mock implements RestaurantFourCustomer {

	public MockRestaurantFourCustomer(String name) {
		super(name);
	}
	public EventLog log = new EventLog();
	
	@Override
	public void setPersonAgent(PersonAgent person) {
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
	public State getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setState(State state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Event getEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEvent(Event event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgGotHungry() {
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
	public int getTableNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTableNumber(int tableNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PersonAgent getPersonAgent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestaurantFourWaiter getWaiter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setWaiter(RestaurantFourWaiter waiter) {
		// TODO Auto-generated method stub
		
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
	public void msgFollowMeToTable(RestaurantFourWaiter waiter,
			int tableNumber, RestaurantFourMenu menu) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgArrivedAtTable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgDoneThinkingOfOrder() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgWhatDoYouWantToOrder() {
		// TODO Auto-generated method stub
		
	}

}
