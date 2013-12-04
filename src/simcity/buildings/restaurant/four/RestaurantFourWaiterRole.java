package simcity.buildings.restaurant.four;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.interfaces.restaurant.four.RestaurantFourWaiter;


//////////////////////////////////////////////////////////////////////////

/**
 * Restaurant Four Waiter Role
 * @author Kevin Allan Chan
 *
 */

public class RestaurantFourWaiterRole extends Role implements RestaurantFourWaiter {
	
	/**
	 * Data
	 */
	
	private RestaurantFourSystem restaurantFourSystem;
	
	public enum Status {none, waitingAtRestaurant, waitingForConfirmation, working};
	private Status status = Status.none;
	
	// Constructors //////////////////////////////////////////////////////////////////////////
	
	public RestaurantFourWaiterRole(PersonAgent person) {
		this.person = person;
	}
	
	// Accessors //////////////////////////////////////////////////////////////////////////
	
	public PersonAgent getPerson() {
		return person;
	}
	
	public void setPerson(PersonAgent person) {
		this.person = person;
	}
	
	public RestaurantFourSystem getSystem() {
		return restaurantFourSystem;
	}
	
	public void setSystem(RestaurantFourSystem restaurantFourSystem) {
		this.restaurantFourSystem = restaurantFourSystem;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Messages
	 */

	public void msgGotToWork() {
		status = Status.waitingAtRestaurant;
		stateChanged();
	}

	public void msgStartWorking() {
		status = Status.working;
		stateChanged();
	}
	
	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Scheduler
	 */
	
	public boolean pickAndExecuteAnAction() {

		if (status == Status.waitingAtRestaurant) {
			status = Status.waitingForConfirmation;
			informHostOfArrival();
		}

		if (status == Status.working) {
			// Scheduler options
		}
		
		return false;
	}



	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Actions
	 */
	
	private void informHostOfArrival() {
		restaurantFourSystem.getHost().msgWaiterReadyForWork(this);
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
		msgGotToWork();
	}

	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
	}
	
	// Utility Classes //////////////////////////////////////////////////////////////////////////
	/**
	 * MyCustomer class - for customers that just walk in
	 */
	
}

