package simcity.buildings.restaurant.four;

import simcity.interfaces.restaurant.four.RestaurantFourCustomer;
import simcity.interfaces.restaurant.four.RestaurantFourWaiter;

//////////////////////////////////////////////////////////////////////////

/**
 * Restaurant Four Table
 * @author Kevin Allan Chan
 *
 */

public class RestaurantFourTable {

	/**
	 * Data
	 */
	
	private int tableNumber;
	private RestaurantFourCustomer customer;
	private RestaurantFourWaiter waiter;
	
	// Constructor //////////////////////////////////////////////////////////////////////////

	public RestaurantFourTable(int i) {
		this.tableNumber = i;
	}
	
	// Accessors //////////////////////////////////////////////////////////////////////////

	public int getTableNumber() {
		return tableNumber;
	}
	
	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}
	
	public RestaurantFourCustomer getCustomer() {
		return customer;
	}
	
	public void setCustomer(RestaurantFourCustomer customer) {
		this.customer = customer;
	}
	
	public RestaurantFourWaiter getWaiter() {
		return waiter;
	}
	
	public void setWaiter(RestaurantFourWaiter waiter) {
		this.waiter = waiter;
	}
	
	public boolean isEmpty() {
		if (customer == null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// Functions //////////////////////////////////////////////////////////////////////////

	
}
