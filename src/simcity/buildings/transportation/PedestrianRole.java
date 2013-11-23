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
		person.Do("PedRole's pick and execute is being called");
		person.Do("destinationList has a a size of: "+destinationList.size());
		if(!destinationList.isEmpty()) {
			GoToDestination(destinationList.get(0));
		}
		person.Do("PedRole is about to return false");
		return false;
	}
	
	// Actions
	private void GoToDestination(Location destination) {
		person.Do("Calling GOTOLOC");
		DoGoToLocation(destination.getX(), destination.getY());
		person.roleFinished();
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

	@Override
	public void msgExitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgEnterBuilding() {
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
