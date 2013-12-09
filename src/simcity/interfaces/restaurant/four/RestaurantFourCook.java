package simcity.interfaces.restaurant.four;

import simcity.SimSystem;
import simcity.buildings.restaurant.four.RestaurantFourSystem;
import simcity.buildings.restaurant.four.RestaurantFourCookRole.Status;
import simcity.interfaces.GuiPartner;

public interface RestaurantFourCook extends GuiPartner{

	// Accessors
	public abstract RestaurantFourSystem getSystem();
	public abstract void setSystem(RestaurantFourSystem restaurantFourSystem);
	public abstract Status getStatus();
	public abstract void setStatus(Status status);
	
	// Messages
	public abstract void msgGotToWork();
	public abstract void msgStartWorking();
	
	// Utilities
	public abstract void exitBuilding();
	public abstract void enterBuilding(SimSystem s);
	
}
