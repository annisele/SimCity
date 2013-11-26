package simcity.buildings.market;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;

import simcity.Directory;
import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.market.MarketCustomerGui;
import simcity.interfaces.market.MarketCashier;
import simcity.interfaces.market.MarketCustomer;

public class MarketCustomerRole extends Role implements MarketCustomer {

	private enum CustomerState {doingNothing, orderSent, billed, paid, delivered};
	private MarketSystem market;
	private Semaphore atDest = new Semaphore(0, true);
	private Map<String, Map<String, Boolean>> marketStock = Collections.synchronizedMap(new HashMap<String, Map<String, Boolean>>());
	private Map<String, Integer> itemsToBuy = null;
	private Map<String, Integer> itemsDelivered = null;
	private CustomerState state = CustomerState.doingNothing;
	private double change = 0;
	private int payment;
	private int orderNumber;
	public MarketCustomerRole(PersonAgent p) {
		person = p;
		this.gui = new MarketCustomerGui(this);
		List<String> markets = Directory.getMarkets();
		for(String m : markets) {
			Map<String, Boolean> stock = Collections.synchronizedMap(new HashMap<String, Boolean>());
			stock.put("steak", true);
			stock.put("chicken", true);
			stock.put("salad", true);
			stock.put("pizza", true);
			marketStock.put(m, stock);
		}
	}
	
	@Override
	public void atDestination() {
		atDest.release();
	}

	@Override
	public void msgPleasePay(MarketCashier c, double payRequested, int orderNum) {
		person.Do("Received msgPleasePay");
	
		orderNumber = orderNum;
		payment = 0;
		
		Set<String> keys = itemsToBuy.keySet();
		for (String key : keys) {
			payment += itemsToBuy.get(key) * market.getComputer().getPrices().get(key);
		}
		
		if(payment >= payRequested) {
			state = CustomerState.billed;
		}
		else {
			Do("You billed me too much!");
		}
		stateChanged();
	}

	@Override
	public void msgDeliveringOrder(Map<String, Integer> itemsToDeliver, double ch) {
		person.Do("Received msgDeliveringOrder");
		itemsDelivered = itemsToDeliver;
		change = ch;
		state = CustomerState.delivered;
		Set<String> keys = itemsToBuy.keySet();
		for (String key : keys) {
			if(!itemsDelivered.containsKey(key)) {
				marketStock.get(market.getName()).put(key, false);
			}
		}
		
		stateChanged();
	}
	
	public boolean pickAndExecuteAnAction() {
		if(itemsToBuy != null && state == CustomerState.doingNothing) {
			System.out.println(state);
			SendOrder();
			return true;
		}
		
		if(state == CustomerState.billed) {
			PayCashier();
			return true;
		}
		else if(state == CustomerState.delivered) {
			ReceiveDelivery();
			return true;
		}
		return false;
	}

	private void SendOrder() {
		market.getCashier().msgHereIsAnOrder(this, this, itemsToBuy);
		state = CustomerState.orderSent;
	}

	private void PayCashier() {
		((MarketCustomerGui)gui).DoGoToCashier();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		person.subtractMoney(payment);
		market.getCashier().msgHereIsPayment(payment, orderNumber);
		state = CustomerState.paid;
	}

	private void ReceiveDelivery() {
		((MarketCustomerGui)gui).DoGoToCashier();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		((MarketCustomerGui) gui).carryItem(true);
		market.getCashier().msgReceivedOrder();
		person.receiveDelivery(itemsDelivered);
		person.addMoney(change);
		change = 0;
		itemsToBuy = null;
		itemsDelivered = null;
		state = CustomerState.doingNothing;
		exitBuilding();
	}
	

	@Override
	public void exitBuilding() {
		person.Do("Leaving market.");
		gui.DoExitBuilding();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		((MarketCustomerGui) gui).carryItem(false);
		market.exitBuilding(this);
		person.roleFinished();		
	}

	@Override
	public void enterBuilding(SimSystem s) {
		market = (MarketSystem)s;
		//hack!
		itemsToBuy = new HashMap<String, Integer>();
		itemsToBuy.put("chicken", 2);
		((MarketCustomerGui)gui).DoGoToCashier();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
