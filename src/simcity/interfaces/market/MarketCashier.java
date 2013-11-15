package simcity.interfaces.market;

import java.util.Map;

import simcity.buildings.market.MarketCustomerRole;

public interface MarketCashier {

	void msgHereIsAnOrder(MarketCustomerRole marketCustomerRole,
			MarketCustomerRole marketCustomerRole2, Map<String, Integer> items);

	void msgHereIsPayment(double payment, int orderNumber);

}
