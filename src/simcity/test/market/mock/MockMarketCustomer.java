package simcity.test.market.mock;

import java.util.HashMap;
import java.util.Map;

import simcity.SimSystem;
import simcity.buildings.market.MarketCashierRole;
import simcity.buildings.market.MarketSystem;
import simcity.interfaces.market.MarketCustomer;
import simcity.test.mock.EventLog;
import simcity.test.mock.LoggedEvent;

public class MockMarketCustomer implements MarketCustomer {

	public MarketCashierRole cashier;
	public EventLog log = new EventLog();
	Map<String, Integer> itemsToBuy = new HashMap<String, Integer>();
	double paymentExpected = 14.0;
	MarketSystem market;
	
	@Override
	public void atDestination() {
		
	}

	@Override
	public void msgHereAreItems(Map<String, Integer> itemsToDeliver, double change) {
		log.add(new LoggedEvent("Received HereAreItems from cashier."));
		cashier.msgReceivedOrder();
	}

	@Override
	public void msgPleasePay(String marketName, double payment, int orderNum) {
		log.add(new LoggedEvent("Received PleasePay from cashier. Payment owed = " + payment +". Order number = " + orderNum));
		if(paymentExpected >= payment) {
			market.getCashier().msgHereIsPayment(payment, orderNum);
		}
	}

	@Override
	public void enterBuilding(SimSystem s) {
		market = (MarketSystem)s;
		itemsToBuy.put("chicken", 2);
		cashier.msgHereIsAnOrder(this, this, itemsToBuy);
	}

}
