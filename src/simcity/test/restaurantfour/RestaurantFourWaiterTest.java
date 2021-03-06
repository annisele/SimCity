package simcity.test.restaurantfour;

import junit.framework.TestCase;
import simcity.PersonAgent;
import simcity.buildings.restaurant.four.RestaurantFourMenu;
import simcity.buildings.restaurant.four.RestaurantFourSystem;
import simcity.buildings.restaurant.four.RestaurantFourWaiterRole;
import simcity.buildings.restaurant.four.RestaurantFourWaiterRole.Status;
import simcity.gui.SimCityGui;
import simcity.test.restaurantfour.mock.MockRestaurantFourCustomer;
import simcity.test.restaurantfour.mock.MockRestaurantFourHost;

public class RestaurantFourWaiterTest extends TestCase {

	RestaurantFourWaiterRole waiter;
	PersonAgent person;
	RestaurantFourSystem restaurant;
	SimCityGui scg;
	
	MockRestaurantFourHost host;
	MockRestaurantFourCustomer customer;
	
	public void setUp() throws Exception {
		super.setUp();
		person = new PersonAgent("PersonAgent");
		waiter = new RestaurantFourWaiterRole(person);
		scg = new SimCityGui();
		restaurant = new RestaurantFourSystem(scg);
	}
	

	// one waiter enters work and contacts host scenario (development stage)
	public void testOneWaiterToWork() {
		System.out.println("testOneWaiterToWork");
		System.out.println("");
		
		// setup
		host = new MockRestaurantFourHost("mockhost");
		restaurant.setHost(host);
		
		// test preconditions
		assertEquals("Waiter status should be none", waiter.getStatus(), Status.none);
		assertEquals("Waiter should have an assigned person", waiter.getPerson(), person);
		assertEquals("Mockhost event log should be empty", host.log.size(), 0);
		assertEquals("Restaurant should know that host is correct", restaurant.getHost(), host);
		
		// step 1 - enterBuilding
		waiter.enterBuilding(restaurant);
		
		// check step 1 postconditions
		assertEquals("Waiter status should be waitingAtRestaurant", waiter.getStatus(), Status.waitingAtRestaurant);
		assertEquals("Waiter should have a restaurant four system registered", waiter.getSystem(), restaurant);
		
		// step 2 - call scheduler to message host of arrival
		assertTrue("Waiter scheduler should return true", waiter.pickAndExecuteAnAction());
		
		// check step 2 postconditions
		assertEquals("Waiter status should be waitingForConfirmation", waiter.getStatus(), Status.waitingForConfirmation);
		assertEquals("Mockhost event log should be 1", host.log.size(), 1);
		assertTrue("Mockhost should have received msgWaiterReadyForWork", host.log.containsString("Received msgWaiterReadyForWork from waiter " + waiter));
		
		// step 3 - msgStartWorking
		waiter.msgStartWorking();
		
		// check step 3 postconditions
		assertEquals("Waiter status should be confirmed", waiter.getStatus(), Status.confirmed);
		
		// step 4 - call scheduler to go to waiter position
		assertTrue("Waiter scheduler should return true", waiter.pickAndExecuteAnAction());
		
		// check step 4 postconditions
		assertEquals("Waiter status should be working", waiter.getStatus(), Status.working);
		
		System.out.println("");
		System.out.println("testOneWaiterToWork done");
		System.out.println("");
	}

	public void testOneWaiterWithCustomer() {
		System.out.println("testOneWaiterWithCustomer");
		System.out.println("");
		
		// setup
		host = new MockRestaurantFourHost("mockhost");
		customer = new MockRestaurantFourCustomer("mockcustomer1");
		restaurant.setHost(host);
		
		// test preconditions
		assertEquals("Waiter status should be none", waiter.getStatus(), Status.none);
		assertEquals("Waiter should have an assigned person", waiter.getPerson(), person);
		assertEquals("Mockhost event log should be empty", host.log.size(), 0);
		assertEquals("Mockcustomer event log should be empty", customer.log.size(), 0);
		assertEquals("Restaurant should know that host is correct", restaurant.getHost(), host);
		
		// step 1 - enterBuilding
		waiter.enterBuilding(restaurant);
		
		// check step 1 postconditions
		assertEquals("Waiter status should be waitingAtRestaurant", waiter.getStatus(), Status.waitingAtRestaurant);
		assertEquals("Waiter should have a restaurant four system registered", waiter.getSystem(), restaurant);
		
		// step 2 - call scheduler to message host of arrival
		assertTrue("Waiter scheduler should return true", waiter.pickAndExecuteAnAction());
		
		// check step 2 postconditions
		assertEquals("Waiter status should be waitingForConfirmation", waiter.getStatus(), Status.waitingForConfirmation);
		assertEquals("Mockhost event log should be 1", host.log.size(), 1);
		assertTrue("Mockhost should have received msgWaiterReadyForWork", host.log.containsString("Received msgWaiterReadyForWork from waiter " + waiter));
		
		// step 3 - msgStartWorking
		waiter.msgStartWorking();
		
		// check step 3 postconditions
		assertEquals("Waiter status should be confirmed", waiter.getStatus(), Status.confirmed);
		
		// step 4 - call scheduler to go to waiter position
		assertTrue("Waiter scheduler should return true", waiter.pickAndExecuteAnAction());
		
		// check step 4 postconditions
		assertEquals("Waiter status should be working", waiter.getStatus(), Status.working);
		
		// step 5 - msgSeatCustomerAtTable
		waiter.msgSeatCustomerAtTable(customer, 1, restaurant.getMenu());
		
		// check step 5 postconditions
		assertEquals("Waiter status should be working", waiter.getStatus(), Status.working);
		assertEquals("Waiter should have a registered menu", waiter.getMenu(), restaurant.getMenu());
		assertEquals("Waiter customer list should not be empty", waiter.getCustomers().size(), 1);
		assertEquals("Customers list should have a MyCustomer with customer", waiter.getCustomers().get(0).getCustomer(), customer);
		assertEquals("Customers list should have a MyCustomer with table 1", waiter.getCustomers().get(0).getTableNumber(), 1);
		
		// step 6 - call scheduler to seat customer at table
		assertTrue("Waiter scheduler should return true", waiter.pickAndExecuteAnAction());
		
		// check step 6 postconditions
		//assertEquals("")
		
		System.out.println("");
		System.out.println("testOneWaiterToWork done");
		System.out.println("");
		
	}
	
}
