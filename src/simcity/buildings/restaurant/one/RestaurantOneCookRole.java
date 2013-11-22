package simcity.buildings.restaurant.one;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import simcity.Role;
import simcity.gui.restaurantone.RestaurantOneCookGui;

public class RestaurantOneCookRole extends Role {

	//Initially had explicit variables for this- Changed that after v2.1 submission
    //Am now using a map
    private Map<String, Food> ItemMap = Collections.synchronizedMap(new HashMap<String, Food>());
private Graphics2D g = null;

    enum State {pending, cooking, done, sent, nostock, waitingforstock};
    String name;
    //boolean busy = false;
    public RestaurantOneCookGui cookGui = null;
    Timer CookTimer = new Timer();
    private int marketchoice = 1; //USE LATER?

    class CookOrder {
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
    
    private List<CookOrder> orders = Collections.synchronizedList(new ArrayList<CookOrder>());




    class Food {
            int amount;
            int cooktime;
            boolean requested = false;
            public Food (int a, int c) {
                    amount = a;
                    cooktime = c;
            }
    }
    private List<MarketAgent> markets = Collections.synchronizedList(new ArrayList<MarketAgent>()); 
    Map <String , Food> foodmatch;
    
    

    public RestaurantOneCookRole(String name) {
            super();
            this.name = name;
            Food steak = new Food(0, 5);
            Food salad = new Food(0, 2);
            Food pizza = new Food(0, 4);
            Food chicken = new Food(0, 2);                
            ItemMap.put("Steak", steak); 
            ItemMap.put("Salad", salad);  
            ItemMap.put("Pizza", pizza);  
            ItemMap.put("Chicken", chicken);
    }

    public void setGui(RestaurantOneCookGui gui) {
            cookGui = gui;
    }

    public void draw(Graphics2D g) {
            
            
    }
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
                                    cookGui.putongrill();
                            }
                            break;
                    }
                    }
                    return true;
            }
            synchronized(ItemMap) {
             for (Map.Entry<String, Food> entry : ItemMap.entrySet()) {
                    if ((entry.getValue().amount <= 0) && (!entry.getValue().requested)){
                            orderFromMarket(entry.getKey());
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


    private void CookIt(CookOrder o){
            if (ItemMap.get(o.choice).amount > 0) {
                    ItemMap.get(o.choice).amount--; 
                    CookFood(o.choice);
            }
            else {
                    //if it doesn't have all it needs for the amt.
                    System.out.println("I am Ordering from the market");
                    o.waiter.msgTellCustomerReorder(o.table);
                    orderFromMarket(o.choice);
                    orders.remove(o);
                    ItemMap.get(o.choice).requested = true;
            }
    }

    private void CookFood(final String choice) {
            CookTimer.schedule(new TimerTask() {
                    public void run() {
                            //print("Done cooking " + choice );
                            markDone(choice);
                    }
            },
            ItemMap.get(choice).cooktime*500);
    }

    private void PlateIt(CookOrder o) {
            o.waiter.msgOrderIsReady(o.choice, o.table); 
            orders.remove(o);
    }

    private void orderFromMarket(String type) {
            markets.get(marketchoice).msgNeedFood(type, 5- (ItemMap.get(type).amount));
    }

    
    public void makeMarkets(List<MarketAgent> markets) {
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


	
}