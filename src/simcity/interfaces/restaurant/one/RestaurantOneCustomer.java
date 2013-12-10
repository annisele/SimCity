package simcity.interfaces.restaurant.one;

import simcity.buildings.restaurant.one.RestaurantOneCheck;
import simcity.buildings.restaurant.one.RestaurantOneHostRole;
import simcity.buildings.restaurant.one.RestaurantOneMenu;
import simcity.buildings.restaurant.one.RestaurantOneWaiterRole;
import simcity.interfaces.GuiPartner;

public interface RestaurantOneCustomer extends GuiPartner{

	void msgWhatWouldYouLike();

	void msgFollowMe(RestaurantOneMenu restaurantOneMenu);

	void setWaiter(RestaurantOneWaiterRole restaurantOneWaiterRole);
	
     void setHost(RestaurantOneHost host);

	void msgFoodOutofStock();

	void msgHereIsYourFood();

	//void getGui();

	void msgHereisYourCheck(RestaurantOneCheck c);

	void msgArrivedAtRestaurant(double money);

}
