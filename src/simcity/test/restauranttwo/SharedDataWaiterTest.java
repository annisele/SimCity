package simcity.test.restauranttwo;

import junit.framework.TestCase;
import simcity.PersonAgent;
import simcity.buildings.bank.BankCustomerRole.State;
import simcity.buildings.market.MarketCashierRole;
import simcity.buildings.restaurant.one.RestaurantOneCheck.CheckState;
import simcity.buildings.restaurant.two.RestaurantTwoOrderWheel;
import simcity.buildings.restaurant.two.RestaurantTwoSharedDataWaiterRole;
import simcity.buildings.restaurant.two.RestaurantTwoSystem;
import simcity.gui.SimCityGui;
import simcity.interfaces.restaurant.two.RestaurantTwoCashier;
import simcity.interfaces.restaurant.two.RestaurantTwoCook;
import simcity.interfaces.restaurant.two.RestaurantTwoCustomer;
import simcity.interfaces.restaurant.two.RestaurantTwoWaiter;
import simcity.test.market.mock.MockMarketCustomer;
import simcity.test.market.mock.MockMarketSystem;
import simcity.test.market.mock.MockMarketTruck;
import simcity.test.market.mock.MockMarketWorker;
import simcity.test.restauranttwo.mock.MockRestaurantTwoCashier;
import simcity.test.restauranttwo.mock.MockRestaurantTwoCook;
import simcity.test.restauranttwo.mock.MockRestaurantTwoCustomer;
import simcity.test.restauranttwo.mock.MockRestaurantTwoHost;
import simcity.test.restauranttwo.mock.MockRestaurantTwoWaiter;

public class SharedDataWaiterTest extends TestCase {
	RestaurantTwoSharedDataWaiterRole SDwaiter;
	MockRestaurantTwoCustomer customer;
	MockRestaurantTwoCook cook;
	MockRestaurantTwoHost host;
	MockRestaurantTwoCashier cashier;
	RestaurantTwoSystem system;
	PersonAgent person;
	SimCityGui scg;
	
	/**
	 * This method is run before each test. You can use it to instantiate the class variables
	 * for your agent and mocks, etc.
	 */
	public void setUp() throws Exception{
		super.setUp();
		person = new PersonAgent("PersonAgent");
		scg = new SimCityGui();
		SDwaiter=new RestaurantTwoSharedDataWaiterRole(person, system, null);
		cashier = new MockRestaurantTwoCashier("mockcashier");		
		customer = new MockRestaurantTwoCustomer("mockcust");	
		system = new RestaurantTwoSystem(scg, null);
	}	
	
	/**
	 * This tests the cashier under very simple terms: one customer is ready to pay the exact bill.
	 */
	public void testOneNormalCustomerScenario()
	{		
		SDwaiter.setCook(cook);
		SDwaiter.setCashier(cashier);
		SDwaiter.setHost(host);
		SDwaiter.setSpot(0);
		
		//check preconditions
		assertEquals("waiter should have 0 customer in it. It doesn't.",SDwaiter.getWaitingCustomers().size(), 0);		
		assertEquals("CashierAgent should have an empty event log before the Cashier's HereIsBill is called. Instead, the Cashier's event log reads: "
				+ SDwaiter.log.toString(), 0, SDwaiter.log.size());
	
		
		
		
}
}
