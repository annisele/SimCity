
package simcity.buildings.restaurant.four;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.restaurantfour.RestaurantFourWaiterGui;
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
	
	public enum customerState {none, withHost, sitting, wantToOrder, beingWalkedToForOrder};
	
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
		restaurantFourSystem.getHost().msgWaiterReadyForWork(this);
	}

	private void seatCustomerAtTable(MyCustomer customer) {
		DoGoToWaitingArea();
		customer.getCustomer().msgFollowMeToTable(this, customer.getTableNumber(), menu);
		DoGoToTable(customer.getTableNumber());
	}

	private void workAtWaiterStation() {
		DoGoToWaiterStation();
	}
	
	private void walkToCustomerForOrder(MyCustomer customer) {
		DoGoToTable(customer.getTableNumber());
	}
	
	// Animation DoXYZ
	private void DoGoToHostLocation() {
		((RestaurantFourWaiterGui) gui).DoGoToHostLocation();
	}
	
	private void DoGoToWaiterStation() {
		
	}
	
	private void DoGoToWaitingArea() {
		
	}
	
	private void DoGoToTable(int tableNumber) {
		
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
	}

	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
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
		
		// Functions ///////////////////////////////////////////////////////////////////////////
		
	}
	
}

