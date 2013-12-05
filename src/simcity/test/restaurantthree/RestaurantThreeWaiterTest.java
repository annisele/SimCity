package simcity.test.restaurantthree;

import junit.framework.TestCase;
import simcity.PersonAgent;
import simcity.buildings.restaurant.three.RestaurantThreeSystem;
import simcity.buildings.restaurant.three.RestaurantThreeWaiterRole;
import simcity.gui.SimCityGui;
import simcity.test.mock.LoggedEvent;
import simcity.test.restaurantthree.mock.MockRestaurantThreeHost;

/*******************
 * RestaurantThreeWaiterTest 
 * @author levonne key
 *
 */
public class RestaurantThreeWaiterTest extends TestCase {

	RestaurantThreeWaiterRole waiter;
	PersonAgent person;
	RestaurantThreeSystem restaurant;
	SimCityGui scg;
	
	MockRestaurantThreeHost host;
	
	public void setUp() throws Exception {
		super.setUp();
		person = new PersonAgent("PersonAgent");
		waiter = new RestaurantThreeWaiterRole(person);
		scg = new SimCityGui();
		restaurant = new RestaurantThreeSystem(scg);
	}
	

	// one waiter enters work and contacts host scenario 
	public void testOneWaiterToWork() {
		
	}

}
