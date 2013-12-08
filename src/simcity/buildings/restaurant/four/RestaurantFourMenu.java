package simcity.buildings.restaurant.four;

import java.util.HashMap;
import java.util.Map;

public class RestaurantFourMenu {
	
	// Data ///////////////////////////////////////////////////////////////////
	
	public enum foodType {steak, chicken, salad, pizza};
	
	public static final double STEAK_PRICE = 15.99;
	public static final double CHICKEN_PRICE = 10.99;
	public static final double SALAD_PRICE = 5.99;
	public static final double PIZZA_PRICE = 8.99;
	
	public Map<Integer,foodType> foodID = new HashMap<Integer,foodType>();
	public Map<foodType,Double> foodPricing = new HashMap<foodType,Double>();
	
	// Constructor ////////////////////////////////////////////////////////////
	
	RestaurantFourMenu() {
		foodID.put(1, foodType.steak);
		foodID.put(2, foodType.chicken);
		foodID.put(3, foodType.salad);
		foodID.put(4, foodType.pizza);
		
		foodPricing.put(foodType.steak, STEAK_PRICE);
		foodPricing.put(foodType.chicken, CHICKEN_PRICE);
		foodPricing.put(foodType.salad, SALAD_PRICE);
		foodPricing.put(foodType.pizza, PIZZA_PRICE);
	}
	
	// Accessors /////////////////////////////////////////////////////////////
	
	public Map<Integer,foodType> getFoodPicking() {
		return foodID;
	}
	
	public Map<foodType,Double> getFoodPricing() {
		return foodPricing;
	}
	
	public foodType getFoodType(int i) {
		return foodID.get(i);
	}
	
	public double getFoodPrice(foodType food) {
		return foodPricing.get(food);
	}
	
	// Functions /////////////////////////////////////////////////////////////
	
}
