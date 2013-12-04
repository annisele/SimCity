package simcity.interfaces.restaurant.four;

import simcity.PersonAgent;
import simcity.SimSystem;
import simcity.buildings.restaurant.four.RestaurantFourWaiterRole.Status;
import simcity.buildings.restaurant.four.RestaurantFourSystem;
import simcity.interfaces.GuiPartner;

public interface RestaurantFourWaiter extends GuiPartner {

	// Accessors
		public abstract PersonAgent getPerson();
		public abstract void setPerson(PersonAgent person);
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
		public abstract void atDestination();
	
}
