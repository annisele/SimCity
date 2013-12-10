package simcity.buildings.restaurant.three;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.restaurantthree.RestaurantThreeHostGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.three.RestaurantThreeCashier;
import simcity.interfaces.restaurant.three.RestaurantThreeCustomer;
import simcity.interfaces.restaurant.three.RestaurantThreeHost;
import simcity.interfaces.restaurant.three.RestaurantThreeWaiter;
import simcity.test.mock.EventLog;

/**
 * Restaurant Three Host Role
 * @author Levonne Key
 *
 */
public class RestaurantThreeHostRole extends Role implements RestaurantThreeHost {
	private Timer timer = new Timer();
	private int numTable = 3;
	private int waiterIndex = 0;
	int tablesOccupiedCounter;
	int waiterAvailable = 0;
	private Table tables[];
	public enum HostState {informed, uninformed};
	public  EventLog log = new EventLog();
	private enum WaiterState {onBreak, work, requestBreak};
	private List<MyWaiter> waiters = Collections.synchronizedList(new ArrayList<MyWaiter>());
	private Semaphore atDest = new Semaphore(0, true);
	//private static Collection<Table> tables;
	private List<RestaurantThreeCustomer> waitingCustomers = Collections.synchronizedList(new ArrayList<RestaurantThreeCustomer>());
	private RestaurantThreeSystem system;

	private class Table {
		RestaurantThreeCustomer occupiedBy;
		int tableNumber;
		boolean occupied;
		
		Table(int tableNum) {
			this.tableNumber = tableNum;
			this.occupied = false;
		}

		void setOccupant(RestaurantThreeCustomer cust) {
			occupiedBy = cust;
		}
		
		void setUnoccupied() {
			occupiedBy = null;
		}

		boolean isOccupied() {
			return occupiedBy != null;
		}

		public String toString() {
			return "table " + tableNumber;
		}

	}
	private class MyWaiter {
		RestaurantThreeWaiter wtr;
		WaiterState waiterState;
		
		MyWaiter(RestaurantThreeWaiter w) {
			wtr = w;
			waiterState = WaiterState.work;
		}
	}

	public RestaurantThreeHostRole(PersonAgent p) {
		person = p;
		this.gui = new RestaurantThreeHostGui(this);
		/*
		tables = Collections.synchronizedList(new ArrayList<Table>(numTable));
		synchronized(tables) {
			for (int i = 1; i <= numTable; i++) {
				tables.add(new Table(i));
			}
		}
		*/
		tables = new Table[numTable];

		for(int i=0; i < numTable; i++){
		    tables[i] = new Table(i);
		}
	}
	
	
	public void atDestination() {
		atDest.release();
	}
	//messages
	
	public void msgAddWaiter(RestaurantThreeWaiter waiter) {
		waiters.add(new MyWaiter(waiter));
		stateChanged();
	}
	public void msgIWantFood(RestaurantThreeCustomer cust) {
		//if there is free table
		for (Table table : tables) {
			if (!table.isOccupied()) {
				waitingCustomers.add(cust);
				AlertLog.getInstance().logMessage(AlertTag.valueOf(system.getName()), "Restaurant 3 Host: " + person.getName(), "Assigning customer to waiter.");
				stateChanged();
				return;
			}
		}
		//no free tables
		AlertLog.getInstance().logMessage(AlertTag.valueOf(system.getName()), "Restaurant 3 Host: " + person.getName(), "Alerting customer that restaurant is full.");
		cust.msgRestaurantFull();
	}
	public void msgTableIsFree(int tableNum) {
		tablesOccupiedCounter--;
		
		for (Table table : tables) {
				table.setUnoccupied();
				stateChanged();
				
		}
		tables[tableNum].occupied = false;
		stateChanged();
	}

	@Override
	public boolean pickAndExecuteAnAction() {

		if(waiters.size() > 0) {
			synchronized(tables) {
				for (Table table : tables) {
					if (!table.isOccupied()) {
						if (!waitingCustomers.isEmpty()) {
							if(waiterIndex + 1 >= waiters.size()) {
								waiterIndex = 0;
							}
							else {
								waiterIndex++;
							}
							//find waiter that's not on break
							while(waiters.get(waiterIndex % waiters.size()).waiterState != WaiterState.work) {
								waiterIndex++;
							}
							RestaurantThreeWaiter w = waiters.get(waiterIndex % waiters.size()).wtr;
							table.setOccupant(waitingCustomers.get(0));
							w.msgPleaseSeatCustomer(waitingCustomers.get(0), table.tableNumber);
							//index increments through waiters list, then wraps to front
							getWaiterSeatCustomer(waiters.get(waiterIndex), waitingCustomers.get(0), table);
							//waitingCustomers.remove(waitingCustomers.get(0));
							return true;//return true to the abstract agent to reinvoke the scheduler.
						}

					}
				}
			}

		}


		return false;
	}

	//actions
	private void getWaiterSeatCustomer(MyWaiter waiter, RestaurantThreeCustomer customer, Table table) {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(system.getName()), "Restaurant 3 Host: " + person.getName(), "Assigning "+waiterIndex+".");
		waiter.wtr.msgPleaseSeatCustomer(customer, table.tableNumber);
		waiterIndex++;
		if(waiterIndex >= waiters.size()) {
			waiterIndex = 0;
		}
		table.setOccupant(customer);
		waitingCustomers.remove(waitingCustomers.get(0));
	}

	
	@Override
	public void exitBuilding() {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(system.getName()), "Restaurant 3 Host: " + person.getName(), "Leaving restaurant three");	
		gui.DoExitBuilding();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
		}
		AlertLog.getInstance().logMessage(AlertTag.valueOf(system.getName()), "Restaurant 3 Host: " + person.getName(), "Leaving the restaurant three.");
		
		system.exitBuilding(this);
		person.roleFinished();	
	}
	public List getWaitersList() {
		return waiters;
	}
	public List getCustomersList() {
		return waitingCustomers;
	}

	@Override
	public void enterBuilding(SimSystem s) {
		system = (RestaurantThreeSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(system.getName()), "Restaurant 3 Host: " + person.getName(), "Ready to work at the restaurant!");
		
		((RestaurantThreeHostGui)gui).DoGoToStand();
		

	}
	@Override
	public int getWaitingCustomers() {
		return waitingCustomers.size();
	}
	public String getName() {
		return person.getName();
	}








}
