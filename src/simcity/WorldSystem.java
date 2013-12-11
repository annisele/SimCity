package simcity;

import java.util.ArrayList;
import java.util.List;

import simcity.gui.SimCityGui;
import simcity.gui.PersonPanel;
import simcity.gui.WorldAnimationPanel;

public class WorldSystem extends SimSystem {
	
	List<PersonAgent> personsList = new ArrayList<PersonAgent>();
	
	public WorldSystem(SimCityGui sc) {
		super(sc);
		super.setAnimationPanel(new WorldAnimationPanel());
		animationPanel.setSimCityGui(sc);
	}
	
	public void clear() {
		for (PersonAgent p : personsList) {
				p.clear();
		}
		personsList.clear();
	}
	
}
