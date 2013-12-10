package simcity.buildings.restaurant.six;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Semaphore;
import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.restaurantsix.RestaurantSixCashierGui;
import simcity.gui.restaurantsix.RestaurantSixHostGui;
import simcity.gui.restaurantsix.RestaurantSixWaiterGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.six.RestaurantSixCashier;
import simcity.interfaces.restaurant.six.RestaurantSixCook;
import simcity.interfaces.restaurant.six.RestaurantSixCustomer;
import simcity.interfaces.restaurant.six.RestaurantSixHost;
import simcity.interfaces.restaurant.six.RestaurantSixWaiter;

public class RestaurantSixWaiterRole extends Role implements RestaurantSixWaiter {
	
	RestaurantSixSystem restaurant;
	enum CustomerState {
		waiting, seated, readyToOrder, waitingForFood, reOrder, eating, waitingForCheck, hasCheck, left;
	}
	
	private String currentOrder = "";
	private List<MyCustomer> myCustomers = new ArrayList<MyCustomer>();
	private List<Order> myOrders = new ArrayList<Order>();
	private List<Order> myCookedOrders = new ArrayList<Order>();
	private List<Check> myChecks = new ArrayList<Check>();
	private RestaurantSixMenu myMenu;
	

	private String name;
	private RestaurantSixHost host;
	private RestaurantSixCook cook;
	private RestaurantSixCashier cashier;
	/*private Semaphore atTable = new Semaphore(0,true);
	private Semaphore atLobby = new Semaphore(1,true);
	private Semaphore atWaitingArea = new Semaphore(0, true);
	private Semaphore atKitchen = new Semaphore(0, true);
	private boolean atLobbyNow = true;
	private boolean atKitchenNow = false;
	private boolean atWaitingAreaNow = false;
	
	private boolean wantToBreak = false;
	private boolean waitingToBreak = false;*/
	private Timer timer = new Timer();
	private Sempaphore atDest = new Semaphore(0, true);

	public RestaurantSixWaiterRole(PersonAgent p) {
		person = p;
		this.gui = new RestaurantSixWaiterGui(this);
	}
	
	// Messages
	
	@Override
	public void msgSitAtTable(RestaurantSixCustomer customer, int tableNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgYouCanBreak(boolean b) {
		// TODO Auto-generated method stub
		
	}
	
	

	// Scheduler
	
	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	// Actions
	
	// Utilities
	
	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterBuilding(SimSystem s) {
		restaurant = (RestaurantSixSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(restaurant.getName()), "RestaurantSixWaiter: " + person.getName(), "Ready to work at the restaurant!");
		
		((RestaurantSixWaiterGui) gui).DoGoToWaitingArea();
		
	}
	
	public void setHost(RestaurantSixHost host) {
		this.host = host;
	}
	
	public void setCook(RestaurantSixCook cook) {
		this.cook = cook;
		//cook.msgNeedMenu(this);
	}
	
	public void setCashier(RestaurantSixCashier cashier) {
		this.cashier = cashier;
	}
	
	public String getWaiterName() {
		return name;
	}

	public String getName() {
		return name;
	}
	
	
	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
	}
	
	private class MyCustomer {
		RestaurantSixCustomer c;
		String choice;
		CustomerState state;
		int tableNumber;
		boolean atTable;
		
		MyCustomer(RestaurantSixCustomer cust, int table) {
			c = cust;
			tableNumber = table;
			atTable = false;
		}
	}
	private class Order {
		String choice;
		int tableNumber;
		int location;
		MyCustomer cust;
		
		Order(MyCustomer c, String ch) {
			cust = c;
			tableNumber = c.tableNumber;
			choice = ch;
		}
		Order(MyCustomer c, String ch, int loc) {
			cust = c;
			tableNumber = c.tableNumber;
			choice = ch;
			location = loc;
		}
	}
	
	private class Check {
		double amount;
		MyCustomer cust;
		
		Check(MyCustomer c, double am) {
			cust = c;
			amount = am;
		}
		void confirm() {
		}
	}

	

}
