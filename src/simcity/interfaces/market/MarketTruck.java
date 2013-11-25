package simcity.interfaces.market;

import java.util.Map;

import simcity.Role;
import simcity.interfaces.GuiPartner;

public interface MarketTruck extends GuiPartner {

	void msgPleaseDeliverOrder(Role r, Map<String, Integer> items);

}
