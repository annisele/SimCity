package simcity.buildings.restaurant.one;

import simcity.Role;
import simcity.interfaces.restaurant.one.RestaurantOneCustomer;
import simcity.interfaces.restaurant.one.RestaurantOneHost;

public class RestaurantOneHostRole extends Role implements simcity.interfaces.restaurant.one.RestaurantOneCustomer {
	private String Name;
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

}
