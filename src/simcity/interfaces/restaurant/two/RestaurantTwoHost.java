package simcity.interfaces.restaurant.two;

import simcity.SimSystem;
import simcity.interfaces.GuiPartner;

public interface RestaurantTwoHost extends GuiPartner {
	abstract void msgEnterBuilding(SimSystem s);

	abstract void msgLeavingTable(
			RestaurantTwoCustomer restaurantTwoCustomer);

	abstract void msgAskToBreak(RestaurantTwoWaiter restaurantTwoWaiterRole);

	abstract void msgRestate(RestaurantTwoWaiter restaurantTwoWaiterRole);

	abstract void addWaiter(RestaurantTwoWaiter role);
	
}
