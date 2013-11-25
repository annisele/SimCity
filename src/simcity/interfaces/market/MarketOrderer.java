package simcity.interfaces.market;

import java.util.Map;

import simcity.buildings.market.MarketCashierRole;

public interface MarketOrderer {
	abstract void msgDeliveringOrder(Map<String, Integer> itemsToDeliver);
}
