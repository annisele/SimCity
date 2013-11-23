package simcity.buildings.market;

import java.util.*;

import simcity.PersonAgent;
import simcity.Role;
import simcity.gui.market.MarketCashierGui;
import simcity.gui.market.MarketCustomerGui;
import simcity.interfaces.market.MarketCustomer;
import simcity.interfaces.market.MarketWorker;

public class MarketCashierRole extends Role implements simcity.interfaces.market.MarketCashier {
	private List<MarketOrder> orders = Collections.synchronizedList(new ArrayList<MarketOrder>());
	private MarketComputer market;
	private enum MarketOrderState {requested, waitingForPayment, paid, filling, found};
	private Map<String, Double> prices = Collections.synchronizedMap(new HashMap<String, Double>());
	private List<MarketWorker> workers = Collections.synchronizedList(new ArrayList<MarketWorker>());
	private List<MarketTruckAgent> trucks = Collections.synchronizedList(new ArrayList<MarketTruckAgent>());
	private class MarketOrder {
		int orderNumber;
		Role deliverRole;
		Role payRole;
		Map<String, Integer> items;
		double payment;
		MarketOrderState state;

		MarketOrder(int oNum, Role rD, Role rP, Map<String, Integer> it, MarketOrderState s) {

		}
	}

	public MarketCashierRole(PersonAgent p) {
		person = p;
		this.gui = new MarketCashierGui(this);
	}
	
	public void msgHereIsAnOrder(MarketCustomerRole mc1, MarketCustomerRole mc2, Map<String, Integer> items) {
		//orders.add(new MarketOrder(orders.size(), mc1, mc2, items, MarketOrderState.requested));
		person.Do("hereIsAnOrder");
		mc1.msgWait(); //HACK
	}

	public void msgHereIsPayment(double payment, int oNum) {
		synchronized (orders) {
			for(MarketOrder o : orders) {
				if(o.orderNumber == oNum) {
					o.state = MarketOrderState.paid;
				}
			}
		}
	}

	public void msgOrderFound(int orderNum) {
		synchronized (orders) {
			for(MarketOrder o : orders) {
				if(o.orderNumber == orderNum) {
					o.state = MarketOrderState.found;
				}
			}
		}
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
		Set<String> keys = prices.keySet();
		for (String key : keys) {
			o.payment += o.items.get(key) * prices.get(key);
		}
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
//		market.addMoney(o.payment);
//		//.getNext() is a stub for load balancing
//		workers.getNext().msgFindOrder(o.orderNumber, o.itemsToBuy);
//		o.state = filling;
	}

	//errors - copied straight from design docs
	private void DeliverOrder(MarketOrder o) {
		//		//.getNext() is a stub for load balancing
		//		if(o.deliverRole instanceof MarketCustomerRole) {
		//			o.deliverRole.msgDeliveringOrder(o.itemsToBuy);
		//		}
		//		else {
		//			o.deliverRole.msgOrderWillBeDelivered(o.itemsToBuy);
		//			trucks.getNext().msgPleaseDeliverOrder(o.deliverRole, o.itemsToBuy);
		//		}
		//		orders.remove(o);
	}

	@Override
	public void msgExitBuilding() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgEnterBuilding() {
		// TODO Auto-generated method stub

	}




}
