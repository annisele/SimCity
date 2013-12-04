package simcity.test.restaurantfour;

import junit.framework.TestCase;
import simcity.PersonAgent;
import simcity.buildings.restaurant.four.RestaurantFourHostRole;
import simcity.buildings.restaurant.four.RestaurantFourHostRole.Status;
import simcity.buildings.restaurant.four.RestaurantFourSystem;
import simcity.gui.SimCityGui;

public class RestaurantFourHostTest extends TestCase {

	RestaurantFourHostRole host;
	PersonAgent person;
	RestaurantFourSystem restaurant;
	SimCityGui scg;
	
	public void setUp() throws Exception {
		super.setUp();
		person = new PersonAgent("PersonAgent");
		host = new RestaurantFourHostRole(person);
		scg = new SimCityGui();
		restaurant = new RestaurantFourSystem(scg);
	}
	

	// one host enters work (development stage)
	public void testOneHostToWork() {
		System.out.println("testOneHostToWork");
		System.out.println("");
		
		// test preconditions
		assertEquals("Host status should be none", host.getStatus(), Status.none);
		assertEquals("Host should have an assigned PersonAgent", host.getPerson(), person);

		// step 1: enterBuilding
		host.enterBuilding(restaurant);
		
		// check step 1 postconditions
		assertEquals("Host status should be waitingAtRestaurant", host.getStatus(), Status.waitingAtRestaurant);
		assertEquals("Host should have a restaurant four system registed", host.getSystem(), restaurant);
		
		// step 2: call scheduler to change bank system settings to accommodate host arrival
		host.pickAndExecuteAnAction();

		// check step 2 postconditions
		assertEquals("Host status should be working", host.getStatus(), Status.working);
		assertEquals("BankSystem host should now be updated", restaurant.getHost(), host);

		System.out.println("");
		System.out.println("testOneHostToWork done");
		System.out.println("");
	}

	// one host welcomes a waiter
	public void testOneHostWithWaiter() {
		System.out.println("testOneHostWithWaiter");
		System.out.println("");
		
		// test preconditions
		assertEquals("Host status should be none", host.getStatus(), Status.none);
		assertEquals("Host should have an assigned PersonAgent", host.getPerson(), person);

		// step 1: enterBuilding
		host.enterBuilding(restaurant);
		
		// check step 1 postconditions
		assertEquals("Host status should be waitingAtRestaurant", host.getStatus(), Status.waitingAtRestaurant);
		assertEquals("Host should have a restaurant four system registed", host.getSystem(), restaurant);
		
		// step 2: call scheduler to change bank system settings to accommodate host arrival
		host.pickAndExecuteAnAction();

		// check step 2 postconditions
		assertEquals("Host status should be working", host.getStatus(), Status.working);
		assertEquals("BankSystem host should now be updated", restaurant.getHost(), host);

		System.out.println("");
		System.out.println("testOneHostToWork done");
		System.out.println("");
	}
	
	// one host does his job with the customer
	public void testOneHostWithCustomer() {
		System.out.println("testOneHostWithCustomer");
		System.out.println("");
		
		// test preconditions
		
		// step 1: enterBuilding
		
		// check step 1 postconditions
		
		System.out.println("");
		System.out.println("testOneHostWithCustomer done");
		System.out.println("");
	}

}
