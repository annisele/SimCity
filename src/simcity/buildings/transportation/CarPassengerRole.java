package simcity.buildings.transportation;
import simcity.Location;
import simcity.Role;

public class CarPassengerRole extends Role implements simcity.interfaces.transportation.CarPassenger {
	Location destination;
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
	public boolean pickAndExecuteAnAction() {
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
	@Override
	public void msgExitBuilding() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void msgEnterBuilding() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
}

