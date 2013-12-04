package simcity.buildings.restaurant.five;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantFiveMenu {
	
	private List<String> options = new ArrayList<String>();
	private Map<String, Double> prices = new HashMap<String, Double>();

	RestaurantFiveMenu() {
		options.add("steak");
		options.add("chicken");
		options.add("salad");
		options.add("pizza");
		prices.put("steak", 15.99);
		prices.put("chicken", 10.99);
		prices.put("salad", 5.99);
		prices.put("pizza", 8.99);

	}

	public double getPrice(String choice) {
		return prices.get(choice);
	}

	public String RandomChoice(){
		return options.get((int) (Math.random() * options.size()));
	}

	public double getLeastExpensivePrice() {
		return prices.get("salad");
	}

	public String getLeastExpensiveItem() {
		return "salad";
	}

	public double getSecondLeastExpensivePrice() {
		return prices.get("pizza");
	}

	public String getChoiceExcept(String badChoice) {
		String toReturn;
		do {
			toReturn = options.get((int) (Math.random() * options.size()));
		} while(toReturn.equals(badChoice));
		return toReturn;
	}
}
