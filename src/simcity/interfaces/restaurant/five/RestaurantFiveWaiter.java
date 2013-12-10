package simcity.interfaces.restaurant.five;

import simcity.interfaces.GuiPartner;

public interface RestaurantFiveWaiter extends GuiPartner {

//	void msgGoOnBreak();
//
//	void msgCannotGoOnBreak();

	void msgPleaseSeatCustomer(RestaurantFiveCustomer restaurantFiveCustomer,
			int tableNumber);

	String getName();

	public abstract void msgIWillFollowYou(RestaurantFiveCustomer cust);

	public abstract void msgReadyToOrder(RestaurantFiveCustomer cust);

	public abstract void msgFoodTooExpensive(RestaurantFiveCustomer cust);

	public abstract void msgHereIsMyChoice(RestaurantFiveCustomer cust, String food);

	public abstract void msgOrderIsReady(String choice, int table);

}
