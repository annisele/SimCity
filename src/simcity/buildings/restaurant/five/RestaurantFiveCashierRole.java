package simcity.buildings.restaurant.five;

import java.util.concurrent.Semaphore;

import simcity.interfaces.market.MarketCashier;
import simcity.interfaces.restaurant.five.RestaurantFiveCashier;

public class RestaurantFiveCashierRole implements RestaurantFiveCashier {

	private Semaphore atDest = new Semaphore(0, true);
	
	@Override
	public void atDestination() {
		atDest.release();
		
	}

	@Override
	public void msgPleasePay(MarketCashier c, double payment, int orderNum) {
		// TODO Auto-generated method stub
		
	}

}
