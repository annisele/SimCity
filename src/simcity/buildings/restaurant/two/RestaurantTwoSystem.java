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
import simcity.gui.restaurantone.RestaurantOneControlPanel;
import simcity.gui.restauranttwo.RestaurantTwoAnimationPanel;
import simcity.gui.restauranttwo.RestaurantTwoControlPanel;
import simcity.interfaces.restaurant.one.*;
import simcity.interfaces.restaurant.two.RestaurantTwoCashier;
import simcity.interfaces.restaurant.two.RestaurantTwoCook;
import simcity.interfaces.restaurant.two.RestaurantTwoCustomer;
import simcity.interfaces.restaurant.two.RestaurantTwoHost;
import simcity.interfaces.restaurant.two.RestaurantTwoWaiter;

public class RestaurantTwoSystem extends SimSystem {

	private SimCityGui scg;
	// public RestaurantControlPanel controlPanel;
	private RestaurantTwoAnimationPanel animationPanel;
	private List<RestaurantTwoCustomer> customers = new ArrayList<RestaurantTwoCustomer>();
	private List<RestaurantTwoWaiter> waiters = new ArrayList<RestaurantTwoWaiter>();
	private RestaurantTwoHost host;
	private RestaurantTwoCook cook;
	private RestaurantTwoCashier cashier;

	public RestaurantTwoSystem(SimCityGui scgui) {
		super(scgui);
		scg = scgui;
		// controlPanel = new RestaurantControlPanel();
		super.setAnimationPanel(new RestaurantTwoAnimationPanel());
		super.setControlPanel(new RestaurantTwoControlPanel());
	}



	public RestaurantTwoAnimationPanel getAnimationPanel() {
		return animationPanel;
	}

}
