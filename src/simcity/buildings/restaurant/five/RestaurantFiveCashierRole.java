package simcity.buildings.restaurant.five;

import java.util.*;

import simcity.Role;
import simcity.SimSystem;
import simcity.buildings.restaurant.one.RestaurantOneCheck.CheckState;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.five.RestaurantFiveCashier;
import simcity.interfaces.restaurant.five.RestaurantFiveCustomer;
import simcity.interfaces.restaurant.five.RestaurantFiveWaiter;
import simcity.test.mock.EventLog;


public class RestaurantFiveCashierRole extends Role implements RestaurantFiveCashier {

	public EventLog log = new EventLog();
	private RestaurantFiveMenu menu = new RestaurantFiveMenu();
	private double money;
	public List <Check> checks = Collections.synchronizedList(new ArrayList<Check>());
	public enum CheckState { requested, producing, sent, paying, stillPaying, done };
	public RestaurantFiveSystem restaurant;
	//	public List <MarketBill> marketBills = Collections.synchronizedList(new ArrayList<MarketBill>());
//	public enum BillState { debt, notDebt };
//	private Semaphore atDest = new Semaphore(0, true);
//	public class MarketBill {
//		MarketSystem m;
//		double bill;
//		public BillState s;
//
//		MarketBill(MarketSystem mIn, double bIn, BillState billState) {
//			m = mIn;
//			bill = bIn;
//			s = billState;
//		}
//	}
	public class Check{
		RestaurantFiveWaiter w;
		public RestaurantFiveCustomer c;
		String choice;
		public CheckState s;
		public Double amount;
		public Double amountPaid;

		Check(RestaurantFiveWaiter wIn, RestaurantFiveCustomer cIn, String choiceIn, CheckState sIn) {
			w = wIn;
			c = cIn;
			choice = choiceIn;
			amount = menu.getPrice(choice);
			s = sIn;
		}
	}
	
	
//	@Override
//	public void atDestination() {
//		atDest.release();
//		
//	}
//
//	@Override
//	public void msgPleasePay(String marketName, double payment, int orderNum) {
//		// TODO Auto-generated method stub
//		
//	}
	
	public void msgProduceCheck(RestaurantFiveWaiter w, RestaurantFiveCustomer c, String choice) {
		//adding amount to existing check
		synchronized(checks) {
			for(Check ch : checks) {
				if(ch.c == c) {
					ch.amount = (double)Math.round(ch.amount * 100) / 100;
					Do(c.getName() + " had an existing debt of " + ch.amount + ".");
					ch.choice = choice;
					ch.amount += menu.getPrice(choice);
					ch.w = w;
					ch.amountPaid = 0.0;
					ch.s = CheckState.requested;
					stateChanged();
					return;
				}
			}
		}
		//creating new check
		checks.add(new Check(w, c, choice, CheckState.requested));
		stateChanged();
	}

//	//sent when timer is done
//	public void msgPayCheck(RestaurantFiveCustomer cust, double cash) {
//		log.add(new LoggedEvent("Received msgPayCheck from customer."));
//		synchronized(checks) {
//			for(Check c : checks) {
//				if(c.c == cust) {
//					c.s = CheckState.paying;
//					c.amountPaid = cash;
//					stateChanged();
//					return;
//				}
//			}
//		}
//		stateChanged();
//	}
//
//	public void msgHereIsMarketBill(double billPrice, MarketSystem market) {
//		marketBills.add(new MarketBill(market, billPrice, BillState.notDebt));
//		stateChanged();
//	}


	@Override
	public boolean pickAndExecuteAnAction() {
		synchronized(checks) {
			for(Check c : checks) {
				if(c.s == CheckState.paying) {
					c.s = CheckState.stillPaying;
					ReceivingCheck(c);
					return true;
				}
			}
		}
//		synchronized(marketBills) {
//			for(MarketBill m : marketBills) {
//				if(m.s == BillState.debt) {
//					if(money > 0) {
//						PayMarketBill(m);
//						return true;
//					}
//				}
//			}
//		}
//		synchronized(marketBills) {
//			for(MarketBill m : marketBills) {
//				if(m.s == BillState.notDebt) {
//					PayMarketBill(m);
//					return true;
//				}
//			}
//		}
		synchronized(checks) {
			for(Check c : checks) {
				if(c.s == CheckState.requested) {
					c.s = CheckState.producing;
					ProduceCheck(c);
					return true;
				}
			}
		}
		return false;
	}

	private void ProduceCheck(Check c) {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantFiveCashier: " + person.getName(), "Sending check for " + c.c.getName() + " to waiter.");
		//c.w.msgHereIsCheck(c.c, c.amount);
		//TODO
		c.s = CheckState.sent;
	}

//	private void PayMarketBill(MarketBill mb) {
//		if(money >= mb.bill) {
//			mb.m.msgHereIsPayment(mb.bill);
//			money -= mb.bill;
//			marketBills.remove(mb);
//		}
//		else {
//			mb.bill -= money;
//			log.add(new LoggedEvent("Can't pay market fully."));
//			Do("Couldn't pay market full amount. Paid $" + money + ". Owe $" + mb.bill);
//			mb.s = BillState.debt;
//			mb.m.msgHereIsPayment(money);
//			//mb.m.msgCannotPayBill(money);
//			money = 0;
//		}
//	}

	private void ReceivingCheck(Check c) {
		c.s = CheckState.done;
		if(c.amountPaid < c.amount) {
			if(c.amountPaid > 0) {
				money += c.amountPaid;
			}
			c.amount -= c.amountPaid;
			money = (double)Math.round(money * 100) / 100;
			Do("Customer can't pay full check. Money = " + money);
			//log.add(new LoggedEvent("Customer can't pay full check."));
			c.c.msgPayNextTime();
		}
		else {
			money += c.amount;
			money = (double)Math.round(money * 100) / 100;
			Do("Customer is paying full check. Money = " + money);
			//c.c.msgHereIsChange(c.amountPaid - c.amount);
			//TODO
			checks.remove(c);
		}
	}

	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgPleasePay(String marketName, double payment, int orderNum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		
	}

}
