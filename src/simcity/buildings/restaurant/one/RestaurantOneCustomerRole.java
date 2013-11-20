package simcity.buildings.restaurant.one;

import simcity.PersonAgent;
import simcity.Role;
import simcity.gui.restaurantone.RestaurantOneCustomerGui;
import simcity.interfaces.restaurant.one.RestaurantOneHost;

public class RestaurantOneCustomerRole extends Role implements simcity.interfaces.restaurant.one.RestaurantOneCustomer {

	// Data
	private String name;
	//private PersonAgent person;
//	private RestaurantOneCustomerGui gui;
	private RestaurantOneHost Host;
	
	// Constructor
	public RestaurantOneCustomerRole(PersonAgent person, RestaurantOneCustomerGui gui) {
		this.person = person;
		this.gui = gui;
	}

	public boolean pickAndExecuteAnAction() {
		Do("ok");
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
