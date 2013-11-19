package simcity.buildings.restaurant.one;

import simcity.PersonAgent;
import simcity.Role;
import simcity.interfaces.restaurant.one.RestaurantOneHost;

public class RestaurantOneCustomerRole extends Role implements simcity.interfaces.restaurant.one.RestaurantOneCustomer {
	private String name;
	private RestaurantOneHostRole Host;
	private PersonAgent person;
	public RestaurantOneCustomerRole(String n, PersonAgent p){
		this.name= n;
		this.person=p;
	}
	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

}
