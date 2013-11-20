package simcity.buildings.restaurant.four;

import java.util.*;

import simcity.PersonAgent;
import simcity.Role;
import simcity.gui.restaurantfour.RestaurantFourHostGui;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Restaurant Four Host Role
 */

public class RestaurantFourHostRole extends Role {
	
	// Data ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// From PersonAgent
	private PersonAgent person;
	private String name;
	private double cash;

	private RestaurantFourHostGui gui;
	
	private int leastBusy; // variable to load balance waiters
	private int numOnBreak = 0;
	
	public List<MyCustomer> waitingCustomers = Collections.synchronizedList(new ArrayList<MyCustomer>());
	
	public enum customerState {waiting, stillWaiting, decidingToStay, willWait, willLeave};
	
	public List<MyWaiter> waitersAvailable = Collections.synchronizedList(new ArrayList<MyWaiter>());
	public Collection<RestaurantFourTable> tables;
	
	// Constructors ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public RestaurantFourHostRole(String name) {
		super();
		this.name = name;
		// make some tables
		tables = new ArrayList<RestaurantFourTable>(stats.getNTables());
		for (int ix = 1; ix <= stats.getNTables(); ix++) {
			tables.add(new RestaurantFourTable(ix));//how you add to a collections
		}
	}

	// Messages ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void msgIWantFood(RestaurantFourCustomerRole cust) {
		waitingCustomers.add(new MyCustomer(cust));
		stateChanged();
	}

	public void msgIWantToGoOnBreak(Waiter waiter) {
		if (waitersAvailable.size()-numOnBreak > 1) {
			synchronized(waitersAvailable) {
				for (MyWaiter w : waitersAvailable) {
					if (w.getWaiter() == waiter) {
						w.setBreak(true);
						numOnBreak++;
						print("You can go on break when you are done.");
						stateChanged();
					}
				}
			}
		}
		else {
			print ("You cannot go on break because you are the only worker. Sorry " + waiter.getName());
			stateChanged();
		}
	}
	
	public void msgBackFromBreak(Waiter waiter) {
		synchronized(waitersAvailable) {
			for (MyWaiter w : waitersAvailable) {
				if (w.getWaiter() == waiter) {
					w.setBusyLevel(1);
					w.setBreak(false);
					numOnBreak--;
					print("Start working!");
					stateChanged();
				}
			}
		}
	}
	
	public void msgImReadyToWork(Waiter waiter) {
		waitersAvailable.add(new MyWaiter(waiter));
		stateChanged();
	}

	public void msgTableEmpty(int tableNumber, Waiter waiter) {
		for (Table table: tables) {
			if (table.getTableNumber() == tableNumber) {
				table.setUnoccupied();
			}
		}
		synchronized(waitersAvailable) {
			for (MyWaiter w: waitersAvailable) {
				if (w.getWaiter() == waiter) {
					w.setLessBusy();
				}
			}
		}
		stateChanged();
	}
	
	public void msgWeAreFull(Customer cust) {
		synchronized(waitingCustomers) {
			for (MyCustomer customer : waitingCustomers) {
				if (customer.getCustomer() == cust) {
					customer.setState(customerState.decidingToStay);
					stateChanged();
				}
			}
		}
	}
	
	public void msgIWillWait(Customer cust) {
		synchronized(waitingCustomers) {
			for (MyCustomer customer : waitingCustomers) {
				if (customer.getCustomer() == cust) {
					customer.setState(customerState.willWait);
					stateChanged();
				}
			}
		}
	}
	
	public void msgImLeaving(Customer cust) {
		synchronized(waitingCustomers) {
			for (MyCustomer customer : waitingCustomers) {
				if (customer.getCustomer() == cust) {
					customer.setState(customerState.willLeave);
					stateChanged();
				}
			}
		}
	}

	// Scheduler ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	protected boolean pickAndExecuteAnAction() {
		/* Think of this next rule as:
            Does there exist a table and customer,
            so that table is unoccupied and customer is waiting.
            If so seat him at the table.
		 */
		
		synchronized(waitingCustomers) {
			for (MyCustomer c : waitingCustomers) {
				if (c.getState() == customerState.willLeave){
					waitingCustomers.remove(c);
					return true;
				}
			}
		}

		synchronized(waitersAvailable) {
			for (MyWaiter w : waitersAvailable) {
				if (w.isOnBreak() && w.getBusyLevel() != 1000) {
					w.setBusyLevel(1000);
					allowWaiterOnBreak(w);
					return true;
				}
			}
		}

		leastBusy = 100;
		
		synchronized(waitersAvailable) {
			for (MyWaiter w : waitersAvailable) {
				if (!w.isOnBreak()) {
					if (w.getBusyLevel() < leastBusy) {
						leastBusy = w.getBusyLevel();
					}
				}
			}
		}
	
		for (Table table : tables) {
			if (!table.isOccupied()) {
				if (!waitingCustomers.isEmpty()) {
					synchronized(waitersAvailable) {
						for (MyWaiter w: waitersAvailable) {
							if (w.getBusyLevel() == leastBusy) {
								print(w.getWaiter().getName() + ", please seat " + waitingCustomers.get(0).getCustomer() + 
										" at table " + table);
								seatCustomer(waitingCustomers.get(0), table, w.getWaiter()); //the action
								w.setMoreBusy();
								return true;
							}
						}
					}
				}
			}
		}	
			
		synchronized(waitingCustomers) {
			for (MyCustomer cust : waitingCustomers) {
				if (cust.state == customerState.waiting) {
					informCustomerWeAreFull(cust.getCustomer());
					return true;
				}
			}
		}

		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}


	// Actions ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void allowWaiterOnBreak(MyWaiter w) {
		w.getWaiter().msgYouCanGoOnBreak();
	}
	
	private void seatCustomer(MyCustomer cust, Table table, Waiter waiter) {
		waiter.msgBringCustomerToTable(cust.getCustomer(), table.getTableNumber());
		table.setOccupant(cust.getCustomer());
		waitingCustomers.remove(cust);
	}

	private void informCustomerWeAreFull(Customer cust) {
		Do("Sorry, we are full right now.");
		cust.msgRestaurantFull();
		msgWeAreFull(cust);
	}

	// utilities //////////////////////////////////////////////////////////////////////
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setGui(HostGui g) {
		this.g = g;
	}
	
	public List getWaitingCustomers() {
		return waitingCustomers;
	}

	public List getWaitersAvailable() {
		return waitersAvailable;
	}
	
	public Collection getTables() {
		return tables;
	}

	// utility classes /////////////////////////////////////////////////////////////////////////////////
	private class MyCustomer {
		
		// variables
		Customer customer;
		customerState state;
		
		// constructors
		MyCustomer(Customer customer) {
			this.customer = customer;
			state = customerState.waiting;
		}
		
		// utilities
		Customer getCustomer() {
			return customer;
		}
		
		customerState getState() {
			return state;
		}
		
		void setState(customerState state) {
			this.state = state;
		}
		
	}
	
	
	private class MyWaiter {
		
		// variables
		Waiter waiter;
		int busyLevel;
		boolean onBreak = false;
		
		// constructors ////////////////////////////////////////////////////////////
		
		MyWaiter(Waiter waiter) {
			this.waiter = waiter;
			busyLevel = 1;
		}
		
		// utilities //////////////////////////////////////////////////////////////
		Waiter getWaiter() {
			return waiter;
		}
		
		void setWaiter(Waiter waiter) {
			this.waiter = waiter;
		}
		
		int getBusyLevel() {
			return busyLevel;
		}
		
		void setBusyLevel(int busyLevel) {
			this.busyLevel = busyLevel;
		}
		
		void setMoreBusy() {
			busyLevel++;
		}
		
		void setLessBusy() {
			busyLevel--;
		}
		
		boolean isOnBreak() {
			return onBreak;
		}
		
		void setBreak(boolean onBreak) {
			this.onBreak = onBreak;
		}
	}
}