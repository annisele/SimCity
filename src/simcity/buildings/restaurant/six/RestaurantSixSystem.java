package simcity.buildings.restaurant.six;

import simcity.SimSystem;
import simcity.gui.SimCityGui;
import simcity.gui.restaurantsix.RestaurantSixAnimationPanel;
import simcity.gui.restaurantsix.RestaurantSixControlPanel;
import simcity.interfaces.restaurant.six.RestaurantSixHost;

// This is Clayton's Restaurant
public class RestaurantSixSystem extends SimSystem {

	private SimCityGui scg;
	// public RestaurantControlPanel controlPanel;
	//private List<RestaurantSixCustomer> customers = new ArrayList<RestaurantSixCustomer>();
	//private List<RestaurantSixWaiter> waiters = new ArrayList<RestaurantSixWaiter>();
	private RestaurantSixHost host;
	//private RestaurantSixCook cook;
	//private RestaurantSixComputer computer;
	//private RestaurantSixCashier cashier;
	
	public RestaurantSixSystem(SimCityGui scg) {
		super(scg);
		super.setAnimationPanel(new RestaurantSixAnimationPanel());
		super.setControlPanel(new RestaurantSixControlPanel());
	}

}
