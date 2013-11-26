package simcity.buildings.transportation;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.gui.transportation.BusPassengerGui;
import simcity.interfaces.Person;

public class BusPassengerRole extends Role implements simcity.interfaces.transportation.BusPassenger {

	BusAgent bus;
	int destination;
	int startingLocation;
	int xLoc;
	int yLoc;
	public enum PassengerState {offBus, waitingForBus, onBus};
	public enum PassengerEvent {atBusStop, busArriving, busStopping};
	PassengerState state = PassengerState.offBus;
	PassengerEvent event;

	public BusPassengerRole(PersonAgent p) {
		person = p;
		this.gui = new BusPassengerGui(this);
	}


	public void msgBusTo(int s, int d) { // from PersonAgent
		destination = d;
		startingLocation = s;
		event = PassengerEvent.atBusStop;
		stateChanged();
	}

	public void msgBusArriving() { //from BusAgent
		event = PassengerEvent.busArriving;
		stateChanged();
	}

	public void msgWeHaveArrived(int x, int y) { // from BusAgent
		event = PassengerEvent.busStopping;
		person.setPedestrianRoleLocation(x, y);
		stateChanged();
	} 

	public boolean pickAndExecuteAnAction() {
		if ((state == PassengerState.offBus) && (event == PassengerEvent.atBusStop)) {
			state = PassengerState.waitingForBus;
			CallBus();
			return true;
		}
		if ((state == PassengerState.waitingForBus) && (event == PassengerEvent.busArriving)) {
			state = PassengerState.onBus;
			GetIn();
			return true;
		}
		if ((state == PassengerState.onBus) && (event == PassengerEvent.busStopping)) {
			state = PassengerState.offBus;
			GetOut();
			return true;
		}
		return false;
	}


	public void CallBus() {
		AlertLog.getInstance().logMessage(AlertTag.WORLD, "BusPassenger: " + person.getName(), "Waiting at bus stop.");
		bus.msgWantBus(this, startingLocation, destination);
	}
	
	public void GetIn() {
		AlertLog.getInstance().logMessage(AlertTag.WORLD, "BusPassenger: " + person.getName(), "Getting on the bus!");
		bus.msgGettingOn(this);

	}

	public void GetOut() {
		AlertLog.getInstance().logMessage(AlertTag.WORLD, "BusPassenger: " + person.getName(), "Getting off the bus.");
		bus.msgGettingOff(this);
		person.roleFinished();
		// Animation
		//WHAT DOES ENABLED EVEN MEAN??? ASK CB or something
	//	Enabled = false;
	}
	



	@Override
	public void exitBuilding() {

	}

	@Override
	public void atDestination() {

	}

	@Override
	public void enterBuilding(SimSystem s) {
	
	}

	public void setBus(BusAgent b) {
		bus = b;
	}





}