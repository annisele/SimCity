package simcity.buildings.transportation;

	public class BusPassengerRole implements simcity.interfaces.transportation.BusPassenger {
		/*
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
	}

	public void msgBusTo(Location l) { // from PersonAgent
		destination = l;
		event = PassengerEvent.atBusStop;
	}
	
	public void msgBusArriving() { //from BusAgent
		event = PassengerEvent.busArriving;
	}
	
	public void msgWeHaveArrived(int x, int y) { // from BusAgent
		event = PassengerEvent.busStopping;
		xLoc = x;
		yLoc = y;
	} 
	
	protected boolean pickAndExecuteAnAction() {
	if ((state == PassengerState.offBus) && (event == PassengerEvent.atBusStop)) {
			state = PassengerState.waitingForBus;
			CallBus();
	}
	if ((state == PassengerState.waitingForBus) && (event == PassengerEvent.busArriving)) {
			state = PassengerState.onBus;
			GetIn();
	}
		if ((state == PassengerState.onBus) && (event == PassengerEvent.busStopping)) {
			state = PassengerState.offBus;
			GetOut();
		}
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

		Enabled = false;
	}
	*/
	
}