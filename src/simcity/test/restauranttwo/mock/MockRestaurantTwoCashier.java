
package simcity.test.restauranttwo.mock;

import simcity.SimSystem;
import simcity.buildings.restaurant.two.*;
import simcity.interfaces.restaurant.two.*;
import simcity.test.mock.EventLog;
import simcity.test.mock.LoggedEvent;
import simcity.test.mock.Mock;

public class MockRestaurantTwoCashier extends Mock implements RestaurantTwoCashier{
	
	public RestaurantTwoSharedDataWaiterRole waiter; 
	//public EventLog log;
	public MockRestaurantTwoCashier(String name){
		super(name);
	}
	public EventLog log = new EventLog();
	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void msgPleasePay(String marketName, double payment, int orderNum) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void enterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void modBalance(double i) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void msgHereIsMoney(RestaurantTwoCustomer restaurantTwoCustomerRole,
			double customer_check) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void msgCustomerOrder(RestaurantTwoWaiter restaurantTwoWaiterRole,
			RestaurantTwoCustomer c, int table_num, String choice) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public double msgGetCheck(RestaurantTwoCustomer c) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void msgLeaveWork() {
		// TODO Auto-generated method stub
		
	}

	
	
}