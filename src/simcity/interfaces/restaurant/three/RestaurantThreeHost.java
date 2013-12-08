package simcity.interfaces.restaurant.three;

import java.util.List;

import simcity.PersonAgent;
import simcity.SimSystem;
import simcity.buildings.restaurant.three.RestaurantThreeSystem;
import simcity.interfaces.GuiPartner;

public interface RestaurantThreeHost extends GuiPartner {


	public abstract void exitBuilding();

	public abstract void enterBuilding(SimSystem s);

	

	public abstract void msgIWantFood(RestaurantThreeCustomer restaurantThreeCustomerRole);

	public abstract void msgAddWaiter(RestaurantThreeWaiter role);

	int getWaitingCustomers();



}
