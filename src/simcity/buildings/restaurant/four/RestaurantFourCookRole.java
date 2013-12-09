package simcity.buildings.restaurant.four;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.buildings.restaurant.four.RestaurantFourMenu.foodType;
import simcity.gui.restaurantfour.RestaurantFourCookGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.four.RestaurantFourCook;
import simcity.interfaces.restaurant.four.RestaurantFourWaiter;

//////////////////////////////////////////////////////////////////////////

/**
 * Restaurant Four Customer Role
 * @author Kevin Allan Chan
 *
 */

public class RestaurantFourCookRole extends Role implements RestaurantFourCook {
	
	/**
	 * Data
	 */

	private RestaurantFourSystem restaurantFourSystem;
	
	private List<MyOrder> orders = Collections.synchronizedList(new ArrayList<MyOrder>());
	
	public enum Status {none, waitingAtRestaurant, waitingForConfirmation, confirmed, working};
	private Status status = Status.none;
	
	public enum OrderState {none, received, processing};
	
	private Semaphore atDest = new Semaphore(0, true);
	
	// Constructors //////////////////////////////////////////////////////////////////////////
	public RestaurantFourCookRole(PersonAgent person) {
		this.person = person;
		this.gui = new RestaurantFourCookGui(this);
	}
	
	// Accessors //////////////////////////////////////////////////////////////////////////
	public RestaurantFourSystem getSystem() {
		return restaurantFourSystem;
	}
	
	public void setSystem(RestaurantFourSystem restaurantFourSystem) {
		this.restaurantFourSystem = restaurantFourSystem;
	}
	
	public List getOrders() {
		return orders;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Messages
	 */

	public void msgGotToWork() {
		status = Status.waitingAtRestaurant;
		stateChanged();
	}
	
	public void msgStartWorking() {
		status = Status.confirmed;
		stateChanged();
	}
	
	public void msgHereIsCustomerOrder(RestaurantFourWaiter waiter, int tableNumber, foodType foodChoice) {
		orders.add(new MyOrder(waiter, tableNumber, foodChoice));
		stateChanged();
	}
	
	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Scheduler
	 */
	
	public boolean pickAndExecuteAnAction() {
		
		if (status == Status.waitingAtRestaurant) {
			status = Status.waitingForConfirmation;
			informHostOfArrival();
			return true;
		}

		if (status == Status.confirmed) {
			status = Status.working;
			workAtCookStation();
			return true;
		}
		
		if (status == Status.working) {
			if (!orders.isEmpty()) {
				synchronized(orders) {
					for (MyOrder order : orders) {
						if (order.getState() == OrderState.received) {
							order.setState(OrderState.processing);
							processOrder(order);
							return true;
						}
					}
				}
			}
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
		restaurantFourSystem.getHost().msgCookReadyForWork(this);
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurantFourSystem.getName()), "Cook: " + person.getName(), "I am ready to work!");
	}
	
	private void workAtCookStation() {
		DoGoToCookStation();
	}
	
	private void processOrder(MyOrder order) {
		
	}
	
	// Animation DoXYZ
	private void DoGoToHostLocation() {
		((RestaurantFourCookGui) gui).DoGoToHostLocation();
	}
	
	private void DoGoToCookStation() {
		((RestaurantFourCookGui) gui).DoGoToCookStationSink();
		try {
    		atDest.acquire();
    	} catch (InterruptedException e) {
    		//e.printStackTrace();
    	}
		((RestaurantFourCookGui) gui).DoGoToCookStation();
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
		msgGotToWork();
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
	public class MyOrder {
		// Data
		RestaurantFourWaiter waiter;
		int tableNumber;
		foodType foodChoice;
		OrderState state = OrderState.none;
		
		// Constructor
		MyOrder(RestaurantFourWaiter waiter, int tableNumber, foodType foodChoice) {
			this.waiter = waiter;
			this.tableNumber = tableNumber;
			this.foodChoice = foodChoice;
		}
		
		public RestaurantFourWaiter getWaiter() {
			return waiter;
		}
		
		public void setWaiter(RestaurantFourWaiter waiter) {
			this.waiter = waiter;
		}
		
		public int getTableNumber() {
			return tableNumber;
		}
		
		public void setTableNumber(int tableNumber) {
			this.tableNumber = tableNumber;
		}
		
		public foodType getFoodChoice() {
			return foodChoice;
		}
		
		public void setFoodChoice(foodType foodChoice) {
			this.foodChoice = foodChoice;
		}
		
		public OrderState getState() {
			return state;
		}
		
		public void setState(OrderState state) {
			this.state = state;
		}
		
	}
	
}

