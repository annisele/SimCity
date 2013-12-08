package simcity.interfaces.restaurant.three;

import java.util.List;

import simcity.PersonAgent;
import simcity.SimSystem;
import simcity.buildings.restaurant.three.RestaurantThreeSystem;
import simcity.interfaces.GuiPartner;

public interface RestaurantThreeHost extends GuiPartner {

	public abstract PersonAgent getPerson();

	public abstract void setPerson(PersonAgent person);

	public abstract RestaurantThreeSystem getSystem();

	public abstract void setSystem(RestaurantThreeSystem resSystem);

	public abstract List getWaitersList();

	public abstract void msgGotToWork();

	public abstract void exitBuilding();

	public abstract void enterBuilding(SimSystem s);

	public abstract int numWaitingCustomers();

	public abstract void msgIWantFood(RestaurantThreeCustomer restaurantThreeCustomerRole);



}
