package simcity.buildings.restaurant.two;


import simcity.Role;
import simcity.gui.restauranttwo.RestaurantTwoHostGui;
import simcity.interfaces.restaurant.two.RestaurantTwoCustomer;
import simcity.interfaces.restaurant.two.RestaurantTwoHost;
import simcity.interfaces.restaurant.two.RestaurantTwoWaiter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class RestaurantTwoHostRole extends Role {//implements simcity.interfaces.restaurant.one.RestaurantOneCustomer {
	static final int NTABLES = 3;//a global for the number of tables.
	//Notice that we implement waitingCustomers using ArrayList, but type it
	//with List semantics.
	public int waiternum;
	public Map<Integer,Boolean> waitingSpots= new HashMap<Integer, Boolean>();
	public Map<Integer,Boolean> waiterSpots= new HashMap<Integer, Boolean>();
	
	public List<RestaurantTwoCustomerRole> waitingCustomers
	= Collections.synchronizedList(new ArrayList<RestaurantTwoCustomerRole>());
	public List<RestaurantTwoWaiter> waiters
	= Collections.synchronizedList(new ArrayList<RestaurantTwoWaiter>());
	public Collection<Table> tables;
	//note that tables is typed with Collection semantics.
	//Later we will see how it is implemented
    public int currentwaiter=0;
	private String name;

	public RestaurantTwoHostGui hostGui = null;

	public RestaurantTwoHostRole(String name) {
		super();
		waitingSpots.put(0,false);
		waitingSpots.put(1,false);
		waitingSpots.put(2,false);
		waitingSpots.put(3,false);
		waiterSpots.put(0,false);
		waiterSpots.put(1,false);
		waiterSpots.put(2,false);
		waiterSpots.put(3,false);
		waiterSpots.put(4,false);
		waiterSpots.put(5,false);
		this.name = name;
		// make some tables
		tables =  Collections.synchronizedList(new ArrayList<Table>(NTABLES));
		for (int ix = 1; ix <= NTABLES; ix++) {
			tables.add(new Table(ix));//how you add to a collections
		}
	}

	public String getMaitreDName() {
		return name;
	}

	public String getName() {
		return name;
	}

	public List getWaitingCustomers() {
		return waitingCustomers;
	}

	public Collection getTables() {
		return tables;
		
	}
	// Messages
	public void msgAskToBreak(RestaurantTwoWaiter w){
		if(waiters.size()>1){
			w.msgCanBreak(true);
			waiters.remove(w);
		}
		else
			w.msgCanBreak(false);
	}
	public void msgRestate(RestaurantTwoWaiter w){
		waiters.add(w);
	}
	public void msgIWantFood(RestaurantTwoCustomerRole cust) {
		waitingCustomers.add(cust);
		stateChanged();
		Do(cust.getName());
	
	}

	public void msgLeavingTable(RestaurantTwoCustomerRole cust) {
		synchronized(tables){
		for(Table t:tables){
			if(t.getOccupant()==cust){
				Do(cust + " leaving " + t);
				t.setUnoccupied();
				stateChanged();
			}
		}
		}
	
	}
//msg break
	//if only waiter null
	

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		/* Think of this next rule as:
            Does there exist a table and customer,
            so that table is unoccupied and customer is waiting.
            If so seat him at the table.
		 */
		synchronized(tables){
		for (Table table : tables) {
			if (!table.isOccupied()) {
				if (!waitingCustomers.isEmpty()) {
					if(!waiters.isEmpty()){			
					assignWaiter(waitingCustomers.get(0),  table);//the action
					
					
					return true;//return true to the abstract agent to reinvoke the scheduler.
				}
					else
						return true;
			}
		}
		}
		}
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	// Actions

	private void assignWaiter(RestaurantTwoCustomerRole customer,  Table t) {
		//msgsitattable to waiter include tablenum 
		Do("assign "+currentwaiter);
		
	
		waiters.get(currentwaiter).msgSeatCustomer(customer, t.tableNumber);
		currentwaiter++;
		Do("assign2 "+currentwaiter+" "+waiters.size());
		if(currentwaiter>=waiters.size()){
			currentwaiter=0;
		}
		Do("assign3 hey "+currentwaiter);
		t.setOccupant(customer);
		waitingCustomers.remove(customer);
	}

	
//wanttobreak
	//mesg
	//utilities

	public void setGui(RestaurantTwoHostGui gui) {
		hostGui = gui;
	}

	public RestaurantTwoHostGui getGui() {
		return hostGui;
	}
	
	public void addWaiter(RestaurantTwoWaiter we){
		
		waiters.add(we);
		Do("added "+waiters.size());
	}
	private class Table {
		RestaurantTwoCustomerRole occupiedBy;
		int tableNumber;

		Table(int tableNumber) {
			this.tableNumber = tableNumber;
		}

		void setOccupant(RestaurantTwoCustomerRole cust) {
			occupiedBy = cust;
		}

		void setUnoccupied() {
			occupiedBy = null;
		}

		RestaurantTwoCustomerRole getOccupant() {
			return occupiedBy;
		}

		boolean isOccupied() {
			return occupiedBy != null;
		}

		public String toString() {
			return "table " + tableNumber;
		}
	}



	@Override
	public void msgExitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgEnterBuilding() {
		// TODO Auto-generated method stub
		
	}


}
