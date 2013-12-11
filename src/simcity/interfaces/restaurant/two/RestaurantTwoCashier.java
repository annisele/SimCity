package simcity.interfaces.restaurant.two;

import simcity.SimSystem;
import simcity.interfaces.GuiPartner;
import simcity.interfaces.market.MarketPayer;

public interface RestaurantTwoCashier extends GuiPartner,MarketPayer{
	abstract void enterBuilding(SimSystem s);

	abstract void modBalance(double i);

	abstract void msgHereIsMoney(
			RestaurantTwoCustomer restaurantTwoCustomerRole,
			double customer_check);

	abstract void msgCustomerOrder(
			RestaurantTwoWaiter restaurantTwoWaiterRole,
			RestaurantTwoCustomer c, int table_num, String choice);

	double msgGetCheck(RestaurantTwoCustomer c);


	abstract void msgLeaveWork();

}
