package simcity.buildings.market;

import java.util.*;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.gui.market.MarketCashierGui;
import simcity.gui.market.MarketWorkerGui;
import simcity.interfaces.market.MarketCashier;

public class MarketWorkerRole extends Role implements simcity.interfaces.market.MarketWorker {
	private MarketComputer computer;
	private List<WorkerOrder> orders = Collections.synchronizedList(new ArrayList<WorkerOrder>());
	private MarketCashier cashier;
	private Semaphore atDest = new Semaphore(0, true);
	
	public MarketWorkerRole(PersonAgent p) {
		person = p;
		this.gui = new MarketWorkerGui(this);
	}
	
	@Override
	public void atDestination() {
		atDest.release();
	}
	
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
		
		((MarketWorkerGui)gui).DoGoToDropOffItems();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(computer == null) System.out.println("computer null");
		if(o == null) System.out.println("order is null");
		o.itemsToFind = computer.fillOrder(o.itemsToFind); //gets full order from system
		if(o.itemsToFind != null) {
			((MarketCashierRole)cashier).msgOrderFound(o.orderNumber);
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
	public void msgEnterBuilding() {
		((MarketWorkerGui)gui).DoGoToHomePosition();
		//HACK - this should be here but doesn't work with it
//		try {
//			atDest.acquire();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
	}
	
	public void setCashier(MarketCashier c) {
		cashier = c;
	}
	
	public void setComputer(MarketComputer com) {
		computer = com;
	}

}
