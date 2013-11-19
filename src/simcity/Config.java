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
		systems.clear();
		
		systems.addMarket("Market", 100, 100);
		systems.addRestaurantOne("RestaurantOne", 300, 100);
		//systems.addBank("Bank", 100, 300);
		systems.addPerson("Jesus", 100);
		
//		PedestrianGui testPed = new PedestrianGui();
//        world.addBuilding(new BuildingGui("Market1", 30, 10));
//        world.addBuilding(new BuildingGui("Bank1", 110, 10));
//        world.addGui(testPed);
	}
	
	public void oneBank() {
		systems.clear();
		

	}
	

}
