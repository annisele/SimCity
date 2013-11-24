package simcity.buildings.market;

import java.util.*;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.gui.market.MarketCashierGui;
import simcity.gui.market.MarketCustomerGui;
import simcity.interfaces.market.MarketCustomer;
import simcity.interfaces.market.MarketWorker;
import sun.tools.tree.SuperExpression;

public class MarketCashierRole extends Role implements simcity.interfaces.market.MarketCashier {
	private List<MarketOrder> orders = Collections.synchronizedList(new ArrayList<MarketOrder>());
	private MarketComputer computer;
	private enum MarketOrderState {requested, waitingForPayment, paid, filling, found};
	private Map<String, Double> prices = Collections.synchronizedMap(new HashMap<String, Double>());
	private List<MarketWorker> workers = Collections.synchronizedList(new ArrayList<MarketWorker>());
	private List<MarketTruckAgent> trucks = Collections.synchronizedList(new ArrayList<MarketTruckAgent>());
	private Semaphore atDest = new Semaphore(0, true);
	private class MarketOrder {
		int orderNumber;
		Role deliverRole;
		Role payRole;
		Map<String, Integer> items;
		double payment;
		MarketOrderState state;

		MarketOrder(int oNum, Role rD, Role rP, Map<String, Integer> it, MarketOrderState s) {
			orderNumber = oNum;
			deliverRole = rD;
			payRole = rP;
			items = it;
			state = s;
		}
	}

	public MarketCashierRole(PersonAgent p) {
		person = p;
		this.gui = new MarketCashierGui(this);

		//hack
		prices.put("chicken", 5.0);
		prices.put("steak", 10.0);

		//hack?
		computer = new MarketComputer();
	}

	@Override
	public void atDestination() {
		atDest.release();
	}

	public void msgHereIsAnOrder(MarketCustomerRole mc1, MarketCustomerRole mc2, Map<String, Integer> items) {
		orders.add(new MarketOrder(orders.size(), mc1, mc2, items, MarketOrderState.requested));
		stateChanged();
	}

	public void msgHereIsPayment(double payment, int oNum) {
		synchronized (orders) {
			for(MarketOrder o : orders) {
				if(o.orderNumber == oNum) {
					o.state = MarketOrderState.paid;
				}
			}
		}
		stateChanged();
	}

	public void msgOrderFound(int orderNum) {
		synchronized (orders) {
			for(MarketOrder o : orders) {
				if(o.orderNumber == orderNum) {
					o.state = MarketOrderState.found;
				}
			}
		}
		stateChanged();
	}

	public boolean pickAndExecuteAnAction() {
		synchronized (orders) {
			for(MarketOrder o : orders) {
				if(o.state == MarketOrderState.requested) {
					SendBill(o);
					return true;
				}
			}
		}
		synchronized (orders) {
			for(MarketOrder o : orders) {
				if(o.state == MarketOrderState.paid) {
					FillOrder(o);
					return true;
				}
			}
		}
		synchronized (orders) {
			for(MarketOrder o : orders) {
				if(o.state == MarketOrderState.found) {
					DeliverOrder(o);
					return true;
				}
			}
		}
		return false;
	}

	//errors - copied straight from design docs
	private void SendBill(MarketOrder o) {
		person.Do("Sending bill to customer");

		Set<String> keys = prices.keySet();
		for (String key : keys) {
			o.payment += o.items.get(key) * prices.get(key);
		}

		//hack!!
		o.payment = 0;
		if(o.payRole instanceof MarketCustomer) {
			((MarketCustomerRole)o.payRole).msgPleasePay(this, o.payment, o.orderNumber);
		}
		else {
			//deal with different types of restaurant cooks
		}
		o.state = MarketOrderState.waitingForPayment;
	}

	//errors - copied straight from design docs
	private void FillOrder(MarketOrder o) {
		person.Do("Asking a worker to fill order.");
		computer.addMoney(o.payment);
		//.getNext() is a stub for load balancing
		if(workers.isEmpty()) {
			System.out.println("No workers to collect order.");
		}
		else {
			//hack! shouldn't cast like this
			((MarketWorkerRole) workers.get(0)).msgFindOrder(o.orderNumber, o.items);
		}

		o.state = MarketOrderState.filling;
	}

	//errors - copied straight from design docs
	private void DeliverOrder(MarketOrder o) {

		((MarketCashierGui)gui).DoGoToCounter();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		((MarketCashierGui)gui).DoGoToCashRegister();
		
		//for some reason if this is here is never acquires??!?? program gets stuck here
//		try {
//			atDest.acquire();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		person.Do("Giving items to customer");
		//.getNext() is a stub for load balancing
		if(o.deliverRole instanceof MarketCustomerRole) {
			//hack!! shouldn't cast like this
			((MarketCustomerRole)o.deliverRole).msgDeliveringOrder(o.items);
		}
		else {
			//o.deliverRole.msgOrderWillBeDelivered(o.items);
			//trucks.getNext().msgPleaseDeliverOrder(o.deliverRole, o.items);
		}
		orders.remove(o);
	}

	@Override
	public void msgExitBuilding() {
		// TODO Auto-generated method stub

	}

	//PROBLEM!!!! Semaphore is never released

	@Override
	public void msgEnterBuilding() {
		((MarketCashierGui)gui).DoGoToCashRegister();
		//HACK - this should be here but doens't work with it
		//		try {
		//			atDest.acquire();
		//		} catch (InterruptedException e) {
		//			e.printStackTrace();
		//		}

	}

	public void addWorker(MarketWorker w) {
		workers.add(w);
		((MarketWorkerRole) w).setCashier(this);
		((MarketWorkerRole) w).setComputer(computer);
	}




}
