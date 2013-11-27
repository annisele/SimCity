package simcity.interfaces.transportation;

import simcity.interfaces.GuiPartner;
import simcity.buildings.transportation.*;;

public interface BusPassenger extends GuiPartner {
	 public abstract void msgBusTo(int s, int d);
	 public abstract void msgBusArriving();
	 public abstract void msgWeHaveArrived(int x, int y);
	 public abstract void CallBus();
	 public abstract void GetIn();
	 public abstract void GetOut();
	 public abstract void setBus(BusAgent b);
}
