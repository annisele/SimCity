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
import simcity.gui.restauranttwo.RestaurantTwoCashierGui;
import simcity.gui.restauranttwo.RestaurantTwoHostGui;
import simcity.interfaces.market.MarketCashier;
import simcity.interfaces.restaurant.two.RestaurantTwoCustomer;
//import restaurant.interfaces.Market;
import simcity.interfaces.restaurant.two.RestaurantTwoWaiter;
import simcity.Directory;
import simcity.PersonAgent;
//import simcity.test.mock.LoggedEvent;
import simcity.Role;
import simcity.SimSystem;

public class RestaurantTwoCashierRole extends Role implements simcity.interfaces.restaurant.two.RestaurantTwoCashier{//implements simcity.interfaces.restaurant.one.RestaurantOneCashier {

	
	 private RestaurantTwoWaiter wait;
	private RestaurantTwoCustomer cust;
	private RestaurantTwoSystem R2;
	private RestaurantTwoComputer computer;
	private MarketCashier marketCashier;
	private Semaphore atDest = new Semaphore(0, true);

	
	private boolean in_debt=false;
	private boolean pay_market=false;
	public List<order> cashiers_list
	= Collections.synchronizedList(new LinkedList<order>());
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
	
	public RestaurantTwoCashierRole(PersonAgent p, RestaurantTwoSystem r, RestaurantTwoComputer r2comp){
		super();
		this.person=p;
		this.gui=new RestaurantTwoCashierGui(this);
		this.R2=r;
		this.computer=r2comp;
		
		
		
	}
	public void modBalance(double b){
		//hack
		computer.balance=b;
		Do("mod balence: "+computer.balance);
	}
	

   public void msgCustomerOrder(RestaurantTwoWaiter w,RestaurantTwoCustomer c,int t, String ch) {
           Do("Recieved "+c+"'s "+ch+" order");
           order o= new order(w, c,t,ch);
           o.state= OrderState.adding;
           Do("order: "+o.choice+" cust: "+o.cust);
           cashiers_list.add(o);
           stateChanged();
   }
  
   public double msgGetCheck(RestaurantTwoCustomer c){
           Do("Giving check to waiter");
           for(order current: cashiers_list){
                   if(current.cust==c){
                           return current.payment;
                   }
   }
           return 0;
   }
	/*msgPleasePay(marketcashier,double payment, order num)
	 */
	 
   public void msgPleasePay(String marketName, double payment,
			int orderNum) {
		double temp=0;
		Do("Recieved bill from market");

		//log.add(new LoggedEvent("Recieved bill"));
		if(computer.getMoney()>=payment){

			synchronized(computer.markets){

				for(int i=0; i<computer.markets.size();i++){

					if(computer.markets.get(i).name.equals(marketName)){
						computer.markets.get(i).ordernum=num;
						computer.subtractMoney(payment);

						temp=payment;
						DecimalFormat fr =new DecimalFormat("##.00");
						String formate=fr.format(temp);
						try {
							computer.markets.get(i).bill=(Double)fr.parse(formate);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						pay_market=true;
						Do("Paying "+computer.markets.get(i).bill+" in full.");
						stateChanged();
					}
				}
			}
		}
		else{

			synchronized(computer.markets){
				for(int j=0; j<computer.markets.size();j++){
					if(computer.markets.get(j).name.equals(marketName)){
						Do("CURRENT BAL: "+computer.getMoney());
						temp= payment-computer.getMoney();
						DecimalFormat f =new DecimalFormat("##.00");
						String formate=f.format(temp);
						try {
							computer.markets.get(j).bill=(Double)f.parse(formate);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						computer.markets.get(j).debt= computer.markets.get(j).bill;
						Do("Cashier is in debt!");
						Do("Cashier owes $"+computer.markets.get(j).debt);
						temp= computer.getMoney();
						computer.subtractMoney(temp);
						in_debt=true;
						stateChanged();
					}
				}
			}
		}

	}
	public void msgHereIsMoney(RestaurantTwoCustomer c, double m){
		Do("Recieved money from cust");
		//log.add(new LoggedEvent("Recieved money"));
		Do("checking payment");
		synchronized(cashiers_list){
		for(order current: cashiers_list){
			if(current.cust==c){
				if(current.payment==m){
					current.state= OrderState.paid;
					computer.addMoney(m);
					Do("Customer has paid in full");
					stateChanged();
				}
				else{
					Do("wrong amount");
				current.state= OrderState.indebt;
				computer.addMoney(m);
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
		//Do("cahsier state");
		if(pay_market==true){

			synchronized(computer.markets){
				for(int i=0; i<computer.markets.size();i++){
					if(computer.markets.get(i).bill>0){
						sendMoney(computer.markets.get(i).name,computer.markets.get(i).bill,computer.markets.get(i).ordernum );
						return true;
					}
				}
			}
		}

		if(!computer.owed_markets.isEmpty()){
			if(computer.checkMarket() != null){
				String mName=computer.checkMarket();
				for(int j=0; j<computer.markets.size();j++){
					if(computer.markets.get(j).name.equals(mName)){

						sendMoney(mName,computer.markets.get(j).bill,computer.markets.get(j).ordernum);
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
					cashiers_list.remove(o);
					return true;
				}
			}

		}
		return false;
	}

	// Actions
	
	
	private void CreateCheck(order o){
		//cust.msgTEST();
		 try {
	o.payment=computer.Menu.get(o.choice);
		 }
	catch (NullPointerException e) {
			e.printStackTrace();
			
		}
	Do("creating $"+o.payment+" check for "+o.cust);
	o.waiter.msgHereIsCheck(o.table,o.payment);
	o.state= OrderState.waiting;
	//stateChanged(
			}
		
	private void paid(RestaurantTwoCustomer c){
		c.msgGoodbye();
	}
	private void sendMoney(String market, double bill, int num){
		((MarketSystem)Directory.getSystem(market)).getCashier().msgHereIsPayment(bill, num);
	//	m.msgHereIsMoney(b);
		pay_market=false;
		Do("Money has been sent to market");
	}
	private void KeepTrack(order o){
		computer.bad_orders.add(o);
		Do("Customer "+ o.cust+ "is in debt");
		
	}



	
	
	
	 

	// The animation DoXYZ() routines
	

	//utilities
	
	
	
	public void printMarket() {
		//System.out.println("her "+ markets.size());
	}
	

	public void atDestination() {
		atDest.release();
	}


			@Override
			public void exitBuilding() {
				// TODO Auto-generated method stub
				
			}


			@Override
			public void enterBuilding(SimSystem s) {
				System.out.println("cashier enters building");
				// TODO Auto-generated method stub
				R2 = (RestaurantTwoSystem)s;
				((RestaurantTwoCashierGui)gui).DoGoToCenter();
				try {
					atDest.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			
			}

		

		

		
}