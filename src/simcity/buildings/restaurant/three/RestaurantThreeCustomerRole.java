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
import simcity.gui.restauranttwo.RestaurantTwoCustomerGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.three.RestaurantThreeCashier;
import simcity.interfaces.restaurant.three.RestaurantThreeCustomer;
import simcity.interfaces.restaurant.three.RestaurantThreeHost;
import simcity.interfaces.restaurant.three.RestaurantThreeWaiter;
import simcity.test.mock.EventLog;
public class RestaurantThreeCustomerRole extends Role implements RestaurantThreeCustomer{
	private Timer timer = new Timer();
	private double customerCash, customerCheck;
	public  EventLog log = new EventLog();
	private String name;
	private int tableNumber;
	//role correspondents
	private RestaurantThreeHost bh;
	private RestaurantThreeCashier ca;
	private RestaurantThreeWaiter waiter;
	private RestaurantThreeSystem rest;
	public enum CustomerState
	{DoingNothing, WaitingInRestaurant, BeingSeated, WaiterCalled, WaitingForFood, Eating, DoneEating, Leaving, WaitingForCheck, NoMoney};
	private CustomerState state = CustomerState.DoingNothing;//The start state
    private RestaurantThreeOrderWheel OrderWheel= new RestaurantThreeOrderWheel();
	private String choice;
    public enum CustomerEvent 
	{none, wait, gotHungry, followWaiter, seated, stayOrLeave, decidedChoice, waiterToTakeOrder, served, finishedEating,checkReceived, notWaiting, keepWaiting, doneLeaving, needReorder, leaveBecauseOfNoMoney, payNextTime, requestReorder};
	CustomerEvent event = CustomerEvent.none;
	
	private Semaphore atDest = new Semaphore(0, true);
	public void atDestination() {
		atDest.release();
	}
	public RestaurantThreeCustomerRole(PersonAgent p) {
		person = p;
		this.gui = new RestaurantThreeCustomerGui(this);
	}
	public String getCustomerName() {
		return person.getName();
	}

	public void msgSitAtTable(RestaurantThreeWaiter w, int tableNum) {
		waiter = w;
		event = CustomerEvent.seated;
		stateChanged();
	}
	public void msgAnimationFinishedGoToSeat() {
		event = CustomerEvent.seated;
		stateChanged();
	}
	public void msgGotHungry() {
		event = CustomerEvent.gotHungry;
		stateChanged();
	}
	public void msgHereIsYourFood(String choice) {
		event = CustomerEvent.served;
		stateChanged();
	}
	public void msgFinishedFood() {
		event = CustomerEvent.finishedEating;
		stateChanged();
	}
	public void msgWhatsYourOrder() {
		event = CustomerEvent.decidedChoice;
		stateChanged();
	}
	@Override
	public boolean pickAndExecuteAnAction() {
		if (state == CustomerState.DoingNothing && event == CustomerEvent.gotHungry ){
            state = CustomerState.WaitingInRestaurant;
            informHostOfArrival();
            return true;
    } 
		  if (state == CustomerState.WaitingInRestaurant && event == CustomerEvent.followWaiter ){
              state = CustomerState.BeingSeated;
              SitDown();
              return true;
      }
		return false;
	}
	//actions
	public void informHostOfArrival() {
		
	}
	public void SitDown() {
		 AlertLog.getInstance().logMessage(AlertTag.valueOf(rest.getName()), "RestaurantCustomer: " + person.getName(),"Being seated. Going to table.");
         ((RestaurantThreeCustomerGui)gui).DoGoToSeat(tableNumber);//hack; only osne table
	}
	@Override
	public void exitBuilding() {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(rest.getName()), "Restaurant Three Waiter: " + person.getName(), "Leaving restaurant three");	
		gui.DoExitBuilding();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			
		}
		rest.exitBuilding(this);
		person.roleFinished();	
	}

	@Override
	public void enterBuilding(SimSystem s) {
		rest = (RestaurantThreeSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(rest.getName()), "Restaurant 3 Customer: " + person.getName(), "I've arrived at restaurant 3");	
		rest.getRestaurantThreeHost().msgIWantFood(this);
		int n = rest.getRestaurantThreeHost().getWaitingCustomers();
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
	public int getTableNumber() {
		return tableNumber;
	}
	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}
}
