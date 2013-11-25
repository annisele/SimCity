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
import simcity.interfaces.market.MarketWorker;

public class MarketWorkerRole extends Role implements MarketWorker {
	private MarketSystem market;
	private List<WorkerOrder> orders = Collections.synchronizedList(new ArrayList<WorkerOrder>());
	private Semaphore atDest = new Semaphore(0, true);
	Timer timer = new Timer();

	public MarketWorkerRole(PersonAgent p) {
		person = p;
		this.gui = new MarketWorkerGui(this);
	}

	@Override
	public void atDestination() {
		atDest.release();
	}

	@Override
	public void msgFindOrder(int orderNum, Map<String, Integer> itemsList) {         
		person.Do("Received msgFindOrder");


		orders.add(new WorkerOrder(orderNum, itemsList));
		stateChanged();
	}

	public boolean pickAndExecuteAnAction() {
		synchronized (orders) {
			if(!orders.isEmpty()) {
				FindAndDeliverOrder(orders.get(0));
			}
		}
		return false;
	}

	private void FindAndDeliverOrder(WorkerOrder o) {

		collectItemsAnimation();

		if(market.getComputer() == null) System.out.println("computer null");
		if(o == null) System.out.println("order is null");
		o.itemsToFind = market.getComputer().fillOrder(o.itemsToFind); //gets full order from system
		if(o.itemsToFind != null) {
			market.getCashier().msgOrderFound(o.orderNumber);
		}
		else {
			//deal with this later, if system can't fill complete or any of order
		}

		((MarketWorkerGui)gui).DoGoToHomePosition();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		orders.remove(o);

	}

	private void collectItemsAnimation() {

		((MarketWorkerGui)gui).DoGoToCenter();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		((MarketWorkerGui)gui).DoGoToShelfOneArea();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		((MarketWorkerGui)gui).DoGoToShelfOne();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		((MarketWorkerGui)gui).DoGoToShelfTwo();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		((MarketWorkerGui)gui).DoGoToCenter();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		((MarketWorkerGui)gui).DoGoToDropOffItems();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private class WorkerOrder {

		int orderNumber;
		Map<String, Integer> itemsToFind;

		WorkerOrder(int oNum, Map<String, Integer> items) {
			orderNumber = oNum;
			itemsToFind = items;
		}
	}

	@Override
	public void msgExitBuilding() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgEnterBuilding(SimSystem s) {
		market = (MarketSystem)s;
		((MarketWorkerGui)gui).DoGoToCenter();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		((MarketWorkerGui)gui).DoGoToHomePosition();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
