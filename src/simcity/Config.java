package simcity;

import simcity.gui.AnimationPanel;
import simcity.gui.BuildingGui;
import simcity.gui.transportation.PedestrianGui;

public class Config {
	
	private SystemManager systems;
	
	public Config(SystemManager s) {
		systems = s;
	}
	public void persons(){
		
	}
	public void oneMarket() {
		systems.addMarket();
		
//		PedestrianGui testPed = new PedestrianGui();
//        world.addBuilding(new BuildingGui("Market1", 30, 10));
//        world.addBuilding(new BuildingGui("Bank1", 110, 10));
//        world.addGui(testPed);
	}
	
	public void oneBank() {
		

	}
}
