package simcity.buildings.restaurant.two;

import simcity.Role;
import simcity.SimSystem;
import simcity.buildings.bank.BankHostRole;
import simcity.buildings.market.MarketCashierRole;
import simcity.gui.SimCityGui;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

import java.awt.*;

import simcity.gui.SimCityGui;
import simcity.gui.bank.BankHostGui;
import simcity.gui.restaurantone.RestaurantOneAnimationPanel;
import simcity.gui.restaurantone.RestaurantOneControlPanel;
import simcity.gui.restauranttwo.RestaurantTwoAnimationPanel;
import simcity.gui.restauranttwo.RestaurantTwoControlPanel;
import simcity.gui.restauranttwo.RestaurantTwoHostGui;
import simcity.interfaces.bank.BankHost;
import simcity.interfaces.market.MarketCashier;
import simcity.interfaces.market.MarketCustomer;
import simcity.interfaces.market.MarketTruck;
import simcity.interfaces.market.MarketWorker;
import simcity.interfaces.restaurant.one.*;
import simcity.interfaces.restaurant.two.RestaurantTwoCashier;
import simcity.interfaces.restaurant.two.RestaurantTwoCook;
import simcity.interfaces.restaurant.two.RestaurantTwoCustomer;
import simcity.interfaces.restaurant.two.RestaurantTwoHost;
import simcity.interfaces.restaurant.two.RestaurantTwoWaiter;

public class RestaurantTwoSystem extends SimSystem {

	private SimCityGui scg;
	// public RestaurantControlPanel controlPanel;
	private List<RestaurantTwoCustomer> customers = new ArrayList<RestaurantTwoCustomer>();
	private List<RestaurantTwoWaiter> waiters = new ArrayList<RestaurantTwoWaiter>();
	private RestaurantTwoOrderWheel owheel;
	private RestaurantTwoHost host;
	private RestaurantTwoCook cook;
	private RestaurantTwoCashier cashier;

	public RestaurantTwoSystem(SimCityGui scgui) {
		super(scgui);
		super.setAnimationPanel(new RestaurantTwoAnimationPanel());
		super.setControlPanel(new RestaurantTwoControlPanel());
		this.owheel = new RestaurantTwoOrderWheel();
	}

	public RestaurantTwoHost getR2Host() {
		return host;
	}
	/*
	public void setRestaurantTwoHost(RestaurantTwoHostRole h) {
		this.host = h;
		RestaurantTwoHostGui hostGui = new RestaurantTwoHostGui(h);
		animationPanel.addGui(hostGui);
	}
*/
	public boolean msgEnterBuilding(Role role) {

		//System.out.println("gui: "+role.getGui());
		//System.out.println("the role "+role+" has a gui: "+role.getGui());

		animationPanel.addGui(role.getGui());
		if(role instanceof RestaurantTwoCustomer) {
			((RestaurantTwoCustomer) role).setHost(host);
			((RestaurantTwoCustomer) role).setCashier(cashier);
			((RestaurantTwoCustomer) role).setCook(cook);
			customers.add((RestaurantTwoCustomer) role);
		}
		else if(role instanceof RestaurantTwoHost) {
			host = (RestaurantTwoHost) role;
		}
		else if(role instanceof RestaurantTwoCook) {
			((RestaurantTwoCook)role).setOrderWheel(owheel);
			cook = (RestaurantTwoCook) role;
		}
		else if(role instanceof RestaurantTwoWaiter) {
			
			((RestaurantTwoWaiter) role).setHost(host);
			((RestaurantTwoWaiter) role).setCashier(cashier);
			((RestaurantTwoWaiter) role).setCook(cook);
			host.addWaiter((RestaurantTwoWaiter) role);
			waiters.add((RestaurantTwoWaiter) role);
		}
		
		else if(role instanceof RestaurantTwoCashier) {
			cashier = (RestaurantTwoCashier) role;
		}
		return true;
	}

	public void exitBuilding(Role role) {
		animationPanel.removeGui(role.getGui());
		if(role instanceof RestaurantTwoCustomer) {
			customers.remove((RestaurantTwoCustomer) role);
		}
		else if(role instanceof RestaurantTwoWaiter) {
			waiters.remove((RestaurantTwoWaiter) role);
		}
		
	}


}
