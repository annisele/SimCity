package simcity.test.market;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import simcity.PersonAgent;
import simcity.buildings.market.MarketCashierRole;
import simcity.buildings.market.MarketCashierRole.MarketOrderState;
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
		Map<String, Integer> itemsToBuy = new HashMap<String, Integer>();
		itemsToBuy.put("chicken", 2);
		cashier.msgHereIsAnOrder(customer, customer, itemsToBuy);
		
		assertEquals("Cashier should have 1 orders in it. It doesn't.", cashier.orders.size(), 1);		
		assertEquals("MarketCashierRole should have an empty event log before the Cashier's HereIsAnOrder is called. Instead, the Cashier's event log reads: "
				+ cashier.log.toString(), 0, cashier.log.size());

		//calling scheduler
		assertTrue("Cashier's scheduler should have returned true (needs to call action to send bill), but didn't.", 
				cashier.pickAndExecuteAnAction());
		
		assertTrue(
				"MockCashier should have logged \"Sending bill to customer\" but didn't. His log reads instead: "
						+ cashier.log.getLastLoggedEvent().toString(), cashier.log.containsString("Sending bill to customer"));	
		//step 2
		cashier.msgHereIsPayment(14.0, 0);
		assertEquals(
				"MockCashier should have changed order state to paid, but didn't. The state instead was: "
						+ cashier.orders.get(0).state, cashier.orders.get(0).state, MarketOrderState.paid);	
		assertTrue(
				"MockCashier should have logged \"Received msgHereIsPayment\" but didn't. His log reads instead: "
						+ cashier.log.getLastLoggedEvent().toString(), cashier.log.containsString("Received msgHereIsPayment"));	
	
	}//end one normal customer scenario
	
}
