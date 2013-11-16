package simcity;

import java.util.*;

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
import simcity.gui.SimCityGui;

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
	
	public SystemManager(SimCityGui g) {
		simcity = g;
		world = new WorldSystem(simcity);
	}
	
	public void addMarket() {
		markets.add(new MarketSystem(simcity));
	}
	
	public void addBank() {
		banks.add(new BankSystem(simcity));
	}
	
	public void addHouse() {
		houses.add(new HouseSystem(simcity));
	}
	
	public void addRestaurantOne() {
		restaurantOnes.add(new RestaurantOneSystem(simcity));
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
	
	
	
}
