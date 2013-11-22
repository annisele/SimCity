package simcity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.lang.reflect.*;

import simcity.gui.Gui;
import simcity.gui.IdlePersonGui;
import simcity.gui.bank.BankCustomerGui;
import simcity.gui.restaurantone.RestaurantOneCustomerGui;
import simcity.gui.transportation.PedestrianGui;
import simcity.interfaces.Person;
import simcity.interfaces.house.HouseInhabitant;
import simcity.interfaces.market.MarketCustomer;
import simcity.interfaces.transportation.Pedestrian;
import simcity.buildings.bank.BankCustomerRole;
import simcity.buildings.house.HouseInhabitantRole;
import simcity.buildings.market.MarketCustomerRole;
import simcity.buildings.market.MarketSystem;
import simcity.buildings.restaurant.five.RestaurantFiveCustomerRole;
import simcity.buildings.restaurant.four.RestaurantFourCustomerRole;
import simcity.buildings.restaurant.one.RestaurantOneCustomerRole;
import simcity.buildings.transportation.*;
import simcity.buildings.restaurant.one.*;
import simcity.buildings.restaurant.two.RestaurantTwoCustomerRole;
import simcity.Role;
import agent.Agent;

public class PersonAgent extends Agent implements Person {

	private Random rand = new Random();
	private String name;
	private List<Role> myRoles = new ArrayList<Role>();
	private List<Event> eventList = new ArrayList<Event>();
	private Role currentRole = null;
	private Event currentEvent = null;
	private Timer timer = new Timer();
	public enum EventType {Eat, GoToMarket, DepositMoney, WithdrawMoney, GetALoan, PayRent, Sleep, Work};
	private IdlePersonGui idleGui;
	
	public PersonAgent(String n) {
		name = n;
		idleGui = new IdlePersonGui(this);
		PedestrianRole p = new PedestrianRole(this);
		HouseInhabitantRole h = new HouseInhabitantRole(this);
		MarketCustomerRole m = new MarketCustomerRole(this);
		BankCustomerRole b = new BankCustomerRole(this);
		//RestaurantOneCustomerRole r1 = new RestaurantOneCustomerRole(this);
		RestaurantTwoCustomerRole r2 = new RestaurantTwoCustomerRole(this);
		//RestaurantThreeCustomerRole r3 = new RestaurantThreeCustomerRole(this);
		RestaurantFourCustomerRole r4 = new RestaurantFourCustomerRole(this);
		RestaurantFiveCustomerRole r5 = new RestaurantFiveCustomerRole(this);
		//RestaurantSixCustomerRole r6 = new RestaurantSixCustomerRole(this);
		myRoles.add(p);
	}
	
	@Override
	public boolean pickAndExecuteAnAction() {

		if(currentRole != null) {
			return currentRole.pickAndExecuteAnAction();
		}
		//move to next step in the event
		else if(currentEvent != null) {
			//does the next step
			//if it returns false because there are no more steps, remove event from the list
			if(!currentEvent.nextStep()) {
				currentEvent = null;
				currentRole = null;
				idleGui.setLocation(currentRole.getGui().getLocation());
				
				eventList.remove(0);
				return true;
			}
		}
		//move to the next event if currentEvent is null
		else {
			if(eventList.size() > 0) {
				if(eventList.get(0).startTime <= Clock.getTime()) {
					currentEvent = eventList.get(0); //set next event to current
					return true;
				}
				else if(Directory.getLocation(eventList.get(0).buildingName) == Directory.getLocation(currentEvent.buildingName) && eventList.get(0).flexible) {
					//set next event to current if at same place and next event is flexible
					currentEvent = eventList.get(0);
					return true;
				}
				else {
					waitForNextEvent();
				}
			}
		}
		return false;
	}

	private void waitForNextEvent() {
		long waitTime = eventList.get(0).startTime - Clock.getTime();
		timer.schedule(new TimerTask() {
			public void run() {
				stateChanged();
			}
		}, waitTime);
	}

	public void scheduleEvent(EventType t) {
		Event e;
		if(t == EventType.GoToMarket) {

			List<String> markets = Directory.getMarkets();
			int index = rand.nextInt(markets.size());
			String buildingName = markets.get(index);
			List<Step> steps = new ArrayList<Step>();
			steps.add(new Step("exitBuilding", this));
			steps.add(new Step("goTo", this));
			steps.add(new Step("enterBuilding", this));
			Role eventR = null;
			for(Role r : myRoles) {
				if(r instanceof MarketCustomer) {
					eventR = r;
				}
			}
			HouseInhabitantRole house = null;
			for(Role r : myRoles) {
				if(r instanceof HouseInhabitant) {
					house = (HouseInhabitantRole) r;
				}
			}
			((MarketCustomer)eventR).msgBuyStuff(house.getListToBuy(), (MarketSystem)(Directory.getSystem(buildingName)));
			e = new Event(buildingName, eventR, 120, true, steps, t);
		}
	}

	//this assumes after roles are done, they go stand outside the building
	//so this only needs to prep the person to walk somewhere by changing it to pedestrian
	public void exitBuilding() {
		for(Role r : myRoles) {
			if(r instanceof Pedestrian) {
				currentRole = r;
			}
		}

	}

	//later, add bus and car options
	public void goTo() {
		Location loc = Directory.getLocation(currentEvent.buildingName);
		((PedestrianRole)currentRole).addDestination(loc);
	}

	public void enterBuilding() {
		if(Directory.getSystem(currentEvent.buildingName).msgEnterBuilding(currentEvent.role)) {
			currentRole = currentEvent.role;
			System.out.println(this.getName() + ": Entered building. Changing role to " + currentRole.getClass());

		}
		else {
			System.out.println(this.getName() + ": Building closed. Cannot enter.");
			scheduleEvent(currentEvent.type); //maybe change this?
		}
	}

	/*****
	 * insertEvent - inserts a new event in the correct position in eventList
	 * @param e - the new event to insert into the list
	 */
	private void insertEvent(Event e) {
		if(!e.flexible) {
			int index = -1;
			List<Event> tempList = new ArrayList<Event>();

			for(Event e2 : eventList) {
				if(e.overlaps(e2)) {
					//copies all events that overlap e to a separate list so we can remove later
					tempList.add(e2); 
					if(index == -1) {
						index = eventList.indexOf(e2);
					}
				}
			}

			for(Event eTemp : tempList) {
				eventList.remove(eTemp); //remove the conflicting event
			}

			//if there was a conflicting event already in the list, move conflicts
			if(index != -1) {
				//add the new event into the correct index
				eventList.add(index, e);
				//reinsert all the conflicting events into the list
				for(Event eTemp : tempList) {
					insertEvent(eTemp);
				}
			}
			//if there were no conflicting events, put new event in right place
			else {
				for(Event e2 : eventList) {
					if(e.startTime + e.duration <= e2.startTime) {
						int ind = eventList.indexOf(e2);
						eventList.add(ind, e); //insert new event at correct index
						return;
					}
				}
			}
		}
		//the new event is flexible
		else {
			//if there is time at the beginning, insert new event first
			if(eventList.get(0).startTime - Clock.getTime() > e.duration) {
				eventList.add(0, e);
			}
			else if(e.type == EventType.Eat) {
				for(int i = 0; i < eventList.size() - 1; i++) {
					//if there is a flexible event, eating takes it's place
					if(eventList.get(i).flexible) {
						Event temp = eventList.get(i);
						eventList.add(i, e); //adding new event into flexible space
						eventList.remove(temp);
						insertEvent(temp);
					}
					//if there is space after that event, put eating event there
					if(eventList.get(i + 1).startTime - 
							eventList.get(i).startTime + eventList.get(i).duration > e.duration) {
						//adding new event into middle of list if there is space
						eventList.add(i + 1, e);
						return;
					}
				}
				eventList.add(e); //adding new event at end of list
			}
			else {
				for(int i = 0; i < eventList.size() - 1; i++) {
					if(eventList.get(i + 1).startTime - 
							eventList.get(i).startTime + eventList.get(i).duration > e.duration) {
						//adding new event into middle of list if there is space
						eventList.add(i + 1, e);
						return;
					}
				}
				eventList.add(e); //adding new event at end of list
			}
		}	
	}

	// Utility functions
	public String getName() {
		return name;
	}
	
	public boolean isIdle() {
		return (currentRole == null);
	}
	
	// Classes
	
	/***
	 * Step - Represents the steps that an event must do
	 * @author rebeccahao
	 *
	 */
	private class Step {
		Role role;
		Method method;
		PersonAgent person;
		Location locParameter;
		String methodName;

		/****
		 * Step - constructor
		 * @param m - String which is a method name that makes up the step
		 * @param p - Person Agent instance which the method will be called on
		 */
		Step(String m, PersonAgent p) {
			person = p;
			methodName = m;

			try {
				method = (PersonAgent.class).getDeclaredMethod(m, String.class);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		
		public void doMethod() {

			try {
				method.invoke(person);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}



		}
	}

	/***
	 * Event - Events that get put in the person's list for the scheduler to execute
	 * @author rebeccahao
	 *
	 */
	private class Event {
		String buildingName;
		Role role;
		int startTime;
		int duration;
		boolean flexible;
		List<Step> steps = new ArrayList<Step>();
		EventType type;

		/****
		 * Event - constructor
		 * 
		 *  @param n - Name of the destination building
		 *  @param r - Role the person takes on after arriving at the location
		 *  @param d - Duration of time the event should take overall
		 *  @param f - Flexibility, meaning whether the event's start time can change or not
		 *  @param s - List of steps that need to be executed to complete the event
		 */
		Event(String n, Role r, int d, boolean f, List<Step> s, EventType t) {
			buildingName = n;
			role = r;
			duration = d;
			flexible = f;
			steps = s;
			type = t;
		}

		private boolean overlaps(Event e) {
			return false;
		}

		private boolean nextStep() {

			return true;

		}
	}

}
























/*************************
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
		for(Role role : myRoles){
			if (role.active==true){
				//Do("action");
				role.active=false;
			}
		}
		Schedule.remove(1);
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
			//Do("here"+ getSchedule().firstEntry().getValue().role);
			Entry<Integer,Event> ent = getSchedule().firstEntry();
			if (gettime() >= ent. getKey()) {

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

		Do("time: "+time);
		return time;
	}

	private void InstantiatePerson() {
		PedestrianRole pedestrianRole = new PedestrianRole(this);
		myRoles.add(pedestrianRole);
		RestaurantOneCustomerGui restaurantOneCustomerGui = new RestaurantOneCustomerGui();
		RestaurantOneCustomerRole restaurantOneCustomer = new RestaurantOneCustomerRole(this, restaurantOneCustomerGui);
		BankCustomerGui bankCustomerGui = new BankCustomerGui();
		BankCustomerRole bankCustomer = new BankCustomerRole(this, bankCustomerGui);

			pedestrianRole.setPerson(this);
			Location l= new Location(250,250);
			Event e = new Event(l,myRoles.get(0));

			getSchedule().put(1, e);
			gettime();

		myRoles.add(restaurantOneCustomer);
		restaurantOneCustomer.setPerson(this);	
		Location l1 = new Location(12, 12);
			Event e1 = new Event(l1,myRoles.get(1));
			getSchedule().put(7, e1);

			myRoles.add(bankCustomer);
			bankCustomer.setPerson(this);
			Location l2= new Location(250,250);
			Event e2 = new Event(l2,myRoles.get(2));
			getSchedule().put(15, e2);

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

 *****************/
