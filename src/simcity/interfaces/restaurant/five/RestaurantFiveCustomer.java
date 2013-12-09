package simcity.interfaces.restaurant.five;

import simcity.buildings.restaurant.five.RestaurantFiveMenu;
import simcity.gui.Gui;
import simcity.interfaces.GuiPartner;

public interface RestaurantFiveCustomer extends GuiPartner {

	public abstract void msgRestaurantFull();

	public abstract void msgHereIsMenu(RestaurantFiveWaiter waiter,
			RestaurantFiveMenu restaurantFiveMenu);
	

	public abstract void msgFollowMeToTable(RestaurantFiveWaiter waiter, int table);

//	public abstract void msgPleaseReorder();
//
//	public abstract void msgWhatWouldYouLike();
//
//	public abstract void msgHereIsCheck(double double1);
//
//	public abstract void msgHereIsYourFood(String choice);

	public abstract String getName();

	public abstract Gui getGui();


}
