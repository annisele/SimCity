package simcity.interfaces.restaurant.one;

import simcity.buildings.restaurant.one.RestaurantOneCashierRole.CashierCheck;
import simcity.buildings.restaurant.one.RestaurantOneCheck;
import simcity.buildings.restaurant.one.RestaurantOneCustomerRole;
import simcity.buildings.restaurant.one.RestaurantOneWaiterRole;

public interface RestaurantOneCashier {
	
	public void msgCustomerPaying(RestaurantOneCheck check, Double payment);
	
	public void msgHereIsorder(String choice, int table, RestaurantOneCustomerRole customer, RestaurantOneWaiterRole waiter);
	
	public void CalculateCheck(RestaurantOneCheck check);
	
	public void finishCheck(CashierCheck c);
	
	public void msgMarketBill(double total);
	

}
