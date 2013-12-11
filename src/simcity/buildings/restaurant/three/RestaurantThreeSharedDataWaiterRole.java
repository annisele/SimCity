package simcity.buildings.restaurant.three;

import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.buildings.restaurant.two.RestaurantTwoOrderWheel;
import simcity.buildings.restaurant.two.RestaurantTwoSystem;
import simcity.buildings.restaurant.two.RestaurantTwoWaiterRole;
import simcity.gui.restaurantthree.RestaurantThreeWaiterGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.three.*;

public class RestaurantThreeSharedDataWaiterRole extends RestaurantThreeWaiterRole  implements simcity.interfaces.restaurant.three.RestaurantThreeWaiter{

	public RestaurantThreeSharedDataWaiterRole(PersonAgent person) {
		super(person);
		
		// TODO Auto-generated constructor stub
	}
	private Semaphore atDest = new Semaphore(0, true);
	private RestaurantThreeOrderWheel orderWheel;
	private RestaurantThreeSystem R3;
	public void setOrderWheel(RestaurantThreeOrderWheel wheel) {
		this.orderWheel = wheel;
	}
	public void DeliverOrder(MyCustomer customer) {
		((RestaurantThreeWaiterGui)gui).DoGoToKitchen();
	}
}