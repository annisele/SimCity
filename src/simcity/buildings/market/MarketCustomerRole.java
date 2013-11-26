package simcity.buildings.market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.market.MarketCustomerGui;
import simcity.interfaces.market.MarketCashier;
import simcity.interfaces.market.MarketCustomer;
import simcity.interfaces.market.MarketPayer;

public class MarketCustomerRole extends Role implements MarketCustomer {

	private List<Invoice> invoices = Collections.synchronizedList(new ArrayList<Invoice>());
	private enum InvoiceState {notSent, expected, requested, billed, paid, delivered};
	private MarketSystem market;
	private Semaphore atDest = new Semaphore(0, true);
	
	public MarketCustomerRole(PersonAgent p) {
		person = p;
		this.gui = new MarketCustomerGui(this);
	}
	
	@Override
	public void atDestination() {
		atDest.release();
	}
	
	@Override
	public void msgBuyStuff(Map<String, Integer> itemsToBuy) {
		invoices.add(new Invoice(InvoiceState.notSent, itemsToBuy, invoices.size()));
		stateChanged();
	}

	@Override
	public void msgPleasePay(MarketCashier c, double payment, int orderNum) {
		person.Do("Received msgPleasePay");
		
		synchronized (invoices) {
			for(Invoice i : invoices) {
				if(i.state == InvoiceState.expected && i.payment == payment) {
					i.state = InvoiceState.billed;
					i.cashier = c;
					i.orderNumber = orderNum;
				}
			}
		}
		stateChanged();
	}

	@Override
	public void msgDeliveringOrder(Map<String, Integer> itemsToDeliver) {
		person.Do("Received msgDeliveringOrder");
		
		synchronized (invoices) {
			for(Invoice i : invoices) {
				if(i.state == InvoiceState.paid && i.items == itemsToDeliver) {
					i.state = InvoiceState.delivered;
				}
			}
		}
		stateChanged();
	}
	
	public boolean pickAndExecuteAnAction() {
		synchronized (invoices) {
			for(Invoice i : invoices) {
				if(i.state == InvoiceState.notSent) {
					SendOrder(i);
					return true;
				}
			}
		}
		synchronized (invoices) {
			for(Invoice i : invoices) {
				if(i.state == InvoiceState.billed) {
					PayCashier(i);
					return true;
				}
			}
		}
		synchronized (invoices) {
			for(Invoice i : invoices) {
				if(i.state == InvoiceState.delivered) {
					ReceiveDelivery(i);
					return true;
				}
			}
		}
		return false;
	}

	private void SendOrder(Invoice i) {
		market.getCashier().msgHereIsAnOrder(this, this, i.items);
		i.state = InvoiceState.expected;
	}

	private void PayCashier(Invoice i) {
		((MarketCustomerGui)gui).DoGoToCashier();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.person.money -= i.payment;
		i.cashier.msgHereIsPayment(i.payment, i.orderNumber);
		i.state = InvoiceState.paid;
	}

	private void ReceiveDelivery(Invoice i) {
		((MarketCustomerGui)gui).DoGoToCashier();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		((MarketCustomerGui) gui).carryItem(true);
		Map<String, Integer> tempItems = i.items;  
		i.cashier.msgReceivedOrder();
		invoices.remove(i);
		person.receiveDelivery(tempItems);
		msgExitBuilding();
	}
	

	@Override
	public void msgExitBuilding() {
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
		person.isIdle();
		
	}

	@Override
	public void msgEnterBuilding(SimSystem s) {
		market = (MarketSystem)s;
		((MarketCustomerGui)gui).DoGoToCashier();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	private class Invoice {
		double payment;
		InvoiceState state;
		MarketCashier cashier;
		Map<String, Integer> items;
		int orderNumber;

		Invoice(InvoiceState s, Map<String, Integer> itemsToBuy, int num) {
			items = itemsToBuy;
			state = s;
			orderNumber = num;
			
			//hack!!
			payment = 0;
		}
	}

}
