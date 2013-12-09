
package simcity.buildings.restaurant.four;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.buildings.restaurant.four.RestaurantFourMenu.foodType;
import simcity.gui.restaurantfour.RestaurantFourWaiterGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.four.RestaurantFourCustomer;
import simcity.interfaces.restaurant.four.RestaurantFourWaiter;


//////////////////////////////////////////////////////////////////////////

/**
 * Restaurant Four Waiter Role
 * @author Kevin Allan Chan
 *
 */

public class RestaurantFourWaiterRole extends Role implements RestaurantFourWaiter {
	
	/**
	 * Data
	 */
	
	private RestaurantFourSystem restaurantFourSystem;
	private List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());
	private RestaurantFourMenu menu;
	
	public enum Status {none, waitingAtRestaurant, waitingForConfirmation, confirmed, working};
	private Status status = Status.none;
	
	public enum customerState {none, withHost, sitting, wantToOrder, beingWalkedToForOrder, ordering, ordered};
	
	private Semaphore atDest = new Semaphore(0, true);
	
	// Constructors //////////////////////////////////////////////////////////////////////////
	
	public RestaurantFourWaiterRole(PersonAgent person) {
		this.person = person;
		this.gui = new RestaurantFourWaiterGui(this);
	}
	
	// Accessors //////////////////////////////////////////////////////////////////////////
	
	public PersonAgent getPerson() {
		return person;
	}
	
	public void setPerson(PersonAgent person) {
		this.person = person;
	}
	
	public RestaurantFourSystem getSystem() {
		return restaurantFourSystem;
	}
	
	public void setSystem(RestaurantFourSystem restaurantFourSystem) {
		this.restaurantFourSystem = restaurantFourSystem;
	}
	
	public List<MyCustomer> getCustomers() {
		return customers;
	}
	
	public RestaurantFourMenu getMenu() {
		return menu;
	}
	
	public void setMenu(RestaurantFourMenu menu) {
		this.menu = menu;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Messages
	 */

	public void msgGotToWork() {
		status = Status.waitingAtRestaurant;
		stateChanged();
	}

	public void msgStartWorking() {
		status = Status.confirmed;
		stateChanged();
	}
	
	public void msgSeatCustomerAtTable(RestaurantFourCustomer customer, int tableNumber, RestaurantFourMenu menu) {
		customers.add(new MyCustomer(customer, tableNumber));
		this.menu = menu;
		stateChanged();
	}
	
	public void msgImReadyToOrder(RestaurantFourCustomer customer) {
		synchronized(customers) {
			for(MyCustomer c : customers) {
				if (c.getCustomer() == customer) {
					c.setState(customerState.wantToOrder);
					stateChanged();
				}
			}
		}
	}
	
	public void msgThisIsMyOrder(RestaurantFourCustomer customer, foodType foodChoice) {
		synchronized(customers) {
			for(MyCustomer c : customers) {
				if (c.getCustomer() == customer) {
					c.setState(customerState.ordered);
					c.setFoodChoice(foodChoice);
					stateChanged();
				}
			}
		}
	}
	
	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Scheduler
	 */
	
	public boolean pickAndExecuteAnAction() {

		if (status == Status.waitingAtRestaurant) {
			status = Status.waitingForConfirmation;
			informHostOfArrival();
			return true;
		}

		if (status == Status.confirmed) {
			status = Status.working;
			workAtWaiterStation();
			return true;
		}
		
		if (status == Status.working) {
			synchronized(customers) {
				for (MyCustomer customer : customers) {
					if (customer.getState() == customerState.ordering) {
						customer.setState(customerState.ordered);
						walkToKitchenToTellCookOfOrder(customer);
					}
					
					if (customer.getState() == customerState.wantToOrder) {
						customer.setState(customerState.beingWalkedToForOrder);
						walkToCustomerForOrder(customer);
						return true;
					}
					if (customer.getState() == customerState.withHost) {
						customer.setState(customerState.sitting);
						seatCustomerAtTable(customer);
						return true;
					}
				}
			}
		}
		
		return false;
	}



	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Actions
	 */
	
	private void informHostOfArrival() {
		DoGoToHostLocation();
		try {
    		atDest.acquire();
    	} catch (InterruptedException e) {
    		//e.printStackTrace();
    	}
		restaurantFourSystem.getHost().msgWaiterReadyForWork(this);
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurantFourSystem.getName()), "Waiter: " + person.getName(), "I am ready to work!");
	}

	private void workAtWaiterStation() {
		DoGoToWaiterStation();
	}
	
	private void seatCustomerAtTable(MyCustomer customer) {
		DoGoToWaitingArea();
		try {
    		atDest.acquire();
    	} catch (InterruptedException e) {
    		//e.printStackTrace();
    	}
		customer.getCustomer().msgFollowMeToTable(this, customer.getTableNumber(), menu);
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurantFourSystem.getName()), "Waiter: " + person.getName(), "Hello! Please follow me to table " + customer.getTableNumber());
		DoGoToTable(customer.getTableNumber());
		try {
    		atDest.acquire();
    	} catch (InterruptedException e) {
    		//e.printStackTrace();
    	}
		DoGoToWaiterStation();
	}
	
	private void walkToCustomerForOrder(MyCustomer customer) {
		DoGoToTable(customer.getTableNumber());
		try {
    		atDest.acquire();
    	} catch (InterruptedException e) {
    		//e.printStackTrace();
    	}
		customer.getCustomer().msgWhatDoYouWantToOrder();
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurantFourSystem.getName()), "Waiter: " + person.getName(), "What would you like to order?");
	}
	
	private void walkToKitchenToTellCookOfOrder(MyCustomer customer) {
		DoGoToCook();
		try {
    		atDest.acquire();
    	} catch (InterruptedException e) {
    		//e.printStackTrace();
    	}
		/*
		 * 
		 */
	}
	
	// Animation DoXYZ
	private void DoGoToHostLocation() {
		((RestaurantFourWaiterGui) gui).DoGoToHostLocation();
	}
	
	private void DoGoToWaiterStation() {
		((RestaurantFourWaiterGui) gui).DoGoToWaiterStation();
	}
	
	private void DoGoToWaitingArea() {
		((RestaurantFourWaiterGui) gui).DoGoToWaitingArea();
	}
	
	private void DoGoToTable(int tableNumber) {
		((RestaurantFourWaiterGui) gui).DoGoToTable(tableNumber);
	}
	
	private void DoGoToCook() {
		((RestaurantFourWaiterGui) gui).DoGoToCookLocation();
	}
	
	// Utilities //////////////////////////////////////////////////////////////////////////
	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		this.restaurantFourSystem = (RestaurantFourSystem)s;
		msgGotToWork();
		try {
    		atDest.acquire();
    	} catch (InterruptedException e) {
    		//e.printStackTrace();
    	}
	}

	public void atDestination() {
		atDest.release();
	}
	
	// Utility Classes //////////////////////////////////////////////////////////////////////////
	/**
	 * MyCustomer class - for customers that just walk in
	 */
	public class MyCustomer {
		
		// Data ////////////////////////////////////////////////////////////////////////////////
		private RestaurantFourCustomer customer;
		private customerState state;
		private int tableNumber;
		private foodType foodChoice;
		
		// Constructor /////////////////////////////////////////////////////////////////////////
		MyCustomer(RestaurantFourCustomer customer, int tableNumber) {
			this.customer = customer;
			this.tableNumber = tableNumber;
			this.state = customerState.withHost;
		}
		
		// Accessors ///////////////////////////////////////////////////////////////////////////
		public RestaurantFourCustomer getCustomer() {
			return customer;
		}
		
		public void setCustomer(RestaurantFourCustomer customer) {
			this.customer = customer;
		}
		
		public customerState getState() {
			return state;
		}
		
		public void setState(customerState state) {
			this.state = state;
		}
		
		public int getTableNumber() {
			return tableNumber;
		}
		
		public void setTableNumber(int tableNumber) {
			this.tableNumber = tableNumber;
		}
		
		public foodType getFoodChoice() {
			return foodChoice;
		}
		
		public void setFoodChoice(foodType foodChoice) {
			this.foodChoice = foodChoice;
		}
		
		// Functions ///////////////////////////////////////////////////////////////////////////
		
	}
	
}

