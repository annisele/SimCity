package simcity.buildings.market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.market.MarketWorkerGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.market.MarketWorker;

public class MarketWorkerRole extends Role implements MarketWorker {
	private MarketSystem market;
	private List<WorkerOrder> orders = Collections.synchronizedList(new ArrayList<WorkerOrder>());
	private Semaphore atDest = new Semaphore(0, true);
	Timer timer = new Timer();
	boolean stopWorking = false;
	private class WorkerOrder {
		int orderNumber;
		Map<String, Integer> itemsToFind;

		WorkerOrder(int oNum, Map<String, Integer> items) {
			orderNumber = oNum;
			itemsToFind = items;
		}
	}

	public MarketWorkerRole(PersonAgent p) {
		person = p;
		this.gui = new MarketWorkerGui(this);
	}

	@Override
	public void atDestination() {
		atDest.release();
	}
	
	public void msgFinishWorking() {
		stopWorking = true;
		stateChanged();
	}

	@Override
	public void msgFindOrder(int orderNum, Map<String, Integer> itemsList) {         
		synchronized(orders) {
			orders.add(new WorkerOrder(orderNum, itemsList));
		}
		stateChanged();
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		synchronized (orders) {
			if(!orders.isEmpty()) {
				FindAndDeliverOrder(orders.get(0));
				return true;
			}
		}
		if(stopWorking) {
			exitBuilding();
		}
		return false;
	}

	private void FindAndDeliverOrder(WorkerOrder o) {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(market.getName()), "MarketWorker: " + person.getName(), "Collecting order.");
		collectItemsAnimation();
		o.itemsToFind = market.getComputer().fillOrder(o.itemsToFind); //gets full order from system
		if(o.itemsToFind != null) {
			market.getCashier().msgOrderFound(o.orderNumber);
		}
		else {
			AlertLog.getInstance().logError(AlertTag.valueOf(market.getName()), "MarketCashier: " + person.getName(), "Computer returned null when trying to fill order.");		
		}
		synchronized(orders) {
			orders.remove(o);
		}
		if(orders.isEmpty()) {
			((MarketWorkerGui)gui).DoGoToHomePosition();
			try {
				atDest.acquire();
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
		}
	}

	private void collectItemsAnimation() {

		((MarketWorkerGui)gui).DoGoToCenter();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}

		((MarketWorkerGui)gui).DoGoToShelfOneArea();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}

		((MarketWorkerGui)gui).DoGoToShelfOne();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}

		((MarketWorkerGui)gui).DoGoToShelfTwo();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}

		((MarketWorkerGui)gui).DoGoToCenter();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}

		((MarketWorkerGui)gui).DoGoToDropOffItems();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}

	@Override
	public void exitBuilding() {
		gui.DoExitBuilding();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		AlertLog.getInstance().logMessage(AlertTag.valueOf(market.getName()), "MarketWorker: " + person.getName(), "Leaving market.");
		market.exitBuilding(this);
		person.roleFinished();
	}

	@Override
	public void enterBuilding(SimSystem s) {
		market = (MarketSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(market.getName()), "MarketWorker: " + person.getName(), "Entering market.");
		((MarketWorkerGui)gui).DoGoToCenter();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		((MarketWorkerGui)gui).DoGoToHomePosition();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}

	}
}
