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
	private enum WaiterState {WORKING,  REQUESTED_BREAK, HAVING_BREAK, ON_BREAK, NEED_BREAK, GOINGOFFBREAK, DENIED_BREAK };  
	private WaiterState waiterState;
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
	public RestaurantThreeWaiterRole(PersonAgent person) {
		//Menu.put("chicken",10.99);	
		//Menu.put("steak",15.99);
		//Menu.put("salad",5.99);
		//Menu.put("pizza",8.99);
		//this.computer = computer;
		this.person = person;
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
		// TODO Auto-generated method stub
		return false;
	}
	
	//messages
	 public void msgFollowMeToTable(RestaurantThreeCustomer customer, int tableNum){
		 try {
			 for (MyCustomer c: customers) {
				 if(c.customer == customer) {
					 c.state = CustomerState.WAITING;
					 stateChanged();
					 return;
				 }
			 }
		 } catch (ConcurrentModificationException e) {
			 AlertLog.getInstance().logError(AlertTag.valueOf(restaurantThreeSystem.getName()), "Restaurant Three Waiter: " + person.getName(), "Concurrent modification exception.");
			}
			customers.add(new MyCustomer(customer, tableNum, CustomerState.WAITING));
			stateChanged();
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
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurantThreeSystem.getName()), "RestaurantThreeHost: " + person.getName(), "Ready to work at the restaurant!");
		
		((RestaurantThreeWaiterGui) gui).DoGoToHome();
		
	}
	public String getName() {
		return person.getName();
	}
	
}




	


