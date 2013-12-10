package simcity.buildings.restaurant.six;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.restaurantsix.RestaurantSixHostGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.six.RestaurantSixCustomer;
import simcity.interfaces.restaurant.six.RestaurantSixHost;
import simcity.interfaces.restaurant.six.RestaurantSixWaiter;

public class RestaurantSixHostRole extends Role implements RestaurantSixHost {

	private RestaurantSixSystem restaurant;
	static final int NTABLES = 4;//a global for the number of tables.
	public List<RestaurantSixCustomer> waitingCustomers = Collections.synchronizedList(new ArrayList<RestaurantSixCustomer>());
	public List<RestaurantSixWaiter> waiters = Collections.synchronizedList(new ArrayList<RestaurantSixWaiter>());
	private List<WaitingArea> waitingAreas = Collections.synchronizedList(new ArrayList<WaitingArea>());
	private Queue<WaitingArea> waitQueue = new LinkedList<WaitingArea>();
	public Collection<Table> tables;

	private int currentWaiter = 0;
	private Semaphore atTable = new Semaphore(0,true);
	private Semaphore atLobby = new Semaphore(1,true);
	private boolean atLobbyNow = true;
	private boolean allTablesFull = false;
	
	public RestaurantSixHostRole(PersonAgent p) {
		person = p;
		this.gui = new RestaurantSixHostGui(this);
		
		tables = Collections.synchronizedList( new ArrayList<Table>(NTABLES));
		// Here we create some waiting areas
		for (int i = 0; i < 10; i++) {
			WaitingArea wait = new WaitingArea(i, 20, 60+i*30);
			waitingAreas.add(wait);
		}
		
		// Here we choose the locations for the tables
		synchronized(tables){
			for (int ix = 1; ix <= NTABLES; ix++) {
				int tableX = 0; int tableY = 0;
				if (ix < 5) {
					tableX = (100*ix);
					tableY = 260;
				}
				else if (ix == 5){
					tableX = 100;
					tableY = 120;
				}
				else if (ix == 6){
					tableX = 150;
					tableY = 120;
				}
				else if (ix == 7){
					tableX = 300;
					tableY = 120;
				}
				else if (ix == 8){
					tableX = 350;
					tableY = 120;
				}
				tables.add(new Table(ix, tableX, tableY));//how you add to a collection
			}
		}
	}
	
	// Messages
	@Override
	public void msgIWantFood(RestaurantSixCustomer cust) {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), 
				"RestaurantSixHost: " + person.getName(), "A customer wants to be seated.");
		waitingCustomers.add(cust);
		stateChanged();
	}
	
	public void msgIWantToBreak(RestaurantSixWaiter waiter) {
		if (waiters.size() > 1) {
			Do("You can go on break!");
			waiter.msgYouCanBreak(true);
			waiters.remove(waiter);
		}
		else {
			Do("You can't go on break!");
			waiter.msgYouCanBreak(false);
		}
	}
	
	public void msgImLeaving(RestaurantSixCustomer cust) {
		synchronized(waitingAreas) {
			for (WaitingArea w : waitingAreas) {
				if (w.cust == cust) {
					w.cust = null;
					w.empty = true;
				}
			}
		}
		synchronized(waitQueue) {
			for (WaitingArea w : waitQueue) {
				if (w.cust == null) {
					waitQueue.remove(w);
				}
				if (w.cust == cust) {
					w.cust = null;
					w.empty = true;
					waitQueue.remove(w);
				}
			}
		}
		stateChanged();
	}
	
	public void msgTableEmpty(int tableNumber) {
		synchronized(tables) {
			for (Table table : tables) {
				if (table.tableNumber == tableNumber) {
					table.setUnoccupied();
					AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantSixHost: " + person.getName(), "Table "+tableNumber+" is now empty.");
					stateChanged();
				}
			}
		}
		allTablesFull = false;
	}
	
	// Scheduler
	@Override
	public boolean pickAndExecuteAnAction() {
		/* Think of this next rule as:
        Does there exist a table and customer,
        so that table is unoccupied and customer is waiting.
        If so seat him at the table.
		 */
		/*
		if (waiters.size() != 0) {
			if (!waitingCustomers.isEmpty() || !waitQueue.isEmpty()) {
				synchronized(tables){
					for (Table table : tables) {
						if (!table.isOccupied()) {
							synchronized(waitQueue){
								if (waitQueue.peek() != null) {
									sitAtTable(waitQueue.remove().cust, table);
									Do("Just sat someone.  There are currently "+(waitingCustomers.size()+waitQueue.size())+" waiting.");
									return true;
								}
							}
							synchronized(waitingCustomers){
								for (CustomerAgent c : waitingCustomers) {
									sitAtTable(c, table);
									Do("Just sat someone.  There are currently "+(waitingCustomers.size()+waitQueue.size())+" waiting.");
									return true;
								}
							}
							//the action
							return true;//return true to the abstract agent to reinvoke the scheduler.
							//}
						}
					}
				}
				allTablesFull = true;
				Do("There are "+(waitingCustomers.size()+waitQueue.size())+" waiting");
				if (waitingCustomers.size() > 0) {
					notifyNoTables(waitingCustomers.get(0));
				}
				//return true;
			}
		}
		 */
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
		
	}

	// Actions
	/*
	private void sitAtTable(CustomerAgent customer, Table table) {
		currentWaiter++;
		currentWaiter %= waiters.size();
		
		synchronized(waitingAreas){
			for (WaitingArea w : waitingAreas) {
				if (w.cust == customer) {
					w.cust = null;
					w.empty = true;
					//waitQueue.remove(w);
					waiters.get(currentWaiter).msgSitAtTable(customer, table.tableNumber);
					table.setOccupant(customer);
					return;
				}
			}
		}
		waiters.get(currentWaiter).msgSitAtTable(customer, table.tableNumber);
		table.setOccupant(customer);
		waitingCustomers.remove(customer);
	}
	
	private void notifyNoTables(CustomerAgent customer) {
		Do("Sorry, " + customer.getCustomerName() + ", there are no open tables currently");
		synchronized(waitingAreas){
			for (WaitingArea w : waitingAreas) {
				if (w.empty == true) {
					w.empty = false;
					w.cust = customer;
					customer.msgNoTables(w.x, w.y);
					waitQueue.add(w);
					waitingCustomers.remove(customer);
					return;
				}
				
			}
		}
		//int x = 20+(waitQueue.size() * 25);
		//int y = -20-(waitQueue.size() * 25);
		//customer.msgNoTables(x, y);
		//waitQueue.add(w);
		//waitQueue.add(customer);
		waitingCustomers.remove(customer);
	}*/
	
	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterBuilding(SimSystem s) {
		restaurant = (RestaurantSixSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), 
				"RestaurantSixHost: " + person.getName(), "Ready to work at the restaurant!");
		
		((RestaurantSixHostGui) gui).DoGoToStand();
		
	}
	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
	}

	
	// Utility Functions
	public void assignWaiter(RestaurantSixWaiter waiter) {
		waiters.add(waiter);
		stateChanged();
	}
	
	public int[] getTablesXPos() {
		synchronized(tables){
			int[] tablesX = new int[NTABLES];
			Iterator<Table> it = this.tables.iterator();
			int i = 0;
			
	        while ( it.hasNext()) {
	        	Table table = it.next();
	        	tablesX[i] = (table.getXPos());
	        	i++;
	        }
	        return tablesX;
		}
	}
	
	public int[] getTablesYPos() {
		synchronized(tables){
			int[] tablesY = new int[NTABLES];
			Iterator<Table> it = this.tables.iterator();
			int i = 0;
			
	        while ( it.hasNext()) {
	        	Table table = it.next();
	        	tablesY[i] = (table.getYPos());
	        	i++;
	        }
	        return tablesY;
		}
	}
	
	// Utility classes
	final class WaitingArea {
		int num;
		int x;
		int y;
		boolean empty;
		RestaurantSixCustomer cust;
		WaitingArea(int number, int xcoord, int ycoord) {
			num = number;
			x = xcoord;
			y = ycoord;
			empty = true;
		}
	}
	
	private class Table {
		RestaurantSixCustomer occupiedBy;
		int tableNumber;
		int xpos;
		int ypos;

		Table(int tableNumber, int xpos, int ypos) {
			this.tableNumber = tableNumber;
			this.xpos = xpos;
			this.ypos = ypos;
		}

		void setOccupant(RestaurantSixCustomer cust) {
			occupiedBy = cust;
		}

		void setUnoccupied() {
			occupiedBy = null;
		}

		RestaurantSixCustomer getOccupant() {
			return occupiedBy;
		}

		boolean isOccupied() {
			return occupiedBy != null;
		}

		public String toString() {
			return "table " + tableNumber;
		}
		
		public int getXPos() {
			return this.xpos;
		}
		
		public int getYPos() {
			return this.ypos;
		}
	}

}
