package simcity.buildings.transportation;

import java.util.List;

import simcity.Location;


//Make Changes to bus stop counter
//
public class BusAgent implements simcity.interfaces.transportation.Bus {

	class MyPassenger {
		BusPassengerRole role;
		Location startLocation;
		Location destination;
		boolean loaded = false;
		MyPassenger(BusPassengerRole r, Location d) {
			role = r;
			
			destination = d;
		}
		
		void loaded() {
			loaded = true;
		}
	
	    }
	
		public BusAgent(String busname) {
			this.name = busname; 
			
		}
		
	private String name;
	List<MyPassenger> passengers;
	List<Location> busStops;
	int busStopCounter;
	public enum BusState {stopped, driving};
	public enum BusEvent {loading, arrived};
	
	BusState state;
	BusEvent event;
	
	public void msgWantBus(BusPassengerRole cp, Location l) {
		passengers.add(new MyPassenger(cp, l));
	}
	public void msgGettingOn(BusPassengerRole cp, Location l) {
		//passengers.get(cp).loaded();
		for (MyPassenger p : passengers) {
			if (p.role == cp) {
				p.loaded();
			}
		}
	}
	
	public void msgGettingOff(BusPassengerRole cp) {
		passengers.remove(cp);
	}

	// from animation
	public void msgArrived() {
		event = BusEvent.arrived;
	}

	

boolean FullyLoaded() {
	for (MyPassenger p : passengers) {
		if (p.startLocation == busStops.get(busStopCounter)) {
			if (p.loaded == false)
				return false;
		}
	}
	return true;
}

private void Drive() {
	busStopCounter ++;

	// Animation
	DoGoTo(busStops.get(busStopCounter).getX(), busStops.get(busStopCounter).getY());
}

private void Stop() {
	for (MyPassenger p : passengers) {
		if (FullyLoaded() == true && p.destination == busStops.get(busStopCounter)) {
			p.role.msgWeHaveArrived(busStops.get(busStopCounter).getX(),
                              busStops.get(busStopCounter).getY());
		}
		else if (p.loaded == false && p.startLocation == busStops.get(busStopCounter)) {
			p.role.msgBusArriving();
                      }
		event = BusEvent.loading;
	}
}
	
private void DoGoTo(int x, int y) {
	//Animation
	//Wait a few Seconds
	
	
}


}




