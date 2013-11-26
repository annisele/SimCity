package simcity.buildings.market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.market.MarketCashierGui;
import simcity.interfaces.market.MarketCashier;
import simcity.interfaces.market.MarketCustomer;
import simcity.interfaces.market.MarketOrderer;
import simcity.interfaces.market.MarketPayer;
import simcity.interfaces.market.MarketWorker;

public class MarketCashierRole extends Role implements MarketCashier {
	private List<MarketOrder> orders = Collections.synchronizedList(new ArrayList<MarketOrder>());
	private MarketComputer computer;
	private MarketSystem market;
	private enum MarketOrderState {requested, waitingForPayment, paid, filling, found};
	private Map<String, Double> prices = Collections.synchronizedMap(new HashMap<String, Double>());
	private Semaphore atDest = new Semaphore(0, true);
	private int numObjectsOnCounter = 0;
	private class MarketOrder {
		int orderNumber;
		MarketOrderer deliverRole;
		MarketPayer payRole;
		Map<String, Integer> items;
		double payment;
		MarketOrderState state;

		MarketOrder(int oNum, MarketOrderer rD, MarketPayer rP, Map<String, Integer> it, MarketOrderState s) {
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

	@Override
	public void msgHereIsAnOrder(MarketOrderer mc1, MarketPayer mc2, Map<String, Integer> items) {
		orders.add(new MarketOrder(orders.size(), mc1, mc2, items, MarketOrderState.requested));
		stateChanged();
	}

	@Override
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

	@Override
	public void msgOrderFound(int orderNum) {
		((MarketCashierGui) gui).addItemToCounter();
		synchronized (orders) {
			for(MarketOrder o : orders) {
				if(o.orderNumber == orderNum) {
					o.state = MarketOrderState.found;
				}
			}
		}
		stateChanged();
	}
	
	public void msgReceivedOrder() {
		((MarketCashierGui) gui).removeItemFromCounter();
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

	private void SendBill(MarketOrder o) {
		person.Do("Sending bill to customer");

		Set<String> keys = o.items.keySet();
		for (String key : keys) {
			o.payment += o.items.get(key) * prices.get(key);
		}

		//hack!!
		o.payment = 0;
		o.payRole.msgPleasePay(this, o.payment, o.orderNumber);
		o.state = MarketOrderState.waitingForPayment;
	}

	//errors - copied straight from design docs
	private void FillOrder(MarketOrder o) {
		person.Do("Asking a worker to fill order.");
		computer.addMoney(o.payment);
		//.getNext() is a stub for load balancing
		if(market.getWorkers().isEmpty()) {
			System.out.println("No workers to collect order.");
		}
		else {
			//hack! need to load balance
			market.getWorkers().get(0).msgFindOrder(o.orderNumber, o.items);
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
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		person.Do("Giving items to customer");
		//.getNext() is a stub for load balancing
		if(o.deliverRole instanceof MarketCustomer) {
			o.deliverRole.msgDeliveringOrder(o.items);
		}
		else {
			//o.deliverRole.msgOrderWillBeDelivered(o.items);
			//trucks.getNext().msgPleaseDeliverOrder(o.deliverRole, o.items);

			if(market.getTrucks().isEmpty()) {
				System.out.println("No trucks to deliver order.");
			}
			else {
				//hack! load balance
				market.getTrucks().get(0).msgPleaseDeliverOrder(o.deliverRole, o.items);
			}
		}
		orders.remove(o);
	}

	@Override
	public void msgExitBuilding() {
		((MarketCashierGui)gui).DoGoToLeftTop();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		((MarketCashierGui)gui).DoGoToLeftCenter();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		((MarketCashierGui)gui).DoGoToCenter();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		gui.DoExitBuilding();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		person.Do("Leaving market.");
		market.exitBuilding(this);
		person.roleFinished();
		person.isIdle();

	}

	@Override
	public void msgEnterBuilding(SimSystem s) {
		market = (MarketSystem)s;
		((MarketCashierGui)gui).DoGoToCenter();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		((MarketCashierGui)gui).DoGoToLeftCenter();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		((MarketCashierGui)gui).DoGoToLeftTop();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		((MarketCashierGui)gui).DoGoToCashRegister();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}



}
