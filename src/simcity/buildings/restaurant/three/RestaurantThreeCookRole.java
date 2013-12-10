package simcity.buildings.restaurant.three;

import java.util.ArrayList;
import java.util.Collections;
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
import simcity.gui.restaurantthree.RestaurantThreeCashierGui;
import simcity.gui.restaurantthree.RestaurantThreeCookGui;
import simcity.gui.restaurantthree.RestaurantThreeHostGui;
import simcity.gui.restaurantthree.RestaurantThreeWaiterGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.three.RestaurantThreeCashier;
import simcity.interfaces.restaurant.three.RestaurantThreeCook;
import simcity.interfaces.restaurant.three.RestaurantThreeHost;
import simcity.interfaces.restaurant.three.RestaurantThreeWaiter;

public class RestaurantThreeCookRole extends Role implements RestaurantThreeCook {

	private RestaurantThreeSystem restaurant;
	private RestaurantThreeHost host;
	private RestaurantThreeCashier cashier;
	private Timer timer = new Timer();
    private Map<String, FoodData> food = new HashMap<String, FoodData>();
	public enum Status {haventcooked, cooking, done, notenough, waitingforsupply}; 
	private List<Order> orders = Collections.synchronizedList(new ArrayList<Order>());
	private List<RestaurantThreeWaiter> waiters = new ArrayList<RestaurantThreeWaiter>();
	//private List<MyMarket> markets = ollections.synchronizedList(new ArrayList<MyMarket>());
	private class FoodData {
		String type;
		int cookTime;
		int amount;
		
		public FoodData(String type, int cookTime, int amount) {
			this.type = type;
			this.cookTime = cookTime;
		    this.amount = amount;
		}
	}
	private class Order {
		 RestaurantThreeWaiter waiter;
		 int tableNum;
		 String choice;
		 Status status;
		 RestaurantThreeFood food;
		
		 public Order(RestaurantThreeWaiter waiter, int tableNum, String choice) {
			 this.waiter = waiter;
			 this.choice = choice;
			 this.tableNum = tableNum;
			 this.status = Status.haventcooked;
		 }
		 public String toString() {
			 return choice + " for " + waiter;
		 }
	}

	private Semaphore atDest = new Semaphore(0, true);
	public enum OrderState { pending, cooking, cooked, plated, taken }
	private Map<String, Integer> cookingTimes = new HashMap<String, Integer>();
	private Map<String, Integer> inventory = new HashMap<String, Integer>();
	private Map<String, Integer> foodOrdered = new HashMap<String, Integer>();
	private int foodThreshold = 2;
	private int foodMax = 4;
	private int stoveCounter = 0;
	private int platingCounter = 0;

	
	public RestaurantThreeCookRole(PersonAgent p) {
		person = p;
		this.gui = new RestaurantThreeCookGui(this);
	}


	@Override
	public void atDestination() {
		atDest.release();
	}


	@Override
	public void msgHereIsAnOrder(RestaurantThreeWaiter waiter, int tableNum,String choice) {
		orders.add(new Order(waiter, tableNum, choice));
		stateChanged();
	}
	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void enterBuilding(SimSystem s) {
		restaurant = (RestaurantThreeSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestauranT 3 Cook: " + person.getName(), "Ready to work at the restaurant!");
		
		((RestaurantThreeCookGui) gui).DoGoToStand();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			
		}
	}



}
