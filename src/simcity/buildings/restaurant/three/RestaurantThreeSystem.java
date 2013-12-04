package simcity.buildings.restaurant.three;

import java.util.*;

import simcity.Role;
import simcity.gui.*;
import simcity.interfaces.restaurant.three.RestaurantThreeWaiter;
import simcity.interfaces.restaurant.three.RestaurantThreeCustomer;
import simcity.interfaces.restaurant.three.RestaurantThreeHost;

public class RestaurantThreeSystem extends simcity.SimSystem {
	private RestaurantThreeComputer computer = new RestaurantThreeComputer();
	private RestaurantThreeHost resthost;
	private List<RestaurantThreeCustomer> customers = Collections.synchronizedList(new ArrayList<RestaurantThreeCustomer>());
	private List<RestaurantThreeWaiter> waiters = Collections.synchronizedList(new ArrayList<RestaurantThreeWaiter>());
	public RestaurantThreeSystem(SimCityGui scg) {
		super(scg);
	}
	public RestaurantThreeComputer getRestaurantThreeComputer() {
		return computer;
	}
	
	public boolean msgEnterBuilding(Role role) {
		if (role instanceof RestaurantThreeHost) {
			if (resthost == null) {
				resthost = (RestaurantThreeHost) role;
				animationPanel.addGui(role.getGui());
				return true;
			}
		}
		else if (resthost != null) {
			if (role instanceof RestaurantThreeCustomer) {
				customers.add((RestaurantThreeCustomer) role);
				animationPanel.addGui(role.getGui());
				return true;
			}
			else if (role instanceof RestaurantThreeWaiter) {
				waiters.add((RestaurantThreeWaiter) role);
				animationPanel.addGui(role.getGui());
				return true;
			}
		}
		return false;
	}
	public void exitBuilding(Role role) {
		if(role instanceof RestaurantThreeCustomer) {
			customers.remove((RestaurantThreeCustomer) role);
		}
		else if(role instanceof RestaurantThreeWaiter) {
			waiters.remove((RestaurantThreeWaiter) role);
		}
		else if (role instanceof RestaurantThreeHost) {
			resthost = null;
		}
	}
	public RestaurantThreeHost getRestaurantThreeHost() {
		return resthost;
	}
	
}
