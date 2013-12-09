package simcity.buildings.restaurant.five;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.restaurantfive.RestaurantFiveCustomerGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.five.RestaurantFiveCustomer;
import simcity.interfaces.restaurant.five.RestaurantFiveWaiter;

public class RestaurantFiveCustomerRole extends Role implements RestaurantFiveCustomer {

	Timer timer = new Timer();
	int tableNum = -1;
//	private Menu menu;
//	private String food;
//	private double money = 30;
	private RestaurantFiveWaiter waiter;
	private RestaurantFiveMenu menu;
//	private double check;
//	private double change;
	private Semaphore atDest = new Semaphore(0, true);
	private RestaurantFiveSystem restaurant;
	public enum AgentState
	{DoingNothing, LookingAtMenu, WaitingInRestaurant, BeingSeated, Seated, WantToOrder, AskedToOrder, Reordering, Reordered, Ordering, Ordered,
		Eating, GettingFood, DoneEating, WaitingForCheck, GoingToPay, Paying, ReceivingChange, DoneWithPaymentProcess, Leaving};
	private AgentState state = AgentState.DoingNothing;//The start state

	public enum AgentEvent 
	{none, gotHungry, willWaitforTable, toldHostIWillWait, lookingAtMenu, followWaiter, seated, doneEating, doneLeaving};
	AgentEvent event = AgentEvent.none;

	
	public RestaurantFiveCustomerRole(PersonAgent p) {
		person = p;
		this.gui = new RestaurantFiveCustomerGui(this);
	}
	
	@Override
	public String getName() {
		return person.getName();
	}
	
	public void msgRestaurantFull() {
		
//		if(person.getName().equalsIgnoreCase("full")) {
//			print("Leaving because restaurant is full.");
//		}
//		else {
//			event = AgentEvent.willWaitforTable;
//			stateChanged();
//		}
		event = AgentEvent.willWaitforTable;
		stateChanged();
	}
	
	public void msgHereIsMenu(RestaurantFiveWaiter w, RestaurantFiveMenu m) {
		event = AgentEvent.lookingAtMenu;
		waiter = w;
		menu = m;
		stateChanged();
	}
	
	public void msgFollowMeToTable(RestaurantFiveWaiter w, int tableNumber) {
		tableNum = tableNumber;
		event = AgentEvent.followWaiter;
		waiter = w;
		stateChanged();
	}

//	public void msgAnimationFinishedGoToSeat() {
//		//from animation
//		event = AgentEvent.seated;
//		stateChanged();
//	}
//	
//	public void msgAnimationFinishedGoToCashier() {
//		//from animation
//		atDest.release();
//		stateChanged();
//	}
	
	public void msgWantToOrder() {
		state = AgentState.WantToOrder;
		stateChanged();
	}
	
//	public void msgWhatWouldYouLike() {
//		if(state == AgentState.Reordering) {
//			if(food.equals(menu.getLeastExpensiveItem())) {
//				if(money < menu.getSecondLeastExpensivePrice()) {
//					//can only afford cheapest food, out of cheapest food, so cust leaves
//					CheckToLeaveBecauseTooExpensive();
//					
//				}
//			}
//			state = AgentState.Reordered;
//			stateChanged();
//		}
//		else {
//			state = AgentState.Ordering;
//			stateChanged();
//		}
//
//	}
//	
//	public void msgPleaseReorder() {
//		state = AgentState.Reordering;
//		stateChanged();
//	}
//	
//	public void msgHereIsYourFood(String f) {
//		state = AgentState.GettingFood;
//		food = f;
//		stateChanged();
//	}
//	
//	public void msgDoneEating() {
//		state = AgentState.DoneEating;
//		stateChanged();
//	}
//
//	@Override
//	public void msgHereIsCheck(double checkIn) {
//		state = AgentState.GoingToPay;
//		check = checkIn;
//		stateChanged();
//	}
//	
//	public void msgHereIsChange(double changeIn) {
//		state = AgentState.ReceivingChange;
//		change = changeIn;
//		stateChanged();
//	}
//	
//	public void msgPayNextTime() {
//		//do nothing for now
//		state = AgentState.DoneWithPaymentProcess;
//		stateChanged();
//	}
//	
//	public void msgAnimationFinishedLeaveRestaurant() {
//		//from animation
//		event = AgentEvent.doneLeaving;
//		if(state == AgentState.DoneEating) {
//		}
//		stateChanged();
//	}
//
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	@Override
	public boolean pickAndExecuteAnAction() {
		AlertLog.getInstance().logDebug(AlertTag.valueOf(restaurant.getName()), "RestaurantFiveHost: " + person.getName(), "state: " + state.toString() + ", event: " + event.toString());
		if (state == AgentState.WaitingInRestaurant && event == AgentEvent.willWaitforTable ){
			event = AgentEvent.toldHostIWillWait;
			waitForTable();
			return true;
		}
		if (state == AgentState.WaitingInRestaurant && event == AgentEvent.lookingAtMenu ){
			state = AgentState.LookingAtMenu;
			LookAtMenu();
			return true;
		}
		if (state == AgentState.LookingAtMenu && event == AgentEvent.followWaiter ){
			state = AgentState.BeingSeated;
			SitDown(tableNum);
			return true;
		}
		
		
		if(state == AgentState.WantToOrder && event == AgentEvent.seated) {
			state = AgentState.AskedToOrder;
			CallWaiter();
			return true;
		}
//		if((state == AgentState.Ordering || state == AgentState.Reordered) && event == AgentEvent.seated) {
//			OrderFood();
//			return true;
//		}
//		if(state == AgentState.GettingFood && event == AgentEvent.seated) {
//			ReceiveFood(food);
//			return true;
//		}
//
//		if(state == AgentState.DoneEating && event == AgentEvent.doneEating) {
//			//LeaveRestaurant();
//			TellWaiterIAmDone();
//			return true;
//		}
//		
//		if(state == AgentState.GoingToPay && event == AgentEvent.doneEating) {
//			state = AgentState.Paying;
//			PayCheck();
//		}
//		if(state == AgentState.ReceivingChange && event == AgentEvent.doneEating) {
//			GetChange();
//		}
//		if(state == AgentState.DoneWithPaymentProcess && event == AgentEvent.doneEating) {
//			LeaveRestaurant();
//		}
//		
//		if (state == AgentState.Leaving && event == AgentEvent.doneLeaving){
//			state = AgentState.DoingNothing;
//			return true;
//		}
//		
		return false;
	}

	
	private void waitForTable() {
		Do("Waiting for table.");
		restaurant.getHost().msgIWillWaitForTable(this);
	}

	private void SitDown(int tn) {
		
		//if(((RestaurantFiveCustomerGui)gui).DoGoToSeat()) {
		((RestaurantFiveCustomerGui)gui).DoGoToSeat();
			state = AgentState.Seated;
			AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantFiveCustomer: " + person.getName(), "Being seated by waiter.");
		//}
		
		timer.schedule(new TimerTask() {
			public void run() {
				msgWantToOrder();
				stateChanged();
			}
		},
		1000);
		
	}
	
	private void CallWaiter() {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantFiveCustomer: " + person.getName(), "Ready to order. Calling waiter.");
		//waiter.msgReadyToOrder(this);
	}
	
	private void LookAtMenu() {
		//if(money < menu.getLeastExpensivePrice()) {
			//if(CheckToLeaveBecauseTooExpensive()) {
			//	return;
			//}
		//}
		//if they can afford food, or they flake, continue to table
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantFiveCustomer: " + person.getName(), "Waiter, I will follow you.");
		waiter.msgIWillFollowYou(this);
	}
	
//	//if they leave, return true. if not (name = flake), return false
//	private boolean CheckToLeaveBecauseTooExpensive() {
//		if(!person.getName().equalsIgnoreCase("flake")) {
//			//reorder, so they are on screen, but they leave
//			if(state == AgentState.Reordering) {
//				((RestaurantFiveCustomerGui)gui).DoExitBuilding();
//				state = AgentState.Leaving;
//				event = AgentEvent.doneEating;
//			}
//			//never enter screen, but they leave
//			else {
//				event = AgentEvent.doneLeaving;
//				state = AgentState.DoingNothing;
//			}
//			waiter.msgFoodTooExpensive(this);
//			Do("I can't afford anything. Leaving restaurant. Money = " + money);
//			money += 20;
//			return true;
//		}
//		return false;
//	}
//	
//	private void OrderFood() {		
//		//reordering something cust can afford
//		if(state == AgentState.Reordered) {
//			String foodOriginal = food;
//			food = menu.getChoiceExcept(foodOriginal);
//			while(money <= menu.getPrice(food)) {
//				food = menu.getChoiceExcept(foodOriginal);
//			}
//		}
//		else if(person.getName().equalsIgnoreCase("steak") || person.getName().equalsIgnoreCase("chicken") || person.getName().equalsIgnoreCase("salad") || person.getName().equalsIgnoreCase("pizza")) {
//			food = person.getName();
//		}
//		else {
//			food = menu.RandomChoice();
//			if(money >= menu.getLeastExpensivePrice()) {
//				//only lets them buy things they can afford, if they are not flake
//				while(money <= menu.getPrice(food)) {
//					food = menu.RandomChoice();
//				}
//			}
//		}
//		state = AgentState.Ordered;
//		waiter.msgHereIsMyChoice(this, food);
//	}
//	
//	private void ReceiveFood(String f) {
//		state = AgentState.Eating;
//		Do("Eating food.");
//		
//		timer.schedule(new TimerTask() {
//			//Object cookie = 1;
//			public void run() {
//				Do("Done eating.");
//				event = AgentEvent.doneEating;
//				msgDoneEating();
//				//isHungry = false;
//				stateChanged();
//			}
//		},
//		getHungerLevel() * 1000);//how long to wait before running task
//	}
//	
//	private void TellWaiterIAmDone() {
//		state = AgentState.WaitingForCheck;
//		Do("Asking waiter for check.");
//		waiter.msgDoneEating(this);
//	}
//	
//	private void PayCheck() {
//		Do("Walking to cashier.");
//		((RestaurantFiveCustomerGui)gui).DoGoToCashier();
//		try {
//			atDest.acquire();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			print("Unexpected exception caught in CustomerAgent thread:", e);
//		}
//		waiter.msgLeaving(this);
//		if(money >= check) {
//			double moneyToPay = (double)Math.round((check) * 100) / 100;
//			money -= moneyToPay;
//			money = (double)Math.round(money * 100) / 100;
//			Do("Paying full check. Paid $" + moneyToPay);
//			cashier.msgPayCheck(this, moneyToPay);
//		}
//		else {
//			cashier.msgPayCheck(this, money);
//			Do("Paying what I can of check. Paid $" + money);
//			money = 0;
//		}
//	}
//	
//	private void GetChange() {
//		state = AgentState.DoneWithPaymentProcess;
//		money += change;
//		change = (double)Math.round(change * 100) / 100;
//		money = (double)Math.round(money * 100) / 100;
//		Do("Receiving $" + change + " in change from cashier. Money left = " + money);
//	}
//	
//	private void LeaveRestaurant() {
//		state = AgentState.Leaving;
//		money += 20;
//		//Do("Leaving table " + tableNum + ".");
//		Do("Leaving restaurant.");
//		tableNum = -1;
//		//waiter.msgLeaving(this);
//		((RestaurantFiveCustomerGui)gui).DoExitRestaurant();
//	}


	@Override
	public void atDestination() {
		atDest.release();
	}

	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterBuilding(SimSystem s) {
		restaurant = (RestaurantFiveSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantFiveCustomer: " + person.getName(), "Can't wait to eat at this restaurant!");
		
		restaurant.getHost().msgIWantFood(this);
		
		int n = restaurant.getHost().getNumWaitingCustomers();
		
		((RestaurantFiveCustomerGui) gui).DoGoToHost(n);
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		state = AgentState.WaitingInRestaurant;
		event = AgentEvent.gotHungry;

		stateChanged();
	}

}
