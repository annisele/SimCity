package simcity;

import simcity.gui.AnimationPanel;
import simcity.gui.BuildingGui;
import simcity.gui.transportation.PedestrianGui;

public class Config {
	
	private AnimationPanel world;
	
	public Config(AnimationPanel w) {
		world = w;
	}
	
	public void onePerson() {
		PedestrianGui testPed = new PedestrianGui();
        world.addBuilding(new BuildingGui("Market1", 30, 10));
        world.addBuilding(new BuildingGui("Bank1", 110, 10));
        world.addGui(testPed);
	}
	
	public void twoPeople() {
		

	}
}
