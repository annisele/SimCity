package simcity.buildings.restaurant.five;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.restaurantfive.RestaurantFiveHostGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.five.RestaurantFiveCustomer;
import simcity.interfaces.restaurant.five.RestaurantFiveHost;
import simcity.interfaces.restaurant.five.RestaurantFiveWaiter;


public class RestaurantFiveHostRole extends Role implements RestaurantFiveHost {

	private int nTables = 1;
	private enum WaiterState { breaking, working, requestedBreak };
	private List<MyWaiter> waiters = Collections.synchronizedList(new ArrayList<MyWaiter>());
	private int waiterIndex = 0;
//	private Semaphore atTable = new Semaphore(0,true);
	private Semaphore atDest = new Semaphore(0, true);
	private static Collection<Table> tables;
	private List<RestaurantFiveCustomer> waitingCustomers = Collections.synchronizedList(new ArrayList<RestaurantFiveCustomer>());
	private RestaurantFiveSystem restaurant;

	private class Table {
		RestaurantFiveCustomer occupiedBy;
		int tableNumber;

		Table(int tableNumber) {
			this.tableNumber = tableNumber;
		}

		void setOccupant(RestaurantFiveCustomer cust) {
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
		RestaurantFiveWaiter w;
		WaiterState s;

		MyWaiter(RestaurantFiveWaiter wIn) {
			w = wIn;
			s = WaiterState.working;
		}

	}
	
	public RestaurantFiveHostRole(PersonAgent p) {
		person = p;
		this.gui = new RestaurantFiveHostGui(this);
		// make some tables
		tables = Collections.synchronizedList(new ArrayList<Table>(nTables));
		synchronized(tables) {
			for (int ix = 1; ix <= nTables; ix++) {
				tables.add(new Table(ix));//how you add to a collections
			}
		}
	}
	
	
	@Override
	public void atDestination() {
		atDest.release();
	}
	
	@Override
	public String getName() {
		return person.getName();
	}
	
	
	public void msgAddWaiter(RestaurantFiveWaiter w) {
		waiters.add(new MyWaiter(w));
		stateChanged();
	}

//	public void msgWantToGoOnBreak(RestaurantFiveWaiter wRequested) {
//		synchronized(waiters) {
//			for(MyWaiter mw : waiters) {
//				if(mw.w == wRequested) {
//					mw.s = WaiterState.requestedBreak;
//					stateChanged();
//					return;
//				}
//			}
//		}
//	}

	public void msgIWantFood(RestaurantFiveCustomer cust) {
		//checking for unoccupied tables
		for (Table table : tables) {
			if (!table.isOccupied()) {
				waitingCustomers.add(cust);
				AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantFiveHost: " + person.getName(), "Assigning customer to waiter.");
				stateChanged();
				return;
			}
		}
		//no free tables
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantFiveHost: " + person.getName(), "Alerting customer that restaurant is full.");
		cust.msgRestaurantFull();
	}

//	public void msgIWillWaitForTable(RestaurantFiveCustomerRole cust) {
//		waitingCustomers.add(cust);
//		Do("Assigning a waiter to customer.");
//		stateChanged();
//	}
//
//	public void msgTableIsFree(int t) {
//		for (Table table : tables) {
//			if (table.tableNumber == t) {
//				System.out.println("Table " + table + " is free.");
//				table.setUnoccupied();
//				stateChanged();
//			}
//		}
//	}
//
//	public void msgAddTable() {
//		nTables++;
//		int index = tables.size();
//		tables.add(new Table(index + 1));
//	//	((RestaurantFiveHostGui)gui).addTable();
//		stateChanged();
//	}
//
//	public void msgAtTable() {//from animation
//		atTable.release();// = true;
//		stateChanged();
//	}
//
//	public int getNTables() {
//		return nTables;
//	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	@Override
	public boolean pickAndExecuteAnAction() {
//		synchronized(waiters) {
//			for(MyWaiter w : waiters) {
//				if(w.s == WaiterState.requestedBreak) {
//					HandleWaiterBreak(w);
//					return true;
//				}
//			}
//		}

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
							while(waiters.get(waiterIndex % waiters.size()).s != WaiterState.working) {
								waiterIndex++;
							}
							RestaurantFiveWaiter w = waiters.get(waiterIndex % waiters.size()).w;
							table.setOccupant(waitingCustomers.get(0));
							w.msgPleaseSeatCustomer(waitingCustomers.get(0), table.tableNumber);
							//index increments through waiters list, then wraps to front

							waitingCustomers.remove(waitingCustomers.get(0));
							return true;//return true to the abstract agent to reinvoke the scheduler.
						}

					}
				}
			}

		}


		return false;
	}


//	private void HandleWaiterBreak(MyWaiter w) {
//		synchronized(waiters) {
//			for(MyWaiter w2 : waiters) {
//				//if there is a working waiter
//				if(w2.s == WaiterState.working) {
//					Do("Sending " + w.w.getName() + " on break.");
//					w.w.msgGoOnBreak();
//					w.s = WaiterState.breaking;
//					return;
//				}
//			}
//		}
//
//		w.w.msgCannotGoOnBreak();
//		w.s = WaiterState.working;
//		Do(w.w.getName() + " cannot go on break.");
//	}
//
//	
//	public void msgIWantFood(RestaurantFiveCustomer cust) {
//		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantHost: " + person.getName(), "A customer has arrived!");
//		//checking for unoccupied tables
//		for (Table table : tables) {
//			if (!table.isOccupied()) {
//				waitingCustomers.add(cust);
//				Do("Assigning a waiter to customer.");
//				stateChanged();
//				return;
//			}
//		}
//		//no free tables
//		Do("Alerting customer that restaurant is full.");
//		cust.msgRestaurantFull();
//	}
//
//
	
	public int getNumWaitingCustomers() {
		return waitingCustomers.size();
	}
	
	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void enterBuilding(SimSystem s) {
		restaurant = (RestaurantFiveSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantFiveHost: " + person.getName(), "Ready to work at the restaurant!");
		
		((RestaurantFiveHostGui) gui).DoGoToStand();
		
	}

}
