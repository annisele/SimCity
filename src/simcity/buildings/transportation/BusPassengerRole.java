package simcity.buildings.transportation;

import simcity.Location;
import simcity.Role;
import agent.Agent;

	public class BusPassengerRole extends Role implements simcity.interfaces.transportation.BusPassenger {
		
		BusAgent bus;
		Location destination;
		Location startingLocation;
		int xLoc;
		int yLoc;
		//PassengerState state = enum{ offBus, waitingForBus, onBus };
		//PassengerEvent event = enum{ atBusStop, busArriving, busStopping };
		public enum PassengerState {offBus, waitingForBus, onBus};
		public enum PassengerEvent {atBusStop, busArriving, busStopping};
		PassengerState state;
		PassengerEvent event;
	

	public void msgBusTo(Location l) { // from PersonAgent
		destination = l;
		event = PassengerEvent.atBusStop;
		stateChanged();
	}
	
	public void msgBusArriving() { //from BusAgent
		event = PassengerEvent.busArriving;
		stateChanged();
	}
	
	public void msgWeHaveArrived(int x, int y) { // from BusAgent
		event = PassengerEvent.busStopping;
		xLoc = x;
		yLoc = y;
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
	
	
	private void CallBus() {
		bus.msgWantBus(this, startingLocation);
	}
	private void GetIn() {
		bus.msgGettingOn(this, destination);

		// Animation
		DoDisableGui();
	}
	
	private void GetOut() {
		bus.msgGettingOff(this);

		// Animation
		DoRedrawAt(xLoc, yLoc);
		//WHAT DOES ENABLED EVEN MEAN??? ASK CB or something
	//	Enabled = false;
	}
	
	private void DoRedrawAt(int x, int y) {
		//Animation
	}
	
	private void DoDisableGui() {
		
		//Animation
	}

	@Override
	public void msgExitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgEnterBuilding() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}