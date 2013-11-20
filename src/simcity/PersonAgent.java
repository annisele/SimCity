package simcity;

import java.util.*;

import simcity.gui.Gui;
import simcity.gui.bank.BankCustomerGui;
import simcity.gui.restaurantone.RestaurantOneCustomerGui;
import simcity.gui.transportation.PedestrianGui;
import simcity.interfaces.Person;
import simcity.buildings.bank.BankCustomerRole;
import simcity.buildings.restaurant.one.RestaurantOneCustomerRole;
import simcity.buildings.transportation.*;
import simcity.buildings.restaurant.one.*;
import simcity.Role;

import java.util.Map.Entry;

import agent.Agent;

public class PersonAgent extends Agent implements Person {

	private List<Role> myRoles;

	private TreeMap<Integer, Event> Schedule=new TreeMap<Integer, Event>();
	//SortedMap Schedule = Collections.synchronizedSortedMap(new TreeMap());

	private List <myRestaurant> Restaurants= new ArrayList<myRestaurant>();
	
	private class myRestaurant{
		String name;
		Location location;
		myRestaurant(){

		}
	}
	
	public String name;
	private boolean inAction, done;
	public double money;
	static long time, startTime=System.currentTimeMillis();
	private int currentTime, hungerLevel;

	//RestaurantOneCustomerRole rcr= new RestaurantOneCustomerRole();
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

	public PersonAgent(String n, double m){
		super();
		this.name = n;
		//this.roleMapping = new HashMap<String, Map<SimEvent.EventType,Role> >();
		this.myRoles=new ArrayList<Role>();
		//myRoles.add ALL USEFUL ROLES with useful perimeters
		//all roles are set to false
		//set person to this
		this.setDone(false);
		currentTime=1;
		this.inAction=false;
		// RestaurantOneCustomerRole rcr= new RestaurantOneCustomerRole("RestaurantOneCustomerRole",this);
		//rcr.setPerson(this);
		gettime();

		//myRoles.add(rcr);
		this.money = m;

		InstantiatePerson();
	}

	public void msgExitMarket(Map<String, Integer> tempItems) {
		// TODO Auto-generated method stub

	}
	public void msgExitRole(){
		Do("change role");
		inAction=false;
		stateChanged();
	}
	public void msgUpdateTime() {
		currentTime ++; 
		hungerLevel ++;
	}
	public void msgScheduleEvent(int time, Location l, Role r) {
		getSchedule().put(time, new Event(l, r));
	}


	@Override
	public boolean pickAndExecuteAnAction() {
		//Do("heree "+ inAction);
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
					//Do("action");
					role.pickAndExecuteAnAction();
					return true;
				}
			}
			return false;
		}
		else{
			Do("here");
			Entry<Integer,Event> ent = getSchedule().firstEntry();
			if (gettime() >= ent. getKey()) {
				Do("CHANGE");
				//gettime();
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
			getSchedule().put(currentTime, null);
		}
		if(i==0){
			getSchedule().put(currentTime, null);
		}
		//saskdjhaskdhkashd NOOOO
	}
	public void EatFoodLater(){
		getSchedule().put(currentTime+10, null);
		//saskdjhaskdhkashd NOOOO
	}
	public void DoEvent(Event e){
		if(currentTime==1){
		inAction=true;}
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
	public long gettime(){
		time= (System.currentTimeMillis()- startTime)/1000;
		
		Do("HAKHSDKJSAHD "+time);
		return time;
	}

	private void InstantiatePerson() {
		PedestrianRole pedestrianRole = new PedestrianRole(this);
		myRoles.add(pedestrianRole);
		RestaurantOneCustomerGui restaurantOneCustomerGui = new RestaurantOneCustomerGui();
		RestaurantOneCustomerRole restaurantOneCustomer = new RestaurantOneCustomerRole(this, restaurantOneCustomerGui);
		BankCustomerGui bankCustomerGui = new BankCustomerGui();
		BankCustomerRole bankCustomer = new BankCustomerRole(this, bankCustomerGui);
		myRoles.add(restaurantOneCustomer);
			pedestrianRole.setPerson(this);
			Location l= new Location(250,250);
			Event e = new Event(l,myRoles.get(0));
			Do(".. "+myRoles.get(0));
			getSchedule().put(1, e);
			gettime();
		myRoles.add(bankCustomer);
			pedestrianRole.setPerson(this);
			Location l1 = new Location(12, 12);
			Event e1 = new Event(l,myRoles.get(2));
			getSchedule().put(2, e);
			restaurantOneCustomer.setPerson(this);
			Location l2= new Location(250,250);
			Event e2 = new Event(l2,myRoles.get(1));
			getSchedule().put(5, e2);
			Do(".. "+getSchedule());
			gettime();
	
	}

	public TreeMap<Integer, Event> getSchedule() {
		return Schedule;
	}

	public void setSchedule(TreeMap<Integer, Event> schedule) {
		Schedule = schedule;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

}
