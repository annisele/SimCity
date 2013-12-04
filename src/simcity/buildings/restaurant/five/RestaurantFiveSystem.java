package simcity.buildings.restaurant.five;

import simcity.gui.SimCityGui;
import simcity.gui.restaurantfive.RestaurantFiveAnimationPanel;
import simcity.gui.restaurantfive.RestaurantFiveControlPanel;

public class RestaurantFiveSystem extends simcity.SimSystem {

	public RestaurantFiveSystem(SimCityGui scg) {
		super(scg);
		super.setAnimationPanel(new RestaurantFiveAnimationPanel());
		super.setControlPanel(new RestaurantFiveControlPanel());
	}

}
