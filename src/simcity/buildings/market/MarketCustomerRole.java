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
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
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
	public void msgPleasePay(String marketName, double payRequested, int orderNum) {	
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
			AlertLog.getInstance().logError(AlertTag.valueOf(market.getName()), "MarketCustomer: " + person.getName() , "You billed me for more than I bought.");
		}
		stateChanged();
	}

	@Override
	public void msgHereAreItems(Map<String, Integer> itemsToDeliver, double ch) {
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
		AlertLog.getInstance().logMessage(AlertTag.valueOf(market.getName()), "MarketCustomer: " + person.getName(), "Here is my order.");
		market.getCashier().msgHereIsAnOrder(this, this, itemsToBuy);
		state = CustomerState.orderSent;
	}

	private void PayCashier() {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(market.getName()), "MarketCustomer: " + person.getName(), "Here is the money I owe for my items.");
		((MarketCustomerGui)gui).DoGoToCashier();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		person.subtractMoney(payment);
		market.getCashier().msgHereIsPayment(payment, orderNumber);
		state = CustomerState.paid;
	}

	private void ReceiveDelivery() {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(market.getName()), "MarketCustomer: " + person.getName(), "Thanks for the stuff!");
		((MarketCustomerGui)gui).DoGoToCashier();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
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
		AlertLog.getInstance().logMessage(AlertTag.valueOf(market.getName()), "MarketCustomer: " + person.getName(), "Leaving the market.");
		gui.DoExitBuilding();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		((MarketCustomerGui) gui).carryItem(false);
		market.exitBuilding(this);
		person.roleFinished();		
	}

	@Override
	public void enterBuilding(SimSystem s) {
		market = (MarketSystem)s;
		if (market.getCashier() == null) {
			AlertLog.getInstance().logMessage(AlertTag.valueOf(market.getName()), "MarketCustomer: " + person.getName(), "This market looks closed!");
			
			market.exitBuilding(this);
			person.roleFinished();	
			return;

		}
		AlertLog.getInstance().logMessage(AlertTag.valueOf(market.getName()), "MarketCustomer: " + person.getName(), "Entering the market.");
		//hack!
		itemsToBuy = new HashMap<String, Integer>();
		itemsToBuy = person.getListToBuy();
		((MarketCustomerGui)gui).DoGoToCashier();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		
	}

}
