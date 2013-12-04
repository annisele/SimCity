package simcity.interfaces.restaurant.one;

import simcity.buildings.restaurant.one.RestaurantOneCustomerRole;
import simcity.buildings.restaurant.one.RestaurantOneWaiterRole;

public interface RestaurantOneHost {
	
	public void msgIWantFood(RestaurantOneCustomerRole cust);
	public void msgNewWaiter(RestaurantOneWaiterRole w);
	public void msgTableIsFree(int table);
	public void msgIWantABreak(RestaurantOneWaiterRole w);
	public void msgImOffBreak(RestaurantOneWaiterRole w);
	public void msgLeaving(RestaurantOneCustomerRole c);
	public void msgCustomerSeated();

}
