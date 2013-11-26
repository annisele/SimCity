package simcity.test.transportation;

import junit.framework.TestCase;
import simcity.PersonAgent;
import simcity.buildings.transportation.BusPassengerRole;
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
	
	public void TestNormBusPassenger() {
		//Checking PreConditions
		//assertEquals("Passenger's State should be OffBus at the beginning of the Scenario")
		
		
	}
	
	
	
}
