package simcity.interfaces.transportation;

import simcity.interfaces.GuiPartner;
import simcity.buildings.transportation.*;
import simcity.Location;
import simcity.SimSystem;

public interface Bus extends GuiPartner {
	public abstract void makeBusMove();
	public abstract void msgWantBus(BusPassenger cp, int s, int d);
	public abstract void msgGettingOn(BusPassenger cp);
	public abstract void msgGettingOff(BusPassenger cp);
	public abstract void msgFinishedLoading();
	public abstract void msgArrived();
	public abstract void Drive();
	public abstract void Stop();
	public abstract void DoGoTo(Location l);
	public abstract void atDestination();
}
