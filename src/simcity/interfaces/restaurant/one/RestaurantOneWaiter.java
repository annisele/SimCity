package simcity.interfaces.restaurant.one;

import simcity.buildings.restaurant.one.RestaurantOneCheck;
import simcity.buildings.restaurant.one.RestaurantOneCustomerRole;

public interface RestaurantOneWaiter {
	
	public void msgSitAtTable(RestaurantOneCustomerRole cust, int table);
	
	public void msgReadyToOrder(RestaurantOneCustomerRole cust);
	
	public void msgGiveChoice(String chocie, RestaurantOneCustomerRole c);
	
	public void msgOrderIsReady(String choice, int table);
	
	public void msgDoneEatingAndLeaving(RestaurantOneCustomerRole c);
	
	public void msgTellCustomerReorder(int table);
	
	public void msgLeftRestaurant(RestaurantOneCustomerRole c);
	
	public void msgAtTable();
	
	public void msgLeavingCustomer();
	
	public void msgWithCook();
	
	public void msgBreakApproved();
	
	public void msgNoBreak();
	
	public void msgWantBreak();
	
	public void msgHereIsComputedCheck(RestaurantOneCheck c);
		
	

}
