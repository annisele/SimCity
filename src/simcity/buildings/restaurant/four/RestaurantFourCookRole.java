package simcity.buildings.restaurant.four;

public class RestaurantFourCookRole {

	package restaurant;

	import agent.Agent;
	import restaurant.interfaces.*;
	import restaurant.gui.CookGui;
	import restaurant.Inventory;
	import restaurant.MarketOrder.marketOrderForCookState;
	import restaurant.Order;
	import restaurant.Order.orderState;

	import java.util.*;
	import java.util.concurrent.Semaphore;
	import java.util.Random;

	////////////////////////////////////////////////////////////////////////////////

	/**
	 * Restaurant Cook Agent
	 */

	public class CookAgent extends Agent implements Cook { 

		// variables
		private Stats stats = new Stats();
		
		Timer timer = new Timer();
		private String name;
		private CookGui gui;
		
		public enum cookState {present, notPresent};
		private cookState state;
		
		private Semaphore atDestination = new Semaphore(0, true);
		
		private Random generator = new Random();	// chooses which market to go to (no preference)
		private int marketID = 0; // determines which market to go to
		
		List<Market> markets = Collections.synchronizedList(new ArrayList<Market>());
			
		List<Order> orders = Collections.synchronizedList(new ArrayList<Order>());
		List<MarketOrder> marketOrders = Collections.synchronizedList(new ArrayList<MarketOrder>());
		
		List<Inventory> inventory = Collections.synchronizedList(new ArrayList<Inventory>());
		
		// constructors /////////////////////////////////////////////////////////////////////
		
		public CookAgent(String name) {
			super();
			this.name = name;
			this.state = cookState.present;
			inventory.add(new Inventory("Steak", stats.getCookInventory()));
			inventory.add(new Inventory("Chicken", stats.getCookInventory()));
			inventory.add(new Inventory("Salad", stats.getCookInventory()));
			inventory.add(new Inventory("Pizza", stats.getCookInventory()));
		}

		// messages ///////////////////////////////////////////////////////////////////

		public void msgAtDestination() { // from animation
			atDestination.release();
			stateChanged();
		}
		
		public void msgHereIsTheOrder(int tableNumber, int choice, Waiter waiter) {
			orders.add(new Order(tableNumber, choice, waiter));
			stateChanged();
		}
		
		public void msgEnoughInventory(Order o, Inventory inv) {
			o.setState(orderState.approved);
			inv.decreaseOne();
			print(inv.getAmount() + " " + inv.getFood() + " left.");
			stateChanged();
		}
		
		public void msgFinishedCooking() {
			synchronized(orders) {
				for (Order order : orders) {
					if (order.state == orderState.cooking) {
						order.setState(orderState.done);
						stateChanged();
					}
				}
			}
			
		}
		
		public void msgCalledWaiter(Order o) {
			o.setState(orderState.called);
			stateChanged();
		}
		
		public void msgHereToTakeFood(int tableNumber) {
			synchronized(orders) {
				for (Order order : orders) {
					if (order.tableNumber == tableNumber) {
						order.state = orderState.taken;
						stateChanged();
					}
				}
			}
		}
		
		public void msgMarketOrderUnavailable(Market market) {
			synchronized(marketOrders) {
				for (MarketOrder order : marketOrders) {
					if (order.getMarket() == market) {
						order.setState2(marketOrderForCookState.notFinished);
						print("That's too bad. We'll look somewhere else for " + order.getChoice());
						stateChanged();
					}
				}
			}
		}
		
		public void msgMarketHasEnough(Market market) {
			synchronized(marketOrders) {
				for (MarketOrder order : marketOrders) {
					if (order.getMarket() == market) {
						order.setState2(marketOrderForCookState.finished);
						stateChanged();
					}
				}
			}
		}
		
		public void msgMarketOrderIncomplete(Market market, int amount) {
			synchronized(marketOrders) {
				for (MarketOrder order : marketOrders) {
					if (order.getMarket() == market) {
						order.setState2(marketOrderForCookState.notFinished);
						order.setAmount(order.getAmount() - amount);
						print("That's alright. Thanks for the few orders!");
						stateChanged();
					}
				}
			}
		}
		
		
		public void msgHereIsTheRestaurantOrder(int amount, String choice) {
			synchronized(inventory) {
				for (Inventory inv : inventory) {
					if (inv.getFood() == choice) {
						inv.increase(amount);
						stateChanged();
					}
				}
			}
		}
		
		public void msgOrderFromMarket(Order o, int marketID) {
			synchronized(orders) {
				for (Order order : orders) {
					if (order == o) {
						o.setMarket(markets.get(marketID));
						o.setState(orderState.ordering);
						stateChanged();
					}
				}
			}
		}
		
		/**
		 * Scheduler.  Determine what action is called for, and do it.
		 */
		
		protected boolean pickAndExecuteAnAction() {
			
			synchronized(marketOrders) {
				for (MarketOrder order : marketOrders) {
					if (order.state2 == marketOrderForCookState.finished){
						order.setState2(marketOrderForCookState.waiting);
						returnToRestaurant(order);
						return true;
					}
				}
			}
			
			synchronized(marketOrders) {
				for (MarketOrder order : marketOrders) {
					if (order.state2 == marketOrderForCookState.notFinished) {
						order.setState2(marketOrderForCookState.reordering); 
						Reorder(order);
						return true;
					}
				}
			}

			synchronized(orders) {
				for (Order order : orders) {
					if (order.state == orderState.ordering) {
						buyFromMarket(order);
						return true;
					}
				}
			}

			synchronized(orders) {
				for (Order order : orders) {
					if (order.state == orderState.taken) {
						giveOrder(order);
						return true;
					}
				}		
			}

			synchronized(orders) {
				for (Order order : orders) {
					if (order.state == orderState.done) {
						callWaiter(order);
						return true;
					}
				}
			}

			synchronized(orders) {
				for (Order order : orders) {
					if (order.state == orderState.approved) {
						order.state = orderState.cooking;
						cookOrder(order);
						return true;
					}
				}
			}
		
			synchronized(orders) {
				for (Order order : orders) {
					if (order.state == orderState.notDone) {
						checkInventory(order);
						return true;
					}
				}
			}

			return false;
		}

		// actions ////////////////////////////////////////////////////////////////////////////
		
		// checks inventory after customer orders, then either approves or has to order from market
		private void checkInventory(Order o) {
			synchronized(inventory) {
				for (Inventory inv : inventory) {
					if (inv.getFood() == o.getChoiceName()) {
						if (inv.getAmount() == 0) {
							Do("No " + o.getChoiceName() + " available.");
							o.getWaiter().msgFoodNotAvailable(o.getTableNumber());
							marketID = generator.nextInt(3);		// makes the random choice
							Do("Going to " + markets.get(marketID).getName() + " to buy some " + o.getChoiceName());
							DoGoToMarket();
							state = cookState.notPresent;
							try {
								atDestination.acquire();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							msgOrderFromMarket(o, marketID);
						}
						else if (inv.getAmount() > 0) {
							msgEnoughInventory(o, inv);
						}
					}
				}
			}
		}
		
		private void cookOrder(Order o) {
			Do("Cooking " + o.choiceName);
			DoGoToCookArea();
			timer.schedule(new TimerTask() {
				public void run() {
					msgFinishedCooking();
				}
			},
			stats.getCookTime());
		}

		private void callWaiter(Order o) {
			Do ("Calling " + o.getWaiter().getName());
			DoGoToPlateArea();
			o.getWaiter().msgOrderIsReady(o.getTableNumber());
			msgCalledWaiter(o);
		}
		
		private void giveOrder(Order o) {
			Do("Gave " + o.choiceName + " to " + o.getWaiter().getName());
			o.waiter.msgHereIsTheFood(o.tableNumber, o.choiceID);
			orders.remove(o);
			DoGoToRestaurant();
		}
		
		private void buyFromMarket(Order o) {
			Do("Please give me some " + o.getChoiceName());
			marketOrders.add(new MarketOrder(o.getChoiceName(), stats.getCookPurchases(), this, o.getMarket()));
			o.getMarket().msgIWantToBuyFood(o.getChoiceName(), stats.getCookPurchases(), this);
			orders.remove(o);
		}
		
		private void returnToRestaurant(MarketOrder o) {
			DoGoToRestaurant();
			marketOrders.remove(o);
			try {
				atDestination.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			state = cookState.present;
		}
		
		private void Reorder(MarketOrder o) {
			marketID = generator.nextInt(3);		// makes the random choice
			o.setMarket(markets.get(marketID));
			Do("Going to " + markets.get(marketID).getName() + " to buy some " + o.getChoice());
			o.getMarket().msgIWantToBuyFood(o.getChoice(), o.getAmount(), this);
		}
		
		// DoXYZ routines
		private void DoGoToRestaurant() {
			Do("Going back to restaurant.");
			gui.DoGoToRestaurant(1);
		}
		
		private void DoGoToMarket() {
			Do("Going to market");
			gui.DoGoToMarket(2);
		}
		
		private void DoGoToCookArea() {
			Do("Cooking.");
			gui.DoGoToCookArea(3);
		}
		
		private void DoGoToPlateArea() {
			Do("Plating.");
			gui.DoGoToPlateArea(4);
		}
		
		// utilities ////////////////////////////////////////////////////////////////////////

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public CookGui getGui() {
			return gui;
		}
		
		public void setGui(CookGui gui) {
			this.gui = gui;
		}
		
		public void addMarket(Market market) {
			markets.add(market);
		}
		
		public cookState getCookState() {
			return state;
		}
	}


	
}
