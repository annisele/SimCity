package simcity.buildings.restaurant.six;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantSixMenu {
	private Map<String, Double> prices = new HashMap<String, Double>();
	private List<String> choices = new ArrayList<String>();
	
	public RestaurantSixMenu() {
		choices.add("steak");
		choices.add("chicken");
		choices.add("salad");
		choices.add("pizza");
		prices.put("steak", 15.99);
		prices.put("chicken", 10.99);
		prices.put("salad", 5.99);
		prices.put("pizza", 8.99);
	}
	void removeItem(String item) {
		choices.remove(item);
	}
	void addItem(String item) {
		choices.add(item);
	}
	int size() {
		return choices.size();
	}
	List<String> getChoices() {
		return choices;
	}
	Map<String, Double> getPrices() {
		return prices;
	}
}
