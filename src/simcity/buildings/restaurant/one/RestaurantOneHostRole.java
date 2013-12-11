package simcity.buildings.restaurant.one;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.buildings.restaurant.three.RestaurantThreeSystem;
import simcity.gui.restaurantone.RestaurantOneHostGui;
import simcity.gui.restaurantthree.RestaurantThreeHostGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.one.RestaurantOneCustomer;
import simcity.interfaces.restaurant.one.RestaurantOneHost;
import simcity.interfaces.restaurant.one.RestaurantOneWaiter;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Semaphore;

public class RestaurantOneHostRole extends Role implements simcity.interfaces.restaurant.one.RestaurantOneHost {
	static final int NTABLES = 3;//a global for the number of tables.
    //Notice that we implement waitingCustomers using ArrayList, but type it
    //with List semantics.
    public List<RestaurantOneCustomerRole> waitingCustomers = Collections.synchronizedList(new ArrayList<RestaurantOneCustomerRole>());
    public Collection<Table> tables;
    //note that tables is typed with Collection semantics.
    //Later we will see how it is implemented

    private String name;
    private boolean alreadySeated = false;
    public RestaurantOneSystem system;;

    private Semaphore atDest = new Semaphore(0, true);
    
    
    private class MyWaiter {
            public MyWaiter(RestaurantOneWaiter w, int nTables) {
                    waiter = (RestaurantOneWaiterRole) w;
                    numTables = nTables; 
                    onBreak = false;
            }
            RestaurantOneWaiter waiter;
            int numTables;
            boolean onBreak;
          
    }

    private List<MyWaiter> waiters = new ArrayList<MyWaiter>();

    public RestaurantOneHostRole(PersonAgent p, RestaurantOneSystem s) {
            this.person = p;
            system = s;
            this.name = p.getName();
            tables = new ArrayList<Table>(NTABLES);
            for (int ix = 1; ix <= NTABLES; ix++) {
                    tables.add(new Table(ix));//how you add to a collections
            }
            this.gui = new RestaurantOneHostGui(this);
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

    public void msgNewWaiter(RestaurantOneWaiter w) {
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

  /*  public void msgIWantABreak(RestaurantOneWaiterRole w)
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
    } */
    
 /*   public void msgImOffBreak(RestaurantOneWaiterRole w) {
            synchronized(waiters) {
            for (MyWaiter mw : waiters) {
                    if (mw.waiter.equals(w)){
                            mw.breakApproved = false;
                            mw.breakDenied = false;
                            mw.onBreak = false;
                    }
            }
            }
            
    } */

    // removes customer when customer chooses to leave early
   /* public void msgLeaving(RestaurantOneCustomerRole c) {
            if (!alreadySeated) {
                    waitingCustomers.remove(c);
                    stateChanged();
            }
    } */

    //message from Gui once customer has been seated
  /*  public void msgCustomerSeated() {
          //  seatCustomer.release();
    } */

    /**
     * Scheduler.  Determine what action is called for, and do it.
     */
    public boolean pickAndExecuteAnAction() {
            alreadySeated = false;

            if (!system.getWaiters().isEmpty())
            {
            	System.out.println("Waiters is not empty");
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
                                            synchronized(system.getWaiters()){
                                          
                                            }
                                            waiters.get(WaiterWithMinTables).numTables++;
                                            if (waitingCustomers.size() > 0) {
                                                    alreadySeated = true;
                                                    
                                                  /*  try {
                                                            atDest.acquire();
                                                    } catch (InterruptedException e) {
                                                            // TODO Auto-generated catch block
                                                            e.printStackTrace();
                                                    } */
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
    	this.gui=gui;
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
		system = (RestaurantOneSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(system.getName()), "Restaurant 1 Host: " + person.getName(), "Ready to work at the restaurant!");
		((RestaurantOneHostGui) gui).DoGoToStand();
		
	}

	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		atDest.release();
		
	}

	@Override
	public void msgIWantABreak(RestaurantOneWaiterRole w) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgImOffBreak(RestaurantOneWaiterRole w) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgLeaving(RestaurantOneCustomerRole c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgCustomerSeated() {
		// TODO Auto-generated method stub
		
	}
	




}
