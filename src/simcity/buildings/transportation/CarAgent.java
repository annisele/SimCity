package simcity.buildings.transportation;

import java.util.concurrent.Semaphore;

import simcity.Location;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.transportation.CarGui;
import agent.Agent;

public class CarAgent extends Role implements simcity.interfaces.transportation.Car {
 CarPassengerRole passenger;
	Location destination;
	CarGui cargui;
	public Semaphore atDestination = new Semaphore(0, true);
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
		cargui.DoGoTo(destination.getX(), destination.getY());
		try {
			atDestination.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		} 
		//UNCOMMENT LINE OF CODE BELOW ONCE MOST ERRORS BE FIXED. ARRR
	//	passenger.msgWeHaveArrived(destination.xLoc, destination.yLoc);
		destination = null;

	} 
	
	
	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void enterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		
	}
}

