package simcity.interfaces.restaurant.four;

import simcity.PersonAgent;
import simcity.SimSystem;
import simcity.buildings.restaurant.four.RestaurantFourCustomerRole.Event;
import simcity.buildings.restaurant.four.RestaurantFourCustomerRole.State;
import simcity.buildings.restaurant.four.RestaurantFourSystem;
import simcity.interfaces.GuiPartner;

public interface RestaurantFourCustomer extends GuiPartner {

	// Accessors
	public abstract void setPersonAgent(PersonAgent person);
	public abstract RestaurantFourSystem getSystem();
	public abstract void setSystem(RestaurantFourSystem restaurantFourSystem);
	public abstract State getState();
	public abstract void setState(State state);
	public abstract Event getEvent();
	public abstract void setEvent(Event event);
	
	// Messages
	public abstract void msgGotHungry();
	
	// Utilities
	public abstract void exitBuilding();
	public abstract void enterBuilding(SimSystem s);
	public abstract void atDestination();
	
}
