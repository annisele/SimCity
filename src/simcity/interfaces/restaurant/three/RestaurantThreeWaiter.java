package simcity.interfaces.restaurant.three;

import simcity.buildings.restaurant.three.*;
import simcity.interfaces.GuiPartner;
import simcity.interfaces.restaurant.three.*;
public interface RestaurantThreeWaiter extends GuiPartner{
	public abstract void setHost(RestaurantThreeHost host);
	public abstract void setCook(RestaurantThreeCook cook);
	public abstract void setCashier(RestaurantThreeCashier cashier);
	void atDestination();

}

	
	