package simcity;

import java.util.*;

import simcity.gui.AnimationPanel;
import simcity.gui.BuildingGui;
import simcity.gui.transportation.PedestrianGui;

public class Config {
	
	private Timer timer = new Timer();
	private SystemManager systems;
	
	public Config(SystemManager s) {
		systems = s;
	}
	
	public void persons(){
		
	}
	
	public void threeBuildings() {
		systems.clear();
		
		systems.addMarket("Market", 100, 100);
		systems.addRestaurantOne("RestaurantOne", 300, 100);
		systems.addBank("Bank", 100, 300);
		systems.addHouse("House of the Lord", 300, 300);
		systems.addPerson("Mark"); // Jesus get back in your house!
		
		systems.clearDetailPane();
		//systems.setCards();
		
//		PedestrianGui testPed = new PedestrianGui();
//        world.addBuilding(new BuildingGui("Market1", 30, 10));
//        world.addBuilding(new BuildingGui("Bank1", 110, 10));
//        world.addGui(testPed);
	}
	
	public void oneMarket() {
		systems.clear();
		systems.clearDetailPane();
		
		systems.addMarket("Market", 100, 100);
		systems.addMarketCashierHack("Mary", "Market");
		
		timer.schedule(new TimerTask() {
			public void run() {
				systems.addMarketWorkerHack("Bob", "Market");
				timer.schedule(new TimerTask() {
					public void run() {
						systems.addPerson("Mark");
					}
				}, 2000);
			}
		}, 2000);
		
		
		
		
		

	}
	

}
