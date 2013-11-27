
package simcity.test.restauranttwo.mock;

import simcity.SimSystem;
import simcity.buildings.restaurant.two.*;
import simcity.interfaces.restaurant.two.*;
import simcity.test.mock.EventLog;
import simcity.test.mock.LoggedEvent;
import simcity.test.mock.Mock;

public class MockRestaurantTwoCustomer extends Mock implements RestaurantTwoCustomer{
	
	public RestaurantTwoSharedDataWaiterRole waiter; 
	//public EventLog log;
	public MockRestaurantTwoCustomer(String name){
		super(name);
	}
	public EventLog log = new EventLog();

	@Override
	 public void setWaiter(RestaurantTwoWaiter waitr){
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
	public void gotHungry() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void msgAnimationFinishedGoToSeat() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void msgWhatsYourOrder() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void msgReorder(int table, String c) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void msgFoodIsHere() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public  void msgHereIsCheck(double c) {
		  log.add(new LoggedEvent("Received check from cashier"));
/*
	        if(this.name.toLowerCase().contains("thief")){
	                //test the non-normative scenario where the customer has no money if their name contains the string "theif"
	                cashier.IAmShort(this, 0);

	        }else if (this.name.toLowerCase().contains("rich")){
	                //test the non-normative scenario where the customer overpays if their name contains the string "rich"
	                cashier.HereIsMyPayment(this, Math.ceil(total));

	        }else{
	                //test the normative scenario
	                cashier.HereIsMyPayment(this, total);
	        }
		*/
	}
	
	@Override
	public void msgPaying() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void msgGoodbye() {
		 log.add(new LoggedEvent("Customer leaving"));
		// TODO Auto-generated method stub
		
	}
	/*@Override
	public void msgAnimationFinishedLeaveRestaurant() {
		// TODO Auto-generated method stub
		
	}*/


	@Override
	public void hack_mdebt() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setHost(RestaurantTwoHost host) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setCook(RestaurantTwoCook cook) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setCashier(RestaurantTwoCashier cashier) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void msgSitAtTable(RestaurantTwoWaiter w, int a) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void enterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void msgArrivedAtRestaurant(double money) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	
}