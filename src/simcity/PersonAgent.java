package simcity;

import java.util.*;

import simcity.interfaces.Person;
import simcity.buildings.transportation.*;

import java.util.Map.Entry;

import agent.Agent;

public class PersonAgent extends Agent implements Person {

	private List<Role> myRoles;
	
	TreeMap<Integer, Event> Schedule=new TreeMap<Integer, Event>();
	//SortedMap Schedul = Collections.synchronizedSortedMap(new TreeMap());
  

	public String name;
	public double money;
	private int currenttime;
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
        this.myRoles=new ArrayList<Role>();
        this.money = m;
    }

	public void msgExitMarket(Map<String, Integer> tempItems) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		for(Role role : myRoles)
			if (role.active()){
				role.pickAndExectuteAnAction();
				return true;
			}
		Entry<Integer,Event> ent = Schedule.firstEntry();
		if (currenttime >= ent. getKey()) {
			DoEvent(((Entry<Integer, Event>) ent).getValue());
			return true;
		}

		return false;
	}

	public void DoEvent(Event e){
		
	}
	
	public List<Role> getRoles() {
		return myRoles;
	}

	public void setRoles(List<Role> roles) {
		this.myRoles = roles;
	}

}
