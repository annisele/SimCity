package simcity.test.market;

import junit.framework.TestCase;
import simcity.PersonAgent;
import simcity.buildings.market.MarketCashierRole;
import simcity.buildings.restaurant.one.RestaurantOneCheck.CheckState;
import simcity.gui.SimCityGui;
import simcity.test.market.mock.MockMarketCustomer;
import simcity.test.market.mock.MockMarketSystem;
import simcity.test.market.mock.MockMarketTruck;
import simcity.test.market.mock.MockMarketWorker;

public class MarketCashierTest extends TestCase {
	MarketCashierRole cashier;
	MockMarketCustomer customer;
	MockMarketWorker worker;
	MockMarketTruck truck;
	PersonAgent person = new PersonAgent("cashier");
	MockMarketSystem market;
	SimCityGui scg;
	
	/**
	 * This method is run before each test. You can use it to instantiate the class variables
	 * for your agent and mocks, etc.
	 */
	public void setUp() throws Exception{
		super.setUp();
		scg = new SimCityGui();
		cashier = new MarketCashierRole(person);		
		customer = new MockMarketCustomer();		
		worker = new MockMarketWorker();
		truck = new MockMarketTruck();
		market = new MockMarketSystem(scg);
	}	
	
	/**
	 * This tests the cashier under very simple terms: one customer is ready to pay the exact bill.
	 */
	public void testOneNormalCustomerScenario()
	{		
		customer.cashier = cashier;		
		worker.cashier = cashier;
		
		//check preconditions
		assertEquals("Cashier should have 0 orders in it. It doesn't.",cashier.orders.size(), 0);		
		assertEquals("MarketCashierRole should have an empty event log before the Cashier's HereIsAnOrder is called. Instead, the Cashier's event log reads: "
						+ cashier.log.toString(), 0, cashier.log.size());
		assertEquals("Cashier's market should be null before cashier has entered building. It isn't.", cashier.market, null);		
		assertEquals("Cashier's worker index should be 0. It isn't.",cashier.workerIndex, 0);		
		assertEquals("Cashier's truck index should be 0. It isn't.",cashier.truckIndex, 0);		
		
		//step 1 of the test - messaging mock customer enterBuilding, so that mock customer will message cashier hereIsAnOrder
		customer.enterBuilding(market);
		
		assertEquals(
				"MockCustomer should have an empty event log after the Cashier's scheduler is called for the first time. Instead, the MockCustomer's event log reads: "
						+ waiter.log.toString(), 0, waiter.log.size());
		
		//check postconditions for step 1 and preconditions for step 2
		assertEquals("MockWaiter should have an empty event log before the Cashier's scheduler is called. Instead, the MockWaiter's event log reads: "
						+ waiter.log.toString(), 0, waiter.log.size());
		
		assertEquals("Cashier should have 1 bill in it. It doesn't.", cashier.checks.size(), 1);
		
		assertTrue("Cashier's scheduler should have returned true (needs to call action to produce check), but didn't.", 
				cashier.pickAndExecuteAnAction());
		
		assertTrue(
				"MockWaiter should have logged \"Received msgHereIsCheck\" but didn't. His log reads instead: "
						+ waiter.log.getLastLoggedEvent().toString(), waiter.log.containsString("Received msgHereIsCheck"));	
		
		//step 2 of the test - alerting the waiter customer is done eating, so waiter
			//will give customer check, and customer will go pay cashier
		waiter.msgDoneEating(customer);
				
		//check postconditions for step 2 / preconditions for step 3
		assertTrue("CashierBill should contain a check with state == paying. It doesn't.",
				cashier.checks.get(0).s == CheckState.paying);
		
		assertTrue("CashierBill should contain a bill of price = $5.99. It contains something else instead: $" 
				+ cashier.checks.get(0).amount, cashier.checks.get(0).amount == 5.99);
		
		assertTrue("CashierBill should contain a bill with the right customer in it. It doesn't.", 
					cashier.checks.get(0).c == customer);
		
		assertTrue("MockCustomer should have logged an event for receiving \"HereIsCheck\" with the correct balance, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(), customer.log.containsString("Received HereIsCheck from waiter. Total = 5.99"));
		
		assertTrue("Cashier should have logged \"Received msgPayCheck\" but didn't. His log reads instead: " 
				+ cashier.log.getLastLoggedEvent().toString(), cashier.log.containsString("Received msgPayCheck"));
		
		
		//step 3 - call scheduler
		//NOTE: I called the scheduler in the assertTrue statement below (to succinctly check the return value at the same time)
		assertTrue("Cashier's scheduler should have returned true (needs to react to customer's PayCheck), but didn't.", 
					cashier.pickAndExecuteAnAction());
			
		//check postconditions for step 3
		assertTrue("MockCustomer should have logged an event for receiving \"msgHereIsChange\" with the correct change, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(), customer.log.containsString("Received msgHereIsChange from cashier. Change = 0.0"));
	
		assertEquals("Cashier should have 0 checks in it. It doesn't.",cashier.checks.size(), 0);		

		assertFalse("Cashier's scheduler should have returned false (no actions left to do), but didn't.", 
				cashier.pickAndExecuteAnAction());
		
	
	}//end one normal customer scenario
	
}
