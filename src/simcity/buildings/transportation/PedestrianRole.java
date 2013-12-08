package simcity.buildings.transportation;

import java.util.*;
import java.util.concurrent.Semaphore;

import simcity.Location;
import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.gui.transportation.PedestrianGui;

public class PedestrianRole extends Role implements simcity.interfaces.transportation.Pedestrian {

	// Data
	public PersonAgent person;
	public PedestrianGui gui;
	private List<Location> destinationList = Collections.synchronizedList(new ArrayList<Location>());
	
	public Semaphore atDest = new Semaphore(0, true); 
	
	// Constructor
	public PedestrianRole(PersonAgent person) {
		this.person = person;
		this.gui =  new PedestrianGui(this);
	}
	
	
	//Messages
	@Override
	public void exitBuilding() {
		
	}
	
	public void msgArrivedAtLocationFromBus(int x, int y) {
		this.gui.setLocation(x, y);
	}
	
	// Scheduler
	public boolean pickAndExecuteAnAction() {
		if(!destinationList.isEmpty()) {
			GoToDestination(destinationList.get(0));
			destinationList.remove(0);
		}
		return false;
	}
	
	// Actions
	private void GoToDestination(Location destination) {
		DoGoToLocation(destination.getX(), destination.getY());
		person.roleFinished();
	}
	
	// Animation DoXYZ
	private void DoGoToLocation(int x, int y) {
		AlertLog.getInstance().logMessage(AlertTag.WORLD, "Pedestrian: " + person.getName(), "Walking to location (" + x + ", " + y + ").");
		gui.DoGoToLocation(x, y);
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
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
	public void enterBuilding(SimSystem s) {
		
	}
	
	public PersonAgent getPerson() {
		return person;
	}
}
