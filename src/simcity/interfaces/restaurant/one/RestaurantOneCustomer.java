package simcity.interfaces.restaurant.one;

import simcity.buildings.restaurant.one.RestaurantOneCheck;
import simcity.buildings.restaurant.one.RestaurantOneMenu;
import simcity.buildings.restaurant.one.RestaurantOneWaiterRole;

public interface RestaurantOneCustomer {

	void msgWhatWouldYouLike();

	void msgFollowMe(RestaurantOneMenu restaurantOneMenu);

	void setWaiter(RestaurantOneWaiterRole restaurantOneWaiterRole);

	void msgFoodOutofStock();

	void msgHereIsYourFood();

	//void getGui();

	void msgHereisYourCheck(RestaurantOneCheck c);

}
