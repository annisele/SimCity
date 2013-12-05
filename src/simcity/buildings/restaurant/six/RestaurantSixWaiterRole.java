package simcity.buildings.restaurant.six;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.restaurantsix.RestaurantSixCashierGui;
import simcity.gui.restaurantsix.RestaurantSixHostGui;
import simcity.gui.restaurantsix.RestaurantSixWaiterGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.six.RestaurantSixWaiter;

public class RestaurantSixWaiterRole extends Role implements RestaurantSixWaiter {
	
	RestaurantSixSystem restaurant;

	public RestaurantSixWaiterRole(PersonAgent p) {
		person = p;
		this.gui = new RestaurantSixWaiterGui(this);
	}
	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
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
		restaurant = (RestaurantSixSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantSixWaiter: " + person.getName(), "Ready to work at the restaurant!");
		
		((RestaurantSixWaiterGui) gui).DoGoToWaitingArea();
		
	}

}
