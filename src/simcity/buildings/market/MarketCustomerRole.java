package simcity.buildings.market;

import java.util.*;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.gui.market.MarketCustomerGui;
import simcity.gui.transportation.PedestrianGui;
import simcity.interfaces.market.MarketCashier;

public class MarketCustomerRole extends Role implements simcity.interfaces.market.MarketCustomer {

	private List<Invoice> invoices = Collections.synchronizedList(new ArrayList<Invoice>());
	private enum InvoiceState {expected, billed, paid, delivered};
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
	public void msgBuyStuff(Map<String, Integer> itemsToBuy, MarketSystem m) {
		invoices.add(new Invoice(InvoiceState.expected, itemsToBuy, invoices.size()));
		market = m;
	}

	public void msgPleasePay(MarketCashierRole c, double payment, int orderNum) {
		synchronized (invoices) {
			for(Invoice i : invoices) {
				if(i.state == InvoiceState.expected && i.payment == payment) {
					i.state = InvoiceState.billed;
					i.cashier = c;
					i.orderNumber = orderNum;
				}
			}
		}
	}

	public void msgDeliveringOrder(Map<String, Integer> itemsToDeliver) {
		synchronized (invoices) {
			for(Invoice i : invoices) {
				if(i.state == InvoiceState.paid && i.items == itemsToDeliver) {
					i.state = InvoiceState.delivered;
				}
			}
		}
	}
	
	//HACK!!
	public void msgWait() {
		System.out.println("Waiting..");
		//invoices.clear();
	}
	
	
	public boolean pickAndExecuteAnAction() {
		synchronized (invoices) {
			for(Invoice i : invoices) {
				if(i.state == InvoiceState.expected) {
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
		((MarketCustomerGui)gui).DoGoToCashier();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		market.getCashier().msgHereIsAnOrder(this, this, i.items);
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
		Map<String, Integer> tempItems = i.items;       
		invoices.remove(i);
		person.receiveDelivery(tempItems);
		msgExitBuilding();
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
		}
	}

	@Override
	public void msgExitBuilding() {
		gui.DoExitBuilding();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		market.exitBuilding(this);
		person.roleFinished();
		person.isIdle();
		
	}

	@Override
	public void msgEnterBuilding() {
		// TODO Auto-generated method stub
		
	}
}
