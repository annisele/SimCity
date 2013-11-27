package simcity.test.transportation.mock;


import simcity.Location;
import simcity.PersonAgent;
import simcity.SimSystem;
import simcity.gui.transportation.PedestrianGui;
import simcity.interfaces.transportation.Pedestrian;
import simcity.test.mock.EventLog;
import simcity.test.mock.LoggedEvent;



public class MockPedestrian implements Pedestrian {
	
    public EventLog log = new EventLog();


	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("I have reached my destination"));
		


		
	}

	@Override
	public void msgArrivedAtLocationFromBus(int x, int y) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Received msgArrivedAtLocationFrom Bus at  " + x + ", " + y));
		

	}

	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addDestination(Location destination) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PedestrianGui getGui() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PersonAgent getPerson() {
		// TODO Auto-generated method stub
		return null;
	}
	
}