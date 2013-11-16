package simcity.buildings.house;

import simcity.gui.SimCityGui;

import simcity.gui.market.MarketAnimationPanel;
import simcity.gui.market.MarketControlPanel;

public class HouseSystem extends simcity.SimSystem {
	
	
	public HouseSystem(SimCityGui scg) {
		super(scg);
		simCityGui = scg;
		controlPanel = new MarketControlPanel();
		animationPanel = new MarketAnimationPanel();
	}
	
	
}
