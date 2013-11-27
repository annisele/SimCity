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
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.market.MarketOrderer;
import simcity.interfaces.market.MarketTruck;
import agent.Agent;

public class MarketTruckAgent extends Agent implements MarketTruck {

	private List<TruckOrder> orders = Collections.synchronizedList(new ArrayList<TruckOrder>());
	private Semaphore atDest = new Semaphore(0, true);
	private MarketSystem market;
	MarketTruckGui gui;
	private class TruckOrder {
		MarketOrderer role;
		Map<String, Integer> itemsToDeliver;

		TruckOrder(MarketOrderer roleIn, Map<String, Integer> itemsToDeliverIn) {
			role = roleIn;
			itemsToDeliver = itemsToDeliverIn;
		}
	}

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
		gui.DoGoToLocation(Directory.getLocation("RESTAURANT2").getX(), Directory.getLocation("RESTAURANT2").getY());
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		AlertLog.getInstance().logMessage(AlertTag.valueOf(market.getName()), "MarketTruck", "Here is your delivery.");
		o.role.msgHereAreItems(o.itemsToDeliver, 0.0);
		orders.remove(o);
	}

	@Override
	public void atDestination() {
		atDest.release();
	}
}
