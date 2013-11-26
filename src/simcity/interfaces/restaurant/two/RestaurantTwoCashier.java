package simcity.interfaces.restaurant.two;

import simcity.SimSystem;
import simcity.interfaces.GuiPartner;

public interface RestaurantTwoCashier extends GuiPartner{
	abstract void enterBuilding(SimSystem s);

	abstract void modBalance(double i);

	abstract void msgHereIsMoney(
			RestaurantTwoCustomer restaurantTwoCustomerRole,
			double customer_check);

	abstract void msgCustomerOrder(
			RestaurantTwoWaiter restaurantTwoWaiterRole,
			RestaurantTwoCustomer c, int table_num, String choice);
}
