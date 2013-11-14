package simcity.buildings.transportation;

public class CarPassengerRole implements simcity.interfaces.transportation.CarPassenger {
/*	Location destination;
	int xLoc;
	int yLoc;
	public enum PassengerState {stopped, driving};
	public enum PassengerEvent {starting, stopping};
	PassengerState state;
	PassengerEvent event;
	CarAgent car;
	
	public void msgDriveTo(location l) { //from PersonAgent
		destination = l;
		event = PassengerEvent.starting;
	}
	public void msgWeHaveArrived(int x, int y) { //from CarAgent
		event = PassengerEvent.stopping;
		xLoc = x;
		yLoc = y;
	}
	protected boolean pickAndExecuteAnAction() {
	if ((state == PassengerState.stopped) && (event == PassengerEvent.starting)) {
			state = PassengerState.driving;
			GetIn();
	}
		if ((state == PassengerState.driving) && (event == PassengerEvent.stopping)) {
			state = PassengerState.stopped;
			GetOut();
}
	}
	
	public void GetIn() {
        car.msgGettingOn(this, destination);
	// Animation
	DoDisableGui();
}

	public void GetOut() {
	car.msgGettingOff(this);
	// Animation
	DoRedrawAt(xLoc, yLoc); */
}

