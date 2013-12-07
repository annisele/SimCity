package simcity.test.restaurantthree;

import junit.framework.TestCase;
import simcity.PersonAgent;
import simcity.buildings.restaurant.three.RestaurantThreeCustomerRole;
import simcity.buildings.restaurant.three.RestaurantThreeSystem;
import simcity.gui.SimCityGui;

public class RestaurantThreeCustomerTest extends TestCase {

	RestaurantThreeCustomerRole customer;
	PersonAgent person;
	RestaurantThreeSystem rest;
	SimCityGui scg;
	
	public void setUp() throws Exception {
		super.setUp();
		person = new PersonAgent("PersonAgent");
		customer = new RestaurantThreeCustomerRole(person);
		scg = new SimCityGui();
		rest = new RestaurantThreeSystem(scg);
	}
	
	public void testOneCustomer() {
		System.out.println("===============testOneCustomer================");
		
		customer.enterBuilding(rest);
	}

}