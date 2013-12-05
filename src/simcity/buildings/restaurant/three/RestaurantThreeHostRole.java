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
import simcity.test.mock.EventLog;

public class RestaurantThreeHostRole extends Role implements RestaurantThreeHost {
	private Timer timer = new Timer();
	public  EventLog log = new EventLog();
	private String name;
	private RestaurantThreeCashier ca;
	private Semaphore atDest = new Semaphore(0, true);
	private static Collection<Table> tables;
	private List<RestaurantThreeCustomer> waitingCustomers = Collections.synchronizedList(new ArrayList<RestaurantThreeCustomer>());
	private RestaurantThreeSystem restaurant;

	private class Table {
		RestaurantThreeCustomer occupiedBy;
		int tableNumber;

		Table(int tableNumber) {
			this.tableNumber = tableNumber;
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
	

	public RestaurantThreeHostRole(PersonAgent p) {
		this.person = p;
		this.gui = new RestaurantThreeHostGui(this);
	}
	
	
	public void atDestination() {
		atDest.release();
	}
	
	public void msgIWantFood(RestaurantThreeCustomer cust) {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantHost: " + person.getName(), "A customer has arrived!");
		//checking for unoccupied tables
		for (Table table : tables) {
			if (!table.isOccupied()) {
				waitingCustomers.add(cust);
				Do("Assigning a waiter to customer.");
				stateChanged();
				return;
			}
		}
		//no free tables
		Do("Alerting customer that restaurant is full.");
		cust.msgRestaurantFull();
	}


	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void exitBuilding() {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "Restaurant Three Host: " + person.getName(), "Leaving restaurant three");	
		gui.DoExitBuilding();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		restaurant.exitBuilding(this);
		person.roleFinished();	
	}


	@Override
	public void enterBuilding(SimSystem s) {
		restaurant = (RestaurantThreeSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantThreeHost: " + person.getName(), "Ready to work at the restaurant!");
		
		((RestaurantThreeHostGui) gui).DoGoToStand();
		
	}

}
