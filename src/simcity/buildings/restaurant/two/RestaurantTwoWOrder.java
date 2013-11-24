package simcity.buildings.restaurant.two;
import simcity.interfaces.restaurant.two.*;

public class RestaurantTwoWOrder {
	  public RestaurantTwoWaiter waiter;
	    public int tableNum;
	    public String choice;

	    public RestaurantTwoWOrder(RestaurantTwoWaiter waiter, int tableNum, String choice) {
	        this.waiter = waiter;
	        this.choice = choice;
	        this.tableNum = tableNum;
	    }

}
