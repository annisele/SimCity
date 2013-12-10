package simcity.buildings.restaurant.three;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import simcity.Directory;
import simcity.buildings.market.MarketSystem;

public class RestaurantThreeComputer {
	public enum MarketStatus{HAVE_STOCK, NO_STOCK};
	Timer timer = new Timer();
	MarketStatus marketStatus;
	private Map<String, FoodData> food = new HashMap<String, FoodData>();
	private List<MyMarket>markets = Collections.synchronizedList(new ArrayList<MyMarket>());
	public RestaurantThreeComputer(int salad_stock, int chicken_stock, int steak_stock, int pizza_stock) {
		List<String> market = Directory.getMarkets();
		marketStatus = MarketStatus.HAVE_STOCK;
		food.put("Steak", new FoodData("Steak", 3, 5));
		food.put("Chicken", new FoodData("Chicken",3, 5));
		food.put("Pizza", new FoodData("Pizza",3,5));
		food.put("Salad", new FoodData("Salad",3,2));
	}
	private class MyMarket {
		MarketSystem marketSystem;
    	String name;
    	int amountOrdered = 0;
    	double paymentToMarket = 0;
    	double oweMarket = 0;
    	public MarketStatus marketStatus;
    	
    	public MyMarket( String marketName, MarketStatus marketState) {
    		this.name = marketName;
    		this.marketStatus = marketState;
    	}
	}
	private class FoodData {
		String type;
		int cookTime;
		int amount;
		
		public FoodData(String type, int cookTime, int amount) {
			this.type = type;
			this.cookTime = cookTime;
		    this.amount = amount;
		}
	}
	public String getMarket() {
		return markets.get(0).name;
	}
}
