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
	
	
	MockBus bus;
	PedestrianGui gui;
	private List<Location> destinationList = Collections.synchronizedList(new ArrayList<Location>());

	
	public void setUp() throws Exception {
		PersonAgent person = new PersonAgent("Kiefer");
		PedestrianRole pedestrian = new PedestrianRole(person);
		bus = new MockBus("Jacinta");
		
	}
	
	public void testpedestrian() {
		//Testing Preconditions
		assertEquals("The Pedestrian should not have another Gui associated with it right now, but it does", this.gui, null );
		assertEquals("The List 'destinationList' should be emptry right now. But it isn't", this.destinationList.size(),0);
		assertEquals("The ")
		
	}
	
	
	
	
}