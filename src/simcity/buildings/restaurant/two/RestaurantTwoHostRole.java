package simcity.buildings.restaurant.two;


import simcity.Clock;
import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.PersonAgent.EventType;
import simcity.buildings.bank.BankSystem;
import simcity.buildings.market.MarketCashierRole.MarketState;
import simcity.gui.bank.BankHostGui;
import simcity.gui.restauranttwo.RestaurantTwoHostGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.market.MarketWorker;
import simcity.interfaces.restaurant.two.RestaurantTwoCustomer;
import simcity.interfaces.restaurant.two.RestaurantTwoHost;
import simcity.interfaces.restaurant.two.RestaurantTwoWaiter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

public class RestaurantTwoHostRole extends Role implements simcity.interfaces.restaurant.two.RestaurantTwoHost{//implements simcity.interfaces.restaurant.one.RestaurantOneCustomer {
	static final int NTABLES = 3;//a global for the number of tables.
	//Notice that we implement waitingCustomers using ArrayList, but type it
	//with List semantics.
	public int waiternum;
	private Semaphore atDest = new Semaphore(0, true);
	Timer timer = new Timer();
	public List<RestaurantTwoCustomer> waitingCustomers
	= Collections.synchronizedList(new ArrayList<RestaurantTwoCustomer>());
	public List<RestaurantTwoWaiter> waiters
	= Collections.synchronizedList(new ArrayList<RestaurantTwoWaiter>());
	public Collection<Table> tables;
	//note that tables is typed with Collection semantics.
	//Later we will see how it is implemented
    public int currentwaiter=0;
	private String name;
	private RestaurantTwoSystem R2;
	public enum R2State {running, closed, allWorkersGone};
	public R2State state = R2State.running;

	//public RestaurantTwoHostGui hostGui = null;

	public RestaurantTwoHostRole(PersonAgent person) {
		super();
		
		this.person = person;
		this.gui = new RestaurantTwoHostGui(this);
	
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
	public R2State getR2State(){
		return state;
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
	public void msgIWantFood(RestaurantTwoCustomer cust) {
		//Do("Recieved msg I want food");
		waitingCustomers.add(cust);
		stateChanged();
		//Do(cust.getName());
	
	}

	public void msgLeavingTable(RestaurantTwoCustomer cust) {
		synchronized(tables){
		for(Table t:tables){
			if(t.getOccupant()==cust){
				AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantHost: " + person.getName(),""+cust + " leaving " + t+".");
				t.setUnoccupied();
				stateChanged();
			}
		}
		}
	
	}
	public void msgLeaveWork(){
		state = R2State.allWorkersGone;
		person.scheduleEvent(EventType.Work);
		stateChanged();
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
		if(state == R2State.allWorkersGone) {
			exitBuilding();
		}
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	// Actions

	private void assignWaiter(RestaurantTwoCustomer customer,  Table t) {
		//msgsitattable to waiter include tablenum 
		AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantHost: " + person.getName(), "Assigning "+currentwaiter+".");
		
	
		waiters.get(currentwaiter).msgSeatCustomer(customer, t.tableNumber);
		currentwaiter++;
		//Do("assign2 "+currentwaiter+" "+waiters.size());
		if(currentwaiter>=waiters.size()){
			currentwaiter=0;
		}
		//Do("assign3 hey "+currentwaiter);
		t.setOccupant(customer);
		waitingCustomers.remove(customer);
	}

	
//wanttobreak
	//mesg
	//utilities

	
	
	public void addWaiter(RestaurantTwoWaiter we){
		
		waiters.add(we);
		AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantHost: " + person.getName(), "Added waiter ");
	}
	private class Table {
		RestaurantTwoCustomer occupiedBy;
		int tableNumber;

		Table(int tableNumber) {
			this.tableNumber = tableNumber;
		}

		void setOccupant(RestaurantTwoCustomer customer) {
			occupiedBy = customer;
		}

		void setUnoccupied() {
			occupiedBy = null;
		}

		RestaurantTwoCustomer getOccupant() {
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
	public void exitBuilding() {
		gui.DoExitBuilding();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantHost: " + person.getName(), "Leaving the market.");
		
		R2.exitBuilding(this);
		person.roleFinished();	
		
	}



	@Override
	public void enterBuilding(SimSystem s) {
		R2 = (RestaurantTwoSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(R2.getName()), "RestaurantHost: " + person.getName(),"Entering building.");
		state = R2State.running;
		timer.schedule(new TimerTask() {
			public void run() {
				
				state = R2State.closed;
				AlertLog.getInstance().logDebug(AlertTag.valueOf(R2.getName()), "RestaurantHost: " + person.getName(), 
						"setting to CLOSED. " + person.getCurrentEventDuration() +
						", " + person.getCurrentEvent().toString());
				R2.EveryoneLeave();
				for(RestaurantTwoWaiter w : waiters) {
					w.msgFinishWorking();
				}
			
			}
		}, Clock.tenMinutesInMillis(person.getCurrentEventDuration()));
	
		((RestaurantTwoHostGui)gui).DoGoToHostPosition();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}

	public void atDestination() {
		atDest.release();
	}

	


}
