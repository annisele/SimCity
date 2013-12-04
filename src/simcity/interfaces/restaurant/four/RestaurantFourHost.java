package simcity.interfaces.restaurant.four;

import java.util.List;

import simcity.PersonAgent;
import simcity.SimSystem;
import simcity.buildings.restaurant.four.RestaurantFourHostRole.Status;
import simcity.buildings.restaurant.four.RestaurantFourSystem;
import simcity.interfaces.GuiPartner;

public interface RestaurantFourHost extends GuiPartner {

	// Accessors
	public abstract PersonAgent getPerson();
	public abstract void setPerson(PersonAgent person);
	public abstract RestaurantFourSystem getSystem();
	public abstract void setSystem(RestaurantFourSystem restaurantFourSystem);
	public abstract Status getStatus();
	public abstract void setStatus(Status status);
	public abstract List getWaitersList();
	public abstract List getCustomersList();
	
	// Messages
	public abstract void msgGotToWork();
	public abstract void msgWaiterReadyForWork(RestaurantFourWaiter waiter);
	public abstract void msgImHungry(RestaurantFourCustomer customer);
	
	// Utilities
	public abstract void exitBuilding();
	public abstract void enterBuilding(SimSystem s);
	public abstract void atDestination();

}
