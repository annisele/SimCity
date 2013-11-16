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
	private boolean inAction;
	public double money;
	private int currentTime, hungerLevel;
	enum Priority{ Now, Next, Later};
	private Event currentEvent;
	public class Event {
		Location location;
		Role role;
		Priority priority;
		
        public Event(Location l, Role r) {        
                this.location = l;
                this.priority=Priority.Later;
                this.role = r;
                
	}
	}
	
	public PersonAgent(String n,double m){
        super();
        this.name = n;
        //this.roleMapping = new HashMap<String, Map<SimEvent.EventType,Role> >();
        this.myRoles=new ArrayList<Role>();
        //myRoles.add ALL USEFUL ROLES with useful perimeters
        //all roles are set to false
        //set person to this
        this.money = m;
        
    }

	public void msgExitMarket(Map<String, Integer> tempItems) {
		// TODO Auto-generated method stub
		
	}
	public void msgUpdateTime() {
		currentTime ++; 
		hungerLevel ++;
	}
	public void msgScheduleEvent(int time, Location l, Role r) {
		Schedule.put(time, new Event(l, r));
	}


	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		if (hungerLevel > 10){
		if (currentEvent.priority==Priority.Later){
			EatFood();
		}
		else if (currentEvent.priority==Priority.Now){
			EatFood();
		}
		}
		if(inAction==true){
		for(Role role : myRoles){
			if (role.active==true){
				
				role.pickAndExecuteAnAction();
				return true;
			}
		}
		return false;
		}
		else{
		Entry<Integer,Event> ent = Schedule.firstEntry();
		if (currentTime >= ent. getKey()) {
			DoEvent(((Entry<Integer, Event>) ent).getValue());
			currentEvent=((Entry<Integer, Event>) ent).getValue();
			return true;
		}

		return false;
	}
	}
	public void EatFood(){
		//saskdjhaskdhkashd NOOOO
	}
	public void DoEvent(Event e){
		inAction=true;
		//message the universe map. 
		//e.location == universemapKEY, then message host/manager 
	
		for( int i=0;  i<myRoles.size();i++){
		if (e.role ==myRoles.get(i)){
			myRoles.get(i).active=true;
			stateChanged();
			
		}
	}
	}
	public List<Role> getRoles() {
		return myRoles;
	}

	public void setRoles(List<Role> roles) {
		this.myRoles = roles;
	}

}
