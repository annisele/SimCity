package simcity;

import simcity.gui.AnimationPanel;
import simcity.gui.BuildingGui;
import simcity.gui.transportation.PedestrianGui;

public class Config {
	
	private SystemManager systems;
	
	public Config(SystemManager s) {
		systems = s;
	}
	
	public void oneMarket() {
		systems.addMarket();
		systems.getWorld().getAnimationPanel().addBuilding(new BuildingGui("Market1", 100, 100));
		//System.out.println("Just added a market1");
		
//		PedestrianGui testPed = new PedestrianGui();
//        world.addBuilding(new BuildingGui("Market1", 30, 10));
//        world.addBuilding(new BuildingGui("Bank1", 110, 10));
//        world.addGui(testPed);
	}
	
	public void oneBank() {
		

	}
}
