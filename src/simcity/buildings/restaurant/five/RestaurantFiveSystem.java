package simcity.buildings.restaurant.five;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import simcity.Role;
import simcity.gui.SimCityGui;
import simcity.gui.restaurantfive.RestaurantFiveAnimationPanel;
import simcity.gui.restaurantfive.RestaurantFiveControlPanel;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.five.RestaurantFiveCustomer;
import simcity.interfaces.restaurant.five.RestaurantFiveHost;
import simcity.interfaces.restaurant.five.RestaurantFiveWaiter;

public class RestaurantFiveSystem extends simcity.SimSystem {

	private RestaurantFiveHost host = null;
	private List<RestaurantFiveCustomer> customers = Collections.synchronizedList(new ArrayList<RestaurantFiveCustomer>());
	private List<RestaurantFiveWaiter> waiters = Collections.synchronizedList(new ArrayList<RestaurantFiveWaiter>());

	
	public RestaurantFiveSystem(SimCityGui scg) {
		super(scg);
		super.setAnimationPanel(new RestaurantFiveAnimationPanel());
		super.setControlPanel(new RestaurantFiveControlPanel());
	}
	
	public RestaurantFiveHost getHost() {
		return host;
	}
	
	/***
	 * msgEnterBuilding - called by people who want to enter the restaurant
	 * @param role - Role that wants to enter
	 * @return - True if person can enter building, false if restaurant is closed
	 */
	public boolean msgEnterBuilding(Role role) {
		if(role instanceof RestaurantFiveHost) {
			if (host == null) {
				host = (RestaurantFiveHost) role;
				animationPanel.addGui(role.getGui());
				return true;
			}
		}
		else if(host != null) {
			if(role instanceof RestaurantFiveCustomer) {
				customers.add((RestaurantFiveCustomer) role);
			}
//			else if(role instanceof MarketWorker) {
//				workers.add((MarketWorker) role);
//			}
			else if(role instanceof RestaurantFiveWaiter) {
				waiters.add((RestaurantFiveWaiter) role);
				host.msgAddWaiter((RestaurantFiveWaiter)role);
			}

			animationPanel.addGui(role.getGui());
			return true;
		}
		return false;
	}


}
