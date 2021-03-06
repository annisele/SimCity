package simcity.test.restaurantfour;

import junit.framework.TestCase;
import simcity.PersonAgent;
import simcity.buildings.restaurant.four.RestaurantFourCustomerRole;
import simcity.buildings.restaurant.four.RestaurantFourCustomerRole.Event;
import simcity.buildings.restaurant.four.RestaurantFourCustomerRole.State;
import simcity.buildings.restaurant.four.RestaurantFourSystem;
import simcity.gui.SimCityGui;
import simcity.test.restaurantfour.mock.MockRestaurantFourHost;
import simcity.test.restaurantfour.mock.MockRestaurantFourWaiter;

public class RestaurantFourCustomerTest extends TestCase {

	RestaurantFourCustomerRole customer;
	PersonAgent person;
	RestaurantFourSystem restaurant;
	SimCityGui scg;
	
	MockRestaurantFourHost host;
	MockRestaurantFourWaiter waiter1;
	
	public void setUp() throws Exception {
		super.setUp();
		person = new PersonAgent("PersonAgent");
		customer = new RestaurantFourCustomerRole(person);
		scg = new SimCityGui();
		restaurant = new RestaurantFourSystem(scg);
		restaurant.setName("RESTAURANT4");
	}
	
	// one customer wants to eat (development stage)
	public void testOneCustomer() {
		System.out.println("testOneCustomer");
		System.out.println("");
		
		// setup
		host = new MockRestaurantFourHost("mockhost");
		waiter1 = new MockRestaurantFourWaiter("mockwaiter1");
		restaurant.setHost(host);
		restaurant.addWaiter(waiter1);
		
		// test preconditions
		assertEquals("Customer state should be none", customer.getState(), State.none);
		assertEquals("Customer event should be none", customer.getEvent(), Event.none);
		assertEquals("Customer should have registered a person agent", customer.getPersonAgent(), person);
		assertEquals("Restaurant should know the host", restaurant.getHost(), host);
		assertEquals("Restaurant should have one waiter", restaurant.getWaitersList().size(), 1);
		
		// step 1: enterBuilding
		customer.atDestination();
		customer.enterBuilding(restaurant);
		
		// check step 1 postconditions
		assertEquals("Customer state should be none", customer.getState(), State.none);
		assertEquals("Customer event should be gotHungry", customer.getEvent(), Event.gotHungry);
		assertEquals("Customer should have registered a restaurant system", customer.getSystem(), restaurant);
		
		// step 2: call customer scheduler to inform host of arrival
		customer.atDestination();
		assertTrue("Customer scheduler should have returned true", customer.pickAndExecuteAnAction());
		
		// check step 2 postconditions
		assertEquals("Customer state should be waitingAtRestaurant", customer.getState(), State.waitingAtRestaurant);
		assertEquals("Customer event should be gotHungry", customer.getEvent(), Event.gotHungry);
		assertTrue("Mockhost should have received msgImHungry", host.log.containsString("Received msgImHungry from customer " + customer));
		
		// step 3: msgFollowMeToTable from waiter
		customer.msgFollowMeToTable(waiter1, 1, restaurant.getMenu());
		
		// check step 3 postconditions
		assertEquals("Customer state should be waitingAtRestaurant", customer.getState(), State.waitingAtRestaurant);
		assertEquals("Customer event should be metWaiter", customer.getEvent(), Event.metWaiter);
		assertEquals("Customer should know who his waiter is", customer.getWaiter(), waiter1);
		assertEquals("Customer table number should now be 1", customer.getTableNumber(), 1);
		assertEquals("Customer should have access to a menu", customer.getMenu(), restaurant.getMenu());
		
		// step 4: call scheduler to follow waiter to table
		customer.atDestination();
		assertTrue("Customer scheduler should return true", customer.pickAndExecuteAnAction());
		
		// check step 4 postconditions
		assertEquals("Customer state should be walkingToTable", customer.getState(), State.walkingToTable);
		assertEquals("Customer event should be gotToTable", customer.getEvent(), Event.gotToTable);

		// step 5: call scheduler to think of order
		customer.atDestination();
		assertTrue("Customer scheduler should return true", customer.pickAndExecuteAnAction());
		
		// check step 5 postconditions
		
		
		System.out.println("");
		System.out.println("testOneCustomer done");
		System.out.println("");
	}
	
}
