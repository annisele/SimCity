package simcity.buildings.market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import simcity.Clock;
import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.PersonAgent.EventType;
import simcity.gui.market.MarketCashierGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.market.MarketCashier;
import simcity.interfaces.market.MarketCustomer;
import simcity.interfaces.market.MarketOrderer;
import simcity.interfaces.market.MarketPayer;
import simcity.interfaces.market.MarketWorker;
import simcity.test.mock.EventLog;
import simcity.test.mock.LoggedEvent;

public class MarketCashierRole extends Role implements MarketCashier {
	//I only made variables public for unit testing. They all used to be private!
	public List<MarketOrder> orders = Collections.synchronizedList(new ArrayList<MarketOrder>());
	public MarketSystem market;
	public enum MarketOrderState {requested, waitingForPayment, paid, filling, found};
	public Semaphore atDest = new Semaphore(0, true);
	public int workerIndex = 0;
	public int truckIndex = 0;
	public EventLog log = new EventLog();
	public enum MarketState {running, closed, allWorkersGone};
	public MarketState marketState = MarketState.running;
	Timer timer = new Timer();
	public class MarketOrder {
		int orderNumber;
		MarketOrderer deliverRole;
		MarketPayer payRole;
		Map<String, Integer> items;
		double payment;
		public MarketOrderState state;

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
	}

	@Override
	public void atDestination() {
		atDest.release();
	}
	
	public MarketState getMarketState() {
		return marketState;
	}

	public void msgLeaveWork() {
		marketState = MarketState.allWorkersGone;
		person.scheduleEvent(EventType.Work);
		stateChanged();
	}
	
	@Override
	public void msgHereIsAnOrder(MarketOrderer mc1, MarketPayer mc2, Map<String, Integer> items) {
		synchronized(orders) {
			orders.add(new MarketOrder(orders.size(), mc1, mc2, items, MarketOrderState.requested));
			AlertLog.getInstance().logDebug(AlertTag.valueOf(market.getName()), "MarketCashier: " + person.getName(), 
					"itemsDelivered has size: "+items.size());
		}
		stateChanged();
	}

	@Override
	public void msgHereIsPayment(double payment, int oNum) {
		log.add(new LoggedEvent("Received msgHereIsPayment for order #" + oNum + " for amount " + payment));
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
		((MarketCashierGui) gui).carryItem(false);
	}

	public boolean pickAndExecuteAnAction() {
		MarketOrder order = null;
		synchronized (orders) {
			for(MarketOrder o : orders) {
				if(o.state == MarketOrderState.requested) {
					order = o;
					break;
				}
			}
		}
		if(order != null) {
			SendBill(order);
			return true;
		}
		MarketOrder order2 = null;
		synchronized (orders) {
			for(MarketOrder o : orders) {
				if(o.state == MarketOrderState.paid) {
					order2 = o;
					break;
				}
			}
		}
		if(order2 != null) {
			FillOrder(order2);
			return true;
		}
		MarketOrder order3 = null;
		synchronized (orders) {
			for(MarketOrder o : orders) {
				if(o.state == MarketOrderState.found) {
					order3 = o;
					break;
				}
			}
		}
		if(order3 != null) {
			DeliverOrder(order3);
			return true;
		}
		if(marketState == MarketState.allWorkersGone) {
			exitBuilding();
		}
		return false;
	}

	private void SendBill(MarketOrder o) {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(market.getName()), "MarketCashier: " + person.getName(), "Here is your bill.");
		log.add(new LoggedEvent("Sending bill to customer for order #" + o.orderNumber));

		Set<String> keys = o.items.keySet();

		for (String key : keys) {
			o.payment += o.items.get(key) * market.getComputer().getPrices().get(key);
		}

		Do("Charging: " + o.payment+ " "+market.getName()+"  "+ o.orderNumber);
		o.payRole.msgPleasePay(market.getName(), o.payment, o.orderNumber);
		o.state = MarketOrderState.waitingForPayment;
	}

	private void FillOrder(MarketOrder o) {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(market.getName()), "MarketCashier: " + person.getName(), "Told a worker to fill your order.");
		market.getComputer().addMoney(o.payment);
		if(market.getWorkers().isEmpty()) {
			System.out.println("No workers to collect order.");
			AlertLog.getInstance().logError(AlertTag.valueOf(market.getName()), "MarketCashier: " + person.getName(), "No workers to collect your order.");
		}
		else {
			int tempSize = market.getWorkers().size();
			market.getWorkers().get(workerIndex % tempSize).msgFindOrder(o.orderNumber, o.items);
			workerIndex++;
		}
		o.state = MarketOrderState.filling;
	}

	private void DeliverOrder(MarketOrder o) {

		((MarketCashierGui)gui).DoGoToCounter();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		((MarketCashierGui) gui).removeItemFromCounter();
		((MarketCashierGui) gui).carryItem(true);

		((MarketCashierGui)gui).DoGoToCashRegister();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}

		if(o.deliverRole instanceof MarketCustomer) {
			AlertLog.getInstance().logMessage(AlertTag.valueOf(market.getName()), "MarketCashier: " + person.getName(), 
					"Here are your items.");
			AlertLog.getInstance().logMessage(AlertTag.valueOf(market.getName()), "MarketCashier: " + person.getName(), 
					"Order has size: "+o.items.size());

			o.deliverRole.msgHereAreItems(o.items, 0.0);
		}
		else {
			//o.deliverRole.msgOrderWillBeDelivered(o.items);
			if(market.getTrucks().isEmpty()) {
				AlertLog.getInstance().logError(AlertTag.valueOf(market.getName()), "MarketCashier: " + person.getName(), "No trucks to deliver order.");			}
			else {
				AlertLog.getInstance().logMessage(AlertTag.valueOf(market.getName()), "MarketCashier: " + person.getName(), "Assigning delivery to a market delivery truck.");
				int tempSize = market.getTrucks().size();
				market.getTrucks().get(truckIndex % tempSize).msgPleaseDeliverOrder(o.deliverRole, o.items);
				((MarketCashierGui) gui).carryItem(false);
				truckIndex++;
			}
		}
		synchronized(orders) {
			orders.remove(o);
		}
	}

	@Override
	public void exitBuilding() {
		((MarketCashierGui)gui).DoGoToLeftTop();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}

		((MarketCashierGui)gui).DoGoToLeftCenter();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}

		((MarketCashierGui)gui).DoGoToCenter();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}

		gui.DoExitBuilding();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		AlertLog.getInstance().logMessage(AlertTag.valueOf(market.getName()), "MarketCashier: " + person.getName(), "Leaving the market.");
		market.exitBuilding(this);
		person.roleFinished();
	}

	@Override
	public void enterBuilding(SimSystem s) {
		market = (MarketSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(market.getName()), "MarketCashier: " + person.getName(), "Entering the market.");
		
		timer.schedule(new TimerTask() {
			public void run() {
				List<MarketWorker> workers = market.getWorkers();
				marketState = MarketState.closed;
				AlertLog.getInstance().logDebug(AlertTag.valueOf(market.getName()), "MarketCashier: " + person.getName(), 
						"setting to CLOSED. " + person.getCurrentEventDuration() +
						", " + person.getCurrentEvent().toString());
				for(MarketWorker w : workers) {
					w.msgFinishWorking();
				}
			}
		}, Clock.tenMinutesInMillis(person.getCurrentEventDuration()));
		
		((MarketCashierGui)gui).DoGoToCenter();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}

		((MarketCashierGui)gui).DoGoToLeftCenter();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}

		((MarketCashierGui)gui).DoGoToLeftTop();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}

		((MarketCashierGui)gui).DoGoToCashRegister();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}



}
