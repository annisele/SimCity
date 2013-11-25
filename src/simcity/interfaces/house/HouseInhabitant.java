package simcity.interfaces.house;

import java.util.Map;

import simcity.interfaces.GuiPartner;

public interface HouseInhabitant extends GuiPartner{
	abstract Map<String, Integer> getListToBuy();
}
