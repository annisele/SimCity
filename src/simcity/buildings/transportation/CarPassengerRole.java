package simcity.buildings.transportation;
import java.util.concurrent.Semaphore;

import simcity.Directory;
import simcity.Location;
import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.transportation.CarGui;

public class CarPassengerRole extends Role implements simcity.interfaces.transportation.CarPassenger {
	public int start;
	public int destination;
	int xLoc;
	int yLoc;
	public enum PassengerState {stopped, driving};
	public enum PassengerEvent {starting, stopping};
	PassengerState state = PassengerState.stopped;
	PassengerEvent event;
	CarAgent car;
	Directory dir;
	public Semaphore atDest = new Semaphore(0, true);
	CarGui gui = new CarGui(this);
	
	
	public CarPassengerRole(PersonAgent p) {
		person = p;
		
		
	}
		
	public void msgDriveTo(int s, int d) { //from PersonAgent
		start = s;
		destination = d;
		event = PassengerEvent.starting;
		stateChanged();
	}
	public void msgWeHaveArrived(int x, int y) { //from CarAgent
		event = PassengerEvent.stopping;
		xLoc = x;
		yLoc = y;
		System.out.println("We Have Arrived");
		stateChanged();
	}
	public boolean pickAndExecuteAnAction() {
		if ((state == PassengerState.stopped) && (event == PassengerEvent.starting)) {
			state = PassengerState.driving;

			Drive();			
			return true;
		}
		if ((state == PassengerState.driving) && (event == PassengerEvent.stopping)) {
			state = PassengerState.stopped;
			GetOut();
			return true;
		}
		return false;
	}
	
	/*public void GetIn() {
        car.msgGettingOn(this, destination);
		// Animation
		DoDisableGui();
	} */
	
	private void Drive() {

		// Animation - call to cargui
		System.out.println(dir.getGarage(1).getX());
		gui.DoGoToLocation(dir.getGarage(destination).getX(), dir.getGarage(destination).getY());
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		} 
		msgWeHaveArrived(dir.getGarage(destination).getX(), dir.getGarage(destination).getY());
		//destination = (Integer) null;
		//UNCOMMENT LINE OF CODE BELOW ONCE MOST ERRORS BE FIXED. ARRR
	//	passenger.msgWeHaveArrived(destination.xLoc, destination.yLoc);

	} 
	

	public void GetOut() {
		//car.msgGettingOff(this);
		// Animation
		//DoRedrawAt(xLoc, yLoc); 
		person.setPedestrianRoleLocation(xLoc, yLoc);

	}
	
	public void DoDisableGui() {
		
	}
	
	public void DoRedrawAt(int x, int y) {
		
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
	
	public void setDirectory(Directory d) {
		dir = d;
	}
	
	
	
	
	
	
}

