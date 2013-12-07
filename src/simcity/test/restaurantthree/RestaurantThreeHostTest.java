package simcity.test.restaurantthree;

import junit.framework.TestCase;
import simcity.PersonAgent;
import simcity.buildings.restaurant.three.RestaurantThreeHostRole;
import simcity.buildings.restaurant.three.RestaurantThreeSystem;
import simcity.gui.SimCityGui;
import simcity.test.restaurantthree.mock.MockRestaurantThreeCustomer;
import simcity.test.restaurantthree.mock.MockRestaurantThreeWaiter;

public class RestaurantThreeHostTest extends TestCase {

	RestaurantThreeHostRole host;
	PersonAgent person;
	RestaurantThreeSystem restaurant;
	SimCityGui scg;
	
	MockRestaurantThreeWaiter waiter1;
	MockRestaurantThreeCustomer customer1;
	
	public void setUp() throws Exception {
		super.setUp();
		person = new PersonAgent("PersonAgent");
		host = new RestaurantThreeHostRole(person);
		scg = new SimCityGui();
		restaurant = new RestaurantThreeSystem(scg);
	}
	public void testOneHost() {
		System.out.println("===============testOneHost================");
		
		host.enterBuilding(restaurant);
	}
}