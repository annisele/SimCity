package simcity.buildings.restaurant.three;

public class RestaurantThreeMenu {
	public String choices[] = new String[]
			{ "Steak", "Chicken", "Salad", "Pizza" };
	
	public double price[] = {15.99, 10.99, 5.99, 8.99};
	public double cheapestPrice() {
		return 5.99;
	}
	public String cheapestItem() {
		return "Salad";
	}
	public double mostExpensivePrice() {
		return 15.99;
	}
	public String mostExpensiveItem() {
		return "Steak";
	}
	public double secondCheapestPrice() {
		return 8.99;
	}
	public String secondCheapestItem() {
		return "Pizza";
	}
}