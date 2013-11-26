package simcity.buildings.transportation;

import simcity.Location;
import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.market.MarketCustomerGui;
import simcity.gui.transportation.BusPassengerGui;
import agent.Agent;

	public class BusPassengerRole extends Role implements simcity.interfaces.transportation.BusPassenger {
		
		BusAgent bus;
		int destination;
		int startingLocation;
		int xLoc;
		int yLoc;
		//PassengerState state = enum{ offBus, waitingForBus, onBus };
		//PassengerEvent event = enum{ atBusStop, busArriving, busStopping };
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
	
	
	private void CallBus() {
		bus.msgWantBus(this, startingLocation, destination);
	}
	private void GetIn() {
		bus.msgGettingOn(this);

		// Animation
	}
	
	private void GetOut() {
		bus.msgGettingOff(this);
		person.roleFinished();
		// Animation
		//WHAT DOES ENABLED EVEN MEAN??? ASK CB or something
	//	Enabled = false;
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
	
	public void setBus(BusAgent b) {
		bus = b;
	}
	
	
	
	
	
}