package simcity.interfaces.market;

import simcity.SimSystem;
import simcity.interfaces.GuiPartner;

public interface MarketCustomer extends GuiPartner, MarketOrderer, MarketPayer {

	abstract void enterBuilding(SimSystem s);

}
