package simcity.buildings.market;

import java.util.*;

import simcity.Role;
import simcity.interfaces.market.MarketCashier;

public class MarketWorkerRole extends Role implements simcity.interfaces.market.MarketWorker {
	private MarketComputer system;
	private List<WorkerOrder> orders = Collections.synchronizedList(new ArrayList<WorkerOrder>());
	private MarketCashier cashier;

	public void msgFindOrder(int orderNum, Map<String, Integer> itemsList) {                                            
		orders.add(new WorkerOrder(orderNum, itemsList));
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
		o.itemsToFind = system.fillOrder(o.itemsToFind); //gets full order from system
		if(o.itemsToFind != null) {
			((MarketCashierRole)cashier).msgOrderFound(o.orderNumber);
		}
		else {
			//deal with this later, if system can't fill complete or any of order
		}
		orders.remove(o);
		
	}

	private class WorkerOrder {

		int orderNumber;
		Map<String, Integer> itemsToFind;

		WorkerOrder(int oNum, Map<String, Integer> items) {
			orderNumber = oNum;
			itemsToFind = items;
		}
	}

}
