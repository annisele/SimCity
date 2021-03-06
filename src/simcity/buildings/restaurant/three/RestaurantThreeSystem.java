package simcity.buildings.restaurant.three;

import java.util.*;

import simcity.Role;
import simcity.buildings.restaurant.four.RestaurantFourTable;
import simcity.gui.*;
import simcity.gui.restaurantthree.RestaurantThreeAnimationPanel;
import simcity.gui.restaurantthree.RestaurantThreeControlPanel;
import simcity.interfaces.restaurant.five.RestaurantFiveWaiter;
import simcity.interfaces.restaurant.four.RestaurantFourCustomer;
import simcity.interfaces.restaurant.four.RestaurantFourWaiter;
import simcity.interfaces.restaurant.three.RestaurantThreeCook;
import simcity.interfaces.restaurant.three.RestaurantThreeWaiter;
import simcity.interfaces.restaurant.three.RestaurantThreeCustomer;
import simcity.interfaces.restaurant.three.RestaurantThreeHost;
import simcity.interfaces.restaurant.three.RestaurantThreeCashier;
import simcity.test.restaurantthree.mock.MockRestaurantThreeHost;

public class RestaurantThreeSystem extends simcity.SimSystem {
	private RestaurantThreeComputer computer;
	private RestaurantThreeHost resthost;
	private RestaurantThreeCook cook;
	private RestaurantThreeCashier cashier;
	private List<RestaurantThreeCustomer> customers = Collections.synchronizedList(new ArrayList<RestaurantThreeCustomer>());
	private List<RestaurantThreeWaiter> waiters = Collections.synchronizedList(new ArrayList<RestaurantThreeWaiter>());
	
	public RestaurantThreeSystem(SimCityGui scg) {
		super(scg);
		super.setControlPanel(new RestaurantThreeControlPanel());
		super.setAnimationPanel(new RestaurantThreeAnimationPanel());
	}
	public RestaurantThreeComputer getRestaurantThreeComputer() {
		return computer;
	}
	
	public RestaurantThreeCook getRestaurantThreeCook() {
		return cook;
	}
	public RestaurantThreeCashier getRestaurantThreeCashier() {
		return cashier;
	}
	public List <RestaurantThreeCustomer> getCustomers() {
		return customers;
	}
	public List <RestaurantThreeWaiter> getWaiters() {
		return waiters;
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
				resthost.msgAddWaiter((RestaurantThreeWaiter)role);
				animationPanel.addGui(role.getGui());
				return true;
			}
			else if (role instanceof RestaurantThreeCook) {
				if (cook == null) {
					cook = (RestaurantThreeCook) role;
					animationPanel.addGui(role.getGui());
					return true;
				}
			}
			else if (role instanceof RestaurantThreeCashier) {
				if (cashier == null) {
					cashier = (RestaurantThreeCashier) role;
					animationPanel.addGui(role.getGui());
					return true;
				}
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
		else if (role instanceof RestaurantThreeCashier) {
			cashier = null;
		}
		else if (role instanceof RestaurantThreeCook) {
			cook = null;
		}
	}
	
	public RestaurantThreeHost getRestaurantThreeHost() {
		// TODO Auto-generated method stub
		return resthost;
	}
	
	public boolean isOpen() {
		return (resthost != null);
	}
	
	
}
