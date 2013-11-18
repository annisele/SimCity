package simcity.buildings.restaurant.two;

import simcity.SimSystem;
import simcity.gui.SimCityGui;

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

public class RestaurantTwoSystem extends SimSystem {

	private SimCityGui scg;
	// public RestaurantControlPanel controlPanel;
	private RestaurantOneAnimationPanel animationPanel;
	private List<RestaurantOneCustomer> customers = new ArrayList<RestaurantOneCustomer>();
	private List<RestaurantOneWaiter> waiters = new ArrayList<RestaurantOneWaiter>();
	private RestaurantOneHost host;
	private RestaurantOneCook cook;
	private RestaurantOneCashier cashier;

	public RestaurantTwoSystem(SimCityGui scgui) {
		super(scgui);
		scg = scgui;
		// controlPanel = new RestaurantControlPanel();
		animationPanel = new RestaurantOneAnimationPanel();
	}


	public RestaurantOneAnimationPanel getAnimationPanel() {
		return animationPanel;
	}

}
