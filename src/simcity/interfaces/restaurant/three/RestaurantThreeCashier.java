package simcity.interfaces.restaurant.three;

import simcity.SimSystem;
import simcity.interfaces.GuiPartner;
import simcity.gui.restaurantthree.RestaurantThreeCustomerGui;
public interface RestaurantThreeCashier extends GuiPartner {
	public abstract void atDestination();
	public abstract void exitBuilding();
	public abstract void enterBuilding(SimSystem s);
}
