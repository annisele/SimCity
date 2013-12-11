/*package simcity.buildings.restaurant.four;

public class RestaurantFourCashierRole {

	package restaurant;

	import agent.Agent;
	import restaurant.interfaces.*;
	import restaurant.gui.CashierGui;
	import restaurant.Bill;
	import restaurant.Bill.billState;
	import restaurant.Stats;


	import java.util.*;

	////////////////////////////////////////////////////////////////////////////////

	/**
	 * Restaurant Cashier Agent
	 */
/*
	public class CashierAgent extends Agent implements Cashier {

		// variables
		private Stats stats = new Stats();
		
		Timer timer = new Timer();
		private String name;
		private CashierGui gui;
		private double cashOnHand;

		public List<Bill> bills = Collections.synchronizedList(new ArrayList<Bill>());
		public List<Bill> marketBills = Collections.synchronizedList(new ArrayList<Bill>());
		
		// constructors /////////////////////////////////////////////////////////////////////
		
		public CashierAgent(String name) {
			super();
			this.name = name;
			this.cashOnHand = stats.getCashOnHand();
		}

		// messages ///////////////////////////////////////////////////////////////////

		public void msgCustomerWantsBill(Customer cust, Waiter wait, String food) {
			bills.add(new Bill(cust, wait, food));
			stateChanged();
		}
		
		public void msgHereIsPayment(Customer cust, double payment) {
			synchronized(bills) {
				for (Bill bill : bills) {
					if (bill.getCustomer() == cust) {
						if (payment == 0) {
							bill.setState(billState.cantPay);
							stateChanged();
						}
						else {
							cashOnHand = cashOnHand + payment;
							bill.setState(billState.paid);
							stateChanged();
						}
					}
				}
			}
		}
		
		public void msgHereIsMarketBill(Market market, double payment) {
			marketBills.add(new Bill(market, payment));
			stateChanged();
		}
		*/
		/**
		 * Scheduler.  Determine what action is called for, and do it.
		 */
		/*
		public boolean pickAndExecuteAnAction() {
			
			if (!marketBills.isEmpty()) {
				payMarketBill(marketBills.get(0));
				return true;
			}
			
			synchronized(bills) {
				for (Bill bill : bills) {
					if (bill.state == billState.paid) {
						bill.setState(billState.done);
						thankCustomer(bill);
						return true;
					}
				}
				
			}

			synchronized(bills) {
				for (Bill bill : bills) {
					if (bill.state == billState.cantPay) {
						bill.setState(billState.done);
						warnCustomer(bill);
						return true;
					}
				}
			}

			synchronized(bills) {
				for (Bill bill : bills) {
					if (bill.state == billState.notified) {
						bill.state = billState.processing;
						giveBillToWaiter(bill);
						return true;
					}
				}
			}

			return false;
		}

		// actions ////////////////////////////////////////////////////////////////////////////

		private void giveBillToWaiter(Bill b) {
			Do("Customer " + b.getCustomer().getName() + "'s bill has a total of $" + b.getPrice());
			b.getWaiter().msgBillTotal(b.getCustomer(), b.getPrice());
		}
		
		private void thankCustomer(Bill b) {
			Do("Thank you very much " + b.getCustomer().getName());
			Do("Current restaurant cash = $" + cashOnHand);
			b.getCustomer().msgYouAreGoodToGo();
			bills.remove(b);
		}
		
		private void warnCustomer(Bill b) {
			Do("Please remember to pay next time!");
			b.getCustomer().msgYouAreGoodToGo();
			bills.remove(b);
		}
		
		private void payMarketBill(Bill b) {
			Do("Paying " + b.getMarket().getName() + " a total of $" + b.getPrice());
			cashOnHand = cashOnHand - b.getPrice();
			Do("Current restaurant cash = $" + cashOnHand);
			b.getMarket().msgHereIsRestaurantPayment(b.getPrice());
			marketBills.remove(b);
		}
		
		// utilities ////////////////////////////////////////////////////////////////////////

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public CashierGui getGui() {
			return this.gui;
		}
		
		public void setGui(CashierGui gui) {
			this.gui = gui;
		}
		
		public double getCashOnHand() {
			return cashOnHand;
		}
	}


	
}*/
