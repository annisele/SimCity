package simcity.buildings.transportation;

import java.util.*;
import java.util.concurrent.Semaphore;

import simcity.Location;
import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.transportation.PedestrianGui;

public class PedestrianRole extends Role implements simcity.interfaces.transportation.Pedestrian {

	// Data
	PersonAgent person;
	PedestrianGui gui;
	private List<Location> destinationList = Collections.synchronizedList(new ArrayList<Location>());
	
	private Semaphore atDest = new Semaphore(0, true);
	
	// Constructor
	public PedestrianRole(PersonAgent person) {
		this.person = person;
		this.gui =  new PedestrianGui(this);
	}
	
	//Messages
	@Override
	public void msgExitBuilding() {
		// TODO Auto-generated method stub
		
	}
	
	// Scheduler
	public boolean pickAndExecuteAnAction() {
		if(!destinationList.isEmpty()) {
			GoToDestination(destinationList.get(0));
			person.Do("going to: " + destinationList.get(0));
		}
		return false;
	}
	
	// Actions
	private void GoToDestination(Location destination) {
		//person.Do("Calling GOTOLOC");
		DoGoToLocation(destination.getX(), destination.getY());
		person.roleFinished();
	}
	
	// Animation DoXYZ
	private void DoGoToLocation(int x, int y) {
		gui.DoGoToLocation(x, y);
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void atDestination() {
		atDest.release();
	}
	
	// Utility functions
	
	
	public void addDestination(Location destination) {
		destinationList.add(destination);
	}
	
	public PedestrianGui getGui() {
		return gui;
	}

	@Override
	public void msgEnterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		
	}

	
	
	/*
	public void msgWalkTo(Location l) {
		destination = l;
	}
	
	protected boolean pickAndExecuteAnAction() {
		if (destination != null)
			Walk(destination);
	}
	
	private void Walk(Location l) {
		// Animation
		DoGoTo(l);
		destination = null;
		person.msgWeHaveArrived();
	}
	
	private void DoGoTo(Location loc)  {
	//pedestrianGui.DoWalkTo...
	}*/

}
