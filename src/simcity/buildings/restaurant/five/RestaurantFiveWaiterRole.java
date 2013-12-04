package simcity.buildings.restaurant.five;

import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.restaurantfive.RestaurantFiveHostGui;
import simcity.gui.restaurantfive.RestaurantFiveWaiterGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.five.RestaurantFiveWaiter;

public class RestaurantFiveWaiterRole extends Role implements RestaurantFiveWaiter {

	private Semaphore atDest = new Semaphore(0, true);
	private RestaurantFiveSystem restaurant;
	
	public RestaurantFiveWaiterRole(PersonAgent p) {
		person = p;
		this.gui = new RestaurantFiveWaiterGui(this);
	}
	
	@Override
	public void atDestination() {
		atDest.release();
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterBuilding(SimSystem s) {
		restaurant = (RestaurantFiveSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantFiveHost: " + person.getName(), "Ready to work at the restaurant!");
		
		((RestaurantFiveWaiterGui) gui).DoGoToHome();
		
		
	}

}
