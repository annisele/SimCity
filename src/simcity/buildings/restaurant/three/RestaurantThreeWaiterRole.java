package simcity.buildings.restaurant.three;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import simcity.interfaces.restaurant.three.RestaurantThreeCustomer;
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
	public Map<String,Double> Menu= new HashMap<String, Double>();
	private RestaurantThreeHost host;

	private RestaurantThreeCook cook;
	private RestaurantThreeComputer computer;
	private RestaurantThreeCashier cashier;
	private enum WaiterState {NONE, WORKING,  REQUESTED_BREAK, HAVING_BREAK, ON_BREAK, NEED_BREAK, GOINGOFFBREAK, DENIED_BREAK };  
	private WaiterState waiterState = WaiterState.NONE;
	private enum CustomerState {WAITING,READY_TO_ORDER,ORDERING,FOOD_SERVED,IS_DONE,NO_ACTION, READY_TO_PAY, EXPENSIVE_LEAVE, REORDER, CANNOT_AFFORD_TO_REORDER};
	private List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());
	
	public class MyCustomer {
		private RestaurantThreeCustomer customer;
		private CustomerState state;
		private int tableNum;
		private String choice;
		
		MyCustomer(RestaurantThreeCustomer customer, int tableNum, CustomerState s) {
			this.customer = customer;
			this.tableNum = tableNum;
			this.state = s;
		}
	}
	public  EventLog log = new EventLog();
	private String name;
	private Semaphore atDest = new Semaphore(0, true);
	private RestaurantThreeSystem restaurantThreeSystem;
	public void atDestination() {
		atDest.release();
	}
	public RestaurantThreeWaiterRole(PersonAgent p) {
		//Menu.put("chicken",10.99);	
		//Menu.put("steak",15.99);
		//Menu.put("salad",5.99);
		//Menu.put("pizza",8.99);
		//this.computer = computer;
		person = p;
		this.gui = new RestaurantThreeWaiterGui(this);
		waiterState = WaiterState.WORKING;
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
		try {
			for(MyCustomer c : customers) {
				if(c.state == CustomerState.WAITING) {
					GiveCustomerMenu(c);
					return true;
				}
			}
		}
		catch(ConcurrentModificationException e) {
			return false;
		}
		return false;
	}
	
	//messages
	@Override
	public void msgPleaseSeatCustomer(RestaurantThreeCustomer c, int tableNumber) {
		try {
			for(MyCustomer mc : customers) {
				if(mc.customer == c) {
					mc.state = CustomerState.WAITING;
					stateChanged();
					return;
				}
			}
		}
		catch(ConcurrentModificationException e) {
			AlertLog.getInstance().logError(AlertTag.valueOf(restaurantThreeSystem.getName()), "Restaurant 3 Waiter: " + person.getName(), "Concurrent modification exception.");
		}
		customers.add(new MyCustomer(c, tableNumber, CustomerState.WAITING));
		stateChanged();
	}
	private void GiveCustomerMenu(MyCustomer c) {
		
		c.state = CustomerState.READY_TO_ORDER;
		//c.c.msgHereIsMenu(this, new RestaurantFiveMenu());
		//System.out.println("HIHIHIHIHIHIHI");
		//System.out.println(restaurantThreeSystem.getName());
		//System.out.println(person.getName());
		//System.out.println(c.customer.getName());
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurantThreeSystem.getName()), "Restaurant 3 Waiter: " + person.getName(), "Here is a menu, " + c.customer.getName() + ".");
	}

	@Override
	public void exitBuilding() {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurantThreeSystem.getName()), "Restaurant 3 Waiter: " + person.getName(), "Leaving restaurant three");	
		gui.DoExitBuilding();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			
		}
		restaurantThreeSystem.exitBuilding(this);
		person.roleFinished();
		
	}

	@Override
	public void enterBuilding(SimSystem s) {
		restaurantThreeSystem = (RestaurantThreeSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurantThreeSystem.getName()), "Restaurant 3 Waiter: " + person.getName(), "Ready to work at the restaurant!");
		
		((RestaurantThreeWaiterGui) gui).DoGoToHome();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			
		}
		
	}
	public String getName() {
		return person.getName();
	}
	
}




	


