package simcity.interfaces.transportation;

import simcity.interfaces.GuiPartner;
import simcity.buildings.transportation.*;;


public interface Pedestrian extends GuiPartner{
	public abstract void msgArrivedAtLocationFromBus(int x, int y);
	
}
