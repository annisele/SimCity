package simcity.interfaces.transportation;

import simcity.Location;
import simcity.PersonAgent;
import simcity.SimSystem;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.gui.transportation.PedestrianGui;
import simcity.interfaces.GuiPartner;
import simcity.buildings.transportation.*;;


public interface Pedestrian extends GuiPartner{
	public abstract void exitBuilding();
	public abstract void msgArrivedAtLocationFromBus(int x, int y);
	public abstract void atDestination();
	public abstract void addDestination(Location destination);
	public abstract PedestrianGui getGui();
	public abstract void enterBuilding(SimSystem s);
	public abstract PersonAgent getPerson();
}
