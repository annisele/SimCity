package simcity.test.restaurantfour;

import junit.framework.TestCase;
import simcity.PersonAgent;
import simcity.buildings.restaurant.four.RestaurantFourSystem;
import simcity.buildings.restaurant.four.RestaurantFourWaiterRole;
import simcity.buildings.restaurant.four.RestaurantFourWaiterRole.Status;
import simcity.gui.SimCityGui;

public class RestaurantFourWaiterTest extends TestCase {

	RestaurantFourWaiterRole waiter;
	PersonAgent person;
	RestaurantFourSystem restaurant;
	SimCityGui scg;
	
	public void setUp() throws Exception {
		super.setUp();
		person = new PersonAgent("PersonAgent");
		waiter = new RestaurantFourWaiterRole(person);
		scg = new SimCityGui();
		restaurant = new RestaurantFourSystem(scg);
	}
	

	// one host enters work (development stage)
	public void testOneWaiterToWork() {
		System.out.println("testOneWaiterToWork");
		System.out.println("");
		
		// test preconditions
		assertEquals("Waiter status should be none", waiter.getStatus(), Status.none);
		assertEquals("Waiter should have an assigned person", waiter.getPerson(), person);
		
		// step 1 - enterBuilding
		waiter.enterBuilding(restaurant);
		
		// check step 1 postconditions
		assertEquals("Waiter status should be waitingAtRestaurant", waiter.getStatus(), Status.waitingAtRestaurant);
		assertEquals("Waiter should have a restaurant four system registered", waiter.getSystem(), restaurant);
		
		// step 2 - call scheduler to message host of arrival
		waiter.pickAndExecuteAnAction();
		
		// check step 2 postconditions
		assertEquals("Waiter status should be waitingForConfirmation", waiter.getStatus(), Status.waitingForConfirmation);

		System.out.println("");
		System.out.println("testOneWaiterToWork done");
		System.out.println("");
	}

}
