package simcity.buildings.transportation;

public class CarPassengerRole implements simcity.interfaces.transportation.CarPassenger {
	/*Location destination;
	int xLoc;
	int yLoc;
	public enum PassengerState {stopped, driving};
	public enum PassengerEvent {starting, stopping};
	PassengerState state;
	PassengerEvent event;
	CarAgent car;
	
	public void msgDriveTo(Location l) { //from PersonAgent
		destination = l;
		event = PassengerEvent.starting;
		stateChanged();
	}
	public void msgWeHaveArrived(int x, int y) { //from CarAgent
		event = PassengerEvent.stopping;
		xLoc = x;
		yLoc = y;
		stateChanged();
	}
	protected boolean pickAndExecuteAnAction() {
	if ((state == PassengerState.stopped) && (event == PassengerEvent.starting)) {
			state = PassengerState.driving;
			GetIn();
			return true;
	}
		if ((state == PassengerState.driving) && (event == PassengerEvent.stopping)) {
			state = PassengerState.stopped;
			GetOut();
			return true;
}
		return false;
	}
	
	public void GetIn() {
        car.msgGettingOn(this, destination);
	// Animation
	DoDisableGui();
}

	public void GetOut() {
	car.msgGettingOff(this);
	// Animation
	DoRedrawAt(xLoc, yLoc); 
	}
	
	public void DoDisableGui() {
		
	}
	
	public void DoRedrawAt(int x, int y) {
		
	}
	
	
	
	*/
	
	
	
}

