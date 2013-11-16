package simcity.buildings.house;

import simcity.gui.SimCityGui;
import simcity.gui.market.MarketAnimationPanel;
import simcity.gui.market.MarketControlPanel;

public class HouseSystem extends simcity.System {
	
	private SimCityGui simCityGui;
	
	public HouseSystem(SimCityGui scg) {
		//super();
		simCityGui = scg;
		controlPanel = new MarketControlPanel();
		animationPanel = new MarketAnimationPanel();
	}
	
	
}
