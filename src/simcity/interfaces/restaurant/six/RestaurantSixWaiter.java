package simcity.interfaces.restaurant.six;

import simcity.interfaces.GuiPartner;

public interface RestaurantSixWaiter extends GuiPartner {

	void msgSitAtTable(RestaurantSixCustomer customer, int tableNumber);

	void msgYouCanBreak(boolean b);


}
