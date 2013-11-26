package simcity.interfaces.restaurant.two;

import simcity.buildings.restaurant.two.*;
import simcity.interfaces.GuiPartner;
import simcity.interfaces.restaurant.two.*;
//import restaurant.test.mock.EventLog;

public interface RestaurantTwoWaiter extends GuiPartner{

	
	//public static EventLog log = new EventLog();
	public abstract void setHost(RestaurantTwoHost host);
	public abstract void setCook(RestaurantTwoCook cook);
	public abstract void setCashier(RestaurantTwoCashier cashier);
	public abstract void wantsaBreak();
	public abstract void msgCanBreak(boolean bool);
	
		public abstract void msgReadyToOrder(RestaurantTwoCustomer c);
			
				
		
	public abstract void msgHereIsMyChoice(RestaurantTwoCustomer c, String choice);
		
	
	
	public abstract void msgnofood(int table);
	public abstract void msgFoodReady(int table);
	public abstract void msgReadyToPay(RestaurantTwoCustomer c);
	public abstract void msgHereIsCheck(int table,double p);
	public abstract void msgCustLeave(RestaurantTwoCustomer c);
	public abstract void msgAtTable();
	public abstract void msgAtLobby();
	public abstract void msgAtKitchen();
	public abstract void msgSeatCustomer(RestaurantTwoCustomer customer,
			int tableNumber);


}
