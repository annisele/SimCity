package simcity.interfaces.market;

import java.util.Map;

import simcity.buildings.market.MarketCashierRole.MarketState;
import simcity.interfaces.GuiPartner;

public interface MarketCashier extends GuiPartner {

	public abstract void msgHereIsPayment(double payment, int orderNumber);
	public abstract void msgOrderFound(int orderNum);
	public abstract void msgHereIsAnOrder(MarketOrderer mc1, MarketPayer mc2,
			Map<String, Integer> items);
	public abstract void msgReceivedOrder();
	public abstract void msgLeaveWork();
	public abstract MarketState getMarketState();
	
}