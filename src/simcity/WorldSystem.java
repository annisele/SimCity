package simcity;

import simcity.gui.AnimationPanel;
import simcity.gui.SimCityGui;
import simcity.gui.WorldAnimationPanel;

public class WorldSystem extends System {
	
	public WorldSystem(SimCityGui sc) {
		super(sc);
		animationPanel = new WorldAnimationPanel();
	}
	
}
