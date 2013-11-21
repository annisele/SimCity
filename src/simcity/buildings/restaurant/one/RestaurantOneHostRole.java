package simcity.buildings.restaurant.one;

import simcity.Role;
import simcity.gui.restaurantone.RestaurantOneHostGui;
import simcity.interfaces.restaurant.one.RestaurantOneCustomer;
import simcity.interfaces.restaurant.one.RestaurantOneHost;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Semaphore;

public class RestaurantOneHostRole extends Role implements simcity.interfaces.restaurant.one.RestaurantOneCustomer {
	private String Name;
	static final int NTABLES = 3;//a global for the number of tables.
    //Notice that we implement waitingCustomers using ArrayList, but type it
    //with List semantics.
    public List<RestaurantOneCustomerRole> waitingCustomers = Collections.synchronizedList(new ArrayList<RestaurantOneCustomerRole>());
    public Collection<Table> tables;
    //note that tables is typed with Collection semantics.
    //Later we will see how it is implemented

    private String name;
    private boolean alreadySeated = false;

    public RestaurantOneHostGui hostGui = null;

    private Semaphore seatCustomer = new Semaphore(0, true);

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


	public RestaurantOneHostRole(){
		Name= "KK";
	}
	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}
	public void msgTableIsFree(int tnumber) {
		// TODO Auto-generated method stub
		
	}
	public void msgLeaving(RestaurantOneCustomer cagent) {
		// TODO Auto-generated method stub
		
	}
	public void msgImOffBreak(RestaurantOneWaiterRole restaurantOneWaiterRole) {
		// TODO Auto-generated method stub
		
	}
	public void msgIWantABreak(RestaurantOneWaiterRole restaurantOneWaiterRole) {
		// TODO Auto-generated method stub
		
	}
	public void msgCustomerSeated() {
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
	

}
