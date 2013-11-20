package simcity;

import java.util.*;

import simcity.gui.AnimationPanel;
import simcity.gui.SimCityGui;
import simcity.gui.WorldAnimationPanel;

public class WorldSystem extends SimSystem {
	
	List<PersonAgent> personsList = new ArrayList<PersonAgent>();
	
	public WorldSystem(SimCityGui sc) {
		super(sc);
		super.setAnimationPanel(new WorldAnimationPanel());
		animationPanel.setSimCityGui(sc);
	}
	
}
