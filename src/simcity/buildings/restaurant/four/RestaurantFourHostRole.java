package simcity.buildings.restaurant.four;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.interfaces.restaurant.four.RestaurantFourHost;


//////////////////////////////////////////////////////////////////////////

/**
 * Restaurant Four Host Role
 * @author Kevin Allan Chan
 *
 */

public class RestaurantFourHostRole extends Role implements RestaurantFourHost {
	
	/**
	 * Data
	 */
	
	private RestaurantFourSystem restaurantFourSystem;

	public enum Status {none, waitingAtRestaurant, working};
	private Status status = Status.none;
	
	// Constructors //////////////////////////////////////////////////////////////////////////
	
	public RestaurantFourHostRole(PersonAgent person) {
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
		status = status.waitingAtRestaurant;
		stateChanged();
	}

	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Scheduler
	 */
	
	public boolean pickAndExecuteAnAction() {

		if (status == Status.waitingAtRestaurant) {
			status = Status.working;
			setupForWork();
		}

		return false;
	}



	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Actions
	 */
	
	private void setupForWork() {
		restaurantFourSystem.setHost(this);
		// Let directory know that the restaurant is open
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

