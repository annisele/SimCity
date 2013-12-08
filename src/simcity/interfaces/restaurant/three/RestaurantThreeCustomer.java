package simcity.interfaces.restaurant.three;

import simcity.SimSystem;
import simcity.interfaces.GuiPartner;
import simcity.gui.restaurantthree.RestaurantThreeCustomerGui;
public interface RestaurantThreeCustomer extends GuiPartner {
	public abstract void atDestination();
	public abstract void exitBuilding();
	public abstract void enterBuilding(SimSystem s);
	public abstract RestaurantThreeHost getRestaurantThreeHost();
	public abstract void setRestaurantThreeHost(RestaurantThreeHost bh);
	public abstract void msgRestaurantFull();
	public abstract String getName();
}
