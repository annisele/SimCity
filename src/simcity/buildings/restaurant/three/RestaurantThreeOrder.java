package simcity.buildings.restaurant.three;
import simcity.interfaces.restaurant.three.*;

public class RestaurantThreeOrder {
	  public RestaurantThreeWaiter waiter;
	    public int tableNum;
	    public String choice;

	    public RestaurantThreeOrder(RestaurantThreeWaiter waiter, int tableNum, String choice) {
	        this.waiter = waiter;
	        this.choice = choice;
	        this.tableNum = tableNum;
	    }

}
