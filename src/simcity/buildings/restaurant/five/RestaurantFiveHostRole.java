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

public class RestaurantFiveHostRole extends Role implements RestaurantFiveHost {

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
	

	public RestaurantFiveHostRole(PersonAgent p) {
		person = p;
		this.gui = new RestaurantFiveHostGui(this);
	}
	
	@Override
	public void atDestination() {
		atDest.release();
	}
	
	public void msgIWantFood(RestaurantFiveCustomer cust) {
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
		// TODO Auto-generated method stub
		
	}


	@Override
	public void enterBuilding(SimSystem s) {
		restaurant = (RestaurantFiveSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantFiveHost: " + person.getName(), "Ready to work at the restaurant!");
		
		((RestaurantFiveHostGui) gui).DoGoToStand();
		
	}

}
