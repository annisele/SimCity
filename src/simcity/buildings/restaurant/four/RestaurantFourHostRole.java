package simcity.buildings.restaurant.four;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.restaurantfour.RestaurantFourHostGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.four.RestaurantFourCook;
import simcity.interfaces.restaurant.four.RestaurantFourCustomer;
import simcity.interfaces.restaurant.four.RestaurantFourHost;
import simcity.interfaces.restaurant.four.RestaurantFourWaiter;


//////////////////////////////////////////////////////////////////////////

/**
 * Restaurant Four Host Role
 * @author Kevin Allan Chan
 *
 */

public class RestaurantFourHostRole extends Role implements RestaurantFourHost {
	
	/**
	 * Data
	 */
	
	private RestaurantFourSystem restaurantFourSystem;
	private List<RestaurantFourWaiter> waiters = Collections.synchronizedList(new ArrayList<RestaurantFourWaiter>());
	private List<RestaurantFourCustomer> customers = Collections.synchronizedList(new ArrayList<RestaurantFourCustomer>());
	private RestaurantFourCook cook = null;
	
	public enum Status {none, waitingAtRestaurant, working};
	private Status status = Status.none;
	
	private Semaphore atDest = new Semaphore(0, true);
	
	// Constructors //////////////////////////////////////////////////////////////////////////
	
	public RestaurantFourHostRole(PersonAgent person) {
		this.person = person;
		this.gui = new RestaurantFourHostGui(this);
	}
	
	// Accessors //////////////////////////////////////////////////////////////////////////
	
	public PersonAgent getPerson() {
		return person;
	}
	
	public void setPerson(PersonAgent person) {
		this.person = person;
	}
	
	public RestaurantFourSystem getSystem() {
		return restaurantFourSystem;
	}
	
	public void setSystem(RestaurantFourSystem restaurantFourSystem) {
		this.restaurantFourSystem = restaurantFourSystem;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List getWaitersList() {
		return waiters;
	}
	
	public List getCustomersList() {
		return customers;
	}
	
	public RestaurantFourCook getCook() {
		return cook;
	}
	
	public void setCook(RestaurantFourCook cook) {
		this.cook = cook;
	}
	
	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Messages
	 */

	public void msgGotToWork() {
		status = status.waitingAtRestaurant;
		stateChanged();
	}

	public void msgWaiterReadyForWork(RestaurantFourWaiter waiter) {
		waiters.add(waiter);
		stateChanged();
	}
	
	public void msgCookReadyForWork(RestaurantFourCook cook) {
		this.cook = cook;
		stateChanged();
	}
	
	public void msgImHungry(RestaurantFourCustomer customer) {
		customers.add(customer);
		stateChanged();
	}
	
	
	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Scheduler
	 */
	
	public boolean pickAndExecuteAnAction() {

		if (status == Status.waitingAtRestaurant) {
			status = Status.working;
			setupForWork();
			return true;
		}

		if (cook != null) {
			welcomeCook(cook);
			return true;
		}
		
		if (!waiters.isEmpty()) {
			welcomeWaiter(waiters.get(0));
			return true;
		}
		
		if (!customers.isEmpty()) {
			if (!restaurantFourSystem.getWaitersList().isEmpty()) {
				if(restaurantFourSystem.freeTableExists()) {
					askWaiterToSeatCustomer(customers.get(0), restaurantFourSystem.getLeastBusyWaiter(), restaurantFourSystem.getFreeTableNumber());
					return true;
				}
				else {
					//informCustomerOfUnavailableService();
				}
			}
			else {
				//informCustomerOfUnavailableService();
			}
		}
		
		return false;
	}



	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Actions
	 */
	
	private void setupForWork() {
		restaurantFourSystem.setHost(this);
		DoGoToHostStation();
		// Let directory know that the restaurant is open
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurantFourSystem.getName()), "Host: " + person.getName(), "I am ready to work!");
	}

	private void welcomeCook(RestaurantFourCook cook) {
		restaurantFourSystem.setCook(cook);
		cook.msgStartWorking();
		this.cook = null;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurantFourSystem.getName()), "Host: " + person.getName(), "Go to your cook station!");
	}
	
	private void welcomeWaiter(RestaurantFourWaiter waiter) {
		restaurantFourSystem.addWaiter(waiter);
		waiter.msgStartWorking();
		waiters.remove(waiter);
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurantFourSystem.getName()), "Host: " + person.getName(), "Go to your waiter station!");

	}

	private void askWaiterToSeatCustomer(RestaurantFourCustomer customer, RestaurantFourWaiter waiter, int tableNumber) {
		restaurantFourSystem.updateTableOccupants(customer, waiter, tableNumber);
		restaurantFourSystem.updateCustomerLoadOfWaiter(waiter);
		waiter.msgSeatCustomerAtTable(customer, tableNumber, restaurantFourSystem.getMenu());
		customers.remove(customer);
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurantFourSystem.getName()), "Host: " + person.getName(), "Please seat " + customer.getPersonAgent().getName() + " at table " + tableNumber + ", " + waiter.getPerson().getName());
	}
	
	// Animation DoXYZ()
	private void DoGoToHostStation() {
		((RestaurantFourHostGui) gui).DoGoToHostStation();
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
	}

	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
	}
	
	// Utility Classes //////////////////////////////////////////////////////////////////////////
	
}

