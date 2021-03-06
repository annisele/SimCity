package simcity.buildings.market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import simcity.Role;
import simcity.buildings.market.MarketCashierRole.MarketState;
import simcity.gui.SimCityGui;
import simcity.gui.market.MarketAnimationPanel;
import simcity.gui.market.MarketControlPanel;
import simcity.gui.restauranttwo.RestaurantTwoControlPanel;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.market.MarketCashier;
import simcity.interfaces.market.MarketCustomer;
import simcity.interfaces.market.MarketTruck;
import simcity.interfaces.market.MarketWorker;
import simcity.interfaces.restaurant.two.RestaurantTwoCook;

/****
 * ISSUES: sometimes there is a flickering gray square in upper left corner of world panel
 * because there is an idle gui showing as it switches between pedestrian and the actual
 * event's role. Change idle gui starting location to fix this.
 * 
 * Customer does not reappear as an idle gui when it exits the market. Need to fix this!
 * 
 * Used a lot of hacks..usually commented //hack!! or something before it. Commented out getting
 * inventory in computer, set prices to 0, hacked to initialize computers, etc.
 */


public class MarketSystem extends simcity.SimSystem {
	
	private MarketComputer computer = new MarketComputer();
	private MarketCashier cashier;
	private List<MarketCustomer> customers = Collections.synchronizedList(new ArrayList<MarketCustomer>());
	private List<MarketTruck> trucks = Collections.synchronizedList(new ArrayList<MarketTruck>());
	private List<MarketWorker> workers = Collections.synchronizedList(new ArrayList<MarketWorker>());

	public MarketSystem(SimCityGui scg) {
	
		super(scg);
		computer.setSystem(this);
		super.setControlPanel(new MarketControlPanel(computer));
		super.setAnimationPanel(new MarketAnimationPanel());
		((MarketControlPanel)controlPanel).updateFoodDisplay(computer.getInventory());
		
	}
	
	//replaces existing inventory with passed in one
	public void updateFoodDisplay() {
		((MarketControlPanel)controlPanel).updateFoodDisplay(computer.getInventory());
	}
	public void setInventory(Map<String, Integer> inv) {
		computer.setInventory(inv);
	}
	
	public MarketCashier getCashier() {
		return cashier;
	}
	
	public List<MarketTruck> getTrucks() {
		return trucks;
	}
	
	public List<MarketWorker> getWorkers() {
		return workers;
	}
	
	public MarketComputer getComputer() {
		return computer;
	}
	
	public void addTruck(MarketTruck t) {
		trucks.add(t);
	}
	
	@Override
	public boolean msgEnterBuilding(Role role) {
		AlertLog.getInstance().logMessage(AlertTag.valueOf("MARKET1"), "MarketSYSTEM: ", 
				"Someone trying to enter: " + role.toString());
		if(role instanceof MarketCashier) {
			if (cashier == null) {
				cashier = (MarketCashier) role;
				animationPanel.addGui(role.getGui());
				return true;
			}
		}
		else if (cashier != null && cashier.getMarketState() == MarketState.running) {
			if(role instanceof MarketWorker) {
				workers.add((MarketWorker) role);
				animationPanel.addGui(role.getGui());
				return true;
			}
			if (workers.size() > 0) {
				if(role instanceof MarketCustomer) {
					customers.add((MarketCustomer) role);
					animationPanel.addGui(role.getGui());
					return true;
				}
			}
			
		} 
		return false;
	}

	public void exitBuilding(Role role) {
		animationPanel.removeGui(role.getGui());
		if(role instanceof MarketCustomer) {
			customers.remove((MarketCustomer) role);
		}
		else if(role instanceof MarketCashier) {
			cashier = null;
		}
		else if(role instanceof MarketWorker) {
			workers.remove((MarketWorker) role);
			if(workers.size() == 0 && cashier.getMarketState() == MarketState.closed) {
				cashier.msgLeaveWork();
			}
		}
		else if(role instanceof MarketTruck) {
			trucks.remove((MarketTruck) role);
		}
	}

	public boolean isOpen() {
		return (cashier != null && cashier.getMarketState() == MarketState.running);
	}
	
}
