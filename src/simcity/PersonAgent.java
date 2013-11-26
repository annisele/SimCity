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

import simcity.buildings.bank.BankCustomerRole;
import simcity.buildings.bank.BankSystem;
import simcity.buildings.house.HouseInhabitantRole;
import simcity.buildings.market.MarketCustomerRole;
import simcity.buildings.restaurant.five.RestaurantFiveCustomerRole;
import simcity.buildings.restaurant.four.RestaurantFourCustomerRole;
import simcity.buildings.restaurant.two.RestaurantTwoCustomerRole;
import simcity.buildings.transportation.BusAgent;
import simcity.buildings.transportation.BusPassengerRole;
import simcity.buildings.transportation.PedestrianRole;
import simcity.gui.IdlePersonGui;
import simcity.interfaces.Person;
import simcity.interfaces.bank.BankCustomer;
import simcity.interfaces.market.MarketCustomer;
import simcity.interfaces.restaurant.two.RestaurantTwoCustomer;
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
	private Timer timer = new Timer();
	
	private List<Role> myRoles = new ArrayList<Role>();
	private List<Event> eventList = new ArrayList<Event>();
	private IdlePersonGui idleGui;
	BusAgent bus;
	private Role currentRole = null;
	private Event currentEvent = null;
	
	public enum EventType { Eat, GoToMarket, BusToMarket, EatAtRestaurant, DepositMoney, WithdrawMoney, GetALoan, PayRent, Sleep, Work };
	
	private String name;
	private double money = 10;
	private double withdrawThreshold = 5; // if money is less than this, we will try to withdraw
	private double depositThreshold = 15; // if money is higher than this, we will try to deposit

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

		BusPassengerRole bp = new BusPassengerRole(this);
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
		myRoles.add(bp);
		//myRoles.add(r2);
		myRoles.add(r2);

		myRoles.add(r4);
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
	
	public void addMoney(double m) {
		money += m;
		checkMoneyThreshold();
	}
	
	public void subtractMoney(double m) {
		money -= m;
		checkMoneyThreshold();
	}
		
	public void checkMoneyThreshold() {
		if(money <= withdrawThreshold) {
			scheduleEvent(EventType.WithdrawMoney);
		}
		else if(money >= depositThreshold) {
			scheduleEvent(EventType.DepositMoney);
		}
	}


	public boolean scheduleEvent(EventType t) {
		Event e;
		if(t == EventType.GoToMarket) {
			
			List<String> markets = Directory.getMarkets();
			if (markets.size() == 0) {
				return false;
			}
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
			
			//((MarketCustomer)eventR).msgBuyStuff(house.getListToBuy());
			//hack
			Map<String, Integer> itemsHack = new HashMap<String, Integer>();
			itemsHack.put("chicken", 1);
			((MarketCustomer)eventR).msgBuyStuff(itemsHack);
			
			e = new Event(buildingName, eventR, 120, -1, true, steps, t);
			//Do("GoToMarket is scheduled, which has "+steps.size()+" steps");
			insertEvent(e);
			stateChanged();
		}
		
		
		if(t == EventType.BusToMarket) {
			List<String> markets = Directory.getMarkets();
			int index = rand.nextInt(markets.size());
			String buildingName = markets.get(index);
			List<Step> steps = new ArrayList<Step>();
			steps.add(new Step("exitBuilding", this));
			//steps.add(new Step("goTo", this));         ///MAKE FUNCTION FOR THESE TWO LINES
			//steps.add(new Step("enterBuilding", this));
			//steps.add(new Step("goToBusStop", this));     ///MAKE FUNCTION FOR THESE FOUR LINES
			//steps.add(new Step("waitForBus", this));
			//steps.add(new Step("goTo", this));
			//steps.add(new Step("enterBuilding", this));
			if (chooseTransportation(buildingName) == 10) {
				steps.add(new Step("goTo", this));
				steps.add(new Step("enterBuilding", this));
				
			}
			else {
				steps.add(new Step("goToBusStop", this));
				steps.add(new Step("waitForBus", this)); 
				steps.add(new Step("goTo",this));
				steps.add(new Step("enterBuilding", this));
				
			}
				//waitForTransport();
			//steps.add(new Step("goTo", this));
			
			/*if (chooseTransportation() == 0) {
			steps.add(new Step("goTo", this));
			}
			else {
				steps.add(new Step("goToBusStop", this)); */
		//HERE NEXT TO FIX	steps.add(new Step())
				//steps.add(new Step("goTo", this));
			//} 
			//steps.add(new Step("enterBuilding", this));
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
			
			((MarketCustomer)eventR).msgBuyStuff(house.getListToBuy());
			//hack
			Map<String, Integer> itemsHack = new HashMap<String, Integer>();
			itemsHack.put("chicken", 1);
			//((MarketCustomer)eventR).msgBuyStuff(itemsHack);
			
			e = new Event(buildingName, eventR, 120, -1, true, steps, t);
			//Do("GoToMarket is scheduled, which has "+steps.size()+" steps");
			insertEvent(e);
			stateChanged();
			
		}
		if(t == EventType.EatAtRestaurant) {
			List<String> restaurants = Directory.getRestaurants();
			//int index = rand.nextInt(restaurants.size());
			//HACK FOR RESTAURANT 2 ONLY
			
			String buildingName = restaurants.get(0);
			List<Step> steps = new ArrayList<Step>();
			steps.add(new Step("exitBuilding", this));
			steps.add(new Step("goTo", this));
			steps.add(new Step("enterBuilding", this));
			Role eventR = null;
			for(Role r : myRoles) {
				if(r instanceof RestaurantTwoCustomer) {
					eventR = r;
				
				}
			}
			
		
			//hack
			//RestaurantTwoCustomerRole rc = new RestaurantTwoCustomerRole(this);
			//((RestaurantTwoCustomer)eventR).msgArrivedAtRestaurant();
			
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
			
			//hack
			((BankCustomer)eventR).msgDepositMoney((BankSystem)(Directory.getSystem(buildingName)));
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

			//hack
			((BankCustomer)eventR).msgWithdrawMoney((BankSystem)(Directory.getSystem(buildingName)));
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
			//hack
			((BankCustomer)eventR).msgGetLoan((BankSystem)(Directory.getSystem(buildingName)));
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
			//hack
			((BankCustomer)eventR).msgPayRent((BankSystem)(Directory.getSystem(buildingName)));
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
		return true;
	}

	//this assumes after roles are done, they go stand outside the building
	//so this only needs to prep the person to walk somewhere by changing it to pedestrian
	
	int chooseTransportation(String dest) {
		Role eventR = null;
		for (Role r : myRoles) {
			if (r instanceof Pedestrian) {
				eventR = r;
				
			}
		}
		int minLocation = 1000;
		int minStop = 10;
		int stopzerodist = (int)Math.sqrt(((Directory.getBusStop(0).getX()-eventR.getGui().getX())^2 + (Directory.getBusStop(0).getY()-eventR.getGui().getY())^2));
		int stoponedist = (int)Math.sqrt(((Directory.getBusStop(1).getX()-eventR.getGui().getX())^2 + (Directory.getBusStop(1).getY()-eventR.getGui().getY())^2));
		int stoptwodist = (int)Math.sqrt(((Directory.getBusStop(2).getX()-eventR.getGui().getX())^2 + (Directory.getBusStop(2).getY()-eventR.getGui().getY())^2));
		int stopthreedist = (int)Math.sqrt(((Directory.getBusStop(3).getX()-eventR.getGui().getX())^2 + (Directory.getBusStop(3).getY()-eventR.getGui().getY())^2));
		if (stopzerodist < minLocation) {
			minLocation = stopzerodist;
			minStop = 0; 
		}
		if (stoponedist < minLocation) {
			minLocation = stoponedist;
			minStop = 1;
		}
		if (stoptwodist < minLocation) {
			minLocation = stoptwodist;
			minStop = 2;
		}
		if (stopthreedist < minLocation) {
			minLocation = stopthreedist;
			minStop = 3;
		}
		PedestrianRole tempR = (PedestrianRole)eventR;
		int buildingdist = (int)Math.sqrt(((Directory.getLocation(dest).getX()-tempR.getGui().getX())^2 + (Directory.getLocation(dest).getX()-tempR.getGui().getX())^2) );
		if (minLocation < buildingdist) {
			System.out.println("Transit");
			return minStop;
		}
		else {
			System.out.println("Walk");
			return 10;
		}
	}
	
	int getClosestStop(String dest) {
		Role eventR = null;
		for (Role r : myRoles) {
			if (r instanceof Pedestrian) {
				eventR = r;
				
			}
		}
		int minLocation = 1000;
		int minStop = 10;
	   int stopzerodist = (int)Math.sqrt(((Directory.getBusStop(0).getX()-Directory.getLocation(dest).getX())^2 + (Directory.getBusStop(0).getY()-Directory.getLocation(dest).getY())^2));
		int stoponedist = (int)Math.sqrt(((Directory.getBusStop(1).getX()-Directory.getLocation(dest).getX())^2 + (Directory.getBusStop(1).getY()-Directory.getLocation(dest).getY())^2));
		int stoptwodist = (int)Math.sqrt(((Directory.getBusStop(2).getX()-Directory.getLocation(dest).getX())^2 + (Directory.getBusStop(2).getY()-Directory.getLocation(dest).getY())^2));
	  int stopthreedist = (int)Math.sqrt(((Directory.getBusStop(3).getX()-Directory.getLocation(dest).getX())^2 + (Directory.getBusStop(3).getY()-Directory.getLocation(dest).getY())^2));
		if (stopzerodist < minLocation) {
			minLocation = stopzerodist;
			minStop = 0; 
		}
		if (stoponedist < minLocation) {
			minLocation = stoponedist;
			minStop = 1;
		}
		if (stoptwodist < minLocation) {
			minLocation = stoptwodist;
			minStop = 2;
		}
		if (stopthreedist < minLocation) {
			minLocation = stopthreedist;
			minStop = 3;
		}
		return minStop;
	}
	
	
			
			
		
	

		
	
	public void exitBuilding() {
		//Do("exitBuilding step is called");
		if (currentRole != null)
			currentRole.msgExitBuilding();
		stateChanged();
	}
	
	public void goToBusStop() {
		for(Role r : myRoles) {
			if(r instanceof Pedestrian) {
				currentRole = r;
				Directory.getWorld().getAnimationPanel().addGui(currentRole.getGui());
		}
	}
		//currentRole.getGui().ge
		Location loc = Directory.getBusStop(chooseTransportation(currentEvent.buildingName));
		((PedestrianRole)currentRole).addDestination(loc);
		//waitForTransport();
		stateChanged();
	}
	
	public void waitForBus() {
		for (Role r : myRoles) {
			if(r instanceof BusPassengerRole) {
				currentRole = r;
				//Location l = new Location(40, 67);
				((BusPassengerRole) r).setBus(bus);
				((BusPassengerRole) r).msgBusTo(chooseTransportation(currentEvent.buildingName), getClosestStop(currentEvent.buildingName));
				idleGui.setLocation(r.getGui().getLocation());	
			}
		}
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
		Do(currentEvent.buildingName + ", " + loc.getX() + ", " + loc.getY());
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
	
	public void waitForTransport() {
		for (Role r : myRoles) {
			if (r instanceof Pedestrian) {
				idleGui.setLocation(r.getGui().getLocation());
			}
		}
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
	
	public void busToMarketNow() {
		this.scheduleEvent(EventType.BusToMarket);
	}

	public void goToBankNow() {
		this.scheduleEvent(EventType.WithdrawMoney);
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
		HouseInhabitantRole h = null;
		for(Role r : myRoles) {
			if(r instanceof HouseInhabitantRole) {
				h = (HouseInhabitantRole) r;
			}
		}  
		h.addItems(tempItems);
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
	
	public void clear() {
		for (Role r : myRoles) {
			r.clear();
		}
		timer.cancel();
		timer.purge();
	}
	
	public void setBus(BusAgent b) {
		bus = b;
	}

	public void setPedestrianRoleLocation(int x, int y) {
		for(Role r : myRoles) {
			if(r instanceof PedestrianRole) {
				PedestrianRole tempR = (PedestrianRole)r;
				tempR.msgArrivedAtLocationFromBus(x, y);
			}
		}  
	}
}
