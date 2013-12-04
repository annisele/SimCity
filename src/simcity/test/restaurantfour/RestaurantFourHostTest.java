package simcity.test.restaurantfour;

import junit.framework.TestCase;
import simcity.PersonAgent;
import simcity.buildings.restaurant.four.RestaurantFourHostRole;
import simcity.buildings.restaurant.four.RestaurantFourHostRole.Status;
import simcity.buildings.restaurant.four.RestaurantFourSystem;
import simcity.gui.SimCityGui;
import simcity.test.restaurantfour.mock.MockRestaurantFourCustomer;
import simcity.test.restaurantfour.mock.MockRestaurantFourWaiter;

public class RestaurantFourHostTest extends TestCase {

	RestaurantFourHostRole host;
	PersonAgent person;
	RestaurantFourSystem restaurant;
	SimCityGui scg;
	
	MockRestaurantFourWaiter waiter1;
	MockRestaurantFourCustomer customer1;
	
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
		assertTrue("Host scheduler should return true", host.pickAndExecuteAnAction());

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
		
		// setup
		waiter1 = new MockRestaurantFourWaiter("mockwaiter1");
		
		// test preconditions
		assertEquals("Host status should be none", host.getStatus(), Status.none);
		assertEquals("Host should have an assigned PersonAgent", host.getPerson(), person);
		assertTrue("Host waiters list should be 0, or empty", host.getWaitersList().isEmpty());
		assertEquals("Mockwaiter1 event log should be empty", waiter1.log.size(), 0);

		// step 1: enterBuilding
		host.enterBuilding(restaurant);
		
		// check step 1 postconditions
		assertEquals("Host status should be waitingAtRestaurant", host.getStatus(), Status.waitingAtRestaurant);
		assertEquals("Host should have a restaurant four system registed", host.getSystem(), restaurant);
		
		// step 2: call scheduler to change bank system settings to accommodate host arrival
		assertTrue("Host scheduler should return true", host.pickAndExecuteAnAction());

		// check step 2 postconditions
		assertEquals("Host status should be working", host.getStatus(), Status.working);
		assertEquals("BankSystem host should now be updated", restaurant.getHost(), host);

		// step 3: mockwaiter messages host that he has arrived to work - msgWaiterReadyToWork
		host.msgWaiterReadyForWork(waiter1);
		
		// check step 3 postconditions
		assertEquals("Waiters list should have size of 1", host.getWaitersList().size(), 1);
		
		// step 4: call scheduler to process waiting waiter
		assertTrue("Host scheduler should return true", host.pickAndExecuteAnAction());
		
		// check step 4 postconditions
		assertTrue("Waiters list should be empty again", host.getWaitersList().isEmpty());
		assertEquals("BankSystem should now be update", restaurant.getWaitersList().size(), 1);
		assertTrue("Mockwaiter1 should have received msgStartWorking", waiter1.log.containsString("Received msgStartWorking from host"));
		assertEquals("Mockwaiter1 event log should be 1", waiter1.log.size(), 1);
		
		System.out.println("");
		System.out.println("testOneHostToWork done");
		System.out.println("");
	}
	
	// one host does his job with the customer
	public void testOneHostWithWaiterAndCustomer() {
		System.out.println("testOneHostWithWaiterAndCustomer");
		System.out.println("");
		
		// setup
		waiter1 = new MockRestaurantFourWaiter("mockwaiter1");
		customer1 = new MockRestaurantFourCustomer("mockcustomer1");
		
		// test preconditions
		assertEquals("Host status should be none", host.getStatus(), Status.none);
		assertEquals("Host should have an assigned PersonAgent", host.getPerson(), person);
		assertTrue("Host waiters list should be 0, or empty", host.getWaitersList().isEmpty());
		assertEquals("Mockwaiter1 event log should be empty", waiter1.log.size(), 0);
		assertEquals("Mockcustomer1 event log should be empty", customer1.log.size(), 0);

		// step 1: enterBuilding
		host.enterBuilding(restaurant);
		
		// check step 1 postconditions
		assertEquals("Host status should be waitingAtRestaurant", host.getStatus(), Status.waitingAtRestaurant);
		assertEquals("Host should have a restaurant four system registed", host.getSystem(), restaurant);
		
		// step 2: call scheduler to change bank system settings to accommodate host arrival
		assertTrue("Host scheduler should return true", host.pickAndExecuteAnAction());

		// check step 2 postconditions
		assertEquals("Host status should be working", host.getStatus(), Status.working);
		assertEquals("BankSystem host should now be updated", restaurant.getHost(), host);

		// step 3: mockwaiter messages host that he has arrived to work - msgWaiterReadyToWork
		host.msgWaiterReadyForWork(waiter1);
		
		// check step 3 postconditions
		assertEquals("Waiters list should have size of 1", host.getWaitersList().size(), 1);
		
		// step 4: call scheduler to process waiting waiter
		assertTrue("Host scheduler should return true", host.pickAndExecuteAnAction());
		
		// check step 4 postconditions
		assertTrue("Waiters list should be empty again", host.getWaitersList().isEmpty());
		assertEquals("BankSystem should now be update", restaurant.getWaitersList().size(), 1);
		assertTrue("Mockwaiter1 should have received msgStartWorking", waiter1.log.containsString("Received msgStartWorking from host"));
		assertEquals("Mockwaiter1 event log should be 1", waiter1.log.size(), 1);
		assertEquals("Mockcustomer1 event log should be 0", customer1.log.size(), 0);
		
		// step 5: mockcustomer messages host that he has arrived to eat - msgImHungry
		host.msgImHungry(customer1);
		
		// check step 5 postconditions
		assertEquals("Customers list should be 1", host.getCustomersList().size(), 1);
		assertEquals("Customer at index 0 should be mockcustomer", host.getCustomersList().get(0), customer1);
		
		// step 6: call scheduler to seat customer at appropriate table with appropriate waiter
		assertTrue("Host scheduler should have returned true", host.pickAndExecuteAnAction());
		
		// check step 6 postconditions
		assertTrue("Customers list should be empty", host.getCustomersList().isEmpty());
		
		System.out.println("");
		System.out.println("testOneHostWithWaiterAndCustomer done");
		System.out.println("");
	}

}
