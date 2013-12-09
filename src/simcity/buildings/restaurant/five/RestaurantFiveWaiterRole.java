package simcity.buildings.restaurant.five;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.restaurantfive.RestaurantFiveWaiterGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.five.RestaurantFiveCustomer;
import simcity.interfaces.restaurant.five.RestaurantFiveWaiter;

public class RestaurantFiveWaiterRole extends Role implements RestaurantFiveWaiter {

	private Semaphore atDest = new Semaphore(0, true);
	private RestaurantFiveSystem restaurant;
	private List<MyCustomer> customers = new ArrayList<MyCustomer>();
	private enum CustomerState { waitingForMenu, hasMenu, waiting, leftWithoutEating, seated, askedToOrder, asked, ordered, orderSent, eating, doneEating, paying, done, left };
	private enum WaiterState { requestedBreak, breaking, onBreak, working, goingOffBreak, deniedBreak };
	public enum WaiterAnimationState { food, plate, both, neither };
	private WaiterState state;
//	private List<Order> finishedOrders = new ArrayList<Order>();
//	private List<Order> failedOrders = new ArrayList<Order>();
//	private RestaurantFiveCook cook;
//	private RestaurantFiveHost host;
//	private RestaurantFiveCashier cashier;
//	private String name;
//	
//	private Map<MyCustomer, Double> checks = new HashMap<MyCustomer, Double>();
//	Timer timer = new Timer();

	private class MyCustomer{
		RestaurantFiveCustomer c;
		int table;
		String choice;
		CustomerState s;

		MyCustomer(RestaurantFiveCustomer cIn, int tIn, CustomerState sIn) {
			c = cIn;
			table = tIn;
			s = sIn;
		}
	}

//	private class Order{
//		String choice;
//		int table;
//
//		Order(String ch, int t) {
//			choice = ch;
//			table = t;
//		}
//	}

	
	public RestaurantFiveWaiterRole(PersonAgent p) {
		person = p;
		this.gui = new RestaurantFiveWaiterGui(this);
		state = WaiterState.working;
	}
	
	@Override
	public void atDestination() {
		atDest.release();
	}
	
	@Override
	public String getName() {
		return person.getName();
	}
	
	public void msgPleaseSeatCustomer(RestaurantFiveCustomer c, int table) {
		try {
			for(MyCustomer mc : customers) {
				if(mc.c == c) {
					mc.s = CustomerState.waitingForMenu;
					stateChanged();
					return;
				}
			}
		}
		catch(ConcurrentModificationException e) {
			AlertLog.getInstance().logError(AlertTag.valueOf(restaurant.getName()), "RestaurantFiveWaiter: " + person.getName(), "Concurrent modification exception.");
		}
		customers.add(new MyCustomer(c, table, CustomerState.waitingForMenu));
		stateChanged();
	}
//
//	public void msgFoodTooExpensive(RestaurantFiveCustomer c) {
//		try {
//			for(MyCustomer mc : customers) {
//				if(mc.c == c) {
//					mc.s = CustomerState.leftWithoutEating;
//					stateChanged();
//					return;
//				}
//			}
//		}
//		catch(ConcurrentModificationException e) {
//			System.out.println("Something went wrong.");
//		}
//	}
//
	public void msgIWillFollowYou(RestaurantFiveCustomer c) {
		try {
			for(MyCustomer mc : customers) {
				if(mc.c == c) {
					mc.s = CustomerState.waiting;
					stateChanged();
					return;
				}
			}
		}
		catch(ConcurrentModificationException e) {
			AlertLog.getInstance().logError(AlertTag.valueOf(restaurant.getName()), "RestaurantFiveWaiter: " + person.getName(), "Concurrent modification exception.");
		}
	}
//
//	public void msgAskForBreak(AnimationPanel rp) {
//		//restPanel = rp;
//		state = WaiterState.requestedBreak;
//		stateChanged();
//	}
//
//	public void msgGoOnBreak() {
//		state = WaiterState.breaking;
//		Do("Going on break after I finish with my customers.");
//		stateChanged();
//	}
//
//	public void msgCannotGoOnBreak() {
//		state = WaiterState.deniedBreak;
//		stateChanged();
//	}
//
//	public void msgGoOffBreak() {
//		Do("Done with break.");
//		//state = WaiterState.working;
//		state = WaiterState.goingOffBreak;
//		stateChanged();
//	}
//
//	public void msgAtDest() {
//		atDest.release();
//		stateChanged();
//	}
//
//	public void msgReadyToOrder(RestaurantFiveCustomer c) {
//		try {
//			for(MyCustomer mc : customers) {
//
//				if(mc.c == c) {
//					mc.s = CustomerState.askedToOrder;
//					stateChanged();
//					return;
//				}
//			}
//		}
//		catch(ConcurrentModificationException e) {
//			System.out.println("Something went wrong.");
//		}
//	}
//
//	public void msgHereIsMyChoice(RestaurantFiveCustomer c, String choice) {
//		try {
//			for(MyCustomer mc : customers) {
//
//				if(mc.c == c) {
//					mc.s = CustomerState.ordered;
//					mc.choice = choice;
//					stateChanged();
//					return;
//				}
//			}
//		}
//		catch(ConcurrentModificationException e) {
//			System.out.println("Something went wrong.");
//		}
//	}
//
//	public void msgOutOfFood(String choice, int table) {
//		failedOrders.add(new Order(choice, table));
//		stateChanged();
//	}
//
//	public void msgOrderIsReady(String choice, int table) {
//		finishedOrders.add(new Order(choice, table));
//		stateChanged();
//	}
//
//	public void msgDoneEating(RestaurantFiveCustomer c) {
//		try {
//			for(MyCustomer mc : customers) {
//				if(mc.c == c) {
//					mc.s = CustomerState.doneEating;
//					stateChanged();
//					return;
//				}
//			}
//		}
//		catch(ConcurrentModificationException e) {
//			System.out.println("Something went wrong.");
//		}
//	}
//
//	public void msgLeaving(RestaurantFiveCustomer c) {
//		try {
//			for(MyCustomer mc : customers) {
//				if(mc.c == c) {
//					if(mc.s != CustomerState.leftWithoutEating) {
//						mc.s = CustomerState.done;
//						stateChanged();
//
//					}	
//					return;
//				}
//			}
//		}
//		catch(ConcurrentModificationException e) {
//			System.out.println("Something went wrong.");
//		}
//	}
//
//	public void msgHereIsCheck(RestaurantFiveCustomer cIn, double checkAmount) {
//		try {
//			for(MyCustomer mc : customers) {
//				if(mc.c == cIn) {
//					checks.put(mc, checkAmount);
//					stateChanged();
//					return;
//				}
//			}
//		}
//		catch(ConcurrentModificationException e) {
//			System.out.println("Something went wrong.");
//		}
//
//	}

	@Override
	public boolean pickAndExecuteAnAction() {
//		if(state == WaiterState.deniedBreak) {
//			state = WaiterState.working;
//			DeniedBreak();
//			return true;
//		}
//		try {
//			for(Order o: finishedOrders) {
//				ServeFood(o);
//				return true;
//			}
//		}
//		catch(ConcurrentModificationException e) {
//			return false;
//		}
		try {
			for(MyCustomer c : customers) {
				if(c.s == CustomerState.waitingForMenu) {
					GiveCustomerMenu(c);
					return true;
				}
			}
		}
		catch(ConcurrentModificationException e) {
			return false;
		}
		try {
			for(MyCustomer c : customers) {
				if(c.s == CustomerState.waiting) {
					SeatCustomer(c);
					return true;
				}
			}
		}
		catch(ConcurrentModificationException e) {
			return false;
		}
//		try {
//			for(MyCustomer c : customers) {
//				if(c.s == CustomerState.askedToOrder) {
//					TakeOrder(c);
//					return true;
//				}
//			}
//		}
//		catch(ConcurrentModificationException e) {
//			return false;
//		}
//		try {
//			for(Order o: failedOrders) {
//				AskToReorder(o);
//				return true;
//			}
//		}
//		catch(ConcurrentModificationException e) {
//			return false;
//		}
//		try {
//			for(MyCustomer c : customers) {
//				if(c.s == CustomerState.ordered) {
//					SendOrderToCook(c);
//					return true;
//				}
//			}
//		}
//		catch(ConcurrentModificationException e) {
//			return false;
//		}
//		try {
//			for(MyCustomer c : customers) {
//				if(c.s == CustomerState.doneEating) {
//					GiveCheck(c);
//					return true;
//				}
//			}
//		}
//		catch(ConcurrentModificationException e) {
//			return false;
//		}
//		try {
//			for(MyCustomer c : customers) {
//				if(c.s == CustomerState.done) {
//					ClearTable(c);
//					return true;
//				}
//			}
//		}
//		catch(ConcurrentModificationException e) {
//			return false;
//		}
//		try {
//			for(MyCustomer c : customers) {
//				if(c.s == CustomerState.leftWithoutEating) {
//					CustomerLeftWithoutEating(c);
//					return true;
//				}
//			}
//		}
//		catch(ConcurrentModificationException e) {
//			return false;
//		}
//		MoveToHome();
//		if(state == WaiterState.goingOffBreak) {
//			GoOffBreak();
//		}
//		if(state == WaiterState.requestedBreak) {
//			RequestBreak();
//		}
//		if(state == WaiterState.breaking) {
//			state = WaiterState.onBreak;
//			GoOnBreak();
//		}
		return false;
	}

//	private void MoveToHome() {
//		((RestaurantFiveWaiterGui)gui).DoGoToHome();
//	}

	private void GiveCustomerMenu(MyCustomer c) {
		
		((RestaurantFiveWaiterGui)gui).DoGoToHost();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		c.s = CustomerState.hasMenu;
		c.c.msgHereIsMenu(this, new RestaurantFiveMenu());
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantFiveWaiter: " + person.getName(), "Here is a menu, " + c.c.getName() + ".");
	}

//	private void CustomerLeftWithoutEating(MyCustomer c) {
//		host.msgTableIsFree(c.table);
//		c.s = CustomerState.left;
//	}
//
	private void SeatCustomer(MyCustomer c) {
//	gui stuff
//		int xCustLoc = c.c.getGui().getXDest();
//		int yCustLoc = c.c.getGui().getYDest();
//		gui.DoGoToSeatCustomer(xCustLoc, yCustLoc);
//		try {
//			atDest.acquire();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			print("Unexpected exception caught in WaiterAgent thread:", e);
//		}
//		while(!c.c.getGui().readyToBeSeated()) {
//			System.out.print(""); //do nothing
//		}
//		gui.DoSeatCustomer(c.c, host.getGui().getTableX(c.table), host.getGui().getTableY(c.table));
//		try {
//			atDest.acquire();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			print("Unexpected exception caught in WaiterAgent thread:", e);
//		}
//		
//		c.c.msgFollowMeToTable(this, c.table);
//
//		System.out.println("Seating " + c.c + " at table " + c.table + ".");
		
		((RestaurantFiveWaiterGui)gui).DoSeatCustomer(c.c, c.table);
		
		
		c.s = CustomerState.seated;
		
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantFiveWaiter: " + person.getName(), "Seating Customer");

		
	}

//	private void AskToReorder(Order o) {
//		try {
//			for(MyCustomer mc : customers) {
//				if(mc.c.tableNum == o.table) {
//					failedOrders.remove(o);
//					//Do(mc.c.getName() + " must order again.");
//					mc.c.msgPleaseReorder();
//					TakeOrder(mc);
//					return;
//					//need gui to draw "Please Reorder"
//				}
//
//			}
//		}
//		catch(ConcurrentModificationException e) {
//			System.out.println("Something went wrong.");
//		}
//
//	}
//
//	private void TakeOrder(MyCustomer c) {
////		gui.DoGoToTable(host.getGui().getTableX(c.table), host.getGui().getTableY(c.table));
////		try {
////			atDest.acquire();
////		} catch (InterruptedException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		Do("Taking order.");
//		c.c.msgWhatWouldYouLike();
//		if(c.s != CustomerState.leftWithoutEating) {
//			c.s = CustomerState.asked;
//		}
//	}
//
//	private void SendOrderToCook(MyCustomer c) {
//		cook.msgHereIsAnOrder(this, c.choice, c.table);
//		Do("Sending order " + c.choice + " from table " + c.table + " to cook.");
//		c.s = CustomerState.orderSent;
//	}
//
//	private void ClearTable(MyCustomer c) {
//		c.s = CustomerState.left;
//
//		Do("Clearing table " + c.table + ".");		
////		gui.DoGoToTable(host.getGui().getTableX(c.table), host.getGui().getTableY(c.table));
////		try {
////			atDest.acquire();
////		} catch (InterruptedException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		gui.DoGoToKitchen();
////		try {
////			atDest.acquire();
////		} catch (InterruptedException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		host.msgTableIsFree(c.table);
//	}
//
//	private void GiveCheck(MyCustomer c) {
//		Do("Giving customer check of $" + checks.get(c));
//		c.c.msgHereIsCheck(checks.get(c)); //passes in amount to pay
//		c.s = CustomerState.paying;
//		checks.remove(c);
//	}
//
//	private void ServeFood(Order o) {
////		gui.DoGoToKitchen();
////		try {
////			atDest.acquire();
////		} catch (InterruptedException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		//cook.msgPickedUpFood();
//		finishedOrders.remove(o);
//		try {
//			for(MyCustomer mc : customers) {
//				if(mc.c.tableNum == o.table) {
//					Do("Serving " + mc.choice + " to " + mc.c + ".");
////					gui.DoServeFood(host.getGui().getTableX(mc.c.tableNum), host.getGui().getTableY(mc.c.tableNum), mc.choice);
////					//gui.DoGoToTable(host.getGui().getTableX(mc.c.tableNum), host.getGui().getTableY(mc.c.tableNum));
////					try {
////						atDest.acquire();
////					} catch (InterruptedException e) {
////						// TODO Auto-generated catch block
////						e.printStackTrace();
////					}
//					mc.s = CustomerState.eating;
//					mc.c.msgHereIsYourFood(mc.choice);
//
//					//asking cashier to produce check for this customer
//					Do("Asking cashier for check.");
//					cashier.msgProduceCheck(this, mc.c, mc.choice);
//					return;
//				}
//			}
//		}
//		catch(ConcurrentModificationException e) {
//			System.out.println("Something went wrong.");
//		}
//	}
//
//	private void GoOnBreak() {
//		//restPanel.triggerUpdateInfoPanel(this);
//		timer.schedule(new TimerTask() {
//			public void run() {
//				msgGoOffBreak();
//				stateChanged();
//			}
//		},
//		20000);
//	}
//
//	private void GoOffBreak() {
//		//restPanel.triggerUpdateInfoPanel(this);
//		state = WaiterState.working;
//	}
//
//	private void RequestBreak() {
//		host.msgWantToGoOnBreak(this);
//		//restPanel.msgWaiterNotOnBreak(name);
//	}
//
//	private void DeniedBreak() {
//		//restPanel.triggerUpdateInfoPanel(this);
//		//restPanel.msgWaiterNotOnBreak(name);
//	}

	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterBuilding(SimSystem s) {
		restaurant = (RestaurantFiveSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantFiveWaiter: " + person.getName(), "Ready to work at the restaurant!");
		
		((RestaurantFiveWaiterGui) gui).DoGoToHome();
		
		
	}

}
