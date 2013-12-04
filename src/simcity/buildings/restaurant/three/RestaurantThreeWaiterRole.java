package simcity.buildings.restaurant.three;

import java.util.Timer;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.bank.BankTellerGui;
import simcity.gui.restaurantthree.RestaurantThreeWaiterGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.three.RestaurantThreeCashier;
import simcity.interfaces.restaurant.three.RestaurantThreeCook;
import simcity.interfaces.restaurant.three.RestaurantThreeHost;
import simcity.interfaces.restaurant.three.RestaurantThreeWaiter;
import simcity.test.mock.EventLog;
public class RestaurantThreeWaiterRole extends Role implements RestaurantThreeWaiter{
	private Timer timer = new Timer();
	public  EventLog log = new EventLog();
	private String name;
	private Semaphore atDest = new Semaphore(0, true);
	private RestaurantThreeSystem restaurantThreeSystem;
	public void atDestination() {
		atDest.release();
	}
	public RestaurantThreeWaiterRole(PersonAgent person, RestaurantThreeSystem rest) {
		this.person = person;
		this.gui = new RestaurantThreeWaiterGui(this);
		this.restaurantThreeSystem = rest;
	}
	@Override
	public void setHost(RestaurantThreeHost host) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCook(RestaurantThreeCook cook) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCashier(RestaurantThreeCashier cashier) {
		// TODO Auto-generated method stub
		
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
			e.printStackTrace();
		}
		restaurantThreeSystem.exitBuilding(this);
		person.roleFinished();
		
	}

	@Override
	public void enterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		
	}
	
	public class MyCustomer {
		
	}
}




	


