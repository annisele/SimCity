package simcity.buildings.restaurant.six;

import java.util.Random;
import java.util.Timer;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.buildings.restaurant.five.RestaurantFiveSystem;
import simcity.buildings.restaurant.five.RestaurantFiveCustomerRole.AgentEvent;
import simcity.buildings.restaurant.five.RestaurantFiveCustomerRole.AgentState;
import simcity.gui.restaurantfive.RestaurantFiveCustomerGui;
import simcity.gui.restaurantsix.RestaurantSixCustomerGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.six.RestaurantSixCashier;
import simcity.interfaces.restaurant.six.RestaurantSixCustomer;
import simcity.interfaces.restaurant.six.RestaurantSixHost;
import simcity.interfaces.restaurant.six.RestaurantSixWaiter;

public class RestaurantSixCustomerRole extends Role implements RestaurantSixCustomer{

	private RestaurantSixSystem restaurant = null;
	Timer timer = new Timer();
	private String myOrder;// = "Chicken";
	private double myBill;
	Random choiceGenerator = new Random();
	private Semaphore atDest = new Semaphore(0, true);
	
	// This is the table the customer is currently going to
	private int myTable = -1;
	private int waitingX = 0;
	private int waitingY = 0;
	private RestaurantSixMenu myMenu;

	// agent correspondents
	private RestaurantSixHost host;
	private RestaurantSixWaiter waiter;
	private RestaurantSixCashier cashier;

	//    private boolean isHungry = false; //hack for gui
	public enum AgentState {
		DoingNothing, WaitingInRestaurant, BeingSeated, Seated, ReadyToOrder, Ordered, Eating, DoneEating, WaitingForCheck, Paying, Leaving
	};
	
	private AgentState state = AgentState.DoingNothing;//The start state

	public enum AgentEvent {
		none, gotHungry, followWaiter, seated, noTables, readyToOrder, ordering, reOrdering, ordered, foodArriving, doneEating, gotCheck, doneLeaving, donePaying
	};
	
	AgentEvent event = AgentEvent.none;
	
	public RestaurantSixCustomerRole(PersonAgent p){
		person = p;
		this.gui = new RestaurantSixCustomerGui(this);		
	}

	// Messages
	@Override
	public void msgNoTables(int x, int y) {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantSixCustomer: " + person.getName(), 
				"I was told there are no tables here!");
		
	}
	
	// Scheduler
	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantSixCustomer: " + person.getName(), 
				"Clayton in da sched");
		return false;
	}
	
	// Actions
	
	
	// Utilities
	
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
		restaurant = (RestaurantSixSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantSixCustomer: " + person.getName(), 
				"Can't wait to eat at this restaurant!");

		//int n = restaurant.getHost().getNumWaitingCustomers();

		((RestaurantSixCustomerGui) gui).DoGoToHost();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		
		restaurant.getHost().msgIWantFood(this);

		state = AgentState.DoingNothing;
		event = AgentEvent.gotHungry;

		stateChanged();
	}

	@Override
	public String getCustomerName() {
		// TODO Auto-generated method stub
		return null;
	}

	

	
}
