package simcity.buildings.restaurant.two;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.Semaphore;

import simcity.buildings.market.MarketSystem;
import simcity.buildings.restaurant.two.RestaurantTwoCustomerRole;
import simcity.buildings.restaurant.two.RestaurantTwoWaiterRole;
import simcity.buildings.restaurant.two.RestaurantTwoCashierRole.OrderState;
import simcity.buildings.restaurant.two.RestaurantTwoCashierRole.mymarket;
import simcity.buildings.restaurant.two.RestaurantTwoCashierRole.order;
import simcity.gui.market.MarketCashierGui;
import simcity.interfaces.restaurant.two.RestaurantTwoCustomer;
//import restaurant.interfaces.Market;
import simcity.interfaces.restaurant.two.RestaurantTwoWaiter;
//import simcity.test.mock.LoggedEvent;
import simcity.Role;
import simcity.SimSystem;
import simcity.buildings.restaurant.one.RestaurantOneCheck.CheckState;
import simcity.interfaces.restaurant.one.RestaurantOneCustomer;

public class RestaurantTwoCashierRole extends Role {//implements simcity.interfaces.restaurant.one.RestaurantOneCashier {

	
	 private RestaurantTwoWaiterRole wait;
	private RestaurantTwoCustomer cust;
	private RestaurantTwoSystem R2;
	private Semaphore atDest = new Semaphore(0, true);
	public double balance;
	public List<mymarket> markets
	= Collections.synchronizedList(new ArrayList<mymarket>());
	public class mymarket{
		public double bill=0;
		MarketSystem m;
		public String name;
		public double debt=0;
		
		mymarket(MarketSystem market){
			m =market;
			name= market.getName();
		}
	}
	private boolean in_debt=false;
	private boolean pay_market=false;
	public List<order> cashiers_list
	= Collections.synchronizedList(new LinkedList<order>());
	public List<order> bad_orders
	= Collections.synchronizedList(new ArrayList<order>());
	private Map<String,Double> Menu= Collections.synchronizedMap (new HashMap<String, Double>());
	public class order{
		int cost;
		String choice;
		RestaurantTwoCustomer cust;
		RestaurantTwoWaiter waiter;
		int table;
		public double payment;
		double debt;
		public OrderState state;
		
		order(RestaurantTwoWaiter w, RestaurantTwoCustomer c, int t, String item){
		waiter=w;
		cust=c;
		table=t;
		choice=item;
		debt=0;
		state=OrderState.adding;
		}
	}
	
	public enum OrderState
	{ nothing, adding, waiting, paid,indebt, getcheck, returnedcheck };
	public int num=0;
	Timer timer = new Timer();
	
	//public void setWaiter(WaiterAgent waitr) {
		//this.waiter = waitr;
	//}
	public void setCustomer(RestaurantTwoCustomer c,RestaurantTwoSystem r) {
		this.cust = c;
		this.R2=r;
	}
	// Messages
	
	public RestaurantTwoCashierRole(){
		super();
		balance=500;
		Menu.put("chicken",10.99);	
		Menu.put("steak",15.99);
		Menu.put("salad",5.99);
		Menu.put("pizza",8.99);
		
	}
	public void modBalance(double b){
		balance=b;
		Do(""+balance);
	}
	/*
	@Override
   public void msgCustomerOrder(Waiter w,Customer c,int t, String ch) {
           Do("Recieved "+c.getCustomerName()+"'s "+ch+" order");
           order o= new order(w, c,t,ch);
           o.state= OrderState.adding;
           Do("order: "+o.choice+" cust: "+o.cust);
           cashiers_list.add(o);
           stateChanged();
   }
   @Override
   public double msgGetCheck(Customer c){
           Do("Giving check to waiter");
           for(order current: cashiers_list){
                   if(current.cust==c){
                           return current.payment;
                   }
   }
           return 0;
   }
	*/public void msgCustomerOrder(RestaurantTwoWaiter w, RestaurantTwoCustomer c,int t, String ch) {
		//Do("Recieved "+((RestaurantTwoCustomerRole) c).getCustomerName()+"'s "+ch+" order");
		//log.add(new LoggedEvent("Recieved order"));
		order o= new order(w, c, t,ch);
		cashiers_list.add(o);
		
		stateChanged();
	}
	
	public void msgHereIsBill(MarketSystem m, Double p){
		double temp=0;
		
		Do("Recieved bill from market");
		
		//log.add(new LoggedEvent("Recieved bill"));
		if(balance>=p){

			synchronized(markets){
	
			for(int i=0; i<markets.size();i++){
		
				if(markets.get(i).m==m){
		balance-=p;
		
		temp=p;
		DecimalFormat fr =new DecimalFormat("##.00");
		String formate=fr.format(temp);
		try {
			markets.get(i).bill=(Double)fr.parse(formate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pay_market=true;
		Do(m.getName()+" has been paid "+markets.get(i).bill+" in full.");
		stateChanged();
		}
			}
			}
		}
		else{

			synchronized(markets){
			for(int j=0; j<markets.size();j++){
				if(markets.get(j).m==m){
					Do(""+balance);
					temp= p-balance;
					DecimalFormat f =new DecimalFormat("##.00");
					String formate=f.format(temp);
					try {
						markets.get(j).bill=(Double)f.parse(formate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					markets.get(j).debt= markets.get(j).bill;
			Do("Cashier is in debt!");
			Do("Cashier owes $"+markets.get(j).debt);
			balance=0;
			in_debt=true;
			stateChanged();
			}
			}
			}
		}
		
	}
	public void msgHereIsMoney(RestaurantTwoCustomer c, Double m){
		Do("Recieved money from cust");
		//log.add(new LoggedEvent("Recieved money"));
		Do("checking payment");
		synchronized(cashiers_list){
		for(order current: cashiers_list){
			if(current.cust==c){
				if(current.payment==m){
					current.state= OrderState.paid;
					balance+=m;
					Do("Customer has paid in full");
					stateChanged();
				}
				else{
					Do("wrong amount");
				current.state= OrderState.indebt;
				balance+=m;
				current.debt=current.payment-m;
				stateChanged();
				}
			}
		}
		}
		//CheckPayment(c,m);
		//stateChanged();
	}
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 * @return 
	 */
	public boolean pickAndExecuteAnAction() {
		/* Think of this next rule as:
           Does there exist a table and customer,
           so that table is unoccupied and customer is waiting.
           If so seat him at the table.
		 */
		if(pay_market==true){
			
			synchronized(markets){
			for(int i=0; i<markets.size();i++){
				if(markets.get(i).bill>0){
		//sentMoney(markets.get(i).m,markets.get(i).bill);
		return true;
		}
		}
		}
		}
		if(in_debt==true){
			synchronized(markets){
		for(mymarket m: markets){
			if(m.debt>0){
				if(balance>m.debt){
					balance-=m.debt;
					Do("$"+m.debt+" has been paid to "+m.name);
					in_debt=false;
					pay_market=true;
					return true;
				}
				if(balance<=m.debt){
					//Do("$$");
				}
			}
			}
		}
		}
		
		synchronized(cashiers_list){  
		for (order o : cashiers_list) {
			if (o.state == OrderState.adding) {
				Do("adding");
				CreateCheck(o);
				return true;
			}
			
			if (o.state == OrderState.paid) {
				paid(o.cust);
				cashiers_list.remove(o);
				return true;
			}
			
			if (o.state == OrderState.indebt) {
				
				KeepTrack(o);
				return true;
			}
		}
		
		}
		
		
		
		
		return false;
	}

	// Actions
	
	
	private void CreateCheck(order o){
		//cust.msgTEST();
	o.payment=Menu.get(o.choice);
	Do("creating $"+o.payment+" check for "+o.cust);
	o.waiter.msgHereIsCheck(o.table,o.payment);
	o.state= OrderState.waiting;
	//stateChanged(
			}
		
	private void paid(RestaurantTwoCustomer c){
		c.msgGoodbye();
	}
	private void sentMoney(MarketSystem m, double b){
		//m.msgHereIsMoney(b);
		pay_market=false;
	}
	private void KeepTrack(order o){
		bad_orders.add(o);
		Do("Customer "+ o.cust+ "is in debt");
		
	}



	
	
	
	 

	// The animation DoXYZ() routines
	

	//utilities
	
	public void addMarket(MarketSystem m){
		mymarket temp= new mymarket(m);
		markets.add(temp);
	}
	
	public void printMarket() {
		System.out.println("her "+ markets.size());
	}
	

	public void atDestination() {
		atDest.release();
	}


			@Override
			public void msgExitBuilding() {
				// TODO Auto-generated method stub
				
			}


			@Override
			public void msgEnterBuilding(SimSystem s) {
				System.out.println("whooo");
				// TODO Auto-generated method stub
				R2 = (RestaurantTwoSystem)s;
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