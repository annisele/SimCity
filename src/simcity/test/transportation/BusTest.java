package simcity.test.transportation;

import junit.framework.*;
import simcity.PersonAgent;
import simcity.buildings.transportation.BusAgent;
import simcity.buildings.transportation.BusPassengerRole;
import simcity.buildings.transportation.BusAgent.BusEvent;
import simcity.buildings.transportation.BusAgent.BusState;
import simcity.gui.transportation.BusGui;
import simcity.test.transportation.mock.MockBus;
import simcity.test.transportation.mock.MockBusPassenger;

public class BusTest extends TestCase {
	
	BusAgent bus;
	MockBusPassenger buspassenger;
	BusGui bgui;
	
	public void setUp() throws Exception {
		super.setUp();
		buspassenger = new MockBusPassenger();
		bus = new BusAgent("Buster");
	
		
	}
	
	public void testBusNorm() {
		
		//Testing Preconditions
		assertEquals("The List mypassengers shouldn't have anything in it, but it does", bus.passengers.size(), 0);
		
		//Check the first and the last locations in busStops to make sure that they are correct
		assertEquals("The x location of the first bus stop should be 68, but it is not", bus.busStops.get(0).getX(), 68);
		assertEquals("The y location of the first bus stop should be 67, but it is not", bus.busStops.get(0).getY(), 67);
		assertEquals("The x location of the fourth bus stop should be 68, but it is not", bus.busStops.get(3).getX(), 68);
		assertEquals("The y location of the fourth bus stop should be 366, but it is not", bus.busStops.get(3).getY(), 366);		
		assertEquals("The busStopCounter should be three, but it is not", bus.busStopCounter, 3);
		
		//Make sure it isn't tied to any existing Gui
		assertEquals("The Gui should not be initialized yet, but it is", bus.gui, null);
		
		
		//Checking the original state and event
		assertEquals("The Beginning state of the Bus should be stopped, but it is not", bus.state, BusState.stopped );
		assertEquals("The beginning even of the BusAgent should be none, but it is not", bus.event, BusEvent.none);
				
		
		//NexStep, after first message is called
		bus.makeBusMove(); //Hack to start the bus moving
		assertEquals("The event should be changed to loaded, but it is not", bus.event, BusEvent.loaded);
		bus.msgWantBus(buspassenger, 0, 1);
		assertEquals("Now the passengers list should have one passenger in it", bus.passengers.size(), 1);
		
		
	}
	
	
}