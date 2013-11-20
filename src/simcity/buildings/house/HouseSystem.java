package simcity.buildings.house;

import simcity.gui.SimCityGui;
import simcity.gui.house.*;

public class HouseSystem extends simcity.SimSystem {
	
	public HouseSystem(SimCityGui scg) {
		super(scg);
		super.setControlPanel(new HouseControlPanel());
		super.setAnimationPanel(new HouseAnimationPanel());
	}
	
	
}
