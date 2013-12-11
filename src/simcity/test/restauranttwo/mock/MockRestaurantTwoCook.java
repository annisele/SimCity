
package simcity.test.restauranttwo.mock;

import java.util.Map;

import simcity.SimSystem;
import simcity.buildings.restaurant.two.*;
import simcity.interfaces.restaurant.two.*;
import simcity.test.mock.EventLog;
import simcity.test.mock.LoggedEvent;
import simcity.test.mock.Mock;

public class MockRestaurantTwoCook extends Mock implements RestaurantTwoCook{
	
	public RestaurantTwoSharedDataWaiterRole waiter; 
	//public EventLog log;
	public MockRestaurantTwoCook(String name){
		super(name);
	}
	public EventLog log = new EventLog();
	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void msgHereAreItems(Map<String, Integer> itemsToDeliver,
			double change) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void enterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hack_chicken() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hack_salad() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hack_steak() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void msgCookOrder(RestaurantTwoWaiter restaurantTwoWaiterRole,
			int table_num, String choice) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setOrderWheel(RestaurantTwoOrderWheel owheel) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Map<String, Double> getMenu() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setCashier(RestaurantTwoCashier cashier) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void msgLeaveWork() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Map<String, Integer> getFoodStock() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}