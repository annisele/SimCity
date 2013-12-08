package simcity.buildings.restaurant.six;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.restaurantsix.RestaurantSixCookGui;
import simcity.gui.restaurantsix.RestaurantSixHostGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.six.RestaurantSixCashier;
import simcity.interfaces.restaurant.six.RestaurantSixCook;
import simcity.interfaces.restaurant.six.RestaurantSixHost;
import simcity.interfaces.restaurant.six.RestaurantSixWaiter;

public class RestaurantSixCookRole extends Role implements RestaurantSixCook {

	private RestaurantSixSystem restaurant;
	private RestaurantSixHost host;
	private RestaurantSixCashier cashier;
	private Timer timer = new Timer();
	private List<Order> myOrders = new ArrayList<Order>();
	private List<RestaurantSixWaiter> waiters = new ArrayList<RestaurantSixWaiter>();
	//private List<MyMarket> markets = new ArrayList<MyMarket>();
	
	private Semaphore atStove = new Semaphore(0, true);
	private Semaphore atKitchen = new Semaphore(0, true);
	private Semaphore atFridge = new Semaphore(0, true);
	private Semaphore atPlating = new Semaphore(0, true);
	private boolean atKitchenNow = false;
	private boolean atFridgeNow = false;
	private boolean atStoveNow = false;
	private boolean atPlatingNow = false;
	
	public enum OrderState { pending, cooking, cooked, plated, taken }
	private Map<String, Integer> cookingTimes = new HashMap<String, Integer>();
	private Map<String, Integer> inventory = new HashMap<String, Integer>();
	private Map<String, Integer> foodOrdered = new HashMap<String, Integer>();
	private int foodThreshold = 2;
	private int foodMax = 4;
	private int stoveCounter = 0;
	private int platingCounter = 0;
	private RestaurantSixMenu myMenu = new RestaurantSixMenu();
	private RestaurantSixMenu waiterMenu = new RestaurantSixMenu();
	private boolean sendMenu = false;
	
	public RestaurantSixCookRole(PersonAgent p) {
		person = p;
		this.gui = new RestaurantSixCookGui(this);
		
		cookingTimes.put("steak", 18000);
        cookingTimes.put("chicken", 16000);
        cookingTimes.put("salad", 13000);
        cookingTimes.put("pizza", 8000);
        
        inventory.put("steak", 1);
        inventory.put("chicken", 1);
        inventory.put("salad", 1);
        inventory.put("pizza", 1);
        
        foodOrdered.put("steak", 0);
        foodOrdered.put("chicken", 0);
        foodOrdered.put("salad", 0);
        foodOrdered.put("pizza", 0);
        
   /*     timer.schedule(new TimerTask() {
			public void run() {
				orderFood("steak");
		        orderFood("chicken");
		        orderFood("salad");
		        orderFood("pizza");
			}
		},
		100);
        
        updateMenu();*/
	}
	
	// Messages
	/*
	public void msgNewOrder(WaiterAgent w, int t, String c) {
		myOrders.add(new Order(w, t, c));
		Do("Just got an order for table "+t);
		stateChanged();
	}

	public void msgOrderDone(Order o) {
		o.state = OrderState.cooked;
		stateChanged();
	}
	
	public void msgTakingPlate(int table) {
		Do("Just got message taking plate!!");
		
		for (Order o:myOrders) {
			if (o.tableNumber == table) {
				o.state = OrderState.taken;
			}
		}
		stateChanged();
	}
	
	public void msgDeliverFood(MarketAgent market, String choice, int quantity, double bill) {
		if (famine == true) {
			return;
		}
		if (nosteak && choice == "steak") {
			for (MyMarket m : markets) {
				m.hasFood.put("steak", false);
			}
			inventory.put("steak", 0);
			return;
		}
		boolean order = false;
		inventory.put(choice, inventory.get(choice)+quantity);
		if (quantity < foodOrdered.get(choice)) {
			for (MyMarket m : markets) {
				if (m.market == market) {
					m.hasFood.put(choice, false);
				}
			}
			order = true;
		}
		//Do("I had ordered "+foodOrdered.get(choice));
		foodOrdered.put(choice, 0);
		
		if (quantity > 0) {
			Do("Just got "+quantity+" "+choice+" delivered from "+market.getName()+" and with a bill of $"+bill);
			for (MyMarket m : markets) {
				if (m.market == market) {
					m.bill += bill;
				}
			}
		} else {
			Do(market.getName()+" is out of "+choice);
		}
		if (order) {
			orderFood(choice);
		}
		
		stateChanged();
	}
	
	public void msgNeedMenu(WaiterAgent w) {
		waiters.add(w);
		sendMenu = true;
		stateChanged();
	}*/
	
	// From GUI
	/*public void msgAtKitchen() {
		if (atKitchenNow == false) {
			//print("atLobby released");
			atKitchenNow = true;
			atKitchen.release();// = true;
			atStoveNow = false;
			atPlatingNow = false;
			atFridgeNow = false;
			stateChanged();
		}	
		
	}
	
	public void msgAtStove() {
		if (atStoveNow == false) {
			//print("atLobby released");
			atStoveNow = true;
			atStove.release();// = true;
			atKitchenNow = false;
			atPlatingNow = false;
			atFridgeNow = false;
			stateChanged();
		}	
		
	}
	
	public void msgAtPlating() {
		if (atPlatingNow == false) {
			//print("atLobby released");
			atPlatingNow = true;
			atPlating.release();// = true;
			atStoveNow = false;
			atKitchenNow = false;
			atFridgeNow = false;
			stateChanged();
		}	
		
	}
	
	public void msgAtFridge() {
		if (atFridgeNow == false) {
			//print("atLobby released");
			atFridgeNow = true;
			atFridge.release();// = true;
			atStoveNow = false;
			atKitchenNow = false;
			atFridgeNow = false;
			stateChanged();
		}	
		
	}*/
	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
	}

	// Scheduler
	@Override
	public boolean pickAndExecuteAnAction() {
		/*
		 try {
			for (Order order : myOrders) {
				if (order.state == OrderState.taken) {
					removePlate(order);
					return true;
				}
				if (order.state == OrderState.cooked) {
					callWaiter(order);
					return true;
				}
				if (order.state == OrderState.pending) {
					cook(order);
					return false;
				}
			}
			
			for (MyMarket m : markets) {
				if (m.bill > 0.0) {
					sendBill(m);
					return true;
				}
			}
			if (sendMenu == true) {
				updateMenu();
				return true;
			}
			if (atKitchenNow == false) {
				DoGoToKitchen();
			}
			return false;
		} 
		catch(ConcurrentModificationException e){
			return false;
		}
		 */
		return false;
	}

	// Actions
	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterBuilding(SimSystem s) {
		restaurant = (RestaurantSixSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantSixCook: " + person.getName(), "Ready to cook at the restaurant!");
		
		((RestaurantSixCookGui) gui).DoGoToKitchen();		
	}
	/*
	private void cook( final Order o) {
		// Check if we're out of this food
		if (inventory.get(o.choice) <= foodThreshold) {
			orderFood(o.choice);
			//markets.get(0).msgNewOrder(this, q, o.choice);
		}
		
		//Do("Current order is: " + o.choice);
		if (inventory.get(o.choice) == 0) {
			myOrders.remove(o);
			o.waiter.msgWereOut(o.choice, o.tableNumber);
			Do("We're out of " + o.choice);
			sendMenu = true;
			return;
		}
		
		o.waiter.msgItsCooking(o.tableNumber, o.choice);	
		DoGoToFridge();
		CookGui.setText(o.choice);
		DoGoToStove(stoveCounter);
		o.setStove(stoveCounter);
		CookGui.DoStartCooking(stoveCounter, o.choice);	
		stoveCounter = (stoveCounter+1) % 4;
		CookGui.setText("");
		inventory.put(o.choice, inventory.get(o.choice) - 1);
		// make a timer, when it's done call msgOrderDone
		
		o.state = OrderState.cooking;
		timer.schedule(new TimerTask() {
			public void run() {
				//Do((o.choice) +" is cooked!");
				msgOrderDone(o);
				stateChanged();
			}
		},
		cookingTimes.get(o.choice));//getHungerLevel() * 1000);//how long to wait before running task
	}
	
	private void callWaiter(Order o) {
		//o.state = OrderState.handled;
		DoGoToStove(o.stoveNumber);
		CookGui.DoStopCooking(o.stoveNumber);
		CookGui.setText(o.choice);
		o.setPlate(platingCounter);
		DoGoToPlating(o.plateNumber);
		CookGui.DoPlate(o.plateNumber, o.choice);
		platingCounter = (platingCounter+1) % 4;
		CookGui.setText("");
		Do("Waiter! "+o.choice+" is cooked for table "+o.tableNumber);
		o.waiter.msgOrderDone(o.tableNumber, o.choice, o.plateNumber);
		o.state = OrderState.plated;
		
	}
	
	private void removePlate(Order o) {
		CookGui.DoRemovePlate(o.plateNumber);
		myOrders.remove(myOrders.indexOf(o));
	}
	
	private void updateMenu() {
		//o.state = OrderState.handled;
		waiterMenu = myMenu;
		//Do("Old menu had "+myMenu.getChoices().size()+" items");
		for (Map.Entry<String, Integer> food : inventory.entrySet()) {
			if (food.getValue() == 0) {
				waiterMenu.removeItem(food.getKey());
			}
		}
		//Do("new menu has "+waiterMenu.getChoices().size()+" items");
		for(WaiterAgent w : waiters) {
			w.msgNewMenu(waiterMenu);
		}
		sendMenu = false;
	}
	
	private void orderFood(String choice) {
		
		int orderAmount = foodMax - inventory.get(choice);
		for (MyMarket m : markets) {
			if (m.hasFood.get(choice) == true) {
				m.market.msgNewOrder(this, orderAmount, choice);
				Do("Ordering "+choice+" from "+m.market.getName());
				foodOrdered.put(choice, orderAmount);
				return;
			}
		}
		foodOrdered.put(choice, orderAmount);
	}
	
	private void sendBill(MyMarket market) {
		//Do("Telling the cashier to pay a bill for $"+market.bill+" to "+market.market.getName());
		cashier.msgPayBill(market.market, market.bill);
		market.bill = 0.0;
	}
	
	private void DoGoToFridge() {
		CookGui.DoGoToFridge();
		//Do("I need to acqure!");
		try {
			atFridge.acquire();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void DoGoToStove(int s) {
		CookGui.DoGoToStove(s);
		
		//Do("I need to acqure!");
		try {
			atStove.acquire();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void DoGoToPlating(int p) {
		CookGui.DoGoToPlating(p);
		//Do("I need to acqure!");
		try {
			atPlating.acquire();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void DoGoToKitchen() {
		CookGui.DoGoToKitchen();
		//Do("I need to acqure!");
		try {
			atKitchen.acquire();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}*/
	
	// Utility Functions
	
	public void setCashier(RestaurantSixCashier cashier) {
		this.cashier = cashier;
	}
	
	//public void addMarket(Market market) {
		//markets.add(new MyMarket(market));
	//}

	public List getMyOrders() {
		return myOrders;
	}
	
	public void forceEmpty() {
		Do("All stock forced to 0");
		inventory.put("steak", 0);
        inventory.put("chicken", 0);
        inventory.put("salad", 0);
        inventory.put("pizza", 0);
       // famine = true;
        sendMenu = true;
	}
	
	public void forceEmptysteak() {
		Do("There's no steak here!");
		inventory.put("steak", 0);
		
        //nosteak = true;
        sendMenu = true;
	}
	
	// Utility Classes
	/*
	private class MyMarket {
		Market market;
		double bill = 0.0;
		private Map<String, Boolean> hasFood = new HashMap<String, Boolean>();
		
		MyMarket(Market m) {
			market = m;
			hasFood.put("steak", true);
			hasFood.put("chicken", true);
			hasFood.put("salad", true);
			hasFood.put("pizza", true);
		}	
	}*/
	
	private class Order {
		int tableNumber;
		String choice;
		RestaurantSixWaiter waiter;
		OrderState state;
		int stoveNumber = 0;
		int plateNumber = 0;
		
		Order(RestaurantSixWaiter w, int t, String c) {
			tableNumber = t;
			choice = c;
			waiter = w;
			state = OrderState.pending;
		}
		public void setStove(int s) { 
			stoveNumber = s; 
		}
		public void setPlate(int p) { 
			plateNumber = p; 
		}
	}

}
