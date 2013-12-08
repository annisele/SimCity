package simcity.buildings.restaurant.one;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.restaurantone.RestaurantOneHostGui;
import simcity.interfaces.restaurant.one.RestaurantOneCustomer;
import simcity.interfaces.restaurant.one.RestaurantOneHost;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Semaphore;

public class RestaurantOneHostRole extends Role implements simcity.interfaces.restaurant.one.RestaurantOneCustomer {
	static final int NTABLES = 3;//a global for the number of tables.
    //Notice that we implement waitingCustomers using ArrayList, but type it
    //with List semantics.
    public List<RestaurantOneCustomerRole> waitingCustomers = Collections.synchronizedList(new ArrayList<RestaurantOneCustomerRole>());
    public Collection<Table> tables;
    //note that tables is typed with Collection semantics.
    //Later we will see how it is implemented

    private String name;
    private boolean alreadySeated = false;

    public RestaurantOneHostGui hostGui = new RestaurantOneHostGui(this);

    private Semaphore seatCustomer = new Semaphore(0, true);
    
    private PersonAgent person;

    private class MyWaiter {
            public MyWaiter(RestaurantOneWaiterRole w, int nTables) {
                    waiter = w;
                    numTables = nTables; 
                    onBreak = false;
            }
            RestaurantOneWaiterRole waiter;
            int numTables;
            boolean onBreak;
            public boolean breakApproved = false;
            public boolean breakDenied = false;

    }

    private List<MyWaiter> waiters = new ArrayList<MyWaiter>();

    public RestaurantOneHostRole(PersonAgent p) {
    		
            super();
            this.person = p;
            this.name = p.getName();
            tables = new ArrayList<Table>(NTABLES);
            for (int ix = 1; ix <= NTABLES; ix++) {
                    tables.add(new Table(ix));//how you add to a collections
            }
    }

    public RestaurantOneHostRole(PersonAgent person,
			RestaurantOneSystem restaurantOneSystem) {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
            return name;
    }

    public List<RestaurantOneCustomerRole> getWaitingCustomers() {
            return waitingCustomers;
    }

    public Collection getTables() {
            return tables;
    }

    // Messages

    public void msgIWantFood(RestaurantOneCustomerRole cust) {
            waitingCustomers.add(cust);
            stateChanged();
    }

    public void msgNewWaiter(RestaurantOneWaiterRole w) {
            waiters.add((new MyWaiter(w, 0)));
            stateChanged();
    }
    public void msgTableIsFree(int table) {
           // print ("Received msgTableIsFree");
            synchronized(tables){
            for (Table tbl : tables) {
                    if (tbl.tableNumber == table) {
                            tbl.setUnoccupied();
                            stateChanged();
                    }
            }
            }
    }

    public void msgIWantABreak(RestaurantOneWaiterRole w)
    {
            if (waiters.size() > 1) {
                    synchronized(waiters) {
                    for (MyWaiter mw : waiters) {
                            if (mw.waiter.equals(w)){
                                    //mw.onBreak = true;
                                    //mw.waiter.msgBreakApproved();
                                    mw.breakApproved  = true;
                                    stateChanged();
                                    break;
                            }
                    }
                    }
            }
            else {
                    synchronized(waiters) {
                    for (MyWaiter mw : waiters) {
                            if (mw.waiter.equals(w)){
                                    mw.breakDenied = true;
                            }
                    }
            }
            }
    }
    
    public void msgImOffBreak(RestaurantOneWaiterRole w) {
            synchronized(waiters) {
            for (MyWaiter mw : waiters) {
                    if (mw.waiter.equals(w)){
                            mw.breakApproved = false;
                            mw.breakDenied = false;
                            mw.onBreak = false;
                    }
            }
            }
            
    }

    // removes customer when customer chooses to leave early
    public void msgLeaving(RestaurantOneCustomerRole c) {
            if (!alreadySeated) {
                    waitingCustomers.remove(c);
                    stateChanged();
            }
    }

    //message from Gui once customer has been seated
    public void msgCustomerSeated() {
            seatCustomer.release();
    }

    /**
     * Scheduler.  Determine what action is called for, and do it.
     */
    public boolean pickAndExecuteAnAction() {
            alreadySeated = false;

            if (!waiters.isEmpty())
            {
                    synchronized(waiters) {
                    for (MyWaiter w : waiters) {
                            if (!w.onBreak) {
                                    if (w.breakApproved) {
                                            w.waiter.msgBreakApproved();
                                            w.onBreak = true;
                                            System.out.println("ON BREAK ON BREAK");
                                    }
                                    else if (w.breakDenied) {
                                            w.waiter.msgNoBreak();
                                    }
                            }
                    }
                    }
                    synchronized(tables) {
                    for (Table table : tables) {
                            if (!table.isOccupied()) {
                                    if (waitingCustomers.size() > 0) {
                                            int i = 0;
                                            synchronized(waiters){
                                            for (MyWaiter m : waiters) {
                                                    if (m.onBreak)
                                                            i++;
                                            }
                                            }
                                            int minTables = waiters.get(i).numTables;
                                            int WaiterWithMinTables = i;
                                            int j = 0;
                                            synchronized(waiters){
                                            for (MyWaiter mw : waiters) {
                                                    if (!mw.onBreak)
                                                    {
                                                            if (mw.numTables < minTables) {
                                                                    minTables = mw.numTables;
                                                                    WaiterWithMinTables = j;
                                                            }
                                                    }
                                                    j++;
                                            }
                                            }
                                            waiters.get(WaiterWithMinTables).numTables++;
                                            if (waitingCustomers.size() > 0) {
                                                    alreadySeated = true;
                                                    if (waitingCustomers.contains(waitingCustomers.get(0))) {
                                                            tellWaiterToSeatCustomer(waitingCustomers.get(0), table, waiters.get(WaiterWithMinTables).waiter);
                                                    }
                                                    try {
                                                            seatCustomer.acquire();
                                                    } catch (InterruptedException e) {
                                                            // TODO Auto-generated catch block
                                                            e.printStackTrace();
                                                    }
                                                    return true;
                                            }
                                    }
                            }
                    }
                    }
            }

            return false;
    }

    // Actions

    private void tellWaiterToSeatCustomer(RestaurantOneCustomerRole customer, Table table, RestaurantOneWaiterRole waiter) {
            waiter.msgSitAtTable(customer, table.tableNumber);
            table.setOccupant(customer);
            waitingCustomers.remove(customer);
    }

    //utilities

    public void setGui(RestaurantOneHostGui gui) {
            hostGui = gui;
    }

    public RestaurantOneHostGui getGui() {
            return hostGui;
    }


    private class Table {
            RestaurantOneCustomerRole occupiedBy;
            int tableNumber;

            Table(int tableNumber) {
                    this.tableNumber = tableNumber;
            }

            void setOccupant(RestaurantOneCustomerRole cust) {
                    occupiedBy = cust;
            }

            void setUnoccupied() {
                    occupiedBy = null;
            }

            RestaurantOneCustomerRole getOccupant() {
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgWhatWouldYouLike() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgFollowMe(RestaurantOneMenu restaurantOneMenu) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWaiter(RestaurantOneWaiterRole restaurantOneWaiterRole) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgFoodOutofStock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgHereIsYourFood() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgHereisYourCheck(RestaurantOneCheck c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgArrivedAtRestaurant(double money) {
		// TODO Auto-generated method stub
		
	}


}
