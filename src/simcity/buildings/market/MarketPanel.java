package simcity.buildings.market;

import java.util.*;

import javax.swing.JPanel;

import simcity.gui.SimCityGui;
import simcity.gui.market.MarketAnimationPanel;
import simcity.gui.market.MarketCashierGui;
import simcity.gui.market.MarketControlPanel;
import simcity.interfaces.market.MarketCashier;
import simcity.interfaces.market.MarketCustomer;
import simcity.interfaces.market.MarketTruck;
import simcity.interfaces.market.MarketWorker;

/*******************
 * Market Panel - Controls everything within the panel. Creates the roles and GUI's.
 * @author rebeccahao
 *
 */
public class MarketPanel extends JPanel {

	private SimCityGui simCityGui;
	public MarketControlPanel controlPanel;
	public MarketAnimationPanel animationPanel;
	private MarketSystem system = new MarketSystem();;
	private MarketCashier cashier;
	private List<MarketCustomer> customers = Collections.synchronizedList(new ArrayList<MarketCustomer>());
	private List<MarketTruck> trucks = Collections.synchronizedList(new ArrayList<MarketTruck>());
	private List<MarketWorker> workers = Collections.synchronizedList(new ArrayList<MarketWorker>());

	public MarketPanel(SimCityGui scg) {
		super();
		controlPanel = new MarketControlPanel();
		animationPanel = new MarketAnimationPanel(scg);
	}
	
	//replaces existing inventory with passed in one
	public void setInventory(Map<String, Integer> inv) {
		system.setInventory(inv);
	}
	
	//sets the cashier
	public void setCashier(MarketCashier c) {
		cashier = c;
		MarketCashierGui cGui = new MarketCashierGui();
		animationPanel.addGui(cGui);
	}
	
	
	
	
	
	
}
