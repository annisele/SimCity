package simcity.test.market.mock;

import java.util.Map;

import simcity.buildings.market.MarketCashierRole;
import simcity.interfaces.market.MarketWorker;
import simcity.test.mock.EventLog;
import simcity.test.mock.LoggedEvent;

public class MockMarketWorker implements MarketWorker {

	public MarketCashierRole cashier;
	public EventLog log = new EventLog();
	
	@Override
	public void atDestination() {
		
	}

	@Override
	public void msgFindOrder(int orderNum, Map<String, Integer> itemsList) {
		log.add(new LoggedEvent("Received msgFindOrder from cashier. Order number = " + orderNum));
		cashier.msgOrderFound(orderNum);
	}

}
