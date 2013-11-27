
package simcity.test.restauranttwo.mock;

import simcity.buildings.restaurant.two.*;
import simcity.interfaces.restaurant.two.*;
import simcity.test.mock.EventLog;
import simcity.test.mock.LoggedEvent;
import simcity.test.mock.Mock;

public class MockRestaurantTwoWaiter extends Mock implements RestaurantTwoWaiter{
	
	public RestaurantTwoSharedDataWaiterRole waiter; 
	public MockRestaurantTwoWaiter(String name){
		super(name);
	}
	public EventLog log = new EventLog();
	
	/*@Override
	public void msgHereIsCheck(Customer c, double d){
		  log.add(new LoggedEvent("test"));
	
	}*/
	
	
	
	@Override
	public void wantsaBreak(){
		
	}@Override
	public void msgCanBreak(boolean bool){
		
	}@Override
	public void msgnofood(int table){
		
	}@Override
	public void msgFoodReady(int table){
		
	}
@Override
	public void msgHereIsCheck(int table,double p){
		log.add(new LoggedEvent("waiter has recieved check"));
		 
	}@Override
	public void msgAtTable(){
		
	}@Override
	public void msgAtLobby(){
		
	}@Override
	public void msgAtKitchen(){
		
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
	public void msgReadyToOrder(RestaurantTwoCustomer c) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void msgHereIsMyChoice(RestaurantTwoCustomer c, String choice) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void msgReadyToPay(RestaurantTwoCustomer c) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void msgCustLeave(RestaurantTwoCustomer c) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void msgSeatCustomer(RestaurantTwoCustomer customer, int tableNumber) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setSpot(int key) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setOrderWheel(RestaurantTwoOrderWheel owheel) {
		// TODO Auto-generated method stub
		
	}





	
}