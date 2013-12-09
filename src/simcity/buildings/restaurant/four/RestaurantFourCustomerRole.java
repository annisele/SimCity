package simcity.buildings.restaurant.four;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.buildings.restaurant.four.RestaurantFourMenu.foodType;
import simcity.gui.restaurantfour.RestaurantFourCustomerGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.four.RestaurantFourCustomer;
import simcity.interfaces.restaurant.four.RestaurantFourWaiter;


//////////////////////////////////////////////////////////////////////////

/**
 * Restaurant Four Customer Role
 * @author Kevin Allan Chan
 *
 */

public class RestaurantFourCustomerRole extends Role implements RestaurantFourCustomer {
	
	/**
	 * Data
	 */

	private int tableNumber;
	
	private RestaurantFourWaiter waiter;
	private RestaurantFourMenu menu;
	private foodType foodChoice;
	
	private RestaurantFourSystem restaurantFourSystem;
	
	public enum State {none, waitingAtRestaurant, walkingToTable, thinkingOfOrder, callingWaiter, ordering};
	private State state = State.none;
	
	public enum Event {none, gotHungry, metWaiter, gotToTable, doneThinkingOfOrder, waiterArrivedToTakeOrder};
	private Event event = Event.none;
	
	private Semaphore atDest = new Semaphore(0, true);
	private Timer timer = new Timer();
	private Random generator = new Random();
	
	// Constructors //////////////////////////////////////////////////////////////////////////
	
	public RestaurantFourCustomerRole(PersonAgent person) {
		this.person = person;
		this.gui = new RestaurantFourCustomerGui(this);
	}
	
	// Accessors //////////////////////////////////////////////////////////////////////////
	
	public PersonAgent getPersonAgent() {
		return person;
	}
	
	public void setPersonAgent(PersonAgent person) {
		this.person = person;
	}
	
	public RestaurantFourSystem getSystem() {
		return restaurantFourSystem;
	}
	
	public void setSystem(RestaurantFourSystem restaurantFourSystem) {
		this.restaurantFourSystem = restaurantFourSystem;
	}
	
	public int getTableNumber() {
		return tableNumber;
	}
	
	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}
	
	public RestaurantFourWaiter getWaiter() {
		return waiter;
	}
	
	public void setWaiter(RestaurantFourWaiter waiter) {
		this.waiter = waiter;
	}
	
	public RestaurantFourMenu getMenu() {
		return menu;
	}
	
	public void setMenu(RestaurantFourMenu menu) {
		this.menu = menu;
	}
	
	public foodType getFoodChoice() {
		return foodChoice;
	}
	
	public void setFoodChoice(foodType foodChoice) {
		this.foodChoice = foodChoice;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public Event getEvent() {
		return event;
	}
	
	public void setEvent(Event event) {
		this.event = event;
	}
	
	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Messages
	 */

	public void msgGotHungry() {	// called after entering a building
		event = Event.gotHungry;
		stateChanged();
	}
	
	public void msgFollowMeToTable(RestaurantFourWaiter waiter, int tableNumber, RestaurantFourMenu menu) {
		event = Event.metWaiter;
		this.waiter = waiter;
		this.tableNumber = tableNumber;
		this.menu = menu;
		stateChanged();
	}
	
	public void msgArrivedAtTable() {
		event = Event.gotToTable;
		stateChanged();
	}
	
	public void msgDoneThinkingOfOrder() {
		event = Event.doneThinkingOfOrder;
		stateChanged();
	}
	
	public void msgWhatDoYouWantToOrder() {
		event = Event.waiterArrivedToTakeOrder;
		stateChanged();
	}
	
	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Scheduler
	 */
	
	public boolean pickAndExecuteAnAction() {

		if (state == State.none && event == Event.gotHungry) {
			state = State.waitingAtRestaurant;
			informHostOfArrival();
			return true;
		}
		
		if (state == State.waitingAtRestaurant && event == Event.metWaiter){
			state = State.walkingToTable;
			followWaiterToTable();
			return true;
		}
		
		if (state == State.walkingToTable && event == Event.gotToTable) {
			state = State.thinkingOfOrder;
			thinkOfOrder();
			return true;
		}
		
		if (state == State.thinkingOfOrder && event == Event.doneThinkingOfOrder) {
			state = State.callingWaiter;
			callWaiterForOrder();
			return true;
		}
		
		if (state == State.callingWaiter && event == Event.waiterArrivedToTakeOrder) {
			state = State.ordering;
			tellWaiterOrder();
			return true;
		}
		
		return false;
	}



	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Actions
	 */
	
	private void informHostOfArrival() {
		DoGoToHostLocation();
		try {
    		atDest.acquire();
    	} catch (InterruptedException e) {
    		//e.printStackTrace();
    	}
		restaurantFourSystem.getHost().msgImHungry(this);
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurantFourSystem.getName()), "Customer: " + person.getName(), "I am hungry!");	
	}
	
	private void followWaiterToTable() {
		DoGoToTable(tableNumber);
		try {
    		atDest.acquire();
    	} catch (InterruptedException e) {
    		//e.printStackTrace();
    	}
		msgArrivedAtTable();
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurantFourSystem.getName()), "Customer: " + person.getName(), "I am at table " + tableNumber);
	}
	
	private void thinkOfOrder() {
		timer.schedule(new TimerTask() {
			public void run() {
				int i = generator.nextInt(4) + 1;
				foodChoice = menu.getFoodType(i);
				msgDoneThinkingOfOrder();
				AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurantFourSystem.getName()), "Customer: " + person.getName(), "Thought of order.");
			}
		}, 1000);
	}
	
	private void callWaiterForOrder() {
		waiter.msgImReadyToOrder(this);
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurantFourSystem.getName()), "Customer: " + person.getName(), "Waiter! I'm ready to order.");
	}
	
	private void tellWaiterOrder() {
		waiter.msgThisIsMyOrder(this, foodChoice);
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurantFourSystem.getName()), "Customer: " + person.getName(), "I want " + foodChoice);
	}
	
	// Animation DoXYZ
	private void DoGoToHostLocation() {
		((RestaurantFourCustomerGui) gui).DoGoToHostLocation();
	}
	
	private void DoGoToTable(int tableNumber) {
		((RestaurantFourCustomerGui) gui).DoGoToTable(tableNumber);
	}
	
	// Utilities //////////////////////////////////////////////////////////////////////////
	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void enterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		this.restaurantFourSystem = (RestaurantFourSystem)s;
		msgGotHungry();
		try {
    		atDest.acquire();
    	} catch (InterruptedException e) {
    		//e.printStackTrace();
    	}
	}

	@Override
	public void atDestination() {
		atDest.release();
	}
	
	// Utility Classes //////////////////////////////////////////////////////////////////////////

	
}

