package simcity.buildings.restaurant.one;

import simcity.Role;
import simcity.interfaces.restaurant.one.RestaurantOneCustomer;

public class RestaurantOneCashierRole extends Role implements simcity.interfaces.restaurant.one.RestaurantOneCashier {

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	public void msgHereIsorder(String choice, int tnumber,
			RestaurantOneCustomer cagent,
			RestaurantOneWaiterRole restaurantOneWaiterRole) {
		// TODO Auto-generated method stub
		
	}

	public void msgCustomerPaying(RestaurantOneCheck check, double d) {
		// TODO Auto-generated method stub
		
	}
	
	
}