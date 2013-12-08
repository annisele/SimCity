package simcity.buildings.restaurant.four;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import simcity.SimSystem;
import simcity.gui.SimCityGui;
import simcity.interfaces.restaurant.four.RestaurantFourCustomer;
import simcity.interfaces.restaurant.four.RestaurantFourHost;
import simcity.interfaces.restaurant.four.RestaurantFourWaiter;

//////////////////////////////////////////////////////////////////////////

/**
 * Restaurant Four System
 * @author Kevin Allan Chan
 *
 */

public class RestaurantFourSystem extends SimSystem {

	/**
	 * Data
	 */
	private RestaurantFourHost host;
	private List<RestaurantWaiter> waiters = Collections.synchronizedList(new ArrayList<RestaurantWaiter>());
	private List<RestaurantFourTable> tables = Collections.synchronizedList(new ArrayList<RestaurantFourTable>());
	private RestaurantFourMenu menu;
	
	public static final int NUMBER_TABLES = 3;
	
	// Constructor //////////////////////////////////////////////////////////////////////////

	public RestaurantFourSystem(SimCityGui scg) {
		super(scg);
		for (int i = 1; i <= NUMBER_TABLES; i++) {
			tables.add(new RestaurantFourTable(i));
		}
		menu = new RestaurantFourMenu();
	}

	// Accessors //////////////////////////////////////////////////////////////////////////

	public RestaurantFourHost getHost() {
		return host;
	}

	public void setHost(RestaurantFourHost host) {
		this.host = host;
	}

	public List getWaitersList() {
		return waiters;
	}
	
	public List getTablesList() {
		return tables;
	}
	
	public RestaurantFourMenu getMenu() {
		return menu;
	}
	
	// Functions //////////////////////////////////////////////////////////////////////////
	public void addWaiter(RestaurantFourWaiter waiter) {
		waiters.add(new RestaurantWaiter(waiter));
	}
	
	public RestaurantFourWaiter getLeastBusyWaiter() {
		RestaurantWaiter tempWaiter = null;
		int minLoad = 100;
		synchronized(waiters) {
			for (RestaurantWaiter waiter : waiters) {
				if (waiter.getCustomerLoad() < minLoad) {
					tempWaiter = waiter;
					minLoad = waiter.getCustomerLoad();
				}
			}
		}
		return tempWaiter.getWaiter();
	}
	
	public boolean freeTableExists() {
		boolean temp = false;
		synchronized(tables) {
			for (RestaurantFourTable table : tables) {
				if (table.isEmpty()) {
					temp = true;
				}
			}
		}
		return temp;
	}
	
	public int getFreeTableNumber() {
		int freeTableNumber = 0;
		synchronized(tables) {
			for (RestaurantFourTable table : tables) {
				if (table.isEmpty()) {
					freeTableNumber = table.getTableNumber();
				}
			}
		}
		return freeTableNumber;
	}
	
	public void updateTableOccupants(RestaurantFourCustomer customer, RestaurantFourWaiter waiter, int tableNumber) {
		synchronized(tables) {
			for (RestaurantFourTable table : tables) {
				if (table.getTableNumber() == tableNumber) {
					table.setCustomer(customer);
					table.setWaiter(waiter);
				}
			}
		}
	}
	
	public void updateCustomerLoadOfWaiter(RestaurantFourWaiter w) {
		synchronized(waiters) {
			for (RestaurantWaiter waiter : waiters) {
				if (waiter.getWaiter() == w) {
					waiter.setCustomerLoad(waiter.getCustomerLoad()+1);
				}
			}
		}
	}
	
	/**
	 * Utility class - RestaurantWaiter
	 */
	public class RestaurantWaiter {
		RestaurantFourWaiter waiter;
		int customerLoad;
		
		public RestaurantWaiter(RestaurantFourWaiter waiter) {
			this.waiter = waiter;
		}
		
		public RestaurantFourWaiter getWaiter() {
			return waiter;
		}
		
		public void setWaiter(RestaurantFourWaiter waiter) {
			this.waiter = waiter;
		}
		
		public int getCustomerLoad() {
			return customerLoad;
		}
		
		public void setCustomerLoad(int customerLoad) {
			this.customerLoad = customerLoad;
		}
		
	}
}
