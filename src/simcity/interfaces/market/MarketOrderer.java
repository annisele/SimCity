package simcity.interfaces.market;

import java.util.Map;

public interface MarketOrderer {
	abstract void msgDeliveringOrder(Map<String, Integer> itemsToDeliver);
}
