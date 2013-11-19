package simcity;

import java.util.*;

import simcity.gui.Gui;
import simcity.gui.restaurantone.RestaurantOneCustomerGui;
import simcity.gui.transportation.PedestrianGui;
import simcity.interfaces.Person;
import simcity.buildings.restaurant.one.RestaurantOneCustomerRole;
import simcity.buildings.transportation.*;
import simcity.buildings.restaurant.one.*;
import simcity.Role;

import java.util.Map.Entry;

import agent.Agent;

public class PersonAgent extends Agent implements Person {

	private List<Role> myRoles;
	
	TreeMap<Integer, Event> Schedule=new TreeMap<Integer, Event>();
	//SortedMap Schedule = Collections.synchronizedSortedMap(new TreeMap());
  
	private List <myRestaurant> Restaurants= new ArrayList<myRestaurant>();
	private class myRestaurant{
		String name;
		Location location;
		myRestaurant(){
			
		}
	}
	public String name;
	private boolean inAction;
	public double money;
	private int currentTime, hungerLevel;

    //RestaurantOneCustomerRole rcr= new RestaurantOneCustomerRole();
	enum Priority{ Now, Next, Later};
	private Event currentEvent;
	private PedestrianGui gui;
	
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
	
	public PersonAgent(String n, double m){
        super();
        this.name = n;
        //this.roleMapping = new HashMap<String, Map<SimEvent.EventType,Role> >();
        this.myRoles=new ArrayList<Role>();
        //myRoles.add ALL USEFUL ROLES with useful perimeters
        //all roles are set to false
        //set person to this

        currentTime=1;
       // RestaurantOneCustomerRole rcr= new RestaurantOneCustomerRole("RestaurantOneCustomerRole",this);
        //rcr.setPerson(this);

       
        //myRoles.add(rcr);
        this.money = m;

        InstantiatePerson();
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
			EatFoodNow();
		}
		else if (currentEvent.priority==Priority.Now){
			EatFoodLater();
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
	public void EatFoodNow(){
		int i=1;
		if(i==0){
			//decide on a restaurant!
			Event e = new Event(Restaurants.get(i).location, myRoles.get(0));
		Schedule.put(currentTime, null);
		}
		if(i==0){
			Schedule.put(currentTime, null);
			}
		//saskdjhaskdhkashd NOOOO
	}
	public void EatFoodLater(){
		Schedule.put(currentTime+10, null);
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
	
	public Gui getGui() {
		return myRoles.get(0).getGui();
	}
	
	private void InstantiatePerson() {
		PedestrianGui pedestrianGui = new PedestrianGui();
		PedestrianRole pedestrianRole = new PedestrianRole(this, pedestrianGui);
		myRoles.add(pedestrianRole);
		RestaurantOneCustomerGui restaurantOneCustomerGui = new RestaurantOneCustomerGui();
		RestaurantOneCustomerRole restaurantOneCustomer = new RestaurantOneCustomerRole(this, restaurantOneCustomerGui);
		myRoles.add(restaurantOneCustomer);
			pedestrianRole.setPerson(this);
			Location l= new Location(25,25);
			Event e = new Event(l,myRoles.get(1));
			Schedule.put(1, e);
	}
	
}
