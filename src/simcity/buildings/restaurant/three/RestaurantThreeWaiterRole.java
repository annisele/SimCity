package simcity.buildings.restaurant.three;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.restaurantthree.RestaurantThreeWaiterGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.three.RestaurantThreeCashier;
import simcity.interfaces.restaurant.three.RestaurantThreeCook;
import simcity.interfaces.restaurant.three.RestaurantThreeHost;
import simcity.interfaces.restaurant.three.RestaurantThreeWaiter;
import simcity.test.mock.EventLog;

/**
 * Restaurant Three Waiter Role
 * @author Levonne Key
 *
 */
public class RestaurantThreeWaiterRole extends Role implements RestaurantThreeWaiter{
	private Timer timer = new Timer();
	private RestaurantThreeHost host;
	private RestaurantThreeCook cook;
	private RestaurantThreeCashier cashier;
	public enum CustomerState {WAITING,READY_TO_ORDER,ORDERING,FOOD_SERVED,IS_DONE,NO_ACTION, READY_TO_PAY, EXPENSIVE_LEAVE, REORDER, CANNOT_AFFORD_TO_REORDER};
	private List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());
	public  EventLog log = new EventLog();
	private String name;
	private Semaphore atDest = new Semaphore(0, true);
	private RestaurantThreeSystem restaurantThreeSystem;
	public void atDestination() {
		atDest.release();
	}
	public RestaurantThreeWaiterRole(PersonAgent person) {
		this.person = person;
		this.gui = new RestaurantThreeWaiterGui(this);
	}
	public void setHost(RestaurantThreeHost host) {
		this.host = host;
	}
	public void setCook(RestaurantThreeCook cook) {
		this.cook = cook;
	}

	public void setCashier(RestaurantThreeCashier cashier) {
		this.cashier = cashier;
	}

	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void exitBuilding() {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurantThreeSystem.getName()), "Restaurant Three Waiter: " + person.getName(), "Leaving restaurant three");	
		gui.DoExitBuilding();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		restaurantThreeSystem.exitBuilding(this);
		person.roleFinished();
		
	}

	@Override
	public void enterBuilding(SimSystem s) {
		restaurantThreeSystem = (RestaurantThreeSystem)s;
		
	}
	
	public class MyCustomer {
		
	}
}




	


