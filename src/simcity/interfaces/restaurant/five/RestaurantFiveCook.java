package simcity.interfaces.restaurant.five;

import simcity.interfaces.GuiPartner;
import simcity.interfaces.market.MarketOrderer;

public interface RestaurantFiveCook extends GuiPartner, MarketOrderer {

	void msgHereIsAnOrder(RestaurantFiveWaiter restaurantFiveWaiter,
			String choice, int table);
	
	public abstract String getName();

}
