package simcity.interfaces.restaurant.five;

import simcity.interfaces.GuiPartner;

public interface RestaurantFiveHost extends GuiPartner {

	public abstract void msgIWantFood(RestaurantFiveCustomer cust);

	public abstract void msgAddWaiter(RestaurantFiveWaiter role);

//	public abstract void msgTableIsFree(int table);
//
//	public abstract void msgWantToGoOnBreak(
//			RestaurantFiveWaiter restaurantFiveWaiterRole);
//
//	public abstract void msgIWillWaitForTable(
//			RestaurantFiveCustomer cust);
	
	String getName();

	public abstract int getNumWaitingCustomers();

}
