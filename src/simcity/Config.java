package simcity;

import simcity.buildings.Building;
import simcity.gui.AnimationPanel;
import simcity.gui.transportation.PedestrianGui;

public class Config {
	
	private AnimationPanel world;
	
	public Config(AnimationPanel w) {
		world = w;
	}
	
	public void onePerson() {
		PedestrianGui testPed = new PedestrianGui();
        world.addBuilding(new Building("Market1", 30, 10));
        world.addBuilding(new Building("Bank1", 110, 10));
        world.addGui(testPed);
	}
	
	public void twoPeople() {
		

	}
}
