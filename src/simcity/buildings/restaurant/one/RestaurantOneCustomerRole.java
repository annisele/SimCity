package simcity.buildings.restaurant.one;

import simcity.PersonAgent;
import simcity.Role;
import simcity.gui.restaurantone.RestaurantOneCustomerGui;

public class RestaurantOneCustomerRole extends Role implements simcity.interfaces.restaurant.one.RestaurantOneCustomer {

	// Data
	PersonAgent person;
	RestaurantOneCustomerGui gui;
	
	// Constructor
	public RestaurantOneCustomerRole(PersonAgent person, RestaurantOneCustomerGui gui) {
		this.person = person;
		this.gui = gui;
	}
	
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
