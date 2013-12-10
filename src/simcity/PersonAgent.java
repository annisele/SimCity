package simcity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import simcity.buildings.bank.BankCustomerRole;
import simcity.buildings.bank.BankRobberRole;
import simcity.buildings.bank.BankSystem;
import simcity.buildings.house.HouseInhabitantRole;
import simcity.buildings.market.MarketCustomerRole;
import simcity.buildings.restaurant.five.RestaurantFiveCustomerRole;
import simcity.buildings.restaurant.five.RestaurantFiveSystem;
import simcity.buildings.restaurant.four.RestaurantFourCustomerRole;
import simcity.buildings.restaurant.four.RestaurantFourSystem;
import simcity.buildings.restaurant.one.RestaurantOneCustomerRole;
import simcity.buildings.restaurant.one.RestaurantOneSystem;
import simcity.buildings.restaurant.three.RestaurantThreeCustomerRole;
import simcity.buildings.restaurant.three.RestaurantThreeSystem;
import simcity.buildings.restaurant.two.RestaurantTwoCustomerRole;
import simcity.buildings.restaurant.two.RestaurantTwoSystem;
import simcity.buildings.transportation.BusAgent;
import simcity.buildings.transportation.BusPassengerRole;
import simcity.buildings.transportation.CarAgent;
import simcity.buildings.transportation.CarPassengerRole;
import simcity.buildings.transportation.PedestrianRole;
import simcity.gui.IdlePersonGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.gui.transportation.CarGui;
import simcity.interfaces.Person;
import simcity.interfaces.bank.BankCustomer;
import simcity.interfaces.bank.BankRobber;
import simcity.interfaces.house.HouseInhabitant;
import simcity.interfaces.market.MarketCustomer;
import simcity.interfaces.restaurant.five.RestaurantFiveCustomer;
import simcity.interfaces.restaurant.four.RestaurantFourCustomer;
import simcity.interfaces.restaurant.one.RestaurantOneCustomer;
import simcity.interfaces.restaurant.three.RestaurantThreeCustomer;
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
	private enum TimingType {Early, Late};
	private TimingType type;
	private List<Role> myRoles = new ArrayList<Role>();
	private List<Event> eventList = Collections.synchronizedList(new ArrayList<Event>());
	private IdlePersonGui idleGui;
	BusAgent bus;
	private Role currentRole = null;
	private Event currentEvent = null;

	public enum EventType { Eat, GoToMarket, BusToMarket, EatAtRestaurant, EatAtHome, DepositMoney, WithdrawMoney, GetALoan, PayRent, Sleep, Work, WorkNow, CarToMarket, RobBank };

	private String name;
	private double money = 10;
	private double withdrawThreshold = 10; // if money is less than this, we will try to withdraw
	private double depositThreshold = 25; // if money is higher than this, we will try to deposit
	final int TWO_HOURS = 12;
	private final int EIGHT_HOURS = 48;
	final int FIRSTSLEEPDURATION = 6;
	final int SLEEPDURATION = 48;
	final int AWAKEDURATION = 88;
	private boolean workClosed = false;
	private String bankPassword;
	private int accountNumber;
	private CarAgent car;
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

		BankRobberRole br = new BankRobberRole(this);

		CarPassengerRole cpr = new CarPassengerRole(this);

		BusPassengerRole bp = new BusPassengerRole(this);
		RestaurantOneCustomerRole r1 = new RestaurantOneCustomerRole(this);
		RestaurantTwoCustomerRole r2 = new RestaurantTwoCustomerRole(this);
		RestaurantThreeCustomerRole r3 = new RestaurantThreeCustomerRole(this);
		RestaurantFourCustomerRole r4 = new RestaurantFourCustomerRole(this);
		RestaurantFiveCustomerRole r5 = new RestaurantFiveCustomerRole(this);
		//RestaurantSixCustomerRole r6 = new RestaurantSixCustomerRole(this);
		myRoles.add(p);
		myRoles.add(h);
		myRoles.add(m);
		myRoles.add(b);
		myRoles.add(br);
		myRoles.add(bp);
		myRoles.add(r1);
		myRoles.add(r2);
		myRoles.add(r3);
		myRoles.add(r4);
		myRoles.add(r5);
		myRoles.add(cpr);
		
		money = 5.0 + 15*rand.nextDouble(); //$5-$20
		  
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		String eventString = "";
		for(Event e : eventList) {
			eventString = eventString + ", " + e.type + " @ " + e.startTime;
		}
		AlertLog.getInstance().logDebug(AlertTag.WORLD, "WORLD: " + getName(), "SCHED: " + eventString);										

		if(currentRole != null) {
			return currentRole.pickAndExecuteAnAction();
		}
		else if(currentEvent != null) {
			//does the next step
			//if it returns false because there are no more steps, remove event from the list
			if(!currentEvent.nextStep()) {
				AlertLog.getInstance().logDebug(AlertTag.WORLD, "WORLD: " + getName(), "Done with last event step!!! " + currentEvent.type + ", " + eventList.size());										
				//Do("Current event.nextStep returned false, so I must be done and idle");
				currentEvent = null;
				if (currentRole != null) {
					AlertLog.getInstance().logError(AlertTag.WORLD, "Person: "+name, "CurrentRole is not null at this point in the scheduler, but it should be!");						
				}
				//currentRole = null;
				//idleGui.setLocation(currentRole.getGui().getLocation());
				eventList.remove(0);
				return true;
			}
		} 
		else {
			AlertLog.getInstance().logDebug(AlertTag.WORLD, "WORLD: " + getName(), "CURRENT EVENT IS NULL.. " + eventList.size());										
			if(eventList.size() > 0) {
				if(eventList.get(0).startTime <= Clock.getTime() || eventList.get(0).flexible) {
					currentEvent = eventList.get(0); //set next event to current
					AlertLog.getInstance().logDebug(AlertTag.WORLD, "WORLD: " + getName(), "STARTING NEXT EVENT: " + currentEvent.type);										
					return true;
				}
				else if(!eventList.get(0).flexible) {
					waitForNextEvent();
				}
				else if(Directory.getLocation(eventList.get(0).buildingName) == Directory.getLocation(currentEvent.buildingName) && eventList.get(0).flexible) {
					//set next event to current if at same place and next event is flexible
					currentEvent = eventList.get(0);
					AlertLog.getInstance().logDebug(AlertTag.WORLD, "WORLD: " + getName(), "event is flexible or in same building, starting... " + currentEvent.type);										
					return true;
				}
				else {
					Do("person agent scheduler unecessary if?");
					waitForNextEvent();
				}
			}
		}
		return false;
	}

	private void waitForNextEvent() {
		long waitTime = 2000 * (eventList.get(0).startTime - Clock.getTime());
		AlertLog.getInstance().logDebug(AlertTag.WORLD, "WORLD: " + getName(), "waiting for event... wait time = " + waitTime);										
		timer.schedule(new TimerTask() {
			public void run() {
				AlertLog.getInstance().logDebug(AlertTag.WORLD, "WORLD: " + getName(), "wait for event done. " + Clock.currentTime);										
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

	public Map<String, Integer> getListToBuy() {
		HouseInhabitantRole house = null;
		for(Role r : myRoles) {
			if(r instanceof HouseInhabitantRole) {
				house = (HouseInhabitantRole) r;
			}
		}  
		AlertLog.getInstance().logDebug(AlertTag.WORLD, "WORLD: " + getName(), 
				"getListToBuy has a size of " + house.getListToBuy().size());										

		return house.getListToBuy();
	}

	private int chooseTransportation() {
		int randchoice = (int)((Math.random()*100)%2);
		return randchoice;
	}

	public boolean scheduleEvent(EventType t) {
		Event e;
		if (t == EventType.GoToMarket) {
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

			e = new Event(buildingName, eventR, 2, -1, true, steps, t);
			AlertLog.getInstance().logDebug(AlertTag.WORLD, "WORLD: " + getName(), 
					"SCHEDULED GOTOMARKET" + e.startTime + ", " + eventList.size());										

			//Do("GoToMarket is scheduled, which has "+steps.size()+" steps");
			insertEvent(e);
			stateChanged();
		}
		else if (t == EventType.BusToMarket) {
			List<String> markets = Directory.getMarkets();
			int index = rand.nextInt(markets.size());
			String buildingName = markets.get(index);
			List<Step> steps = new ArrayList<Step>();
			steps.add(new Step("exitBuilding", this));

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

			Role eventR = null;
			for(Role r : myRoles) {
				if(r instanceof MarketCustomer) {
					eventR = r;
				}
			} 

			e = new Event(buildingName, eventR, TWO_HOURS, -1, true, steps, t);
			//Do("GoToMarket is scheduled, which has "+steps.size()+" steps");
			insertEvent(e);
			stateChanged();

		}
		
		else if (t == EventType.CarToMarket) {
			System.out.println("In car to market first line personagent");
			List<String> markets = Directory.getMarkets();
			int index = rand.nextInt(markets.size());
			String buildingName = markets.get(index);
			List<Step> steps = new ArrayList<Step>();
			steps.add(new Step("exitBuilding", this));
			steps.add(new Step("goToParkingGarage", this));
			steps.add(new Step("driveTo", this));
			steps.add(new Step("goTo", this));
			steps.add(new Step("enterBuilding", this));
			//steps.add(new Step("goTo", this));
			//steps.add(new Step("enterBuilding", this));
			
			Role eventR = null;
			for(Role r : myRoles) {
				if (r instanceof MarketCustomer) {
					eventR = r;
					
				}
			}
			e = new Event(buildingName, eventR, TWO_HOURS, -1, true, steps, t);
			insertEvent(e);
			stateChanged();

		}

		else if (t == EventType.DepositMoney) {

			List<String> banks = Directory.getBanks();

			//Do("We're Depositing, and banks size is "+banks.size());
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
			((BankCustomer)eventR).hackDepositMoney((BankSystem)(Directory.getSystem(buildingName)));
			e = new Event(buildingName, eventR, TWO_HOURS, -1, true, steps, t);

			insertEvent(e);
			stateChanged();


		}
		else if (t == EventType.WithdrawMoney) {
			List<String> banks = Directory.getBanks();
			if(banks.size() > 0) {
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
				((BankCustomer)eventR).hackWithdrawMoney((BankSystem)(Directory.getSystem(buildingName)));
				e = new Event(buildingName, eventR, TWO_HOURS, -1, true, steps, t);

				insertEvent(e);
			}
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
			((BankCustomer)eventR).hackGetLoan((BankSystem)(Directory.getSystem(buildingName)));
			e = new Event(buildingName, eventR, TWO_HOURS, -1, true, steps, t);

			insertEvent(e);
			stateChanged();
		}
		else if (t == EventType.RobBank) {

			List<String> banks = Directory.getBanks();

			//Do("We're Depositing, and banks size is "+banks.size());
			int index = rand.nextInt(banks.size());
			String buildingName = banks.get(index);
			List<Step> steps = new ArrayList<Step>();
			steps.add(new Step("exitBuilding", this));
			steps.add(new Step("goTo", this));
			steps.add(new Step("enterBuilding", this));
			Role eventR = null;
			for(Role r : myRoles) {
				if(r instanceof BankRobber) {
					eventR = r;
				}
			}

			//hack
			((BankRobber)eventR).hackRobBank((BankSystem)(Directory.getSystem(buildingName)));
			e = new Event(buildingName, eventR, TWO_HOURS, -1, true, steps, t);

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
			((BankCustomer)eventR).hackPayRent((BankSystem)(Directory.getSystem(buildingName)));
			e = new Event(buildingName, eventR, TWO_HOURS, -1, true, steps, t);

			insertEvent(e);
			stateChanged();
		}
		else if (t == EventType.Work) {
			List<Step> steps = new ArrayList<Step>();
			steps.add(new Step("exitBuilding", this));
			steps.add(new Step("goTo", this));
			steps.add(new Step("enterBuilding", this));
			int workTime;
			if(workClosed) {
				workTime = Clock.getTime() + 3;
				workClosed = false;
			}
			else {
				int offSet = 0;
				if(currentEvent != null && currentEvent.type == EventType.Work) {
					offSet = 6 * 24; //24 hours
				}
				if(type == TimingType.Early) {
					workTime = Clock.getScheduleTime(8, 0) + offSet;
					//workTime = 8am
				}
				else {
					workTime = Clock.getScheduleTime(13, 0) + offSet;
					//workTime = 1pm
				}
				if (home == null || home.equals("")) {
					workTime = Clock.getTime();
				}
			}

			e = new Event(workBuilding, workRole, EIGHT_HOURS, workTime, false, steps, t);
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
			int sleepTime;
			int sleepDuration;
			if (Clock.getTime() < 32) {
				sleepTime = Clock.getTime();
				//sleepDuration = FIRSTSLEEPDURTION;//6;
				if(type == TimingType.Early) {
					sleepDuration = 3;
					//sleepDuration = 30 minutes
				}
				else {
					sleepDuration = 3*6;
					//sleepDuration = 3 hours
				}
			}
			else {
				//sleepTime = Clock.getTime() + AWAKEDURATION;
				if(type == TimingType.Early) {
					sleepTime = Clock.getScheduleTime(21, 30);
				}
				else {
					sleepTime = Clock.getScheduleTime(23, 50);
					//sleepTime = midnight
				}
				sleepDuration = SLEEPDURATION;//48; // 8 * 6 = 8 * (6 * 10 min) = 8 hours
			}
			e = new Event(home, house, sleepDuration, sleepTime, false, steps, t);
			AlertLog.getInstance().logDebug(AlertTag.WORLD, "Person: "+getName(), 
					"I'm going to sleep at "+Clock.getDebugTime(sleepTime)+" and it's currently "+Clock.getTime());						

			insertEvent(e);
			stateChanged();
		} 
		
		else if (t == EventType.Eat) {
			t = EventType.EatAtHome;
			if (rand.nextBoolean() && Directory.getRestaurants().size() > 0)
				t = EventType.EatAtRestaurant;				
		}
		if (t == EventType.EatAtHome) {
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
			house.msgNeedToEat();
			e = new Event(home, house, TWO_HOURS, -1, true, steps, t);
			insertEvent(e);
			stateChanged();
		}
		else if (t == EventType.EatAtRestaurant) {
			List<String> restaurants = Directory.getRestaurants();
			List<Step> steps = new ArrayList<Step>();
			steps.add(new Step("exitBuilding", this));
			steps.add(new Step("goTo", this));
			// instead of this step ^ call transportationSteps(steps);
			steps.add(new Step("enterBuilding", this));
			int index = rand.nextInt(restaurants.size());
			String buildingName = restaurants.get(index);
			Role eventR = null;
			if(Directory.getSystem(buildingName) instanceof RestaurantFiveSystem) {
				for(Role r : myRoles) {
					if(r instanceof RestaurantFiveCustomer) {
						eventR = r;
					}
				}
			}
			else if(Directory.getSystem(buildingName) instanceof RestaurantTwoSystem) {
				for(Role r : myRoles) {
					if(r instanceof RestaurantTwoCustomer) {
						eventR = r;
					}
				}
				((RestaurantTwoCustomer)eventR).msgArrivedAtRestaurant(money);
			}
			else if(Directory.getSystem(buildingName) instanceof RestaurantOneSystem) {
				for(Role r : myRoles) {
					if (r instanceof RestaurantOneCustomer) {
						eventR = r;
					}
				}
				((RestaurantOneCustomer)eventR).msgArrivedAtRestaurant(money);	 
			}
			else if(Directory.getSystem(buildingName) instanceof RestaurantThreeSystem) {
				for(Role r : myRoles) {
					if(r instanceof RestaurantThreeCustomer) {
						eventR = r;
					}
				}
			}
			else if(Directory.getSystem(buildingName) instanceof RestaurantFourSystem) {
				for(Role r : myRoles) {
					if(r instanceof RestaurantFourCustomer) {
						eventR = r;
					}
				}
			}
			

			
			e = new Event(buildingName, eventR, TWO_HOURS, -1, true, steps, t);
			insertEvent(e);
			stateChanged();
		}
		
		return true;
	}

	//this assumes after roles are done, they go stand outside the building
	//so this only needs to prep the person to walk somewhere by changing it to pedestrian
	
	private void transportationSteps(List<Step> steps) {
	//	if (hasCar) {
			// steps.add go to car
			// drive to destination
			// go to building
			//steps.add(new Step("goTo", this));
	//	} else if (busIsFaster) {
			// go to bus stop
			// bus to place
			// go to building
			
		}  
		
	
	int findGarage(String dest) {
		double minLocation = 10000;
		int minGarage = 20;

		Role eventR = null;
		for (Role r : myRoles) {
			if (r instanceof Pedestrian) {
				eventR = r;
			}
		}
		for (int i = 0; i<6; i++) {
			int tempX = Directory.getGarage(i).getX()-eventR.getGui().getX();
			double tempX2 = Math.pow(tempX, 2);
			int tempY = Directory.getGarage(i).getY()-eventR.getGui().getY();
			double tempY2 = Math.pow(tempY, 2);
			double tempXY = tempX2 + tempY2;
			double tempLocation = Math.sqrt(tempXY);
			if (minLocation > tempLocation) {
				minLocation = tempLocation;
				minGarage = i;
			}
		}
		return minGarage;
		
	}

	int chooseTransportation(String dest) {
		Role eventR = null;
		for (Role r : myRoles) {
			if (r instanceof Pedestrian) {
				eventR = r;

			}
		}

		double minLocation = 1000;
		int minStop = 10;

		for (int i=0; i<4; i++) {
			int tempX = Directory.getBusStop(i).getX()-eventR.getGui().getX();
			double tempX2 = Math.pow(tempX, 2);
			int tempY = Directory.getBusStop(i).getY()-eventR.getGui().getY();
			double tempY2 = Math.pow(tempY, 2);
			double tempXY = tempX2 + tempY2;
			double tempLocation = Math.sqrt(tempXY);
			if (minLocation > tempLocation) {
				minLocation = tempLocation;
				minStop = i;
			}
		}

		PedestrianRole tempR = (PedestrianRole)eventR;
		//int buildingdist = 4000;
		int btempX = Directory.getLocation(dest).getX()-tempR.getGui().getX();
		int btempY = Directory.getLocation(dest).getY()-tempR.getGui().getY();
		double tempX2 = Math.pow(btempX, 2);
		double tempY2 = Math.pow(btempY, 2);
		double buildingdist = Math.sqrt(tempX2 + tempY2);
		//int buildingdist = (int)Math.sqrt(((Directory.getLocation(dest).getX()-tempR.getGui().getX())^2 + (Directory.getLocation(dest).getX()-tempR.getGui().getX())^2) );
		if (minLocation < buildingdist) {
			return minStop;
		}
		else {
			return 10;
		}
	}

	int getClosestStop(String dest) {
		double minLocation = 10000;
		int minStop = 10;

		for (int i=0; i<4; i++) {
			int tempX = Directory.getBusStop(i).getX()-Directory.getLocation(dest).getX();
			double tempX2 = Math.pow(tempX, 2);
			int tempY = Directory.getBusStop(i).getY()-Directory.getLocation(dest).getY();
			double tempY2 = Math.pow(tempY, 2);
			double tempXY = tempX2 + tempY2;
			double tempLocation = Math.sqrt(tempXY);
			if (minLocation > tempLocation) {
				minLocation = tempLocation;
				minStop = i;
			}
		}
		return minStop;

	}
	
	int getClosestGarage(String dest) {
		double minLocation = 1000;
		int minGarage = 10;
		
		for (int i = 0; i<6; i++) {
			int tempX = Directory.getGarage(i).getX()-Directory.getLocation(dest).getX();
			double tempX2 = Math.pow(tempX, 2);
			int tempY = Directory.getGarage(i).getY()-Directory.getLocation(dest).getY();
			double tempY2 = Math.pow(tempY,  2);
			double tempXY = tempX2 + tempY2;
			double tempLocation = Math.sqrt(tempXY);
			if(minLocation > tempLocation) {
				minLocation = tempLocation;
				minGarage = i;
				
			}
		}
		return minGarage;
	}









	public void exitBuilding() {
		//Do("exitBuilding step is called");
		AlertLog.getInstance().logDebug(AlertTag.WORLD, "Person: "+name, "EXITING building from person agent.");						
		if (currentRole != null)
			currentRole.exitBuilding();
		stateChanged();
	}

	public void goToBusStop() {
		for(Role r : myRoles) {
			if(r instanceof Pedestrian) {
				currentRole = r;
				Directory.getWorld().getAnimationPanel().addGui(currentRole.getGui());
			}
		}

		Location loc = Directory.getBusStop(chooseTransportation(currentEvent.buildingName));
		((PedestrianRole)currentRole).addDestination(loc);
		//waitForTransport();
		stateChanged();
	}
	
	public void goToParkingGarage() {
		for(Role r : myRoles) {
			if(r instanceof Pedestrian) {
				currentRole = r;
				Directory.getWorld().getAnimationPanel().addGui(currentRole.getGui());
			}
		}
		Location loc = Directory.getGarage(findGarage(currentEvent.buildingName));
		//Location loc = Directory.getGarage(2);
		((PedestrianRole)currentRole).addDestination(loc);
		System.out.println("Destination has been added, going to garage");
		stateChanged();

	}

	public void waitForBus() {
		for (Role r : myRoles) {
			if(r instanceof BusPassengerRole) {
				currentRole = r;
				//Location l = new Location(40, 67);
				((BusPassengerRole) r).setBus(bus);
				((BusPassengerRole) r).msgBusTo(chooseTransportation(currentEvent.buildingName), getClosestStop(currentEvent.buildingName));
				//idleGui.setLocation(r.getGui().getLocation());	
			}
		}
	}
	
	public void driveTo() {
		for (Role r : myRoles) {
			if (r instanceof CarPassengerRole ) {
				currentRole = r;
				int startingGarage = findGarage(currentEvent.buildingName);
				int endingGarage = getClosestGarage(currentEvent.buildingName);
				int xCar = Directory.getGarage(startingGarage).getX();
				int yCar = Directory.getGarage(startingGarage).getY();
				((CarGui) ((CarPassengerRole) r).getGui()).setLocation(xCar, yCar);
				Directory.getWorld().getAnimationPanel().addGui(currentRole.getGui());
				System.out.println("Driving to now...");
				((CarPassengerRole)r).msgDriveTo(findGarage(currentEvent.buildingName), getClosestGarage(currentEvent.buildingName) );
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
		System.out.println(currentEvent.buildingName + ", " + getName() + ", " + currentEvent.role);
		Location loc = Directory.getLocation(currentEvent.buildingName);
		Do(currentEvent.buildingName + ", " + loc.getX() + ", " + loc.getY());
		//Do("Location is: "+loc.getX()+", "+loc.getY());
		//Do("PedRole is being given a destination!");
		((PedestrianRole)currentRole).addDestination(loc);
		stateChanged();

	}

	public void enterBuilding() {
		AlertLog.getInstance().logDebug(AlertTag.WORLD, "WORLD: " + getName(), ""+currentEvent.buildingName +" is about to be entered as role: "+currentEvent.role.toString());						

		if(Directory.getSystem(currentEvent.buildingName).msgEnterBuilding(currentEvent.role)) {
			currentRole = currentEvent.role;
			currentRole.enterBuilding(Directory.getSystem(currentEvent.buildingName));	
			
			AlertLog.getInstance().logMessage(AlertTag.WORLD, "Pedestrian: "+name, "Entering building" + currentEvent.buildingName );						

		}
		else {
			AlertLog.getInstance().logMessage(AlertTag.WORLD, "Pedestrian: "+name, 
					currentEvent.buildingName +" is closed.  I can't enter");						
			currentRole = currentEvent.role;
			roleFinished();
			if(currentEvent.type == EventType.Work) {
				workClosed = true;
				eventList.remove(0);
				scheduleEvent(EventType.Work);
				currentRole = null;
				currentEvent = null;
			}
		}
		if (currentEvent == null) {
			AlertLog.getInstance().logDebug(AlertTag.WORLD, "Pedestrian: "+name, 
					"My currentEvent is null, so I cannot call marketDone for HouseInhab");						
		}
		else if(currentEvent.type == EventType.GoToMarket) {
			for (Role r : myRoles) {
				if (r instanceof HouseInhabitant) {
					((HouseInhabitant)r).marketDone();
				}
			}
		}
		stateChanged();
	}


	public void roleFinished() {
		if(currentRole instanceof Pedestrian) {
			Directory.getWorld().getAnimationPanel().removeGui(currentRole.getGui());
		}
		else {
			//Directory.getSystem(currentEvent.buildingName).animationPanel.removeGui(currentRole.getGui());
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

		synchronized (eventList) {
			if(!e.flexible) {
				int index = -1;
				List<Event> tempList = new ArrayList<Event>();


				for(Event e2 : eventList) {
					if(e.overlaps(e2)) {
						if(!e2.flexible) {
							AlertLog.getInstance().logError(AlertTag.WORLD, "WORLD: " + getName(), "Trying to schedule an inflexible event that overlaps an inflexible event!");										
						}
						//copies all events that overlap e to a separate list so we can remove later
						tempList.add(e2); 
						if(index == -1) {
							index = eventList.indexOf(e2);
						}
					}
				}

				//if there was a conflicting event already in the list, move conflicts
				if(index != -1) {
					for(Event eTemp : tempList) {
						eventList.remove(eTemp); //remove the conflicting event
					}
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
						eventList.add(e);
					}

				}
			}

			//the new event is flexible
			else {
				//if there is time at the beginning, insert new event first
				if(eventList.isEmpty()) {
					e.startTime = Clock.getTime();
					eventList.add(0, e);
				}
				else if(eventList.get(0).startTime - Clock.getTime() > e.duration) {
					e.startTime = Clock.getTime();
					eventList.add(0, e);
				}
				else if(e.type == EventType.Eat) {
					for(int i = 0; i < eventList.size() - 1; i++) {
						//if there is a flexible event, eating takes it's place
						if(eventList.get(i).flexible) {
							Event temp = eventList.get(i);
							e.startTime = eventList.get(i).startTime;
							eventList.add(i, e); //adding new event into flexible space
							eventList.remove(temp);
							insertEvent(temp);
						}
						//if there is space after that event, put eating event there
						if(eventList.get(i + 1).startTime - 
								eventList.get(i).startTime + eventList.get(i).duration > e.duration) {
							//adding new event into middle of list if there is space
							e.startTime = eventList.get(i).startTime + eventList.get(i).duration;
							eventList.add(i + 1, e);
							return;
						}
					}
					int index = eventList.size() - 1; //index of last element in eventList
					e.startTime = eventList.get(index).startTime + eventList.get(index).duration;
					eventList.add(e); //adding new event at end of list
				}
				else {
					for(int i = 0; i < eventList.size() - 1; i++) {
						if(eventList.get(i + 1).startTime - 
								eventList.get(i).startTime + eventList.get(i).duration > e.duration) {
							//adding new event into middle of list if there is space
							e.startTime = eventList.get(i).startTime + eventList.get(i).duration;
							eventList.add(i + 1, e);
							return;
						}
					}
					int index = eventList.size() - 1; //index of last element in eventList
					e.startTime = eventList.get(index).startTime + eventList.get(index).duration;
					eventList.add(e); //adding new event at end of list
				}
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
	
	
	public void carToMarketNow() {
		this.scheduleEvent(EventType.CarToMarket);
		System.out.println("Scheduling car to market now");
	}

	public void goToBankNow() {
		this.scheduleEvent(EventType.WithdrawMoney);
	}
	public void goRobBankNow() {
		this.scheduleEvent(EventType.RobBank);
	}
	public void goToRestaurantThreeNow() {
		this.scheduleEvent(EventType.EatAtRestaurant);
	}
	public void goToRestaurantTwoNow() {
		this.scheduleEvent(EventType.EatAtRestaurant);
	}
	
	public void goToRestaurantOneNow() {
		this.scheduleEvent(EventType.EatAtRestaurant);
	}

	public void goToRestaurantFourNow() {
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
		if(Directory.hasEarlySchedule(building)) {
			type = TimingType.Early;
		}
		else {
			type = TimingType.Late;
		}
		scheduleEvent(EventType.Work);
	}
	
	public void scheduleFirstSleep() {
		if(home != null) {
			scheduleEvent(EventType.Sleep);
		}
	}
	

	public void addHome(String building) {
		//myRoles.add(r);
		home = building;
		//scheduleEvent(EventType.Sleep);
		idleGui.setLocation(Directory.getLocation(home));
		PedestrianRole ped = null;
		for(Role r : myRoles) {
			if(r instanceof PedestrianRole) {
				ped = (PedestrianRole) r;
				ped.setLocation(Directory.getLocation(home).getX(), Directory.getLocation(home).getY());
			}
		}  
		
	}
	
	public void setLowFood() {
		HouseInhabitantRole h = null;
		for(Role r : myRoles) {
			if(r instanceof HouseInhabitantRole) {
				h = (HouseInhabitantRole) r;
			}
		}  
		h.setLowFood();
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
		
		public String toString() {
			return "[" + person.getName() + ", " + methodName + "]";
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
		
		public List<Step> steps() {
			return steps;
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
		System.out.println("A person is being cleared");
		for (Role r : myRoles) {
			r.clear();
		}
		timer.cancel();
		timer.purge();
		super.stopThread();
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

	public Role getCurrentRole() {
		return currentRole;
	}
	
	public String getCurrentEvent() {
		return currentEvent.type.toString();
	}
	
	public int getCurrentEventDuration() {
		return currentEvent.duration;
	}

	public void setCurrentRole(Role r) {
		this.currentRole = r;
	}
	
	public String getBankPassword() {
		return bankPassword;
	}
	
	public void setBankPassword(String bankPassword) {
		this.bankPassword = bankPassword;
	}
	
	public int getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public List<Role> getRolesList() {
		return myRoles;
	}
}
