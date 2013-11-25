package simcity.interfaces.market;

import java.util.Map;

import simcity.buildings.market.MarketCustomerRole;
import simcity.buildings.market.MarketSystem;
import simcity.interfaces.GuiPartner;

public interface MarketCashier extends GuiPartner {

	void msgHereIsAnOrder(MarketCustomerRole marketCustomerRole,
			MarketCustomerRole marketCustomerRole2, Map<String, Integer> items);

	void msgHereIsPayment(double payment, int orderNumber);

}
