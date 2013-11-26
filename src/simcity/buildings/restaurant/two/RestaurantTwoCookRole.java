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
import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.restaurantone.RestaurantOneCookGui;
import simcity.interfaces.restaurant.two.RestaurantTwoWaiter;

public class RestaurantTwoCookRole extends Role implements simcity.interfaces.restaurant.two.RestaurantTwoCook{

	//Initially had explicit variables for this- Changed that after v2.1 submission
    //Am now using a map
	private RestaurantTwoWaiter waiter;
	private RestaurantTwoSystem R2;
	private Semaphore atDest = new Semaphore(0, true);
	//private WaiterAgent waiter;
	private static int num_items =10;
	public List<MarketSystem> markets
	= Collections.synchronizedList(new ArrayList<MarketSystem>());
	public Map<String,Double> Menu= new HashMap<String, Double>();
	private RestaurantTwoOrderWheel orderWheel;
	public class inventory{
		int steak;
		boolean steak_low=false;
		boolean steak_gone=false;
		int chicken;
		boolean chicken_low=false;
		boolean chicken_gone=false;
		int salad;
		boolean salad_low=false;
		boolean salad_gone=false;
		int pizza;
		boolean pizza_low=false;
		boolean pizza_gone=false;
		
		inventory( int st, int ch, int sa, int pi ){
		steak=st;
		chicken=ch;
		salad=sa;
		pizza=pi;
		
		}
	}
	int x;
	public inventory v;
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
	public void setOrderWheel(RestaurantTwoOrderWheel wheel) {
		this.orderWheel= wheel;
	}

	// Messages
	
	public RestaurantTwoCookRole(PersonAgent p, RestaurantTwoSystem r, int st,int ch,int sa,int pi){
		super();
		this.person=p;
		v= new inventory(st,ch,sa,pi);
		this.gui = new RestaurantTwoCookGui(this);
		this.R2=r;
		cooking=false;
		Menu.put("chicken",10.99);	
		Menu.put("steak",15.99);
		Menu.put("salad",5.99);
		Menu.put("pizza",8.99);
		//this.orderWheel= od;
		
	}

	public String getName() {
		return name;
	}
	public void hack_chicken(){
		v.chicken=0;
		num_items=5;
	}
	public void hack_salad(){
		v.salad=0;
	}
	public void hack_steak(){
		v.steak=0;
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
		Do("Recieved msg items are restocked");
				if(item.equals("steak")){
					v.steak=+10;
			v.steak_low=false;
			if(v.steak_gone==true){
				addToMenu("steak");
			}
		}
		if(item.equals("chicken")){
			v.chicken=+10;
			v.chicken_low=false;
			if(v.chicken_gone==true){
				addToMenu("chicken");
			}
		}
		if(item.equals("salad")){
			v.salad=+10;
			v.salad_low=false;
			if(v.salad_gone==true){
				addToMenu("salad");
			}
		}
		if(item.equals("pizza")){
			v.pizza=+10;
			v.pizza_low=false;
			if(v.pizza_gone==true){
				addToMenu("pizza");
			}
		}
			
	}
	public void msgNoInventory(String item, int num){
		Do("Recieved msg market out of item");
		c_market++;
		callMarket(item, num);
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
			if (v.chicken!=0){
			v.chicken--;
				if(v.chicken<=5){
					v.chicken_low=true;
					callMarket(c,num_items);
					Do("order chicken !!!!");
				}
			return true;
			}
			else{
				callMarket(c,num_items);
			return false;
			}
		}
			
		if (c.equals("steak")){
			if (v.steak!=0){
			v.steak--;
			if(v.steak<=5){
				v.steak_low=true;
				callMarket(c,num_items);
			}
			return true;
		}
		else{
			callMarket(c,num_items);
			return false;
		}
		}
			
		if (c.equals( "salad")){
			if (v.salad!=0){
			v.salad--;
			if(v.salad<=5){
				v.salad_low=true;
				callMarket(c,num_items);
			}
			return true;
		}
		else{
			callMarket(c,num_items);
			return false;
		}
		}
		if (c.equals( "pizza")){
			if (v.pizza!=0){
			v.pizza--;
			if(v.pizza<=5){
				v.pizza_low=true;
				callMarket(c,num_items);
			}
			return true;
		}
		else
		{
			callMarket(c,num_items);
			return false;
		}
		}
		return false;
	}
	private void notcool(order o,int t){
		Do("not cool-"+o.choice+ " is out");
		setWaiter(o.w);
		removeFromMenu(o.choice);
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
	private void removeFromMenu(String choice){
		Menu.remove(choice);
		Do(""+choice+" has been removed from Menu.");
		if(choice.equals("steak")){
			v.steak_gone=true;
		}
		if(choice.equals("chicken")){
			v.chicken_gone=true;
		}
		if(choice.equals("salad")){
			v.salad_gone=true;
		}
		if(choice.equals("pizza")){
			v.pizza_gone=true;
		}
	}
	private void addToMenu(String choice){
		Do(""+choice+" has been added to Menu");
		if(choice.equals("steak")){
			Menu.put("steak",15.99);
			v.steak_gone=false;
		}
		if(choice.equals("chicken")){
			Menu.put("chicken",11.99);
			v.chicken_gone=false;
		}
		if(choice.equals("salad")){
			Menu.put("salad",5.99);
			v.salad_gone=false;
		}
		if(choice.equals("pizza")){
			Menu.put("pizza",8.99);
			v.pizza_gone=false;
		}
	}
	private void callMarket(String item, int n){
		Do("ordering from market "+c_market);
	
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
	public void msgExitBuilding() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void msgEnterBuilding(SimSystem s) {
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

}