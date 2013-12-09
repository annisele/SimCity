package simcity.buildings.transportation;

import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import simcity.Directory;
import simcity.Location;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.transportation.CarGui;
import agent.Agent;

public class CarAgent extends Agent implements simcity.interfaces.transportation.Car {
	public CarPassengerRole passenger;
	public Location destination;
	public CarGui gui;
	public String name;
	Directory directory;
	public Semaphore atDestination = new Semaphore(0, true);
	//CarState state = enum{ waiting, driving }
	public enum Carstate { waiting, driving }
	Carstate state;
	public CarAgent(String n) {
		this.name = n;
	}
	
	public void msgGettingOn(CarPassengerRole cp, Location l) {
		passenger = cp;
		destination = l;
		state = Carstate.waiting;
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
		gui.DoGoTo(destination.getX(), destination.getY());
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
	public void atDestination() {
		  
		  atDestination.release();
	}
	

	public void setDirectory(Directory dir) {
		// TODO Auto-generated method stub
		this.directory = dir;
		
	}

	public void setGui(CarGui tcg) {
		// TODO Auto-generated method stub
		this.gui = tcg;
	}
}

