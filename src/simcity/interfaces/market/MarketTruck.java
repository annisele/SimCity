package simcity.interfaces.market;

import java.util.Map;

import simcity.interfaces.GuiPartner;

public interface MarketTruck extends GuiPartner {

	void msgPleaseDeliverOrder(MarketOrderer r, Map<String, Integer> items);

}
