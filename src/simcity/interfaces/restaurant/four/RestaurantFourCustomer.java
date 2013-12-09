package simcity.interfaces.restaurant.four;

import simcity.PersonAgent;
import simcity.SimSystem;
import simcity.buildings.restaurant.four.RestaurantFourCustomerRole.Event;
import simcity.buildings.restaurant.four.RestaurantFourCustomerRole.State;
import simcity.buildings.restaurant.four.RestaurantFourMenu.foodType;
import simcity.buildings.restaurant.four.RestaurantFourMenu;
import simcity.buildings.restaurant.four.RestaurantFourSystem;
import simcity.interfaces.GuiPartner;

public interface RestaurantFourCustomer extends GuiPartner {

	// Accessors
	public abstract PersonAgent getPersonAgent();
	public abstract void setPersonAgent(PersonAgent person);
	public abstract RestaurantFourSystem getSystem();
	public abstract void setSystem(RestaurantFourSystem restaurantFourSystem);
	public abstract int getTableNumber();
	public abstract void setTableNumber(int tableNumber);
	public abstract RestaurantFourWaiter getWaiter();
	public abstract void setWaiter(RestaurantFourWaiter waiter);
	public abstract RestaurantFourMenu getMenu();
	public abstract void setMenu(RestaurantFourMenu menu);
	public abstract foodType getFoodChoice();
	public abstract void setFoodChoice(foodType foodChoice);
	public abstract State getState();
	public abstract void setState(State state);
	public abstract Event getEvent();
	public abstract void setEvent(Event event);
	
	// Messages
	public abstract void msgGotHungry();
	public abstract void msgFollowMeToTable(RestaurantFourWaiter waiter, int tableNumber, RestaurantFourMenu menu);
	public abstract void msgArrivedAtTable();
	public abstract void msgDoneThinkingOfOrder();
	public abstract void msgWhatDoYouWantToOrder();
	
	// Utilities
	public abstract void exitBuilding();
	public abstract void enterBuilding(SimSystem s);
	
}
