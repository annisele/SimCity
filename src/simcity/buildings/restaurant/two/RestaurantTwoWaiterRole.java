package simcity.buildings.restaurant.two;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.interfaces.restaurant.two.*;
import simcity.gui.restauranttwo.*;


public class RestaurantTwoWaiterRole extends Role implements simcity.interfaces.restaurant.two.RestaurantTwoWaiter{
	static final int NTABLES = 3;//a global for the number of tables.
	//Notice that we implement waitingCustomers using ArrayList, but type it
	//with List semantics.

	//note that tables is typed with Collection semantics.
	//Later we will see how it is implemented
public List <mycustomer>customers= Collections.synchronizedList(new ArrayList<mycustomer>());
public Map<String,Double> Menu= new HashMap<String, Double>();
private RestaurantTwoHost host;
private RestaurantTwoCook cook;
private RestaurantTwoCashier cashier;
private RestaurantTwoSystem R2;
private RestaurantTwoComputer computer;
private Semaphore atDest = new Semaphore(0, true);
private boolean Dest=false;
enum CustomerState{
	waiting,seated,asked, readytoorder, askedtoorder,ordered, 
	waitingfororder, mustreorder, pend, waitingforfood, eating, 
	waitingtopay,finished,leaving, nothing;
}
class mycustomer {
	RestaurantTwoCustomer c;
	int table_num;
	boolean at_table;
	double check;
	String choice;
	CustomerState state;
	
	mycustomer(RestaurantTwoCustomer c2, int tablenum){
	c=c2;
	table_num=tablenum;
	state= CustomerState.waiting;
	check=0;
	
	}
}
	private String name;
	public int spot;
	Timer breaktimer = new Timer();
	private Semaphore atTable = new Semaphore(0,true);
	private Semaphore atLobby =new Semaphore(1,true);
	private Semaphore atKitchen =new Semaphore(0,true);
	public boolean w_at_table;
	public boolean w_at_lobby;
	private boolean WantToGoOnBreak=false;
	private boolean WaitingToBreak=false;

	

	public RestaurantTwoWaiterRole(PersonAgent p,RestaurantTwoSystem r, RestaurantTwoComputer r2comp) {
		
		
		Menu.put("chicken",10.99);	
		Menu.put("steak",15.99);
		Menu.put("salad",5.99);
		Menu.put("pizza",8.99);
		this.person = p;
		this.R2=r;
		this.gui = new RestaurantTwoWaiterGui(this);
		this.computer=r2comp;
		spot=0;
		
	}

	public String getMaitreDName() {
		return name;
	}

	public String getName() {
		return name;
	}

	public List getWaitingCustomers() {
		return customers;
	}
	
	public void setHost(RestaurantTwoHost host) {
		this.host = host;
	}
	public void setCook(RestaurantTwoCook cook) {
	
		this.cook = cook;
	}
	public void setCashier(RestaurantTwoCashier c) {
		this.cashier = c;
	}
	public void setSpot(int s) {
		
		this.spot = s;
	}
	// Messages
	public void wantsaBreak(){
		Do("waiter wants break");
		WantToGoOnBreak=true;
	}
	public void msgCanBreak(boolean bool){
		if(bool==true){
			Do("host allow break");
			WaitingToBreak=true;
			WantToGoOnBreak=false;
		}
		if(bool==false){
			Do("host refuse break");
			WantToGoOnBreak=false;
			((RestaurantTwoWaiterGui)gui).reset();
		}
	}
	public void msgSeatCustomer(RestaurantTwoCustomer c, int table_num){
		Do("Recieved msg seat "+c);
		//GetsMenu();
		mycustomer cust = new mycustomer (c, table_num);
	
		cust.state= CustomerState.waiting;
		customers.add(cust);
	stateChanged();
	
		
	}
		public void msgReadyToOrder(RestaurantTwoCustomer c){
			Do("Recieved msg "+c+" is ready to order");
				try{
			for (int i = 0; i< customers.size();i++){
				
				if (customers.get(i).c == c){
					customers.get(i).state=CustomerState.askedtoorder;
					stateChanged();
				}
			}
				}
				catch(Exception e){
					e.printStackTrace();
					Do("PROBLEM");
				}
				
			
	}
		
	public void msgHereIsMyChoice(RestaurantTwoCustomer c, String choice){
		//findthingforme();
		Do("Recieved msg "+c+" has made choice");
		try{
		for (int i = 0; i< customers.size();i++){
			
			if (customers.get(i).c == c){
				customers.get(i).state=CustomerState.ordered;
				customers.get(i).choice=choice;
				stateChanged();
				}
		
		//waitForOrder.release();
		//customers.remove(customer);
		//waiterGui.DoLeaveCustomer();
		//w_at_table=false;
		
	}
		}
		catch(Exception e){
			e.printStackTrace();
			Do("PROBLEM");
		}
	}
	
	public void msgnofood(int table){
		Do("recieved msg that food is unavailable");
		try{
		for (int i = 0; i< customers.size();i++){
			if(customers.get(i).table_num==table){
				customers.get(i).state=CustomerState.mustreorder;
				stateChanged();
			}
		}
		}
		catch(Exception e){
			e.printStackTrace();
			Do("PROBLEM");
		}
	}
	public void msgFoodReady(int table){
		Do("Recieved msg from cook that food is ready");
		try{
		for (int i = 0; i< customers.size();i++){
			if(customers.get(i).table_num==table){
				customers.get(i).state=CustomerState.waitingforfood;
				stateChanged();
				
			}
		}
		}
		catch(Exception e){
			e.printStackTrace();
			Do("PROBLEM");
		}
		
	}
	public void msgReadyToPay(RestaurantTwoCustomer c){
		Do("Recieved msg cust is ready to pay");
		try{
			//synchronized(customers){
		for (int i = 0; i< customers.size();i++){
		//Do("here"+customers.get(i).c+"  "+customers.get(i).state);
			if(customers.get(i).c==c){
		
				customers.get(i).state=CustomerState.waitingtopay;
				//Do("here2"+customers.get(i).c+"  "+customers.get(i).state);
				stateChanged();
				//break;
			//}
		}
		
	}
		}
	catch(Exception e){
		e.printStackTrace();
		Do("PROBLEM");
	}
		
	
	}
	public void msgHereIsCheck(int table,double p){
		try{
		for (int i = 0; i< customers.size();i++){
			if (customers.get(i).table_num == table){
				customers.get(i).check=p;
				Do("recieved check of"+ p+" for customer");
				stateChanged();
			}
		}
		
		}
		catch(Exception e){
			e.printStackTrace();
			Do("PROBLEM");
		}
	}
	public void msgCustLeave(RestaurantTwoCustomer c){
		try{
		for (int i = 0; i< customers.size();i++){
			if (customers.get(i).c == c){
				customers.remove(customers.get(i));
				Do(""+c+" has been removed");
				break;
			}
		}
		stateChanged();
		}
		catch(Exception e){
			e.printStackTrace();
			Do("PROBLEM");
		}
		((RestaurantTwoWaiterGui)gui).DoLeaveCustomer(spot);
	}
	public void msgAtTable() {//from animation
	Do("at table");

		atTable.release();// = true;
		w_at_table=true;
		
		stateChanged();
	}
	public void msgAtLobby() {//from animation

			atLobby.release();// = true;
			w_at_lobby=true;
			
			stateChanged();
		}
	public void msgAtKitchen() {//from animation

		atKitchen.release();// = true;
		//w_at_lobby=true;
		
		stateChanged();
	}	/**

	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		//Do("waiter state");
		/* Think of this next rule as:
            Does there exist a table and customer,
            so that table is unoccupied and customer is waiting.
            If so seat him at the table.
		 */
	//if want to break
		//ask host
		if(WantToGoOnBreak==true){
			host.msgAskToBreak(this);
			return true;
		}
		if(WantToGoOnBreak==false&&WaitingToBreak==true){
			if(customers.size()==0){
				((RestaurantTwoWaiterGui)gui).DoLeaveCustomer(spot);
				((RestaurantTwoWaiterGui)gui).set();
				Do("On break");
				breaktimer.schedule(new TimerTask(){
					Object cookie = 1;
					public void run() {
						EndBreak();
						WaitingToBreak=false;
					}
				}, 8000);
			}
		}
		try{
		for (mycustomer c : customers) {
	
				if (c.state == CustomerState.waiting) {
					
					//if(Dest==true){
					SeatCustomer(c, c.table_num);//the action
					return true;//return true to the abstract agent to reinvoke the scheduler.
				//}
				}
				
				if (c.state == CustomerState.askedtoorder) {
					
						Do("waiter at table and customer is ready to order.");
					TakeOrder(c);//the action
					return true;//return true to the abstract agent to reinvoke the scheduler.
					
				}
	
				if (c.state == CustomerState.ordered) {
					Do("customer has ordered.");
					
					//if(w_at_lobby==true){
						c.state=CustomerState.waitingfororder;
						DeliverOrder(c);
						return true;
					//}
					
						
					
				}
				if (c.state == CustomerState.mustreorder) {
					
					Do("customer must reorder.");
					
					ReOrder(c);
					//waiterGui.DoBringToTable( c.table_num);
				
					c.state=CustomerState.pend;
					return true;//return true to the abstract agent to reinvoke the scheduler.
					
				}
				if (c.state == CustomerState.waitingforfood) {
					
						Do("customer is waiting for food.");
						GettingFood(c);
						ServeFood(c);
						c.state = CustomerState.eating;
							
							return true;
				//return true to the abstract agent to reinvoke the scheduler.
					
				}
				//Do("");
				if(c.state == CustomerState.waitingtopay){
					Do("customer is waiting to pay.");
						DeliverCheck(c);
						c.state=CustomerState.finished;
						return true;
					
				}
					
					
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		
		
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	// Actions
	
	//goone
	private void SeatCustomer(mycustomer customer, int table) {
		((RestaurantTwoWaiterGui)gui).DoLeaveCustomer(spot);
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		w_at_lobby=false;
		customer.c.msgSitAtTable(this, table);
		customer.state=CustomerState.seated;
		DoSeatCustomer(customer.c, table);
		
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//customers.remove(customer);
		((RestaurantTwoWaiterGui)gui).DoLeaveCustomer(spot);
		w_at_table=false;
		
		
	}

	// The animation DoXYZ() routines
	private void DoSeatCustomer(RestaurantTwoCustomer c, int table) {
		//Notice how we print "customer" directly. It's toString method will do it.
		//Same with "table"
		//int tnum=tables.size();
		int []tablelist = new int[3];
		tablelist[0]=200;
		tablelist[1]=300;
		tablelist[2]=400;
		//int tablenum= table.tableNumber;
		Do("Seating " + c + " at " + table);
		((RestaurantTwoWaiterGui)gui).DoBringToTable(table); 
	}

public void TakeOrder (mycustomer c){
	
	((RestaurantTwoWaiterGui)gui).DoBringToTable( c.table_num);
	w_at_lobby=false;
	try {
		atDest.acquire();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Do("take order");
	c.c.msgWhatsYourOrder();
		w_at_table=false;
	
	
	
}
//reorder function
public void DeliverOrder (mycustomer c){
((RestaurantTwoWaiterGui)gui).GoToKitchen();
	
 try {
		atDest.acquire();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
 cook.msgCookOrder(this, c.table_num, c.choice);
}
public void GettingFood(mycustomer c){
	Do("waiter is getting food");
	//getfood, go to lobby try at lobby
	//go to table
	 Do("gives cashier "+c.c+" order");
	 cashier.msgCustomerOrder(this,c.c,c.table_num, c.choice);
	 ((RestaurantTwoWaiterGui)gui).GoToKitchen();
	try {
		atDest.acquire();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	
	
}
public void ServeFood(mycustomer c){
	Do("waiter is delivering food");
	((RestaurantTwoWaiterGui)gui).DoBringToTable(c.table_num); 
	try {
		atDest.acquire();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	c.c.msgFoodIsHere();
	((RestaurantTwoWaiterGui)gui).DoLeaveCustomer(spot);
}
/*servefood
 * at tabe aquire
 * msg customer here is food
 * leavecustomer
 */

public void ReOrder(mycustomer c){
	((RestaurantTwoWaiterGui)gui).DoBringToTable(c.table_num); 
	try {
		atDest.acquire();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	((RestaurantTwoWaiterGui)gui).GoToKitchen();
	w_at_table=false;
	c.c.msgReorder(c.table_num,c.choice);
	
}
public void DeliverCheck(mycustomer c){
Do("delivering check");
	//cashier.msgGetCheck(c.c);
((RestaurantTwoWaiterGui)gui).DoBringToTable(c.table_num); 
	try {
		atDest.acquire();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	c.c.msgHereIsCheck(c.check);
	Do("customer recieved check");
	((RestaurantTwoWaiterGui)gui).Start(spot);
	customers.remove(c);
}

public void EndBreak(){
	Do("End break");
	host.msgRestate(this);
}
	//utilities


	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void enterBuilding(SimSystem s) {
		Do("waiter enters building");
		R2 = (RestaurantTwoSystem)s;
		((RestaurantTwoWaiterGui)gui).Start(spot);
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void atDestination() {
		atDest.release();
		
		
	}

	@Override
	public void setOrderWheel(RestaurantTwoOrderWheel owheel) {
		// TODO Auto-generated method stub
		
	}

	
	
}
