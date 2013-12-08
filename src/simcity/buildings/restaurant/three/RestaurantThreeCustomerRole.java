/*******************
 * RestaurantThreeCustomerRole 
 * @author levonne key
 *
 */
package simcity.buildings.restaurant.three;

import java.util.Timer;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;

import simcity.gui.restaurantthree.RestaurantThreeCustomerGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.three.RestaurantThreeCashier;
import simcity.interfaces.restaurant.three.RestaurantThreeCustomer;
import simcity.interfaces.restaurant.three.RestaurantThreeHost;
import simcity.interfaces.restaurant.three.RestaurantThreeWaiter;
import simcity.test.mock.EventLog;
public class RestaurantThreeCustomerRole extends Role implements RestaurantThreeCustomer{
	private Timer timer = new Timer();
	public  EventLog log = new EventLog();
	private String name;
	//role correspondents
	private RestaurantThreeHost bh;
	private RestaurantThreeCashier ca;
	private RestaurantThreeWaiter waiter;
	private RestaurantThreeSystem rest;
	public enum CustomerState
	{DoingNothing, WaitingInRestaurant, BeingSeated, WaiterCalled, WaitingForFood, Eating, DoneEating, Leaving, WaitingForCheck, NoMoney};
	private CustomerState state = CustomerState.DoingNothing;//The start state

	public enum CustomerEvent 
	{none, wait, gotHungry, seated, stayOrLeave, decidedChoice, waiterToTakeOrder, served, finishedEating,checkReceived, notWaiting, keepWaiting, doneLeaving, needReorder, leaveBecauseOfNoMoney, payNextTime, requestReorder};
	CustomerEvent event = CustomerEvent.none;
	
	private Semaphore atDest = new Semaphore(0, true);
	private RestaurantThreeSystem restaurantThreeSystem;
	public void atDestination() {
		atDest.release();
	}
	public RestaurantThreeCustomerRole(PersonAgent person) {
		this.person = person;
		this.gui = new RestaurantThreeCustomerGui(this);
	}
	public String getCustomerName() {
		return person.getName();
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void exitBuilding() {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurantThreeSystem.getName()), "Restaurant Three Waiter: " + person.getName(), "Leaving restaurant three");	
		gui.DoExitBuilding();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			
		}
		restaurantThreeSystem.exitBuilding(this);
		person.roleFinished();	
	}

	@Override
	public void enterBuilding(SimSystem s) {
		rest = (RestaurantThreeSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(rest.getName()), "Restaurant 3 Customer: " + person.getName(), "I've arrived at restaurant 3");	
		rest.getRestaurantThreeHost().msgIWantFood(this);
		int n = rest. getRestaurantThreeHost().numWaitingCustomers();
		AlertLog.getInstance().logMessage(AlertTag.valueOf(rest.getName()), "Restaurant 3 Customer: " + person.getName(), "Num waiting customers: " + n);
		
		((RestaurantThreeCustomerGui) gui).DoGoToHost(n);
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			
		}
		
		event = CustomerEvent.gotHungry;
		

		stateChanged();
	}
	@Override
	public RestaurantThreeHost getRestaurantThreeHost() {
		// TODO Auto-generated method stub
		return bh;
	}
	@Override
	public void setRestaurantThreeHost(RestaurantThreeHost bh) {
		this.bh = bh;
	}
	public void setWaiter(RestaurantThreeWaiter w) {
		this.waiter = waiter;
	}
	public void setCashier(RestaurantThreeCashier ca) {
		this.ca = ca;
	}
	@Override
	public void msgRestaurantFull() {
		event = CustomerEvent.wait;
		stateChanged();
	}
	
}
