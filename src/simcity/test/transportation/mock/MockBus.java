package simcity.test.transportation.mock;

import simcity.Location;
import simcity.interfaces.transportation.Bus;
import simcity.interfaces.transportation.BusPassenger;
import simcity.test.mock.EventLog;
import simcity.test.mock.LoggedEvent;
import simcity.test.mock.Mock;

public class MockBus extends Mock implements Bus {

	public MockBus(String n) {
		super(n);
	}
	public EventLog log = new EventLog();

	@Override
	public void makeBusMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgWantBus(BusPassenger cp, int s, int d) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Received msgWantBus from BusPassengerRole"));
	}

	@Override
	public void msgGettingOn(BusPassenger cp) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Received msgGettingOn from BusPassengerRole"));

	}

	@Override
	public void msgGettingOff(BusPassenger cp) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Received msgGettingOn from BusPassengerRole " + cp));

	}

	@Override
	public void msgFinishedLoading() {
		log.add(new LoggedEvent("Received msgFinishedLoading "));

	}

	@Override
	public void msgArrived() {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Received msgArrived from busPassenger "));

	}

	@Override
	public void Drive() {
		// TODO Auto-generated method stub
	    log.add(new LoggedEvent("Received msgArrived from busPassenger "));

	}

	@Override
	public void Load() {
		// TODO Auto-generated method stub

	}

	@Override
	public void DoGoTo(Location l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atDestination() {
		
              //atDestination.release();
	}
	
}