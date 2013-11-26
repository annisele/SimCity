package simcity.interfaces.restaurant.two;

import simcity.SimSystem;
import simcity.buildings.restaurant.two.RestaurantTwoOrderWheel;
import simcity.interfaces.GuiPartner;

public interface RestaurantTwoCook extends GuiPartner{
	abstract void enterBuilding(SimSystem s);

	abstract void hack_chicken();

	abstract void hack_salad();

	abstract void hack_steak();

	abstract void msgCookOrder(RestaurantTwoWaiter restaurantTwoWaiterRole,
			int table_num, String choice);

	abstract void setOrderWheel(RestaurantTwoOrderWheel owheel);

}
