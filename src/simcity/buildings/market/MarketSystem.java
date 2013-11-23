package simcity.buildings.market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import simcity.gui.AnimationPanel;
import simcity.gui.ControlPanel;
import simcity.gui.SimCityGui;
import simcity.Directory;
import simcity.Role;
import simcity.gui.market.MarketAnimationPanel;
import simcity.gui.market.MarketCashierGui;
import simcity.gui.market.MarketControlPanel;
import simcity.interfaces.market.MarketCashier;
import simcity.interfaces.market.MarketCustomer;
import simcity.interfaces.market.MarketTruck;
import simcity.interfaces.market.MarketWorker;

public class MarketSystem extends simcity.SimSystem {
	
	private MarketComputer computer = new MarketComputer();
	private MarketCashier cashier;
	private List<MarketCustomer> customers = Collections.synchronizedList(new ArrayList<MarketCustomer>());
	private List<MarketTruck> trucks = Collections.synchronizedList(new ArrayList<MarketTruck>());
	private List<MarketWorker> workers = Collections.synchronizedList(new ArrayList<MarketWorker>());

	public MarketSystem(SimCityGui scg) {
		super(scg);
		super.setControlPanel(new MarketControlPanel());
		super.setAnimationPanel(new MarketAnimationPanel());
		
	}
	
	//replaces existing inventory with passed in one
	public void setInventory(Map<String, Integer> inv) {
		computer.setInventory(inv);
	}
	
	//sets the cashier
	public void setCashier(MarketCashier c) {
		cashier = c;
		MarketCashierGui cGui = new MarketCashierGui();
		animationPanel.addGui(cGui);
	}
	
	@Override
	public boolean msgEnterBuilding(Role role) {
		
		return true;
	}
	
	
}
