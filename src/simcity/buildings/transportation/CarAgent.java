package simcity.buildings.transportation;

import simcity.Location;
import simcity.Role;
import agent.Agent;

public class CarAgent extends Role implements simcity.interfaces.transportation.Car {
 CarPassengerRole passenger;
	Location destination;
	//CarState state = enum{ waiting, driving }
	public enum Carstate { waiting, driving }
	Carstate state;
	
	public void msgGettingOn(CarPassengerRole cp, Location l) {
		passenger = cp;
		destination = l;
		state = Carstate.driving;
	}
	public void msgGettingOff(CarPassengerRole cp) {
		passenger = null;
		destination = null;
	}
	
	public boolean pickAndExecuteAnAction() {
	if ((state == Carstate.waiting) && (destination != null)) {
			state = Carstate.driving;
			Drive();
			return true;
		}
	return false;
	}
	
	private void Drive() {

		// Animation - call to cargui
		DoGoTo(destination.getX(), destination.getY());
		//UNCOMMENT LINE OF CODE BELOW ONCE MOST ERRORS BE FIXED. ARRR
	//	passenger.msgWeHaveArrived(destination.xLoc, destination.yLoc);
		destination = null;

	} 
	
	private void DoGoTo(int x, int y) {
		
	}
}

