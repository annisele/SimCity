package simcity.test.restaurantfour;

import junit.framework.TestCase;
import simcity.PersonAgent;
import simcity.buildings.restaurant.four.RestaurantFourCustomerRole;
import simcity.buildings.restaurant.four.RestaurantFourCustomerRole.Event;
import simcity.buildings.restaurant.four.RestaurantFourCustomerRole.State;
import simcity.buildings.restaurant.four.RestaurantFourSystem;
import simcity.gui.SimCityGui;

public class RestaurantFourCustomerTest extends TestCase {

	RestaurantFourCustomerRole customer;
	PersonAgent person;
	RestaurantFourSystem restaurant;
	SimCityGui scg;
	
	public void setUp() throws Exception {
		super.setUp();
		person = new PersonAgent("PersonAgent");
		customer = new RestaurantFourCustomerRole(person);
		scg = new SimCityGui();
		restaurant = new RestaurantFourSystem(scg);
	}
	
	// one customer wants to eat (development stage)
	public void testOneCustomer() {
		System.out.println("testOneCustomer");
		System.out.println("");
		
		// test preconditions
		assertEquals("Customer state should be none", customer.getState(), State.none);
		assertEquals("Customer event should be none", customer.getEvent(), Event.none);
		assertEquals("Customer should have registered a person agent", customer.getPersonAgent(), person);
		
		// step 1: enterBuilding
		customer.enterBuilding(restaurant);
		
		// check step 1 postconditions
		assertEquals("Customer state should be none", customer.getState(), State.none);
		assertEquals("Customer event should be gotHungry", customer.getEvent(), Event.gotHungry);
		assertEquals("Customer should have registered a restaurant system", customer.getSystem(), restaurant);
		
		System.out.println("");
		System.out.println("testOneCustomer done");
		System.out.println("");
	}
	
}
