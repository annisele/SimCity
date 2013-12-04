package simcity.buildings.restaurant.four;

import simcity.gui.SimCityGui;
import simcity.interfaces.restaurant.four.RestaurantFourHost;

public class RestaurantFourSystem extends simcity.SimSystem {

	private RestaurantFourHost host;
	
	public RestaurantFourSystem(SimCityGui scg) {
		super(scg);
	}

	public RestaurantFourHost getHost() {
		return host;
	}

	public void setHost(RestaurantFourHost host) {
		this.host = host;
	}

}
