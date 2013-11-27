package simcity.test.transportation.mock;


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
	
}