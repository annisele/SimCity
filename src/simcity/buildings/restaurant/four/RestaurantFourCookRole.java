package simcity.buildings.restaurant.four;

import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.buildings.restaurant.four.RestaurantFourWaiterRole.Status;
import simcity.gui.restaurantfour.RestaurantFourCookGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.four.RestaurantFourCook;

//////////////////////////////////////////////////////////////////////////

/**
 * Restaurant Four Customer Role
 * @author Kevin Allan Chan
 *
 */

public class RestaurantFourCookRole extends Role implements RestaurantFourCook {
	
	/**
	 * Data
	 */

	private RestaurantFourSystem restaurantFourSystem;
	
	public enum Status {none, waitingAtRestaurant, waitingForConfirmation, confirmed, working};
	private Status status = Status.none;
	
	private Semaphore atDest = new Semaphore(0, true);
	
	// Constructors //////////////////////////////////////////////////////////////////////////
	public RestaurantFourCookRole(PersonAgent person) {
		this.person = person;
		this.gui = new RestaurantFourCookGui(this);
	}
	
	// Accessors //////////////////////////////////////////////////////////////////////////
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
		status = Status.confirmed;
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
			return true;
		}

		if (status == Status.confirmed) {
			status = Status.working;
			workAtCookStation();
			return true;
		}
		
		return false;
	}



	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Actions
	 */

	private void informHostOfArrival() {
		DoGoToHostLocation();
		try {
    		atDest.acquire();
    	} catch (InterruptedException e) {
    		//e.printStackTrace();
    	}
		restaurantFourSystem.getHost().msgCookReadyForWork(this);
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurantFourSystem.getName()), "Cook: " + person.getName(), "I am ready to work!");
	}
	
	private void workAtCookStation() {
		DoGoToCookStation();
	}
	
	// Animation DoXYZ
	private void DoGoToHostLocation() {
		((RestaurantFourCookGui) gui).DoGoToHostLocation();
	}
	
	private void DoGoToCookStation() {
		((RestaurantFourCookGui) gui).DoGoToCookStationSink();
		try {
    		atDest.acquire();
    	} catch (InterruptedException e) {
    		//e.printStackTrace();
    	}
		((RestaurantFourCookGui) gui).DoGoToCookStation();
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
		try {
    		atDest.acquire();
    	} catch (InterruptedException e) {
    		//e.printStackTrace();
    	}
	}

	@Override
	public void atDestination() {
		atDest.release();
	}
	
	// Utility Classes //////////////////////////////////////////////////////////////////////////

	
}

