package simcity.buildings.restaurant.two;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;





import java.util.concurrent.Semaphore;

import simcity.buildings.market.MarketSystem;
//import restaurant.MarketAgent;
import simcity.buildings.restaurant.two.*;
import simcity.gui.restauranttwo.*;
import simcity.interfaces.restaurant.two.*;
import simcity.Directory;
import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.restaurantone.RestaurantOneCookGui;
import simcity.interfaces.restaurant.two.RestaurantTwoWaiter;

public class RestaurantTwoCookRole extends Role implements simcity.interfaces.restaurant.two.RestaurantTwoCook{

	//Initially had explicit variables for this- Changed that after v2.1 submission
    //Am now using a map
	private RestaurantTwoWaiter waiter;
	private RestaurantTwoCashier cashier;
	private RestaurantTwoSystem R2;
	private RestaurantTwoComputer computer;
	private Semaphore atDest = new Semaphore(0, true);
	//private WaiterAgent waiter;
	private static int num_items =10;
	public List<MarketSystem> markets
	= Collections.synchronizedList(new ArrayList<MarketSystem>());
	private RestaurantTwoOrderWheel orderWheel;
	
	public class order{
		RestaurantTwoWaiter w;
		String choice;
		int table;
		OrderState state;
		
		order(RestaurantTwoWaiter w_, String c_, int t_){
			w=w_;
			choice=c_;
			table=t_;
			state=OrderState.prep;
		}
	}
	
	public List <order> orders= Collections.synchronizedList(new ArrayList<order>());
	public enum OrderState
	{ prep, notready, done };
	public int num=0;
	Timer timer = new Timer();
	String name;
	private boolean cooking=false;
	private int c_market=0;

	
	public void setWaiter(RestaurantTwoWaiter waitr) {
		this.waiter = waitr;
	}
	public void setCashier(RestaurantTwoCashier cash) {
		this.cashier = cash;
	}
	public void setOrderWheel(RestaurantTwoOrderWheel wheel) {
		this.orderWheel= wheel;
	}

	// Messages
	
	public RestaurantTwoCookRole(PersonAgent p, RestaurantTwoSystem r, RestaurantTwoComputer c){
		super();
		this.person=p;
		this.computer = c;
		this.gui = new RestaurantTwoCookGui(this);
		this.R2=r;
		cooking=false;
		//this.orderWheel= od;
		
	}

	public String getName() {
		return name;
	}
	public void hack_chicken(){
		computer.inventory.chicken=0;
		num_items=5;
	}
	public void hack_salad(){
		computer.inventory.salad=0;
	}
	public void hack_steak(){
		computer.inventory.steak=0;
		//markets.get(0).hack_steak();
	}
	public void msgCookOrder(RestaurantTwoWaiter w, int tnum, String choice) {
		Do("Recieve msg to cook order");
		//waiter=w;
		order o = new order (w,choice, tnum);
		if(checkorder(choice)==true){
		//o.state= OrderState.prep;
		orders.add(o);
		stateChanged();
		}
		else
			o.state=OrderState.notready;
			orders.add(o);
			stateChanged();

	}
	public void msgFoodDone(order oo) {
		oo.state= OrderState.done;
		stateChanged();
	}
	
	public void msgHereAreItems(String item){
		//msgDeliveringOrder(map<string,int>, double change)
		Do("Recieved msg items are restocked");
				if(item.equals("steak")){
					computer.inventory.steak=+10;
			computer.inventory.steak_low=false;
			if(computer.inventory.steak_gone==true){
				computer.addToMenu("steak");
			}
		}
		if(item.equals("chicken")){
			computer.inventory.chicken=+10;
			computer.inventory.chicken_low=false;
			if(computer.inventory.chicken_gone==true){
				computer.addToMenu("chicken");
			}
		}
		if(item.equals("salad")){
			computer.inventory.salad=+10;
			computer.inventory.salad_low=false;
			if(computer.inventory.salad_gone==true){
				computer.addToMenu("salad");
			}
		}
		if(item.equals("pizza")){
			computer.inventory.pizza=+10;
			computer.inventory.pizza_low=false;
			if(computer.inventory.pizza_gone==true){
				computer.addToMenu("pizza");
			}
		}
			
	}
	public void msgNoInventory(String item, int num){
		Do("Recieved msg market out of item");
		
		callMarket(computer.getMarket(),item, num);
	}
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 * @return 
	 */
	public boolean pickAndExecuteAnAction() {
		//Do("cook state");
		/* Think of this next rule as:
            Does there exist a table and customer,
            so that table is unoccupied and customer is waiting.
            If so seat him at the table.
		 */
		if(cooking==false){
			synchronized(orders){
		for (order o : orders) {
			if (o.state == OrderState.prep) {
				cooking= true;
				CookIt(o);
				return true;
			}
			if (o.state == OrderState.notready) {
				cooking= false;
				Do("No Iventory, Customer must reorder");
				notcool(o, o.table);
				return true;
			}
			
			if (o.state == OrderState.done) {
				PlateIt(o);
				return true;
			}
		}
		
		}
		}
		timer.schedule(new TimerTask(){
            public void run(){  
            checkOrderWheel();  
            }
        }, (int)(15000));

		return false;
	}

	// Actions
	
	
	private boolean checkorder(String c){
		if (c.equals("chicken")) {
			if (computer.inventory.chicken!=0){
			computer.inventory.chicken--;
				if(computer.inventory.chicken<=5){
					computer.inventory.chicken_low=true;
					callMarket(computer.getMarket(),c,num_items);
				}
			return true;
			}
			else{
				callMarket(computer.getMarket(),c,num_items);
			return false;
			}
		}
			
		if (c.equals("steak")){
			if (computer.inventory.steak!=0){
			computer.inventory.steak--;
			if(computer.inventory.steak<=5){
				computer.inventory.steak_low=true;
				callMarket(computer.getMarket(),c,num_items);
			}
			return true;
		}
		else{
			callMarket(computer.getMarket(),c,num_items);
			return false;
		}
		}
			
		if (c.equals( "salad")){
			if (computer.inventory.salad!=0){
			computer.inventory.salad--;
			if(computer.inventory.salad<=5){
				computer.inventory.salad_low=true;
				callMarket(computer.getMarket(),c,num_items);
			}
			return true;
		}
		else{
			callMarket(computer.getMarket(),c,num_items);
			return false;
		}
		}
		if (c.equals( "pizza")){
			if (computer.inventory.pizza!=0){
			computer.inventory.pizza--;
			if(computer.inventory.pizza<=5){
				computer.inventory.pizza_low=true;
				callMarket(computer.getMarket(),c,num_items);
			}
			return true;
		}
		else
		{
			callMarket(computer.getMarket(),c,num_items);
			return false;
		}
		}
		return false;
	}
	private void notcool(order o,int t){
		Do("not cool-"+o.choice+ " is out");
		setWaiter(o.w);
		computer.removeFromMenu(o.choice);
		waiter.msgnofood(t);
		orders.remove(o);
	}
	 private void checkOrderWheel()
	    {
	        Do("Checking order wheel...");
	        RestaurantTwoWOrder order= new RestaurantTwoWOrder(waiter, 2, "sam");
	        order= orderWheel.remove(); // shared data!
	        if(order == null)  /// if nothing on wheel
	        {   Do("order wheel empty");
	        }
	        else
	        {
	            Do("Found ");
	            msgCookOrder( order.waiter, order.tableNum, order.choice); 
	        }
	        stateChanged();
	    }
	private void CookIt(order o){
		Do(""+o.choice);
		((RestaurantTwoCookGui)gui).Prep();
		//RestaurantTwocookGui.setText(o.choice);
		num=find(o);
		timer.schedule(new TimerTask(){
			Object cookie = 1;
			
			public void run() {
				orders.get(num).state= OrderState.done;
				Do("finished cooking");
				cooking=false;
				stateChanged();
			}
		}, 3000);
		
	}
	private int find(order o){
		synchronized(orders){
		for(int i=0; i<orders.size();i++){
			if( orders.get(i)==o){
			return i;
			}
		}
		}
		return 0;
	}
	
	private void PlateIt(order o){
		((RestaurantTwoCookGui)gui).Plate();
		setWaiter(o.w);
		Do("done plating");
		//RestaurantTwocookGui.setText("");
		waiter.msgFoodReady(o.table);
		orders.remove(o);
		
	}
	
	private void callMarket(String market, String item, int n){
		Do("ordering from market "+c_market);
		Map<String, Integer> temp = new HashMap<String, Integer>();
		temp.put(item, n);
		((MarketSystem)Directory.getSystem(market)).getCashier().msgHereIsAnOrder(this,cashier,temp);
		
		//markets.get(c_market).msgLowOnItem(this, item, n);
		
	}
	

	// The animation DoXYZ() routines
	

	//utilities
	public void atDestination() {
		atDest.release();
	}

public void addMarket(MarketSystem m){
	markets.add(m);
}
	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void enterBuilding(SimSystem s) {
		System.out.println("cook enters building");
		// TODO Auto-generated method stub
		R2 = (RestaurantTwoSystem)s;
		((RestaurantTwoCookGui)gui).DoGoToPosition();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	public Map<String, Double> getMenu() {
		// TODO Auto-generated method stub
		return computer.Menu;
	}

}