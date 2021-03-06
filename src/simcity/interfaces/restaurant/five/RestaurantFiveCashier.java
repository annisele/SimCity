package simcity.interfaces.restaurant.five;

import simcity.interfaces.GuiPartner;
import simcity.interfaces.market.MarketPayer;

public interface RestaurantFiveCashier extends GuiPartner, MarketPayer {

//	void msgProduceCheck(RestaurantFiveWaiter restaurantFiveWaiter,
//			RestaurantFiveCustomer c, String choice);
	
	public abstract String getName();

	public abstract void msgProduceCheck(
			RestaurantFiveWaiter waiter,
			RestaurantFiveCustomer c, String choice);

	public abstract void msgPayCheck(
			RestaurantFiveCustomer cust, double moneyToPay);

}
