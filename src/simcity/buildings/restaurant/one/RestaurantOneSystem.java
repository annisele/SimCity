package simcity.buildings.restaurant.one;

import simcity.Role;
import simcity.SimSystem;
import simcity.gui.SimCityGui;
import simcity.gui.restaurantone.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

import java.awt.*;

import simcity.gui.SimCityGui;
import simcity.gui.restaurantone.RestaurantOneAnimationPanel;

import simcity.interfaces.restaurant.one.*;


/* public class RestaurantOneSystem extends simcity.System {

	import javax.swing.JPanel;
	import simcity.gui.SimCityGui;
	import simcity.gui.restaurant.RestaurantAnimationPanel; */

public class RestaurantOneSystem extends SimSystem {

	// public RestaurantControlPanel controlPanel;
	private List<RestaurantOneCustomer> customers = new ArrayList<RestaurantOneCustomer>();
	private List<RestaurantOneWaiter> waiters = new ArrayList<RestaurantOneWaiter>();
	private RestaurantOneHost host;
	private RestaurantOneCook cook;
	private RestaurantOneCashier cashier;
	private SimCityGui scgui;

	public RestaurantOneSystem(SimCityGui scgui) {
		super(scgui);
		// controlPanel = new RestaurantControlPanel();
		super.setAnimationPanel(new RestaurantOneAnimationPanel());
		super.setControlPanel(new RestaurantOneControlPanel());
	}
	
	public boolean msgEnterBuilding(Role role) {
		if(role instanceof RestaurantOneHost) {
			if (host == null) {
				host = (RestaurantOneHost) role;
				animationPanel.addGui(role.getGui());
				return true;
			}
		}
		else if(host != null) {
			if(role instanceof RestaurantOneCustomer) {
				customers.add((RestaurantOneCustomer) role);
			}
//			else if(role instanceof MarketWorker) {
//				workers.add((MarketWorker) role);
//			}
			else if(role instanceof RestaurantOneWaiter) {
				waiters.add((RestaurantOneWaiter) role);
				//host.msgNewWaiter((RestaurantOneWaiter)role);
			}
			else if(role instanceof RestaurantOneCook) {
				cook = ((RestaurantOneCook)role);
			}
			else if(role instanceof RestaurantOneCashier) {
				cashier = ((RestaurantOneCashier)role);
			}

			animationPanel.addGui(role.getGui());
			return true;
		}
		return false;
	}

}
