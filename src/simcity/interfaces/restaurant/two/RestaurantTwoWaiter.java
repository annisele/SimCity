package simcity.interfaces.restaurant.two;

import simcity.buildings.restaurant.two.*;
import simcity.interfaces.restaurant.two.*;
//import restaurant.test.mock.EventLog;

public interface RestaurantTwoWaiter {

	
	//public static EventLog log = new EventLog();
	public abstract void setHost(RestaurantTwoHostRole host);
	public abstract void setCook(RestaurantTwoCookRole cook);
	public abstract void setCashier(RestaurantTwoCashierRole cashier);
	public abstract void wantsaBreak();
	public abstract void msgCanBreak(boolean bool);
	public abstract void msgSeatCustomer(RestaurantTwoCustomerRole c, int table_num);
	
		public abstract void msgReadyToOrder(RestaurantTwoCustomerRole c);
			
				
		
	public abstract void msgHereIsMyChoice(RestaurantTwoCustomerRole c, String choice);
		
	
	
	public abstract void msgnofood(int table);
	public abstract void msgFoodReady(int table);
	public abstract void msgReadyToPay(RestaurantTwoCustomerRole c);
	public abstract void msgHereIsCheck(int table,double p);
	public abstract void msgCustLeave(RestaurantTwoCustomerRole c);
	public abstract void msgAtTable();
	public abstract void msgAtLobby();
	public abstract void msgAtKitchen();


}
