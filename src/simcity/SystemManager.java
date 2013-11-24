
package simcity;

import java.util.*;

import javax.swing.JPanel;

import simcity.Directory.EntryType;
import simcity.buildings.bank.BankHostRole;
import simcity.buildings.bank.BankSystem;
import simcity.buildings.bank.BankTellerRole;
import simcity.buildings.house.HouseSystem;
import simcity.buildings.market.MarketCashierRole;
import simcity.buildings.market.MarketSystem;
import simcity.buildings.market.MarketWorkerRole;
import simcity.buildings.restaurant.five.RestaurantFiveSystem;
import simcity.buildings.restaurant.four.RestaurantFourSystem;
import simcity.buildings.restaurant.one.RestaurantOneSystem;
import simcity.buildings.restaurant.six.RestaurantSixSystem;
import simcity.buildings.restaurant.three.RestaurantThreeSystem;
import simcity.buildings.restaurant.two.RestaurantTwoSystem;
import simcity.buildings.transportation.BusAgent;
import simcity.buildings.transportation.TransportationSystem;
import simcity.gui.Gui;
import simcity.gui.SimCityGui;
import simcity.gui.BuildingGui;
import simcity.gui.transportation.BusGui;
import simcity.interfaces.market.MarketCashier;

public class SystemManager {
	
	SimCityGui simcity;
	WorldSystem world;
	Directory dir = new Directory();
	List<MarketSystem> markets = new ArrayList<MarketSystem>();
	List<BankSystem> banks = new ArrayList<BankSystem>();
	List<HouseSystem> houses = new ArrayList<HouseSystem>();
	List<RestaurantOneSystem> restaurantOnes = new ArrayList<RestaurantOneSystem>();
	List<RestaurantTwoSystem> restaurantTwos = new ArrayList<RestaurantTwoSystem>();
	List<RestaurantThreeSystem> restaurantThrees = new ArrayList<RestaurantThreeSystem>();
	List<RestaurantFourSystem> restaurantFours = new ArrayList<RestaurantFourSystem>();
	List<RestaurantFiveSystem> restaurantFives = new ArrayList<RestaurantFiveSystem>();
	List<RestaurantSixSystem> restaurantSixes = new ArrayList<RestaurantSixSystem>();
	List<TransportationSystem> transportations = new ArrayList<TransportationSystem>();
	
	List<BuildingGui> buildings = new ArrayList<BuildingGui>();
	List<PersonAgent> people = new ArrayList<PersonAgent>();
	
	public SystemManager(SimCityGui g) {
		simcity = g;
		world = new WorldSystem(simcity);//simcity.getWorld();
		dir.setWorld(world);
	}
	
	public void clear() {
		world.getAnimationPanel().clear();
		
		markets.clear();
		banks.clear();
		houses.clear();
		restaurantOnes.clear();
		restaurantTwos.clear();
		restaurantThrees.clear();
		restaurantFours.clear();
		restaurantFives.clear();
		restaurantSixes.clear();
		transportations.clear();
	}
	
	public void getContact(SimSystem s) {
		if(s instanceof MarketSystem) {
			for(MarketSystem m : markets) {
				if(m == s) {
					
				}
			}
		}
	}
	
	public void addPerson(String name) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		people.add(person);
		
		//hacks
		person.startThread();
		
		// Hack because Mark goes to Market
		if (name == "Mark") {
			person.goToMarketNow();
		}
		if (name == "Homie") {
			person.goToHomeNow();
		}
		//world.getAnimationPanel().addGui(person.getRoles().get(0).getGui());

		//restaurantOnes.get(0).getAnimationPanel().addGui(person.getRoles().get(1).getGui());
		//restaurantOnes.get(0).getAnimationPanel().addGui(person.getRestaurantOneCustomer().getGui());

		//restaurantOnes.get(0).getAnimationPanel().addGui(person.getRoles().get(1).getGui());
		//banks.get(0).getAnimationPanel().addGui(person.getRoles().get(2).getGui());
	}
	
	public void addMarket(String name, int xLoc, int yLoc) {
		MarketSystem temp = new MarketSystem(simcity);
		temp.setName(name);
		markets.add(temp);
		BuildingGui building = new BuildingGui(temp, "Market", xLoc, yLoc);
		world.getAnimationPanel().addBuilding(building);
		Location loc = new Location(xLoc, yLoc);
		
		dir.add(name, EntryType.Market, loc, temp);
	}
	
	public void addBank(String name, int xLoc, int yLoc) {
		BankSystem temp = new BankSystem(simcity);
		temp.setName(name);
		banks.add(temp);
		BuildingGui building = new BuildingGui(temp, "Bank", xLoc, yLoc);
		world.getAnimationPanel().addBuilding(building);
		Location loc = new Location(xLoc, yLoc);
		
		dir.add(name, EntryType.Bank, loc, temp);
	}
	
	public void addHouse(String name, int xLoc, int yLoc) {
		HouseSystem temp = new HouseSystem(simcity);
		temp.setName(name);
		houses.add(new HouseSystem(simcity));
		BuildingGui building = new BuildingGui(temp, "House", xLoc, yLoc);
		world.getAnimationPanel().addBuilding(building);
		Location loc = new Location(xLoc, yLoc);
		
		dir.add(name, EntryType.House, loc, temp);
	}
	
	public void addBus(String name) {
		// TODO Auto-generated method stub
		TransportationSystem temp = new TransportationSystem(simcity);
		temp.setName(name);
		transportations.add(temp);
		Location loc = new Location(100, 400);
		dir.add(name, EntryType.Bus, loc, temp);
		BusAgent bus = new BusAgent(name);
		BusGui tbg = new BusGui(bus);
		world.getAnimationPanel().addBus(tbg);
		
		
	}
	
	public void addRestaurantOne(String name, int xLoc, int yLoc) {
		RestaurantOneSystem temp = new RestaurantOneSystem(simcity);
		temp.setName(name);
		restaurantOnes.add(temp);
		BuildingGui building = new BuildingGui(temp, "RestaurantOne", xLoc, yLoc);
		world.getAnimationPanel().addBuilding(building);
		Location loc = new Location(xLoc, yLoc);
		
		dir.add(name, EntryType.Restaurant, loc, temp);
		
	}
	
	public void addRestaurantTwo() {
		restaurantTwos.add(new RestaurantTwoSystem(simcity));
	}
	
	public void addRestaurantThree() {
		restaurantThrees.add(new RestaurantThreeSystem(simcity));
	}
	
	public void addRestaurantFour() {
		restaurantFours.add(new RestaurantFourSystem(simcity));
	}
	
	public void addRestaurantFive() {
		restaurantFives.add(new RestaurantFiveSystem(simcity));
	}
	
	public void addRestaurantSix() {
		restaurantSixes.add(new RestaurantSixSystem(simcity));
	}
	
	public MarketSystem getMarket(int i) {
		return markets.get(i);
	}
	
	public BankSystem getBank(int i) {
		return banks.get(i);
	}
	
	public HouseSystem getHouse(int i) {
		return houses.get(i);
	}
	
	public RestaurantOneSystem getRestaurantOne(int i) {
		return restaurantOnes.get(i);
	}
	
	public RestaurantTwoSystem getRestaurantTwo(int i) {
		return restaurantTwos.get(i);
	}
	
	public RestaurantThreeSystem getRestaurantThree(int i) {
		return restaurantThrees.get(i);
	}
	
	public RestaurantFourSystem getRestaurantFour(int i) {
		return restaurantFours.get(i);
	}
	
	public RestaurantFiveSystem getRestaurantFive(int i) {
		return restaurantFives.get(i);
	}
	
	public RestaurantSixSystem getRestaurantSix(int i) {
		return restaurantSixes.get(i);
	}
	
	public WorldSystem getWorld() {
		return world;
	}
	
	public void setCards() {
		Map<String, JPanel> panels = new HashMap<String, JPanel>();
		for(RestaurantOneSystem r : restaurantOnes) {
			panels.put(r.getName(), r.getAnimationPanel());
		}
		for(MarketSystem m : markets) {
			panels.put(m.getName(), m.getAnimationPanel());
		}
		for(BankSystem b : banks) {
			panels.put(b.getName(), b.getAnimationPanel());
		}
		
		simcity.setCards(panels);
	}
	
	public Map<String, JPanel> getCards() {
		Map<String, JPanel> marketPanels = new HashMap<String, JPanel>();
		
		for(RestaurantOneSystem r : restaurantOnes) {
			marketPanels.put(r.getName(), r.getAnimationPanel());
		}
		for(MarketSystem m : markets) {
			marketPanels.put(m.getName(), m.getAnimationPanel());
		}
		for(BankSystem b : banks) {
			marketPanels.put(b.getName(), b.getAnimationPanel());
		}
		return marketPanels;
	}
	
	public void clearDetailPane() {
		simcity.clearDetailPane();
	}

	public void addMarketCashierHack(String name, String market) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role cashier = new MarketCashierRole(person);
		person.addWork(cashier, market);
		people.add(person);
		person.startThread();
	}
	
	
	
	public void addMarketWorkerHack(String name, String market) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role worker = new MarketWorkerRole(person);
		person.addWork(worker, market);
		people.add(person);
		person.startThread();
	}

	public void addBankHostHack(String name, String bank) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role bankHost = new BankHostRole(person);
		person.addWork(bankHost, bank);
		people.add(person);
		person.startThread();
	}
	public void addBankTellerHack(String name, String bank) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role bankTeller = new BankTellerRole(person);
		person.addWork(bankTeller, bank);
		people.add(person);
		person.startThread();
	}

	
}
