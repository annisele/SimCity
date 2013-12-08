package simcity.interfaces.restaurant.one;

import simcity.buildings.restaurant.one.RestaurantOneCustomerRole;
import simcity.buildings.restaurant.one.RestaurantOneWaiterRole;

public interface RestaurantOneHost {
	
	public abstract void msgIWantFood(RestaurantOneCustomerRole cust);
	
	public abstract void msgNewWaiter(RestaurantOneWaiterRole w);
	
	public abstract void msgTableIsFree(int table);
	
	public abstract void msgIWantABreak(RestaurantOneWaiterRole w);
	
	public abstract void msgImOffBreak(RestaurantOneWaiterRole w);
	
	public abstract void msgLeaving(RestaurantOneCustomerRole c);
	
	public abstract void msgCustomerSeated();

}
