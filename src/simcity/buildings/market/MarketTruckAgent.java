package simcity.buildings.market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import simcity.Directory;
import simcity.Location;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.market.MarketTruckGui;
import simcity.interfaces.market.MarketOrderer;
import simcity.interfaces.market.MarketTruck;
import agent.Agent;

public class MarketTruckAgent extends Agent implements MarketTruck {

	private List<TruckOrder> orders = Collections.synchronizedList(new ArrayList<TruckOrder>());
	private Semaphore atDest = new Semaphore(0, true);
	private MarketSystem market;
	MarketTruckGui gui;
	
	public MarketTruckAgent(SimSystem m) {
		market = (MarketSystem)m;
		Location loc = Directory.getLocation(market.getName());
		
		gui = new MarketTruckGui(this, loc.getX(), loc.getY());
	}
	
	public MarketTruckGui getGui() {
		return gui;
	}

	@Override
	public void msgPleaseDeliverOrder(MarketOrderer r, Map<String, Integer> items) {
		orders.add(new TruckOrder(r, items));
		stateChanged();
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		synchronized(orders) {
			if(!orders.isEmpty()) {
				DeliverOrder(orders.get(0));
				return true;
			}
		}
		return false;
	}

	private void DeliverOrder(TruckOrder o) {
		gui.DoGoToMarket();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gui.DoGoToLocation(200, 250);
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		o.role.msgDeliveringOrder(o.itemsToDeliver);
		orders.remove(o);
	}

	private class TruckOrder {
		MarketOrderer role;
		Map<String, Integer> itemsToDeliver;

		TruckOrder(MarketOrderer roleIn, Map<String, Integer> itemsToDeliverIn) {
			role = roleIn;
			itemsToDeliver = itemsToDeliverIn;
		}

	}

	@Override
	public void atDestination() {
		atDest.release();
	}
}
