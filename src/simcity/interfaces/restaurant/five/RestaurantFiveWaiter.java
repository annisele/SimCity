package simcity.interfaces.restaurant.five;

import simcity.interfaces.GuiPartner;

public interface RestaurantFiveWaiter extends GuiPartner {

//	void msgGoOnBreak();
//
//	void msgCannotGoOnBreak();

	void msgPleaseSeatCustomer(RestaurantFiveCustomer restaurantFiveCustomer,
			int tableNumber);

	String getName();

}
