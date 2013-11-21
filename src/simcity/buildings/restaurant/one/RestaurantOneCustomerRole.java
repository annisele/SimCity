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
		// TODO Auto-generated method stub
		return false;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
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
