package simcity.interfaces.restaurant.six;

import simcity.interfaces.GuiPartner;
import simcity.interfaces.restaurant.five.RestaurantFiveCustomer;

public interface RestaurantSixHost extends GuiPartner {
	
	public abstract void msgIWantFood(RestaurantSixCustomer restaurantSixCustomerRole);
	
}
