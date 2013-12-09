package simcity.buildings.restaurant.one;

import java.awt.Graphics2D;
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
import simcity.gui.restaurantone.RestaurantOneCashierGui;
import simcity.gui.restaurantone.RestaurantOneCookGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.one.RestaurantOneCook;
import simcity.buildings.market.MarketSystem;


public class RestaurantOneCookRole extends Role implements RestaurantOneCook {

	//Initially had explicit variables for this- Changed that after v2.1 submission
    //Am now using a map
    private Map<String, Food> ItemMap = Collections.synchronizedMap(new HashMap<String, Food>());
private Semaphore atDest = new Semaphore(0, true);

	RestaurantOneSystem restaurant;
	PersonAgent person;

    enum State {pending, cooking, done, sent, nostock, waitingforstock};
    String name;
    //boolean busy = false;
   // public RestaurantOneCookGui cookGui = null;
    Timer CookTimer = new Timer();
    private int marketchoice = 1; //USE LATER?

    public class CookOrder {
            RestaurantOneWaiterRole waiter;
            String choice;
            int table;
            State state;


            public CookOrder(RestaurantOneWaiterRole w, String c, int t, State s) {
                    waiter = w;
                    choice = c;
                    table = t;
                    state = s;
            }
    }
    
    public List<CookOrder> orders = Collections.synchronizedList(new ArrayList<CookOrder>());




    class Food {
            int amount;
            int cooktime;
            boolean requested = false;
            public Food (int a, int c) {
                    amount = a;
                    cooktime = c;
            }
    }
    public List<MarketSystem> markets = Collections.synchronizedList(new ArrayList<MarketSystem>());    Map <String , Food> foodmatch;
    
    

    public RestaurantOneCookRole(PersonAgent p) {
            super();
            person = p;
            this.name = p.getName();
            Food steak = new Food(0, 5);
            Food salad = new Food(0, 2);
            Food pizza = new Food(0, 4);
            Food chicken = new Food(0, 2);                
            ItemMap.put("Steak", steak); 
            ItemMap.put("Salad", salad);  
            ItemMap.put("Pizza", pizza);  
            ItemMap.put("Chicken", chicken);
            gui = new RestaurantOneCookGui(this);

    }

  //  public void setGui(RestaurantOneCookGui gui) {
    //        cookGui = gui;
   // }

    public void msgHereIsAnOrder(RestaurantOneWaiterRole w, String choice, int table) {
            orders.add(new CookOrder(w, choice, table, State.pending));
           // print("Received the order " + choice);
            stateChanged();
    }
    
    public void msgOrderComplete(String choice, int amount) {
            ItemMap.get(choice).amount += amount;
            ItemMap.get(choice).requested = false;
            stateChanged();
    }

    @Override
    public boolean pickAndExecuteAnAction() {
            if (!orders.isEmpty())
            {
                    synchronized(orders){
                    for (CookOrder o : orders){
                            if (o.state == State.done) {
                                    PlateIt(o);        
                            }
                            break;
                    }
                    }
                    synchronized(orders){
                    for (CookOrder o : orders){
                            if (o.state == State.pending) {
                                    CookIt(o);
                                    o.state = State.cooking;
                                   // cookGui.putongrill();
                            }
                            break;
                    }
                    }
                    return true;
            }
            synchronized(ItemMap) {
             for (Map.Entry<String, Food> entry : ItemMap.entrySet()) {
                    if ((entry.getValue().amount <= 0) && (!entry.getValue().requested)){
                          //  orderFromMarket(entry.getKey());
                            entry.getValue().requested = true;
                            return true;
                    }
            } 
            }
            
            return false;
    }

    

//Market can't supply anything at all        
    public void msgOrderIncomplete() {
            marketchoice++;
            if (marketchoice == 2)
                    marketchoice = 0;
            stateChanged();
    }


    public void CookIt(CookOrder o){
            if (ItemMap.get(o.choice).amount > 0) {
                    ItemMap.get(o.choice).amount--; 
                    CookFood(o.choice);
            }
            else {
                    //if it doesn't have all it needs for the amt.
                    System.out.println("I am Ordering from the market");
                    o.waiter.msgTellCustomerReorder(o.table);
                   // orderFromMarket(o.choice);
                    orders.remove(o);
                    ItemMap.get(o.choice).requested = true;
            }
    }

    public void CookFood(final String choice) {
            CookTimer.schedule(new TimerTask() {
                    public void run() {
                            //print("Done cooking " + choice );
                            markDone(choice);
                    }
            },
            ItemMap.get(choice).cooktime*500);
    }

    public void PlateIt(CookOrder o) {
            o.waiter.msgOrderIsReady(o.choice, o.table); 
            orders.remove(o);
    }

  /*  private void orderFromMarket(String type) {
            markets.get(0).msgNeedFood(type, 5- (ItemMap.get(type).amount));
    } */

    
    public void makeMarkets(List<MarketSystem> markets) {
            this.markets = markets;
    }

    public void msgPartialComplete(String choice, int restoforder) {
            // TODO Auto-generated method stub
            ItemMap.get(choice).amount += (5-restoforder);
            System.out.println("Amount of  " + choice + " = " + ItemMap.get(choice).amount);
            ItemMap.get(choice).requested = true;
            stateChanged();
            
            
    }
    
    /*public void msgOrderComplete(String choice, int amount) {
            ItemMap.get(choice).amount += amount;
            ItemMap.get(choice).requested = false;
            stateChanged();
    } */


    public void markDone(String choice) {
            for (CookOrder o : orders){
                    if (o.choice == choice) {
                            o.state = State.done;
                    }
                    break;
            }
            stateChanged();
    }

	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		restaurant = (RestaurantOneSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantOneCook: " + person.getName(), "Ready to work at the restaurant!");


		((RestaurantOneCookGui)gui).DoGoToKitchen();
		
	}

	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		atDest.release();
		System.out.println("At Destination");
		
	}

	@Override
	public void msgHereIsAnorder(RestaurantOneWaiterRole w, String choice,
			int table) {
		// TODO Auto-generated method stub
		
	}





	
}