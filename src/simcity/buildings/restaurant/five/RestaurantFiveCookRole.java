package simcity.buildings.restaurant.five;

import java.util.*;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.restaurantfive.RestaurantFiveCookGui;
import simcity.gui.restaurantfive.RestaurantFiveWaiterGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.five.RestaurantFiveCook;
import simcity.interfaces.restaurant.five.RestaurantFiveWaiter;

public class RestaurantFiveCookRole extends Role implements RestaurantFiveCook {

	private RestaurantFiveSystem restaurant;
	private Semaphore atDest = new Semaphore(0, true);
	private List <Order> orders = Collections.synchronizedList(new ArrayList<Order>());
//	private List<MarketAgent> markets = Collections.synchronizedList(new ArrayList<MarketAgent>());
//	private List<MarketOrder> mOrders = Collections.synchronizedList(new ArrayList<MarketOrder>());
//	private List<FailedOrder> fOrders = Collections.synchronizedList(new ArrayList<FailedOrder>());
//	private Map<String, List<MarketAgent>> outOfStock = new HashMap<String, List<MarketAgent>>();
	private enum OrderState { pending, cooking, done, finished, terminated };
	private Timer timer = new Timer();
	private Map<String, Food> foods = Collections.synchronizedMap(new HashMap<String, Food>());
//	private String name;
//	private int marketIndex = 0;
//	private CookGui gui;
	

	private class Order{
		RestaurantFiveWaiter w;
		String choice;
		int table;
		OrderState s;

		Order(RestaurantFiveWaiter wIn, String choiceIn, int tableIn, OrderState sIn) {
			w = wIn;
			choice = choiceIn;
			table = tableIn;
			s = sIn;
		}
	}

//	private class FailedOrder{
//		MarketAgent m;
//		MarketOrder o;
//		int amountFilled;
//
//		FailedOrder(MarketAgent mIn, MarketOrder oIn, int am) {
//			m = mIn;
//			o = oIn;
//			amountFilled = am;
//		}
//	}

	private static class Food{
		String type;
		int cookingTime;
		int amount;
		int lowThresh;
		int capacity;

		Food(String typeIn, int cookingTimeIn, int amountIn, int lowThreshIn, int capIn) {
			type = typeIn;
			cookingTime = cookingTimeIn;
			amount = amountIn;
			lowThresh = lowThreshIn;
			capacity = capIn;
		}

	}
	
	public RestaurantFiveCookRole(PersonAgent p) {
		person = p;
		this.gui = new RestaurantFiveCookGui(this);
		
		foods.put("steak", new Food("steak", 4000, 1, 2, 10));
		foods.put("chicken", new Food("chicken", 3000, 5, 2, 10));
		foods.put("salad", new Food("salad", 2000, 5, 1, 5));
		foods.put("pizza", new Food("pizza", 2500, 5, 1, 5));
	}
	
	@Override
	public void atDestination() {
		//AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantFiveCook: " + person.getName(), "ATDESTFUNCTION: " + atDest.availablePermits());

		atDest.release();
	}
	
	@Override
	public String getName() {
		return person.getName();
	}


	public void msgHereIsAnOrder(RestaurantFiveWaiter w, String choice, int table) {
		orders.add(new Order(w, choice, table, OrderState.pending));
		stateChanged();
	}

	//sent when timer is done
	public void msgFoodDone(Order o) {
		o.s = OrderState.done;
		stateChanged();
	}
	
//	public void msgPickedUpFood() {
//		gui.removeFromCounter();
//	}
//
//	public void msgHereIsDelivery(MarketOrder o) {
//		mOrders.add(new MarketOrder(o.getChoice(), o.getAmount()));
//		stateChanged();
//	}
//
//	public void msgCannotFillOrder(MarketAgent mIn, MarketOrder o, int amountFilled) {
//		MarketOrder oNew = new MarketOrder(o.getChoice(), o.getAmount());
//		fOrders.add(new FailedOrder(mIn, oNew, amountFilled));
//	}

	@Override
	public boolean pickAndExecuteAnAction() {
		synchronized(orders) {
			for(Order o : orders) {
				if(o.s == OrderState.done) {
					PlateIt(o);
					return true;
				}
			}
		}
		synchronized(orders) {
			for(Order o : orders) {
				if(o.s == OrderState.pending) {
					//HACK for now
					o.s = OrderState.cooking;
					CookIt(o);
					return true;
				}
			}
		}
//		if(!mOrders.isEmpty()) {
//			FillInventory(mOrders.get(0));
//		}
//		if(!fOrders.isEmpty()) {
//			HandleFailedOrder(fOrders.get(0));
//		}
//		boolean cooking = false;
//		synchronized(orders) {
//			for(Order o : orders) {
//				if(o.s == OrderState.cooking) {
//					gui.DoMoveToStove();
//					cooking = true;
//				}
//			}
//			
//		}
//		if(!cooking) {
//			gui.DoMoveToHome();
//		}
		
		return false;
	}

	private void PlateIt(Order o) {
		//to pick up food
		((RestaurantFiveCookGui) gui).DoMoveToStove();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			
		}
	//	gui.carryPlate = true;
		//to drop off food
		Do("Plating " + o.choice + ".");
		((RestaurantFiveCookGui) gui).DoGoToCounter();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
		}
//		while(!gui.atDestination()) {
//			System.out.print("");
//		}
//		gui.carryPlate = false;
//		gui.addToCounter();
		//gui.DoMoveToHome();
		o.w.msgOrderIsReady(o.choice, o.table);
		o.s = OrderState.finished;
	}

	private void CookIt(final Order o) {
		//DoCooking(o);
		Food f = foods.get(o.choice);
		if(f.amount < f.lowThresh) {
			//do nothing for now
		}
		if(f.amount <= 0) {
			AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantFiveCook: " + person.getName(), "Out of that food.");
//			o.w.msgOutOfFood(o.choice, o.table);
//			o.s = OrderState.terminated;
//			Do("Out of " + o.choice);
		}
		else {
			DoCookFood();
			f.amount--;
			o.s = OrderState.cooking;
			AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantFiveCook: " + person.getName(), "Cooking " + o.choice + ".");

//			gui.carryFood = false;
//			gui.addToStove();
			timer.schedule(new TimerTask() {
				public void run() {
					msgFoodDone(o);
					//gui.removeFromStove();
				}
			},
			foods.get(o.choice).cookingTime); //how long to wait before running task
		}
		//OrderFoodFromMarket();
	}
	
	private void DoCookFood() {
	//	AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantFiveCook: " + person.getName(), "ATDEST: " + atDest.availablePermits());

		((RestaurantFiveCookGui) gui).DoGoToRefrigerator();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {

		}		
		((RestaurantFiveCookGui) gui).DoGoToHome();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {

		}
		((RestaurantFiveCookGui) gui).DoMoveToStove();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {

		}
		
//		while(!gui.atDestination()) {
//		System.out.print("");
//	}
//	gui.carryFood = true;
	}
	
//	private void HandleFailedOrder(FailedOrder f) {
//		fOrders.remove(f);
//
//		if(outOfStock.containsKey(f.o.getChoice())) {
//			outOfStock.get(f.o.getChoice()).add(f.m);
//		}
//		else {
//			List<MarketAgent> temp = new ArrayList<MarketAgent>();
//			temp.add(f.m);
//			outOfStock.put(f.o.getChoice(), temp);
//		}
//
//		List<MarketOrder> orderList = new ArrayList<MarketOrder>();
//
//		//list of markets that are out of stock of choice that needs to be reordered
//		List<MarketAgent> array = outOfStock.get(f.o.getChoice());
//		boolean reordered = false;
//		synchronized(markets) {
//			for(MarketAgent ma : markets) {
//				if(!array.contains(ma)) {
//					orderList.add(new MarketOrder(f.o.getChoice(), f.o.getAmount() - f.amountFilled));
//					ma.msgRestockFood(this, orderList);
//					Do("Filling rest of order, because market ran out. Requested " + (f.o.getAmount() - f.amountFilled) + " " + f.o.getChoice() + "." );
//					reordered = true;
//					break;
//				}
//			}
//		}
//		if(!reordered) {
//			Do("All markets are out of " + f.o.getChoice() + ".");
//		}
//	}
//
//	private void OrderFoodFromMarket() {
//		List<MarketOrder> orderList = new ArrayList<MarketOrder>();
//		synchronized(foods) {
//			for(Map.Entry<String, Food> m : foods.entrySet()) {
//				if(m.getValue().amount <= m.getValue().lowThresh) {
//					//if one or more markets are out of choice
//					if(outOfStock.containsKey(m.getKey())) {
//						//if markets out of choice list size = all markets list size
//						if(outOfStock.get(m.getKey()).size() == markets.size()) {
//							Do(m.getKey() + " is low, but all markets are out.");
//							return;
//						}
//					}
//					orderList.add(new MarketOrder(m.getKey(), m.getValue().capacity - m.getValue().amount));
//
//				}
//			}
//		}
//		if(!markets.isEmpty()) {
//			markets.get(marketIndex % markets.size()).msgRestockFood(this, orderList);
//			Do("Restocking kithcen.");
//			marketIndex++;
//		}
//	}
//
//
//	private void FillInventory(MarketOrder m) {
//		foods.get(m.getChoice()).amount += m.getAmount();
//		if(m.getAmount() > 0) {
//			Do("Restocked inventory. Amount of " + m.getChoice() + " = " + foods.get(m.getChoice()).amount);
//		}
//		mOrders.remove(m);
//	}
	
	

	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterBuilding(SimSystem s) {
		restaurant = (RestaurantFiveSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantFiveCook: " + person.getName(), "Ready to work at the restaurant!");
		
		((RestaurantFiveCookGui) gui).DoGoToHome();
		
	}

	@Override
	public void msgHereAreItems(Map<String, Integer> itemsToDeliver,
			double change) {
		// TODO Auto-generated method stub
		
	}


}
