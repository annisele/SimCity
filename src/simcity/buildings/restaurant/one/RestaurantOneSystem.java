package simcity.buildings.restaurant.one;

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

	public RestaurantOneSystem(SimCityGui scgui) {
		super(scgui);
		// controlPanel = new RestaurantControlPanel();
		super.setAnimationPanel(new RestaurantOneAnimationPanel());
		super.setControlPanel(new RestaurantOneControlPanel());
	}

}
