package simcity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;



import simcity.gui.Gui;
import simcity.gui.IdlePersonGui;
import simcity.gui.bank.BankCustomerGui;
import simcity.gui.restaurantone.RestaurantOneCustomerGui;
import simcity.gui.transportation.PedestrianGui;
import simcity.interfaces.Person;
import simcity.interfaces.bank.BankCustomer;
import simcity.interfaces.house.HouseInhabitant;
import simcity.interfaces.market.MarketCustomer;
import simcity.interfaces.restaurant.one.RestaurantOneCustomer;
import simcity.interfaces.restaurant.two.RestaurantTwoCustomer;
import simcity.interfaces.transportation.Pedestrian;
import simcity.buildings.bank.BankCustomerRole;
import simcity.buildings.bank.BankSystem;
import simcity.buildings.house.HouseInhabitantRole;
import simcity.buildings.market.MarketCustomerRole;
import simcity.buildings.restaurant.five.RestaurantFiveCustomerRole;
import simcity.buildings.restaurant.four.RestaurantFourCustomerRole;
import simcity.buildings.restaurant.two.RestaurantTwoCustomerRole;
import simcity.buildings.transportation.PedestrianRole;
import simcity.gui.IdlePersonGui;
import simcity.interfaces.Person;
import simcity.interfaces.bank.BankCustomer;
import simcity.interfaces.market.MarketCustomer;
import simcity.interfaces.transportation.Pedestrian;
import agent.Agent;


/**
 * ISSUES: It adds GoToMarket event, then does the first step (ExitBuilding),
 *  so person is now a pedestrian gui. However, it never goes on to the next step
 *  so it never sets the pedestrian's destination. It only calls event.nextStep once
 *  for some reason.
 */

public class PersonAgent extends Agent implements Person {

	private Random rand = new Random();
	private String name;
	private List<Role> myRoles = new ArrayList<Role>();
	private List<Event> eventList = new ArrayList<Event>();
	private Role currentRole = null;
	private Event currentEvent = null;
	private Timer timer = new Timer();
	public enum EventType { Eat, GoToMarket,EatAtRestaurant, DepositMoney, WithdrawMoney, GetALoan, PayRent, Sleep, Work };
	private IdlePersonGui idleGui;

	public double money = 40;
	private String home;
	private String workBuilding;
	private Role workRole;

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
		myRoles.add(h);
		myRoles.add(m);
		myRoles.add(b);
		myRoles.add(r2);
		Do("r: "+r2);
		myRoles.add(r4);
		Do("roles "+myRoles.toString());
		//myRoles.add(r5);
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		if(currentRole != null) {
			return currentRole.pickAndExecuteAnAction();
		}
		else if(currentEvent != null) {
			//does the next step
			//if it returns false because there are no more steps, remove event from the list
			//Do("Current event is not null");
			if(!currentEvent.nextStep()) {
				//Do("Current event.nextStep returned false, so I must be done and idle");
				currentEvent = null;
				if (currentRole != null)
					Do("CurrentRole is not null at this point in the scheduler, but it should be!");
				//currentRole = null;
				//idleGui.setLocation(currentRole.getGui().getLocation());
				eventList.remove(0);
				return true;
			}
		}
		// I switched the order of those first to scheduler if statements, and it seems to help


		//move to next step in the event
		//else
		//move to the next event if currentEvent is null
		else {
			//Do("EventList has a size of "+eventList.size());
			if(eventList.size() > 0) {
				if(eventList.get(0).startTime <= Clock.getTime() || currentEvent == null) {
					currentEvent = eventList.get(0); //set next event to current
					//Do("Just set next event to current");
					return true;
				}
				else if(Directory.getLocation(eventList.get(0).buildingName) == Directory.getLocation(currentEvent.buildingName) && eventList.get(0).flexible) {
					//set next event to current if at same place and next event is flexible
					currentEvent = eventList.get(0);
					//Do("Just set next event to current");
					return true;
				}
				else {
					//Do("Calling waitForNextEvent()");
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
				if(r instanceof HouseInhabitantRole) {
					house = (HouseInhabitantRole) r;
				}
			}
			
			//((MarketCustomer)eventR).msgBuyStuff(house.getListToBuy(), (MarketSystem)(Directory.getSystem(buildingName)));
			//hack
			Map<String, Integer> itemsHack = new HashMap<String, Integer>();
			itemsHack.put("chicken", 1);
			((MarketCustomer)eventR).msgBuyStuff(itemsHack);
			
			e = new Event(buildingName, eventR, 120, -1, true, steps, t);
			//Do("GoToMarket is scheduled, which has "+steps.size()+" steps");
			insertEvent(e);
			stateChanged();
		}
		if(t == EventType.EatAtRestaurant) {
			List<String> restaurants = Directory.getRestaurants();
			//int index = rand.nextInt(restaurants.size());
			//HACK FOR RESTAURANT 2 ONLY
			Do("NAME: "+ restaurants.get(0));
			String buildingName = restaurants.get(0);
			List<Step> steps = new ArrayList<Step>();
			steps.add(new Step("exitBuilding", this));
			steps.add(new Step("goTo", this));
			steps.add(new Step("enterBuilding", this));
			Role eventR = null;
			for(Role r : myRoles) {
				if(r instanceof RestaurantTwoCustomer) {
					eventR = r;
					Do("ppppwef: "+eventR);
				}
			}
			Do("pppp: "+eventR);
			HouseInhabitantRole house = null;
			for(Role r : myRoles) {
				if(r instanceof HouseInhabitantRole) {
					house = (HouseInhabitantRole) r;
				}
			}
			//((MarketCustomer)eventR).msgBuyStuff(house.getListToBuy(), (MarketSystem)(Directory.getSystem(buildingName)));
			
			//hack
			//RestaurantTwoCustomerRole rc = new RestaurantTwoCustomerRole(this);
			((RestaurantTwoCustomer)eventR).msgArrivedAtRestaurant();
			
			e = new Event(buildingName, eventR, 120, -1, true, steps, t);
			//Do("GoToMarket is scheduled, which has "+steps.size()+" steps");
			insertEvent(e);
			stateChanged();
		}
		else if (t == EventType.DepositMoney) {
			
			List<String> banks = Directory.getBanks();
			Do("We're Depositing, and banks size is "+banks.size());
			int index = rand.nextInt(banks.size());
			String buildingName = banks.get(index);
			List<Step> steps = new ArrayList<Step>();
			steps.add(new Step("exitBuilding", this));
			steps.add(new Step("goTo", this));
			steps.add(new Step("enterBuilding", this));
			Role eventR = null;
			for(Role r : myRoles) {
				if(r instanceof BankCustomer) {
					eventR = r;
				}
			}
			HouseInhabitantRole house = null;
			for(Role r : myRoles) {
				if(r instanceof HouseInhabitantRole) {
					house = (HouseInhabitantRole) r;
				}
			}
			//hack
			//BankCustomerRole bc = new BankCustomerRole(this);
			
			((BankCustomer)eventR).msgDepositMoney((BankSystem)(Directory.getSystem(buildingName)));
			//((BankCustomer)eventR).msgArrivedAtBank();
			e = new Event(buildingName, eventR, 120, -1, true, steps, t);
			
			insertEvent(e);
			stateChanged();
			
			
		}
		else if (t == EventType.WithdrawMoney) {
			List<String> banks = Directory.getBanks();
			int index = rand.nextInt(banks.size());
			String buildingName = banks.get(index);
			List<Step> steps = new ArrayList<Step>();
			steps.add(new Step("exitBuilding", this));
			steps.add(new Step("goTo", this));
			steps.add(new Step("enterBuilding", this));
			Role eventR = null;
			for(Role r : myRoles) {
				if(r instanceof BankCustomer) {
					eventR = r;
				}
			}
			HouseInhabitantRole house = null;
			for(Role r : myRoles) {
				if(r instanceof HouseInhabitantRole) {
					house = (HouseInhabitantRole) r;
				}
			}
			//hack
			((BankCustomer)eventR).msgWithdrawMoney((BankSystem)(Directory.getSystem(buildingName)));
			((BankCustomer)eventR).msgArrivedAtBank();
			
			e = new Event(buildingName, eventR, 120, -1, true, steps, t);
			
			insertEvent(e);
			stateChanged();
		}
		else if (t == EventType.GetALoan) {
			List<String> banks = Directory.getBanks();
			int index = rand.nextInt(banks.size());
			String buildingName = banks.get(index);
			List<Step> steps = new ArrayList<Step>();
			steps.add(new Step("exitBuilding", this));
			steps.add(new Step("goTo", this));
			steps.add(new Step("enterBuilding", this));
			Role eventR = null;
			for(Role r : myRoles) {
				if(r instanceof BankCustomer) {
					eventR = r;
				}
			}
			HouseInhabitantRole house = null;
			for(Role r : myRoles) {
				if(r instanceof HouseInhabitantRole) {
					house = (HouseInhabitantRole) r;
				}
			}
			//hack
			BankCustomerRole o = new BankCustomerRole(this);
			//assuming that getting a loan increases the amount of money in account
			((BankCustomer)eventR).msgHereIsYourLoan(o, 123456, 1500, 500);
			
			e = new Event(buildingName, eventR, 120, -1, true, steps, t);
			
			insertEvent(e);
			stateChanged();
		}
		else if (t == EventType.PayRent) {
			List<String> banks = Directory.getBanks();
			int index = rand.nextInt(banks.size());
			String buildingName = banks.get(index);
			List<Step> steps = new ArrayList<Step>();
			steps.add(new Step("exitBuilding", this));
			steps.add(new Step("goTo", this));
			steps.add(new Step("enterBuilding", this));
			Role eventR = null;
			for(Role r : myRoles) {
				if(r instanceof BankCustomer) {
					eventR = r;
				}
			}
			HouseInhabitantRole house = null;
			for(Role r : myRoles) {
				if(r instanceof HouseInhabitantRole) {
					house = (HouseInhabitantRole) r;
				}
			}
			//hack
			BankCustomerRole z = new BankCustomerRole(this);
			//assuming that paying rent reduces the amount of money in account
			((BankCustomer)eventR).msgMoneyIsDeposited(z, 123456, 1000, 500);
			
			e = new Event(buildingName, eventR, 120, -1, true, steps, t);
			
			insertEvent(e);
			stateChanged();
		}
		else if (t == EventType.Work) {
			List<Step> steps = new ArrayList<Step>();
			steps.add(new Step("exitBuilding", this));
			steps.add(new Step("goTo", this));
			steps.add(new Step("enterBuilding", this));
			//Do("building: "+workBuilding+" workrole: "+workRole);
			e = new Event(workBuilding, workRole, 120, 3, false, steps, t);
			//Do("GoToWork is scheduled, which has "+steps.size()+" steps");
			insertEvent(e);
			stateChanged();
		}
		else if (t == EventType.Sleep) {
			List<Step> steps = new ArrayList<Step>();
			steps.add(new Step("exitBuilding", this));
			steps.add(new Step("goTo", this));
			steps.add(new Step("enterBuilding", this));
			HouseInhabitantRole house = null;
			for(Role r : myRoles) {
				if(r instanceof HouseInhabitantRole) {
					house = (HouseInhabitantRole) r;
				}
			}
			house.msgGoToBed();
			e = new Event(home, house, 480, 3, false, steps, t);
			//Do("GoToWork is scheduled, which has "+steps.size()+" steps");
			insertEvent(e);
			stateChanged();
		}
	}

	//this assumes after roles are done, they go stand outside the building
	//so this only needs to prep the person to walk somewhere by changing it to pedestrian
	public void exitBuilding() {
		//Do("exitBuilding step is called");
		stateChanged();
	}

	//later, add bus and car options
	public void goTo() {
		for(Role r : myRoles) {
			if(r instanceof Pedestrian) {
				currentRole = r;
				Directory.getWorld().getAnimationPanel().addGui(currentRole.getGui());
			}
		}
		Location loc = Directory.getLocation(currentEvent.buildingName);
		//Do("Location is: "+loc.getX()+", "+loc.getY());
		//Do("PedRole is being given a destination!");
		((PedestrianRole)currentRole).addDestination(loc);
		stateChanged();
		
	}

	public void enterBuilding() {
		//Do("buildng name: "+ currentEvent.buildingName+" rold: "+currentEvent.role);
		if(Directory.getSystem(currentEvent.buildingName).msgEnterBuilding(currentEvent.role)) {
			currentRole = currentEvent.role;
			Do("Entered building. Changing role to " + currentRole.getClass());
			
			currentRole.msgEnterBuilding(Directory.getSystem(currentEvent.buildingName));
			
		} else {
			Do("Building closed. Cannot enter.");
			scheduleEvent(currentEvent.type); //maybe change this?
		}
		stateChanged();
	}

	public void roleFinished() {
		//Do("Role is finished");
		//TODO: add more if statements once bus/car agents are in
		if(currentRole instanceof Pedestrian) {
			Directory.getWorld().getAnimationPanel().removeGui(currentRole.getGui());
		}
		else {
			Directory.getSystem(currentEvent.buildingName).animationPanel.removeGui(currentRole.getGui());
			for (Role r : myRoles) {
				if (r instanceof Pedestrian) {
					idleGui.setLocation(r.getGui().getLocation());
				}
			}
		}
		currentRole = null;
		stateChanged();
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
				if(eventList.isEmpty()) {
					eventList.add(0, e);
				}
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
		}
		//the new event is flexible
		else {
			//if there is time at the beginning, insert new event first
			if(eventList.isEmpty()) {
				eventList.add(0, e);
			}
			else if(eventList.get(0).startTime - Clock.getTime() > e.duration) {
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

	//Test event
	public void goToMarketNow() {
		this.scheduleEvent(EventType.GoToMarket);
	}

	public void goToBankNow() {
		this.scheduleEvent(EventType.DepositMoney);
	}
	public void goToRestaurantTwoNow() {
		this.scheduleEvent(EventType.EatAtRestaurant);
	}
	
	public boolean isIdle() {
		return (currentRole == null);
	}
	
	//hack
	public IdlePersonGui getIdleGui() {
		return idleGui;
	}

	public void receiveDelivery(Map<String, Integer> tempItems) {
		// TODO Auto-generated method stub

	}

	public void addWork(Role r, String building) {
		myRoles.add(r);
		workBuilding = building;
		workRole = r;
		scheduleEvent(EventType.Work);
	}
	
	public void addHome(String building) {
		//myRoles.add(r);
		home = building;
		scheduleEvent(EventType.Sleep);
		//scheduleEvent(EventType.Sleep);
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
				method = (PersonAgent.class).getDeclaredMethod(m);
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
				e.getCause().printStackTrace();
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
		 *  @param st - Start time of the event, if it is flexible. Otherwise, should be ignored.
		 *  @param f - Flexibility, meaning whether the event's start time can change or not
		 *  @param s - List of steps that need to be executed to complete the event
		 */
		Event(String n, Role r, int d, int st, boolean f, List<Step> s, EventType t) {
			buildingName = n;
			role = r;
			startTime = st;
			duration = d;
			flexible = f;
			steps = s;
			type = t;
		}

		private boolean overlaps(Event e) {
			//e starts halfway through this event
			int end = startTime + duration;
			int e_end = e.startTime + e.duration;
			if(startTime <= e.startTime && end >= e.startTime) {
				return true;
			}
			//if e ends halfway into this event
			else if(startTime >= e.startTime && startTime <= e_end) {
				return true;
			}
			//if e completely surrounds this event
			else if(e.startTime <= startTime && e_end >= end) {
				return true;
			}
			//if e is completely inside this event
			else if(startTime <= e.startTime && end >= e_end) {
				return true;
			}
			//if it passes all those tests, the two events are not conflicting
			return false;
		}

		private boolean nextStep() {
			//Do("nextStep is being called, currently "+steps.size()+" steps");
			if(steps.isEmpty()) {
				return false;
			}
			else {
				steps.get(0).doMethod();
				//Do("An event's step is being removed");
				steps.remove(0);
				return true;
			}


		}
	}
}
