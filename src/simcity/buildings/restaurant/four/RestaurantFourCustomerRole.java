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
	
	private RestaurantFourSystem restaurantFourSystem;
	
	public enum State {none};
	private State state = State.none;
	
	public enum Event {none, gotHungry};
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
	
	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Scheduler
	 */
	
	public boolean pickAndExecuteAnAction() {

		if (state == State.none && event == Event.gotHungry) {
			informHostOfArrival();
		}
		
		return false;
	}



	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Actions
	 */
	
	private void informHostOfArrival() {
		//restaurantFourSystem.getHost().msgImHungry(this);
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
	}

	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
	}
	
	// Utility Classes //////////////////////////////////////////////////////////////////////////

	
}

