package simcity;

import java.util.Timer;
import java.util.TimerTask;

public class Config {
	
	private Timer timer = new Timer();
	private SystemManager systems;
	
	public Config(SystemManager s) {
		systems = s;
	}

	public void threeBuildings() {
		systems.clear();
		systems.clearDetailPane();
		
		
		systems.addMarket("Market", 100, 100);
		//systems.addRestaurantTwo("RestaurantTwo", 300, 100);
		systems.addRestaurantOne("RestaurantOne", 300, 100);
		systems.addBank("Bank", 100, 300);
		systems.addHouse("House of the Lord", 300, 300);
		systems.addPerson("Mark");

		systems.addBus("Buster");
		
	}
	
	public void oneMarket() {
		systems.clear();
		systems.clearDetailPane();
		
		systems.addMarket("Market", 100, 100);
		systems.addMarketCashierHack("Mary", "Market");
		systems.addBus("Buster"); //Take this out if you don't want the bus here
		systems.addMarketTruck("Market");
		
		timer.schedule(new TimerTask() {
			public void run() {
				systems.addMarketWorkerHack("Bob", "Market");
				timer.schedule(new TimerTask() {
					public void run() {
						systems.addPerson("Rebecca");
					}
				}, 2000);
			}
		}, 2000);
		
	}
	
	public void oneHouse() {
		systems.clear();
		systems.clearDetailPane();
		
		systems.addHouse("HouseOne", 100, 100);
		//systems.addPerson("Homie"); // This guy will live in the house, hence his name
		systems.addPerson("Homie");
		systems.setHome("Homie", "HouseOne");
	}
	
	public void oneBank() {
		systems.clear();
		systems.clearDetailPane();
		
		systems.addBank("Bank", 100, 300);
		systems.addBankHostHack("Kevin", "Bank");
		
		timer.schedule(new TimerTask(){
			public void run() {
				systems.addBankTellerHack("Key", "Bank");
				timer.schedule(new TimerTask(){
					public void run() {
						systems.addPerson("Levonne");
					}
				}, 4000);
			}
		}, 4000);
	}
	
	public void oneRestaurant() {
		systems.clear();
		systems.clearDetailPane();
		
		systems.addRestaurantTwo("RestaurantTwo", 300, 100);
		systems.addRestaurantTwoHostHack("Bloke", "RestaurantTwo");
		
		timer.schedule(new TimerTask() {
			public void run() {
				systems.addRestaurantTwoWaiterHack("Bob dylan", "RestaurantTwo");
				timer.schedule(new TimerTask() {
					public void run() {
						System.out.println("hi");
						systems.addPerson("jenny");
					}
				}, 2000);
			}
		}, 2000);
	}
	
	public void fullCity() {
		systems.clear();
		systems.clearDetailPane();
		
	}
	
	public void clearTimer() {
		timer.cancel();
		timer.purge();
	}

}
