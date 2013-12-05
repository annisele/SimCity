package simcity.interfaces.restaurant.three;

import java.util.List;

import simcity.PersonAgent;
import simcity.SimSystem;
import simcity.buildings.restaurant.three.RestaurantThreeSystem;
import simcity.interfaces.GuiPartner;

public interface RestaurantThreeHost extends GuiPartner {

	PersonAgent getPerson();

	void setPerson(PersonAgent person);

	RestaurantThreeSystem getSystem();

	void setSystem(RestaurantThreeSystem resSystem);

	List getWaitersList();

	void msgGotToWork();

	void exitBuilding();

	void enterBuilding(SimSystem s);



}
