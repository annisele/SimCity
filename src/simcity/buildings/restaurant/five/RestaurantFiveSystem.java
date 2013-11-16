package simcity.buildings.restaurant.five;

import simcity.gui.SimCityGui;
import simcity.gui.WorldAnimationPanel;


public class RestaurantFiveSystem extends System {
	
	public RestaurantFiveSystem(SimCityGui sc) {
		super(sc);
		animationPanel = new RestaurantFiveAnimationPanel();
	}

}
