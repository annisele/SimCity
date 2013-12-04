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
import simcity.gui.bank.BankTellerGui;
import simcity.gui.restaurantthree.RestaurantThreeCustomerGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.restaurant.three.RestaurantThreeCashier;
import simcity.interfaces.restaurant.three.RestaurantThreeCook;
import simcity.interfaces.restaurant.three.RestaurantThreeCustomer;
import simcity.interfaces.restaurant.three.RestaurantThreeHost;
import simcity.interfaces.restaurant.three.RestaurantThreeWaiter;
import simcity.test.mock.EventLog;
public class RestaurantThreeCustomerRole extends Role implements RestaurantThreeCustomer{
	private Timer timer = new Timer();
	public  EventLog log = new EventLog();
	private String name;
	private Semaphore atDest = new Semaphore(0, true);
	private RestaurantThreeSystem restaurantThreeSystem;
	public void atDestination() {
		atDest.release();
	}
	public RestaurantThreeCustomerRole(PersonAgent person) {
		this.person = person;
		this.gui = new RestaurantThreeCustomerGui(this);
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
	
}
