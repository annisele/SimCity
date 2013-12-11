package simcity.interfaces.restaurant.three;

import simcity.buildings.restaurant.three.*;
import simcity.interfaces.GuiPartner;
import simcity.interfaces.restaurant.three.*;
public interface RestaurantThreeWaiter extends GuiPartner{
	public abstract void setHost(RestaurantThreeHost host);
	public abstract void setCook(RestaurantThreeCook cook);
	public abstract void setCashier(RestaurantThreeCashier cashier);
	public abstract void atDestination();
	public abstract void msgPleaseSeatCustomer(
			RestaurantThreeCustomer restaurantThreeCustomer, int tableNumber);
	
	public abstract void msgGetMyOrder(
			RestaurantThreeCustomer restaurantThreeCustomerRole);
	public abstract void msgHereIsMyChoice(RestaurantThreeCustomer restaurantThreeCustomerRole,String choice);
	public abstract void msgCheckPlease(
			RestaurantThreeCustomer restaurantThreeCustomerRole);

}

	
	