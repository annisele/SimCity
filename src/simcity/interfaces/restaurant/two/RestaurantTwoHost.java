package simcity.interfaces.restaurant.two;

import simcity.SimSystem;
import simcity.buildings.market.MarketCashierRole.MarketState;
import simcity.buildings.restaurant.two.RestaurantTwoHostRole.R2State;
import simcity.interfaces.GuiPartner;

public interface RestaurantTwoHost extends GuiPartner {
	abstract void enterBuilding(SimSystem s);

	abstract void msgLeavingTable(
			RestaurantTwoCustomer restaurantTwoCustomer);

	abstract void msgAskToBreak(RestaurantTwoWaiter restaurantTwoWaiterRole);

	abstract void msgRestate(RestaurantTwoWaiter restaurantTwoWaiterRole);

	abstract void addWaiter(RestaurantTwoWaiter role);

	abstract R2State getR2State();

	abstract void msgLeaveWork();
	
}
