package simcity.interfaces.market;

import java.util.Map;

public interface MarketOrderer {
	abstract void msgHereAreItems(Map<String, Integer> itemsToDeliver, double change);
}
