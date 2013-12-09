package simcity.buildings.restaurant.four;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.restaurantfour.RestaurantFourCustomerGui;
import simcity.interfaces.restaurant.four.RestaurantFourCustomer;
import simcity.interfaces.restaurant.four.RestaurantFourWaiter;


//////////////////////////////////////////////////////////////////////////

/**
 * Restaurant Four Customer Role
 * @author Kevin Allan Chan
 *
 */

public class RestaurantFourCustomerRole extends Role implements RestaurantFourCustomer {
	
	/**
	 * Data
	 */

	//private PersonAgent person;
	private RestaurantFourCustomerGui gui;
	private int tableNumber;
	
	private RestaurantFourWaiter waiter;
	private RestaurantFourMenu menu;
	
	private RestaurantFourSystem restaurantFourSystem;
	
	public enum State {none, waitingAtRestaurant, walkingToTable, thinkingOfOrder};
	private State state = State.none;
	
	public enum Event {none, gotHungry, metWaiter, gotToTable};
	private Event event = Event.none;
	
	public PersonAgent getPersonAgent() {
		return person;
	}
	
	// Constructors //////////////////////////////////////////////////////////////////////////
	
	public RestaurantFourCustomerRole(PersonAgent person) {
		this.person = person;
		this.gui = new RestaurantFourCustomerGui(this);
	}
	
	// Accessors //////////////////////////////////////////////////////////////////////////
	
	public void setPersonAgent(PersonAgent person) {
		this.person = person;
	}
	
	public RestaurantFourSystem getSystem() {
		return restaurantFourSystem;
	}
	
	public void setSystem(RestaurantFourSystem restaurantFourSystem) {
		this.restaurantFourSystem = restaurantFourSystem;
	}
	
	public int getTableNumber() {
		return tableNumber;
	}
	
	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}
	
	public RestaurantFourWaiter getWaiter() {
		return waiter;
	}
	
	public void setWaiter(RestaurantFourWaiter waiter) {
		this.waiter = waiter;
	}
	
	public RestaurantFourMenu getMenu() {
		return menu;
	}
	
	public void setMenu(RestaurantFourMenu menu) {
		this.menu = menu;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public Event getEvent() {
		return event;
	}
	
	public void setEvent(Event event) {
		this.event = event;
	}
	
	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Messages
	 */

	public void msgGotHungry() {	// called after entering a building
		event = Event.gotHungry;
		stateChanged();
	}
	
	public void msgFollowMeToTable(RestaurantFourWaiter waiter, int tableNumber, RestaurantFourMenu menu) {
		event = Event.metWaiter;
		this.tableNumber = tableNumber;
		stateChanged();
	}
	
	public void msgArrivedAtTable() {
		event = Event.gotToTable;
		stateChanged();
	}
	
	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Scheduler
	 */
	
	public boolean pickAndExecuteAnAction() {

		if (state == State.none && event == Event.gotHungry) {
			state = State.waitingAtRestaurant;
			informHostOfArrival();
			return true;
		}
		
		if (state == State.waitingAtRestaurant && event == Event.metWaiter){
			state = State.walkingToTable;
			followWaiterToTable();
			return true;
		}
		
		if (state == State.walkingToTable && event == Event.gotToTable) {
			state = State.thinkingOfOrder;
			thinkOfOrder();
			return true;
		}
		
		return false;
	}



	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Actions
	 */
	
	private void informHostOfArrival() {
		restaurantFourSystem.getHost().msgImHungry(this);
	}
	
	private void followWaiterToTable() {
		DoGoToTable();
		msgArrivedAtTable();
	}
	
	private void thinkOfOrder() {
		//waiter.msgImReadyToOrder();
	}
	
	// Animation DoXYZ
	private void DoGoToHostLocation() {
		((RestaurantFourCustomerGui) gui).DoGoToHostLocation();
	}
	
	private void DoGoToTable() {
		
	}
	
	// Utilities //////////////////////////////////////////////////////////////////////////
	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void enterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		this.restaurantFourSystem = (RestaurantFourSystem)s;
		msgGotHungry();
		DoGoToHostLocation();
	}

	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
	}
	
	// Utility Classes //////////////////////////////////////////////////////////////////////////

	
}

