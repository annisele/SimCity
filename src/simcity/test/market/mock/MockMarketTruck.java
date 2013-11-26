package simcity.test.market.mock;

import java.util.Map;

import simcity.interfaces.market.MarketOrderer;
import simcity.interfaces.market.MarketTruck;

public class MockMarketTruck implements MarketTruck {

	@Override
	public void atDestination() {
		
	}

	@Override
	public void msgPleaseDeliverOrder(MarketOrderer r, Map<String, Integer> items) {
		
	}

}
