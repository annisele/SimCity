package simcity.test.transportation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;
import simcity.test.mock.LoggedEvent;
import simcity.Location;
import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.gui.transportation.PedestrianGui;
import simcity.buildings.transportation.*;
import simcity.interfaces.transportation.*;
import simcity.test.transportation.mock.*;


public class PedestrianTest extends TestCase {

	PedestrianRole pedestrian;
	MockBus bus;
	PersonAgent person;
	
	public void setUp() throws Exception {
		super.setUp();
		person = new PersonAgent("PersonAgent");
		pedestrian = new PedestrianRole(person);
		bus = new MockBus("mockbus");
	}
	
	public void testOnePedestrianDroppedOff() {
		System.out.println("testOnePedestrianWithBus");
		System.out.println("");
		
		// setup
		pedestrian.getGui().setLocation(0, 0);
		
		// check setup postconditions
		assertEquals("Pedestrian should have a matching person named PersonAgent", pedestrian.getPerson().getName(), "PersonAgent");
		assertEquals("Pedestrian gui should have x-location 0", pedestrian.getGui().getX(), 0);
		assertEquals("Pedestrian gui should have y-location 0", pedestrian.getGui().getY(), 0);
		
		// step 1 person messages pedestrian role to show up in a certain location
		pedestrian.msgArrivedAtLocationFromBus(150, 200);
		
		// check step 1 postconditions
		assertEquals("Pedestrian gui should be at x-location 150", pedestrian.getGui().getX(), 150);
		assertEquals("Pedestrian gui should be at y-location 200", pedestrian.getGui().getY(), 200);
		
		System.out.println("");
		System.out.println("testOnePedestrianDroppedOff");
		System.out.println("");
	}
	
	
	
	
}