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

	private PersonAgent person;
	
	// Constructors //////////////////////////////////////////////////////////////////////////
	

	
	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Messages
	 */

	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Scheduler
	 */
	
	public boolean pickAndExecuteAnAction() {

		return false;
	}



	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Actions
	 */
	
	// Utilities //////////////////////////////////////////////////////////////////////////
	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		
	}
	
	// Utility Classes //////////////////////////////////////////////////////////////////////////
	/**
	 * MyCustomer class - for customers that just walk in
	 */
	
}

