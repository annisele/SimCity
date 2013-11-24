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
		systems.clearDetailPane();
		
		systems.addMarket("Market", 100, 100);
		systems.addRestaurantOne("RestaurantOne", 300, 100);
		systems.addBank("Bank", 100, 300);
		systems.addHouse("House of the Lord", 300, 300);
		systems.addPerson("Mark"); // Jesus get back in your house!

		systems.addBus("Buster");
		
		systems.startThreads();				
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
		
		systems.startThreads();
	}
	
	public void oneHouse() {
		systems.clear();
		systems.clearDetailPane();
		
		systems.addHouse("HouseOne", 100, 100);
		//systems.addPerson("Homie"); // This guy will live in the house, hence his name
		systems.addPerson("Homie");
		systems.setHome("Homie", "HouseOne");
		
		systems.startThreads();
	}
	
	public void oneBank() {
		systems.clear();
		systems.clearDetailPane();
		
		systems.addPerson("Levonne");
		systems.addBank("Bank", 100, 300);
		systems.addBankHostHack("Kevin", "Bank");
		
		timer.schedule(new TimerTask(){
			public void run() {
				systems.addBankTellerHack("Key", "Bank");
				timer.schedule(new TimerTask(){
					public void run() {
						systems.addPerson("Zuckerberg");
					}
				}, 2000);
			}
		}, 2000);
		
		systems.startThreads();
	}
	
	public void oneRestaurant() {
		systems.clear();
		systems.clearDetailPane();
	}

}
