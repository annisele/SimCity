package simcity.buildings.restaurant.three;

import java.util.ArrayList;
import java.util.Collection;
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
 * Restaurant Three Waiter Role
 * @author Levonne Key
 *
 */
public class RestaurantThreeHostRole extends Role implements RestaurantThreeHost {
	private Timer timer = new Timer();
	private int numTable = 3;
	int tablesOccupiedCounter;
	int waiterAvailable = 0;
	private int nextWaiter = 0;
	public enum HostState {informed, uninformed};
	public  EventLog log = new EventLog();
	private enum WaiterState {onBreak, work, requestBreak};
	private List<MyWaiter> waiters = Collections.synchronizedList(new ArrayList<MyWaiter>());
	private String name;
	private RestaurantThreeCashier ca;
	private Semaphore atDest = new Semaphore(0, true);
	private static Collection<Table> tables;
	private List<RestaurantThreeCustomer> waitingCustomers = Collections.synchronizedList(new ArrayList<RestaurantThreeCustomer>());
	private RestaurantThreeSystem system;

	private class Table {
		RestaurantThreeCustomer occupiedBy;
		int tableNumber;
		boolean occupied;
		
		Table(int tableNumber) {
			this.tableNumber = tableNumber;
			this.occupied = false;
		}

		void setOccupant(RestaurantThreeCustomer cust) {
			occupiedBy = cust;
		}
		RestaurantThreeCustomer getOccupant() {
			return occupiedBy;
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
		RestaurantThreeWaiter waiter;
		WaiterState waiterState;
		
		MyWaiter(RestaurantThreeWaiter w) {
			waiter = w;
			waiterState = WaiterState.work;
		}
	}

	public RestaurantThreeHostRole(PersonAgent p) {
		this.person = p;
		this.gui = new RestaurantThreeHostGui(this);
		tables = Collections.synchronizedList(new ArrayList<Table>(numTable));
		synchronized(tables) {
			for (int i = 1; i <= numTable; i++) {
				tables.add(new Table(i));
			}
		}
	}
	
	
	public void atDestination() {
		atDest.release();
	}
	public void msgAddWaiter(RestaurantThreeWaiter waiter) {
		waiters.add(new MyWaiter(waiter));
		stateChanged();
	}
	public void msgIWantFood(RestaurantThreeCustomer cust) {
		//if there is free table
		for (Table table : tables) {
			if (!table.isOccupied()) {
				waitingCustomers.add(cust);
				AlertLog.getInstance().logMessage(AlertTag.valueOf(system.getName()), "RestaurantThreeHost: " + person.getName(), "Assigning customer to waiter.");
				stateChanged();
				return;
			}
		}
		//no free tables
		AlertLog.getInstance().logMessage(AlertTag.valueOf(system.getName()), "RestaurantThreeHost: " + person.getName(), "Alerting customer that restaurant is full.");
		cust.msgRestaurantFull();
	}


	@Override
	public boolean pickAndExecuteAnAction() {
		synchronized(waitingCustomers)
    	{
    		synchronized(waiters)
    		{
		
		for (Table table : tables) {
			if (!table.isOccupied()) {
				if (!waitingCustomers.isEmpty() && !waiters.isEmpty()) {
					
					getWaiterSeatCustomer(waiters.get(nextWaiter), waitingCustomers.get(0), table);//the action
					return true;//return true to the abstract agent to reinvoke the scheduler.
				}
			}	
		}
		for(RestaurantThreeCustomer c: waitingCustomers) {
		 
			
			c.msgRestaurantFull();
			
			return true;
		
		}
    		}
    	}
		return false;
	}


	private void getWaiterSeatCustomer(MyWaiter myWaiter,
			RestaurantThreeCustomer restaurantThreeCustomer, Table table) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void exitBuilding() {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(system.getName()), "Restaurant Three Host: " + person.getName(), "Leaving restaurant three");	
		gui.DoExitBuilding();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		system.exitBuilding(this);
		person.roleFinished();	
	}


	@Override
	public void enterBuilding(SimSystem s) {
		system = (RestaurantThreeSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(system.getName()), "RestaurantThreeHost: " + person.getName(), "Ready to work at the restaurant!");
		
		((RestaurantThreeHostGui) gui).DoGoToStand();
		
	}


	@Override
	public PersonAgent getPerson() {
		// TODO Auto-generated method stub
		return person;
	}


	@Override
	public void setPerson(PersonAgent person) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public RestaurantThreeSystem getSystem() {
		// TODO Auto-generated method stub
		return system;
	}


	@Override
	public void setSystem(RestaurantThreeSystem resSystem) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List getWaitersList() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void msgGotToWork() {
		// TODO Auto-generated method stub
		
	}

	public List getWaitingCustomer() {
		return waitingCustomers;
	}
	@Override
	public int numWaitingCustomers() {
		// TODO Auto-generated method stub
		return 0;
	}
	public String getName() {
		return person.getName();
	}
}
