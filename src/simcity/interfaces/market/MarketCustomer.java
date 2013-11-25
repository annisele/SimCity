package simcity.interfaces.market;

import java.util.Map;

import simcity.SimSystem;
import simcity.buildings.market.MarketCashierRole;
import simcity.interfaces.GuiPartner;

public interface MarketCustomer extends GuiPartner, MarketOrderer, MarketPayer {

	abstract void msgBuyStuff(Map<String, Integer> i);
	abstract void msgEnterBuilding(SimSystem s);

}
