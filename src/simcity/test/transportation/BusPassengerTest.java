package simcity.test.transportation;

import junit.framework.*;
import simcity.PersonAgent;
import simcity.buildings.transportation.BusPassengerRole;
import simcity.buildings.transportation.BusPassengerRole.PassengerEvent;
import simcity.buildings.transportation.BusPassengerRole.PassengerState;
import simcity.test.transportation.mock.MockBus;;

public class BusPassengerTest extends TestCase {
	BusPassengerRole buspassenger;
	MockBus bus;
	PersonAgent person = new PersonAgent("Traveler");

	
	public void setUp() throws Exception {
		super.setUp();
		bus = new MockBus("mockbus");
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
		assertFalse("Passenger's schedule should return false, since nothing has called it", buspassenger.pickAndExecuteAnAction());
		
		//Next Step: Getting a message with a destination from PersonAgent
		buspassenger.msgBusTo(1, 2);
		buspassenger.setBus(bus);
		assertEquals("Passenger should have a starting Location that is equal to one. It isn't", buspassenger.startingLocation,1);
		assertEquals("Passenger should have a destination that is equal to two. It isn't", buspassenger.destination,2);
		assertEquals("Passenger's event should be  'atBusStop', but it is not", buspassenger.event, PassengerEvent.atBusStop);
		assertTrue("Passenger's schedule should return true, because of msgBusTo", buspassenger.pickAndExecuteAnAction());

		//The Passenger will next get a message from the Bus, saying that the Bus is at his stop
		buspassenger.msgBusArriving();
		assertEquals("Passenger's event should be 'BusArriving', but it is not", buspassenger.event, PassengerEvent.busArriving);
	
		//The Passenger will next get a message from a Bus after getting on, saying that he has reached his destination
		buspassenger.msgWeHaveArrived(1, 2); //Bogus Coordinates, just testing whether the message works or not
		assertEquals("Passenger's event should be busStopping, but it isn't",buspassenger.event, PassengerEvent.busStopping );
		
		

		

		
	}
	
	
	
	
}
