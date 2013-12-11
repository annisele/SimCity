package simcity.buildings.restaurant.six;

import java.util.ArrayList;
import java.util.List;

import simcity.Role;
import simcity.SimSystem;
import simcity.gui.SimCityGui;
import simcity.gui.restaurantsix.RestaurantSixAnimationPanel;
import simcity.gui.restaurantsix.RestaurantSixControlPanel;
import simcity.interfaces.restaurant.five.RestaurantFiveHost;
import simcity.interfaces.restaurant.six.RestaurantSixCustomer;
import simcity.interfaces.restaurant.six.RestaurantSixHost;

// This is Clayton's Restaurant
public class RestaurantSixSystem extends SimSystem {

	private SimCityGui scg;
	// public RestaurantControlPanel controlPanel;
	private List<RestaurantSixCustomer> customers = new ArrayList<RestaurantSixCustomer>();
	//private List<RestaurantSixWaiter> waiters = new ArrayList<RestaurantSixWaiter>();
	private RestaurantSixHost host;
	//private RestaurantSixCook cook;
	//private RestaurantSixComputer computer;
	//private RestaurantSixCashier cashier;
	
	public RestaurantSixSystem(SimCityGui scg) {
		super(scg);
		super.setAnimationPanel(new RestaurantSixAnimationPanel());
		super.setControlPanel(new RestaurantSixControlPanel());
		host = null;
	}
	
	public RestaurantSixHost getHost() {
		return host;
	}
	
	public boolean msgEnterBuilding(Role role) {
		if(role instanceof RestaurantSixHost) {
			if (host == null) {
				
				host = (RestaurantSixHost) role;
				animationPanel.addGui(role.getGui());
				return true;
			}
		}
		else if(host != null) {
			if(role instanceof RestaurantSixCustomer) {
				customers.add((RestaurantSixCustomer) role);
			}
//			else if(role instanceof MarketWorker) {
//				workers.add((MarketWorker) role);
//			}
			if(role == null) System.out.println("ROLE NULL");
			else if(animationPanel == null) System.out.println("ANIMATION PANEL NULL");
			animationPanel.addGui(role.getGui());
			return true;
		}
		return false;
	}
	
	public boolean isOpen() {
		return (host != null);
	}

}
