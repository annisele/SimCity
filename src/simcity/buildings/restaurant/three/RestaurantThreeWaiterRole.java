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
import simcity.buildings.restaurant.four.RestaurantFourWaiterRole.MyCustomer;
import simcity.buildings.restaurant.four.RestaurantFourWaiterRole.customerState;
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
	private RestaurantThreeMenu menu;
	private List<Order> finishedOrders = new ArrayList<Order>();
	private RestaurantThreeCook cook;
	private RestaurantThreeComputer computer;
	private RestaurantThreeCashier cashier;
	private enum WaiterState {NONE, ENTERING_RESTAURANT, INFORM_HOST, WORKING,  REQUESTED_BREAK, HAVING_BREAK, ON_BREAK, NEED_BREAK, GOINGOFFBREAK, DENIED_BREAK };  
	private WaiterState waiterState = WaiterState.NONE;
	private enum CustomerState {NONE, WAITING, READY_TO_ORDER,ORDERING,FOOD_SERVED,IS_DONE,NO_ACTION, READY_TO_PAY, EXPENSIVE_LEAVE, REORDER, CANNOT_AFFORD_TO_REORDER};
	private CustomerState status = CustomerState.NONE;
	private List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());
	private class Order {
		String choice;
		int tableNum;
		
		Order (String choice,int table ) {
			this.choice = choice;
			this.tableNum = table;
		}
	}
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
		public RestaurantThreeCustomer getCustomer() {
			return customer;
		}
		public void setCustomer(RestaurantThreeCustomer c) {
			this.customer = c;
		}
		public CustomerState getState() {
			return state;
		}
		public void setState(CustomerState s) {
			this.state = s;
		}
		public int getTableNumber() {
			return tableNum;
		}
		public void setTableNumber(int tableNumber) {
			this.tableNum = tableNumber;
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
		person = p;
		this.gui = new RestaurantThreeWaiterGui(this);
		waiterState = WaiterState.WORKING;
	}
	//utility
	public PersonAgent getPerson() {
		return person;
	}
	public void setPerson(PersonAgent p) {
		person = p;
	}
	public RestaurantThreeSystem getSystem() {
		return restaurantThreeSystem;
	}
	public void setSystem(RestaurantThreeSystem system) {
		restaurantThreeSystem = system;
	}
	public List<MyCustomer> getCustomers() {
		return customers;
	}
	public RestaurantThreeMenu getMenu() {
		return menu;
	}
	public WaiterState getWaiterState() {
		return waiterState;
	}
	public void setWaiterState(WaiterState w) {
		waiterState = w;
	}
	public void setMenu(RestaurantThreeMenu m) {
		menu = m;
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
	//messages
		public void msgGotToWork() {
			waiterState = WaiterState.ENTERING_RESTAURANT;
			stateChanged();
		}
		public void msgBeginWork() {
			waiterState = WaiterState.WORKING;
			stateChanged();
		}
		@Override
		public void msgPleaseSeatCustomer(RestaurantThreeCustomer c, int tableNumber) {
			synchronized(customers) {
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
		}
		public void msgGetMyOrder(RestaurantThreeCustomer customer) {
			synchronized(customers) {
				for(MyCustomer c : customers) {
					if (c.getCustomer() == customer) {
						c.setState(CustomerState.READY_TO_ORDER);
						stateChanged();
					}
				}
			}
		}
		public void msgHereIsMyChoice(RestaurantThreeCustomer customer, String choice) {
			synchronized(customers) {
			for(MyCustomer c:customers){
			    if(c.getCustomer() == customer){
			    	c.choice = choice;
			    	c.setState(CustomerState.ORDERING);
			    	stateChanged();
			    }
			}
			}
		}
		//from cook
		public void msgOrderIsReady(int tableNum, String choice) {
			synchronized(customers) {
			for(MyCustomer c:customers){
			    if(c.tableNum == tableNum){
				c.state = CustomerState.FOOD_SERVED;
				stateChanged();
				
			    }
			}
			finishedOrders.add(new Order(choice, tableNum));
			}
		}
		//scheduler
		public boolean pickAndExecuteAnAction() {

		if (waiterState == WaiterState.ENTERING_RESTAURANT) {
			waiterState = WaiterState.INFORM_HOST;
			meetHost();
			return true;
		}

		if (waiterState == WaiterState.WORKING) {
			
			synchronized(customers) {
				for (MyCustomer customer : customers) {
					if (customer.getState() == CustomerState.READY_TO_ORDER) {
					//	customer.setState(CustomerState.beingWalkedToForOrder);
					//	walkToCustomerForOrder(customer);
						return true;
					}
					//if (customer.getState() == CustomerState.withHost) {
					//	customer.setState(CustomerState.sitting);
					//	seatCustomer(customer);
					//	return true;
					//}
				}
			}
		}
		/*
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
		} */
		return false;
	}
	
	//actions

	private void meetHost() {
		((RestaurantThreeWaiterGui)gui).DoGoToHost();
		try {
    		atDest.acquire();
    	} catch (InterruptedException e) {
    		
    	}
		restaurantThreeSystem.getRestaurantThreeHost().msgWaiterReadyForWork(this);
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurantThreeSystem.getName()), "Restaurant 3 Waiter: " + person.getName(), "I am ready to work!");	
		((RestaurantThreeWaiterGui)gui).DoGoToStation();
		try {
    		atDest.acquire();
    	} catch (InterruptedException e) {
    		
    	}
	}
	
	private void seatCustomer(MyCustomer c) {
		((RestaurantThreeWaiterGui)gui).DoGoToWaitingCustomer();
		try {
    		atDest.acquire();
    	} catch (InterruptedException e) {
    		
    	}
		c.getCustomer().msgFollowMeToTable(this, c.getTableNumber());
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurantThreeSystem.getName()), "Restaurant 3 Waiter: " + person.getName(), "PLease follow me to table, " + c.customer.getName() + ".");
	
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
		msgGotToWork();
		try {
    		atDest.acquire();
    	} catch (InterruptedException e) {
    		//e.printStackTrace();
    	}
		/*
		((RestaurantThreeWaiterGui) gui).DoGoToHome();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			
		}
		*/
		
	}
	
	public String getName() {
		return person.getName();
	}
	
	
}




	


