package simcity.interfaces.restaurant.two;

import simcity.SimSystem;
import simcity.buildings.restaurant.two.*;
//import restaurant.test.mock.EventLog;
import simcity.interfaces.GuiPartner;

public interface RestaurantTwoCustomer extends GuiPartner{
	//EventLog log = new EventLog();
	public abstract void setWaiter(RestaurantTwoWaiter waitr);
	
	
	public abstract void setHost(RestaurantTwoHostRole host);
	public abstract void setCook(RestaurantTwoCookRole cook);
	public abstract void setCashier(RestaurantTwoCashierRole cashier);
	
public abstract void hack_chicken();
	
public abstract void hack_mdebt();
public abstract void hack_salad();

public abstract void hack_steak();

//public abstract String getCustomerName() ;

// Messages

public abstract void gotHungry() ;//from animation


public abstract void msgSitAtTable(RestaurantTwoWaiter w, int a) ;


public abstract void msgAnimationFinishedGoToSeat() ;

public abstract void msgWhatsYourOrder();

public abstract void msgReorder(int table, String c);

public abstract void msgFoodIsHere();

public abstract void msgHereIsCheck(double c);

public abstract void msgPaying() ;
	//from animation
public abstract void msgGoodbye() ;
//public abstract void msgAnimationFinishedLeaveRestaurant() ;
	//from animation

abstract void msgEnterBuilding(SimSystem s);
public abstract void msgArrivedAtRestaurant();

}
