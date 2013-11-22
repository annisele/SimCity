package simcity.buildings.restaurant.four;

import java.util.*;

import simcity.PersonAgent;
import simcity.Role;
import simcity.gui.restaurantfour.RestaurantFourCustomerGui;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Restaurant Four Customer Role
 */

public class RestaurantFourCustomerRole extends Role {
	
	// Data ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// From PersonAgent
	private PersonAgent person;
	private String name;
	private double cash;
	
	private double minPrice;
	private double maxPrice;
	
	private int tableNumber;
	private String choice;
	private double payment;
	
	private RestaurantFourCustomerGui gui;
	
	Timer timer = new Timer();
	Random generator = new Random();

	// Agent correspondents
	private RestaurantFourHostRole host;
	private RestaurantFourWaiterRole waiter;
	private RestaurantFourCashierRole cashier;

	// States and events
	public enum AgentState {
		doingNothing,
		hungryAtRestaurant,
		choiceToLeave,
		waitingAtRestaurant,
		beingSeated,
		seated,
		waitingForWaiter,
		waitingForFood,
		eating,
		checkPlease,
		walkingToCashier,
		paying,
		leaving
	};

	private AgentState state = AgentState.doingNothing;

	public enum AgentEvent {
		none,
		gotHungry, 
		restaurantFull,
		willWait,
		followHost, 
		sittingDown, 
		gotSeated, 
		rethinking,
		thoughtOfOrder, 
		ordered, 
		gotFood, 
		doneEating, 
		gotCheck,
		gotToCashier,
		paid,
		left,
		cantPay
	};

	AgentEvent event = AgentEvent.none;
	
	// Constructors ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public RestaurantFourCustomerRole(PersonAgent personAgent) {

	}

	/*
	public CustomerAgent(String name){
		super();
		this.name = name;
		setCash(stats.getStartCash());
	}
	
	public CustomerAgent(String name, Boolean hunger) {
		super();
		this.name = name;
		if (hunger) {										// check to see if customer is already hungry when declared
			event = AgentEvent.gotHungry;
			stateChanged();
		}
		setCash(stats.getStartCash());
	}
	*/

	// Messages ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	public void msgGotHungry() {								// called by something... when customer enters restaurant
		event = AgentEvent.gotHungry;
		stateChanged();
	}
	
	public void msgFollowMe(double unpaidBalance, Menu m) {		// called by waiter when asked to sit
		event = AgentEvent.followHost;
		stateChanged();
	}

	public void msgArrivedAtSeatGui() { 						// called by gui when arrived at seat
		event = AgentEvent.gotSeated;
		stateChanged();
	}

	public void msgIHaveThoughtOfOrder() {						// called by customer self when finish thinking of order
		event = AgentEvent.thoughtOfOrder;
		stateChanged();
	}
	
	public void msgWhatDoYouWant() {							// called by waiter when waiter arrives to take order
		event = AgentEvent.ordered;
		stateChanged();
	}
	
		/*
		public void msgThinkAgain() {							// NON-NORM called by waiter in the case that order is not available
			if (cash >= minPrice && cash < maxPrice) {
				print("Not available? Too bad that's the only one I can afford.");
				event = AgentEvent.cantPay;
				stateChanged();
			}
			else {
				print("Not available? I'll choose another one.");
				event = AgentEvent.rethinking;
				stateChanged();
			}
		}
		*/

	public void msgHereIsYourFood(int choice) {					// called by waiter when food is being delivered
		event = AgentEvent.gotFood;
		stateChanged();
	}

	public void msgDoneEating() {								// called by customer self when finished eating
		event = AgentEvent.doneEating;
		stateChanged();
	}
	
	public void msgHereIsYourBill(double price) {				// called by waiter when bill arrives
		this.payment = price;
		event = AgentEvent.gotCheck;
		stateChanged();
	}
	
	public void msgGotToCashierGui() {							// called by gui when arrived in front of cashier
		event = AgentEvent.gotToCashier;
		stateChanged();
	}
	
	public void msgYouAreGoodToGo() {							// called by cashier when finish settling bill
		event = AgentEvent.paid;
		stateChanged();
	}
	
	public void msgLeftRestaurantGui() {						// called by gui when finished leaving
		event = AgentEvent.left;
		stateChanged();
	}

		/*
		public void msgRestaurantFull() {						// NON-NORM: called by host when restaurant is full
			event = AgentEvent.restaurantFull;
			stateChanged();
		}
		
		public void msgWillWait() {								// NON-NORM: called by ... when customer chooses to wait
			event = AgentEvent.willWait;
			stateChanged();
		}
		
		public void msgCantPayLeaving() {						// NON-NORM: called by ... when customer cannot pay for anything
			event = AgentEvent.cantPay;
			stateChanged();
		}
		*/

	// Scheduler ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean pickAndExecuteAnAction() {

		if (state == AgentState.doingNothing && event == AgentEvent.gotHungry) {
			state = AgentState.hungryAtRestaurant;
			goToRestaurant();
			return true;
		}
	
		/* NON-NORM
		if (state == AgentState.hungryAtRestaurant && event == AgentEvent.restaurantFull) {
			state = AgentState.choiceToLeave;
			leaveOrNot();
			return true;
		}
		
		if (state == AgentState.choiceToLeave && event == AgentEvent.willWait) {
			state = AgentState.waitingAtRestaurant;
			return true;
		}
		*/

		if (state == AgentState.hungryAtRestaurant && event == AgentEvent.followHost) {
			state = AgentState.beingSeated;
			sitDown();
			return true;
		}
		
		/* NON-NORM
		if (state == AgentState.waitingAtRestaurant && event == AgentEvent.followHost ){
			state = AgentState.beingSeated;
			sitDown();
			return true;
		}
		*/

		if (state == AgentState.beingSeated && event == AgentEvent.gotSeated){
			state = AgentState.seated;
			thinkOrder();
			return true;
		}
		
		/* NON-NORM
		if (state == AgentState.seated && event == AgentEvent.cantPay) {
			state = AgentState.leaving;
			cantPayLeaving();
			return true;
		}
		*/
		
		if (state == AgentState.seated && event == AgentEvent.thoughtOfOrder) {
			state = AgentState.waitingForWaiter;
			readyToOrder();
			return true;
		}
		
		if (state == AgentState.waitingForWaiter && event == AgentEvent.ordered) {
			state = AgentState.waitingForFood;
			myOrder();
			return true;
		}
		
		/*
		if (state == AgentState.waitingForFood && event == AgentEvent.rethinking) {
			state = AgentState.seated;
			thinkOrder();
			return true;
		}
		
		if (state == AgentState.waitingForFood && event == AgentEvent.cantPay) {
			state = AgentState.leaving;
			cantPayLeaving();
			return true;
		}
		*/
		
		if (state == AgentState.waitingForFood && event == AgentEvent.gotFood) {
			state = AgentState.eating;
			eatFood();
			return true;
		}

		if (state == AgentState.eating && event == AgentEvent.doneEating) {
			state = AgentState.checkPlease;
			requestBill();
			return true;
		}
		
		if (state == AgentState.checkPlease && event == AgentEvent.gotCheck) {
			state = AgentState.walkingToCashier;
			walkToCashier();
			return true;
		}
		
		if (state == AgentState.walkingToCashier && event == AgentEvent.gotToCashier) {
			state = AgentState.paying;
			payBill();
			return true;
		}
		
		if (state == AgentState.paying && event == AgentEvent.paid){
			state = AgentState.leaving;
			leaveTable();
			return true;
		}
		
		if (state == AgentState.leaving && event == AgentEvent.left){
			state = AgentState.doingNothing;
			//no action
			return true;
		}
		
		/* NON-NORM
		if (state == AgentState.choiceToLeave && event == AgentEvent.left){
			state = AgentState.doingNothing;
			return true;
		}
		*/
		
		return false;
	}

	// Actions //////////////////////////////////////////////////////////////////////////////

	private void goToRestaurant() {							// go to host
		gui.DoGoToFrontArea();
		host.msgIWantFood(this);
	}
	
	/* NON-NORM
	private void leaveOrNot() {
		int i = generator.nextInt(2);
		if (i == 0) {
			Do("Staying.");
			host.msgIWillWait(this);
			msgWillWait();
		}
		else {
			Do("Leaving.");
			host.msgImLeaving(this);
			customerGui.DoExitRestaurant();
		}
	}
	*/
	
	private void sitDown() {								// sit down at table
		gui.DoGoToTable(tableNumber);
	}

	private void thinkOrder() {								// think of order based on cash restrictions (supposedly)
		timeThinkOrder();
	}

	/* NON-NORM VERSION
	private void thinkOrder() {
		if (cash < minPrice) {
			int i = generator.nextInt(2);
			if (i == 0) {
				Do("I don't have enough, but I'll eat anyway");
				timeThinkOrder();
			}
			else {
				Do("I can't pay! Leaving.");
				msgCantPayLeaving();
			}
		}
		else {
			timeThinkOrder();
		}
	}
	*/
	
	private void timeThinkOrder() {							// think of order
		Do("Thinking of order.");
		timer.schedule(new TimerTask() {
			public void run() {
				int a = generator.nextInt(4);
				if (a == 0) {
					choice = "Steak";
				}
				else if (a == 1) {
					choice = "Chicken";
				}
				else if (a == 2) {
					choice = "Salad";
				}
				else if (a == 3) {
					choice = "Pizza";
				}
				msgIHaveThoughtOfOrder();
			}
		},
		stats.getTimeThink());
	}

	/* NON-NORM
	private void cantPayLeaving() {
		Do("Leaving.");
		waiter.msgCantPayLeaving(this);
		customerGui.DoExitRestaurant();
	}
	*/

	private void readyToOrder() {
		waiter.msgReadyToOrder(this.getTableNumber());
	}
	
	/* NON-NORM VERSION
	private void myOrder() {
		if (cash < minPrice) {
			choice = generator.nextInt(4);		// makes the random choice
			if (choice == 0) {
				food = "Steak";
			}
			else if (choice == 1) {
				food = "Chicken";
			}
			else if (choice == 2) {
				food = "Salad";
			}
			else if (choice == 3) {
				food = "Pizza";
			}
		}
		else {
			Boolean chosen = false;
			while (!chosen) {
				choice = generator.nextInt(4);		// makes the random choice
				if (choice == 0) {
					if (cash >= 15.99) {
						food = "Steak";
						chosen = true;
					}
				}
				else if (choice == 1) {
					if (cash >= 10.99) {
						food = "Chicken";
						chosen = true;
					}
				}
				else if (choice == 2) {
					if (cash >= 5.99) {
						food = "Salad";
						chosen = true;
					}
				}
				else if (choice == 3) {
					if (cash >= 8.99) {
						food = "Pizza";
						chosen = true;
					}
				}
			}
		}
		
		waiter.msgHereIsMyOrder(this, this.choice);
		Do("I want " + food);
	}
	*/

	private void myOrder() {							// give choice to waiter
		waiter.msgHereIsMyOrder(this, this.choice);
	}

	private void eatFood() {							// eat food
		Do("Eating " + food);
		//This next complicated line creates and starts a timer thread.
		//We schedule a deadline of getHungerLevel()*1000 milliseconds.
		//When that time elapses, it will call back to the run routine
		//located in the anonymous class created right there inline:
		//TimerTask is an interface that we implement right there inline.
		//Since Java does not all us to pass functions, only objects.
		//So, we use Java syntactic mechanism to create an
		//anonymous inner class that has the public method run() in it.
		timer.schedule(new TimerTask() {
			public void run() {
				msgDoneEating();
			}
		},
		stats.getHungerLevel());//getHungerLevel() * 1000);//how long to wait before running task
	}

	private void requestBill() {						// ask for bill from waiter
		waiter.msgRequestBill(this);
	}
	
	private void walkToCashier() {						// go to cashier
		gui.DoGoToCashier();
	}
	
	private void payBill() {							// give payment to cashier
		decreaseCash(payment);
		cashier.msgHereIsPayment(this, this.payment);
	}

	/* NON-NORM
	private void payBill() {
		if (cash < minPrice) {
			addUnpaidBalance(payment);
			decreaseUnpaidBalance(cash);
			setCash(0);
			Do("Can't pay now. Will pay next time. I owe you $" + getUnpaidBalance());
			cashier.msgHereIsPayment(this, 0);
		}
		else {
			decreaseCash(payment);
			Do("Paid cashier $" + payment + ". $" + cash + " left.");
			cashier.msgHereIsPayment(this, this.payment);
		}
	}
	*/
	
	private void leaveTable() {							// leave
		waiter.msgDoneEating(this);
		gui.DoExitRestaurant();
	}

	// utilities ////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getTableNumber() {
		return tableNumber;
	}
	
	
	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}
	
	
	public String getChoice() {
		return choice;
	}
	
	
	public void setChoice(String choice) {
		this.choice = choice;
	}
	
	
	public void setGui(RestaurantFourCustomerGui g) {
		gui = g;
	}
	

	public RestaurantFourCustomerGui getGui() {
		return gui;
	}
	
	
	public void setHost(RestaurantFourHostRole host) {
		this.host = host;
	}
	

	public void setWaiter(RestaurantFourWaiterRole waiter) {
		this.waiter = waiter;
	}
	
	public void setCashier(RestaurantFourCashierRole cashier) {
		this.cashier = cashier;
	}
	
	public String toString() {
		return "customer " + getName();
	}
	
	public double getCash() {
		return cash;
	}
	
	public void setCash(double cash) {
		this.cash = cash;
	}

	public void addCash(double cash) {
		this.cash = this.cash + cash;
	}
	
	public void decreaseCash(double cash) {
		this.cash = this.cash - cash;
	}
	
	/*
	public double getUnpaidBalance() {
		return unpaidBalance;
	}
	
	public void setUnpaidBalance(double unpaidBalance) {
		this.unpaidBalance = unpaidBalance;
	}
	
	public void addUnpaidBalance(double price) {
		this.unpaidBalance = this.unpaidBalance + price;
	}
	
	public void decreaseUnpaidBalance(double price) {
		this.unpaidBalance = this.unpaidBalance - price;
	}
	*/
}

