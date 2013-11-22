package simcity.buildings.transportation;

import java.util.*;

import simcity.Location;
import simcity.PersonAgent;
import simcity.Role;
import simcity.gui.transportation.PedestrianGui;

public class PedestrianRole extends Role implements simcity.interfaces.transportation.Pedestrian {

	// Data
	PersonAgent person;
	PedestrianGui gui;
	private List<Location> destinationList = Collections.synchronizedList(new ArrayList<Location>());
	
	// Constructor
	public PedestrianRole(PersonAgent person) {
		this.person = person;
		this.gui =  new PedestrianGui();
	}
	
	// Scheduler
	public boolean pickAndExecuteAnAction() {
		if(person.gettime()>6){
			person.msgExitRole();
		}
		if(!destinationList.isEmpty()) {
			GoToDestination(destinationList.get(0));
		}
		
		return false;
	}
	
	// Actions
	private void GoToDestination(Location destination) {
		DoGoToLocation(destination.getX(), destination.getY());
	}
	
	// Animation DoXYZ
	private void DoGoToLocation(int x, int y) {
		gui.DoGoToLocation(x, y);
	}
	
	// Utility functions
	public void addDestination(Location destination) {
		destinationList.add(destination);
	}
	
	public PedestrianGui getGui() {
		return gui;
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
