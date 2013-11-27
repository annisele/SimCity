package simcity.test.transportation;

import junit.framework.*;
import simcity.PersonAgent;
import simcity.buildings.transportation.BusPassengerRole;
import simcity.buildings.transportation.BusPassengerRole.PassengerState;
import simcity.test.transportation.mock.MockBus;;

public class BusPassengerTest extends TestCase {
	BusPassengerRole buspassenger;
	MockBus bus;
	PersonAgent person = new PersonAgent("Traveler");

	
	public void setUp() throws Exception {
		super.setUp();
		bus = new MockBus();
		buspassenger = new BusPassengerRole(person);
		
	}
	
	public void testnorm() {
		//Checking PreConditions
		assertEquals("Passenger's State should be OffBus at the beginning of the Scenario", buspassenger.state, PassengerState.offBus );
		assertEquals("Passenger's Bus shouldn't be set yet, so it should return null. It doesn't", buspassenger.bus, null);
		assertEquals("Passenger's xCoordinate should be initialized at zero", buspassenger.xLoc, 0);
		assertEquals("Passenger's yCoordinate should be initialized at zero", buspassenger.yLoc, 0);
		assertEquals("Passenger's startLocation should not be set", buspassenger.startingLocation,0);
		assertEquals("Passenger's destination should not be set", buspassenger.destination,0);
		
		
		

		

		
	}
	
	
	
}
