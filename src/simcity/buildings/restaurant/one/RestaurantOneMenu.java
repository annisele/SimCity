package simcity.buildings.restaurant.one;

import java.util.HashMap;
import java.util.Map;

public class RestaurantOneMenu {


	public class Menu {
	        
	         String[] menuItems = {"Steak", "Chicken", "Salad", "Pizza"}; 
	         Map<String, Double> priceMap = new HashMap<String, Double>() { { 
	                put ("Steak", 15.99); 
	                put ("Chicken", 10.99);
	                put ("Salad", 5.99);
	                put ("Pizza", 8.99);
	        }};


	}
	
	
}