package simcity;

import simcity.gui.AnimationPanel;
import simcity.gui.SimCityGui;
import simcity.gui.WorldAnimationPanel;

public class WorldSystem extends System {
	private SimCityGui simCityGui;
	
	public WorldSystem(SimCityGui sc) {
		simCityGui = sc;
		animationPanel = new WorldAnimationPanel();
	}
	
}
