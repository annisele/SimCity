
package simcity.buildings.restaurant.four;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
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
	
	public enum Status {none, waitingAtRestaurant, waitingForConfirmation, confirmed, working};
	private Status status = Status.none;
	
	public enum customerState {none, withHost};
	
	// Constructors //////////////////////////////////////////////////////////////////////////
	
	public RestaurantFourWaiterRole(PersonAgent person) {
		this.person = person;
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
	
	public void msgSeatCustomerAtTable(RestaurantFourCustomer customer, int tableNumber) {
		customers.add(new MyCustomer(customer, tableNumber));
		stateChanged();
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

		if (status == St)
		
		if (status == Status.working) {
			synchronized(customers) {
				for (MyCustomer customer : customers) {
					if (customer.getState() == customerState.withHost) {
						seatCustomerAtTable(customer);
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
		
	}

	// Animation DoXYZ
	private void DoGoToHostLocation() {
		
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

