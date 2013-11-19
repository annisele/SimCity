package simcity;

import java.util.*;

import javax.swing.JPanel;

import simcity.buildings.bank.BankSystem;
import simcity.buildings.house.HouseSystem;
import simcity.buildings.market.MarketSystem;
import simcity.buildings.restaurant.five.RestaurantFiveSystem;
import simcity.buildings.restaurant.four.RestaurantFourSystem;
import simcity.buildings.restaurant.one.RestaurantOneSystem;
import simcity.buildings.restaurant.six.RestaurantSixSystem;
import simcity.buildings.restaurant.three.RestaurantThreeSystem;
import simcity.buildings.restaurant.two.RestaurantTwoSystem;
import simcity.buildings.transportation.TransportationSystem;
import simcity.gui.Gui;
import simcity.gui.SimCityGui;
import simcity.gui.BuildingGui;

public class SystemManager {
	
	SimCityGui simcity;
	WorldSystem world;
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
	
	public void addPerson(String name, double money) {
		PersonAgent person = new PersonAgent(name, money);
		people.add(person);
		world.getAnimationPanel().addGui(person.getGui());
	}
	
	public void addMarket(String name, int xLoc, int yLoc) {
		MarketSystem temp = new MarketSystem(simcity);
		temp.setName(name);
		markets.add(temp);
		BuildingGui building = new BuildingGui(temp, xLoc, yLoc);
		world.getAnimationPanel().addBuilding(building);
	}
	
	public void addBank() {
		banks.add(new BankSystem(simcity));
	}
	
	public void addHouse() {
		houses.add(new HouseSystem(simcity));
	}
	
	public void addRestaurantOne(String name, int xLoc, int yLoc) {
		RestaurantOneSystem temp = new RestaurantOneSystem(simcity);
		temp.setName(name);
		restaurantOnes.add(temp);
		BuildingGui building = new BuildingGui(temp, xLoc, yLoc);
		world.getAnimationPanel().addBuilding(building);
		
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
		return marketPanels;
	}
	
	
}
