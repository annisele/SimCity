package simcity;

import java.util.ArrayList;
import java.util.List;

import simcity.gui.SimCityGui;
import simcity.gui.PersonPanel;

public class WorldSystem extends SimSystem {
	
	List<PersonAgent> personsList = new ArrayList<PersonAgent>();
	
	public WorldSystem(SimCityGui sc) {
		super(sc);
		super.setAnimationPanel(new PersonPanel());
		animationPanel.setSimCityGui(sc);
	}
	
	public void clear() {
		for (PersonAgent p : personsList) {
				p.clear();
		}
		personsList.clear();
	}
	
}
