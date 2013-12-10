package simcity.interfaces.market;

import java.util.Map;

import simcity.interfaces.GuiPartner;

public interface MarketWorker extends GuiPartner {

	void msgFindOrder(int orderNum, Map<String, Integer> itemsList);

	public abstract void msgFinishWorking();

}
 