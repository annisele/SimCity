package simcity.buildings.restaurant.five;

import java.util.Map;
import java.util.concurrent.Semaphore;

import simcity.Role;
import simcity.SimSystem;
import simcity.interfaces.restaurant.five.RestaurantFiveCook;

public class RestaurantFiveCookRole extends Role implements RestaurantFiveCook {

	private Semaphore atDest = new Semaphore(0, true);
	
	@Override
	public void atDestination() {
		atDest.release();
	}
	
	@Override
	public String getName() {
		return person.getName();
	}

	@Override
	public void msgHereAreItems(Map<String, Integer> itemsToDeliver,
			double change) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void msgHereAreItems(Map<String, Integer> itemsToDeliver,
//			double change) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void msgHereIsAnOrder(RestaurantFiveWaiter restaurantFiveWaiter,
//			String choice, int table) {
//		// TODO Auto-generated method stub
//		
//	}

}
