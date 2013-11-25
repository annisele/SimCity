package simcity.interfaces.market;

import java.util.Map;

import simcity.SimSystem;
import simcity.buildings.market.MarketSystem;

public interface MarketCustomer extends GuiPartner {

	abstract void msgBuyStuff(Map<String, Integer> i);
	abstract void msgEnterBuilding(SimSystem s);
	

}
