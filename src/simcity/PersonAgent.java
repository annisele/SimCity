package simcity;

import java.util.*;

import simcity.interfaces.Person;
import simcity.buildings.transportation.*;
import agent.Agent;

public class PersonAgent extends Agent implements Person {

	private List<Role> roles;
	
	//sMap<Integer, Event> Schedule;
	SortedMap Schedule = Collections.synchronizedSortedMap(new TreeMap());

	public String name;
	public double money;
	public class Event {
		Location location;
		Role role;
		
        public Event(Location l, Role r) {        
                this.location = l;
                this.role = r;
	}
	}
	
	public PersonAgent(String n,double m){
        super();
        this.name = n;
        //this.roleMapping = new HashMap<String, Map<SimEvent.EventType,Role> >();
        this.setRoles(new ArrayList<Role>());
        
        this.money = m;
    }

	public void msgExitMarket(Map<String, Integer> tempItems) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
