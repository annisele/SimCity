package simcity.buildings.restaurant.four;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.restaurantfour.RestaurantFourCustomerGui;
import simcity.interfaces.restaurant.four.RestaurantFourCustomer;


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
	
	private RestaurantFourSystem restaurantFourSystem;
	
	public enum State {none, waitingAtRestaurant, walkingToTable};
	private State state = State.none;
	
	public enum Event {none, gotHungry, metWaiter};
	private Event event = Event.none;
	
	public PersonAgent getPersonAgent() {
		return person;
	}
	
	// Constructors //////////////////////////////////////////////////////////////////////////
	
	public RestaurantFourCustomerRole(PersonAgent person) {
		this.person = person;
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
	
	public void msgFollowMeToTable(int tableNumber) {
		event = Event.metWaiter;
		this.tableNumber = tableNumber;
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
	}
	
	// Animation DoXYZ
	private void DoGoToHostLocation() {
		
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

