package simcity.interfaces.restaurant.one;

import simcity.buildings.restaurant.one.*;
import simcity.buildings.restaurant.one.RestaurantOneCookRole.CookOrder;
import simcity.interfaces.GuiPartner;

public interface RestaurantOneCook extends GuiPartner{
	
	public void msgHereIsAnorder(RestaurantOneWaiterRole w, String choice, int table);
	
	public void msgOrderComplete(String choice, int amount);
	
	public void msgOrderIncomplete();
	
	public void CookIt(CookOrder o);
	
	public void CookFood(final String choice);
	
	public void PlateIt(CookOrder o);
}
