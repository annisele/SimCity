
package simcity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import simcity.Directory.EntryType;
import simcity.PersonAgent.EventType;
import simcity.buildings.bank.BankHostRole;
import simcity.buildings.bank.BankSystem;
import simcity.buildings.bank.BankTellerRole;
import simcity.buildings.house.HouseSystem;
import simcity.buildings.market.MarketCashierRole;
import simcity.buildings.market.MarketSystem;
import simcity.buildings.market.MarketTruckAgent;
import simcity.buildings.market.MarketWorkerRole;
import simcity.buildings.restaurant.one.*;
import simcity.buildings.restaurant.six.*;
import simcity.buildings.restaurant.two.*;
import simcity.buildings.restaurant.three.*;
import simcity.buildings.restaurant.four.*;
import simcity.buildings.restaurant.five.*;
import simcity.buildings.transportation.BusAgent;
import simcity.buildings.transportation.Intersection;
import simcity.buildings.transportation.TransportationSystem;
import simcity.gui.BuildingGui;
import simcity.gui.SimCityGui;
import simcity.gui.WorldAnimationPanel;
import simcity.gui.transportation.BusGui;

public class SystemManager {

	SimCityGui simcity;
	WorldSystem world;
	Directory dir = new Directory();
	List<Intersection> intersections = new ArrayList<Intersection>();
	List<MarketSystem> markets = new ArrayList<MarketSystem>();
	List<BankSystem> banks = new ArrayList<BankSystem>();
	List<HouseSystem> houses = new ArrayList<HouseSystem>();
	List<RestaurantOneSystem> restaurantOnes = new ArrayList<RestaurantOneSystem>();
	List<RestaurantTwoSystem> restaurantTwos = new ArrayList<RestaurantTwoSystem>();
	List<RestaurantThreeSystem> restaurantThrees = new ArrayList<RestaurantThreeSystem>();
	List<RestaurantFourSystem> restaurantFours = new ArrayList<RestaurantFourSystem>();
	List<RestaurantFiveSystem> restaurantFives = new ArrayList<RestaurantFiveSystem>();
	List<RestaurantSixSystem> restaurantSixes = new ArrayList<RestaurantSixSystem>();
	List<TransportationSystem> transportations = new ArrayList<TransportationSystem>();
	//RestaurantTwoComputer R2comp =new RestaurantTwoComputer(15,15,15,15);
	RestaurantTwoComputer R2comp =new RestaurantTwoComputer(15,15,15,15);
	BusAgent bus;
	List<BuildingGui> buildings = new ArrayList<BuildingGui>();
	List<BusGui> busGuis = Collections.synchronizedList(new ArrayList<BusGui>());
	List<PersonAgent> people = new ArrayList<PersonAgent>();

	public SystemManager(SimCityGui g) {
		simcity = g;
		world = new WorldSystem(simcity);//simcity.getWorld();
		dir.setWorld(world);
	}

	public void clear() {

		for (BusGui b : busGuis) {
			b.clear();
		}
		for (PersonAgent p : people) {
			p.clear();
		}
		world.clear();
		world.getAnimationPanel().clear();
		world.simCityGui.clearTracePanels();
		intersections.clear();
		dir.clear();
		markets.clear();
		banks.clear();
		houses.clear();
		restaurantOnes.clear();
		restaurantTwos.clear();
		restaurantThrees.clear();
		restaurantFours.clear();
		restaurantFives.clear();
		restaurantSixes.clear();
		transportations.clear();
		busGuis.clear();
		buildings.clear();
		people.clear();

		Clock.reset();

	}

	public void getContact(SimSystem s) {
		if(s instanceof MarketSystem) {
			for(MarketSystem m : markets) {
				if(m == s) {

				}
			}
		}
	}

	public void setBackgroundOne() {
		WorldAnimationPanel w = (WorldAnimationPanel)world.getAnimationPanel();
		w.setBackgroundOne();
		dir.makeBusStops1();
	} 

	public void setBackgroundTwo() {
		WorldAnimationPanel w = (WorldAnimationPanel)world.getAnimationPanel();
		w.setBackgroundTwo();
		dir.makeBusStops2();
	}

	public void setBackgroundThree() {
		WorldAnimationPanel w = (WorldAnimationPanel)world.getAnimationPanel();
		w.setBackgroundThree();
		dir.makeBusStops2();
	}

	public void addPerson(String name) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());

		person.setBus(bus);

		//hacks
		if (name.equalsIgnoreCase("Rebecca")) {
			person.goToMarketNow();
		}
		if(name.equalsIgnoreCase("Hungry Jenny") || name.equalsIgnoreCase("Hungry Clayton")) {
			person.scheduleEvent(EventType.EatAtRestaurant);
		}
		if (name.equalsIgnoreCase("Hungry Harry")||name.equalsIgnoreCase("Hungry Aaron")) {

			person.goToRestaurantThreeNow();
		}

		if(name.equalsIgnoreCase("Josh")) {
			person.busToMarketNow();
		}
		if (name == "Levonne") {
			person.goToBankNow();
		}
		if (name == "Levanne") {
			person.goToBankNow();
		}
		if (name == "jenny"||name == "jenny1"||name == "jenny2"||name == "jenny3") {

			person.goToRestaurantTwoNow();
		}


		if (name.equalsIgnoreCase("Gus Fring") || name.equalsIgnoreCase("Ted Benacke") || name .equalsIgnoreCase("jack")) {
			person.goToRestaurantOneNow();
		}
		
		if (name == "Hungry R4Customer1" || name == "Hungry R4Customer2" || name == "Hungry R4Customer3") {
			person.goToRestaurantFourNow();
		}
		
		people.add(person);
		person.startThread();
	}

	public void addMarket(String name, int xLoc, int yLoc) {
		MarketSystem temp = new MarketSystem(simcity);
		temp.setName(name);
		markets.add(temp);
		BuildingGui building = new BuildingGui(temp, "Market", xLoc, yLoc);
		world.getAnimationPanel().addBuilding(building);
		Location loc = new Location(xLoc, yLoc);

		dir.add(name, EntryType.Market, loc, temp);
	}
	public void addIntersection(String name, int x, int y) {
		//Intersection temp = new Intersection(simcity);
		//temp.setName(name);
		
	}
	public void addBank(String name, int xLoc, int yLoc) {
		BankSystem temp = new BankSystem(simcity);
		temp.setName(name);
		banks.add(temp);
		BuildingGui building = new BuildingGui(temp, "Bank", xLoc, yLoc);
		world.getAnimationPanel().addBuilding(building);
		Location loc = new Location(xLoc, yLoc);

		dir.add(name, EntryType.Bank, loc, temp);
	}

	public void addHouse(String name, int xLoc, int yLoc) {
		HouseSystem temp = new HouseSystem(simcity);
		temp.setName(name);
		houses.add(new HouseSystem(simcity));
		BuildingGui building = new BuildingGui(temp, "House", xLoc, yLoc);
		world.getAnimationPanel().addBuilding(building);
		Location loc = new Location(xLoc, yLoc);

		dir.add(name, EntryType.House, loc, temp);
	}

	public void addBus(String name) {
		// TODO Auto-generated method stub
		TransportationSystem temp = new TransportationSystem(simcity);
		temp.setName(name);
		transportations.add(temp);
		Location loc = new Location(100, 400);
		dir.add(name, EntryType.Bus, loc, temp);
		bus = new BusAgent(name);
		bus.setDirectory(dir);
		BusGui tbg = new BusGui(bus);
		bus.setGui(tbg);
		world.getAnimationPanel().addBus(tbg);
		bus.startThread();
		bus.makeBusMove();
	}

	public void addRestaurantOne(String name, int xLoc, int yLoc) {
		RestaurantOneSystem temp = new RestaurantOneSystem(simcity);
		temp.setName(name);
		restaurantOnes.add(temp);
		BuildingGui building = new BuildingGui(temp, "RestaurantOne", xLoc, yLoc);
		world.getAnimationPanel().addBuilding(building);
		Location loc = new Location(xLoc, yLoc);

		dir.add(name, EntryType.Restaurant, loc, temp);

	}

	public void addRestaurantTwo(String name, int xLoc, int yLoc) {
		//restaurantTwos.add(new RestaurantTwoSystem(simcity));
		List<String> markets = Directory.getMarkets();
		//System.out.println("UU "+markets);
		R2comp.addMarket(markets);
		RestaurantTwoSystem temp = new RestaurantTwoSystem(simcity,R2comp);
		temp.setName(name);
		restaurantTwos.add(temp);
		BuildingGui building = new BuildingGui(temp, "RestaurantTwo", xLoc, yLoc);
		world.getAnimationPanel().addBuilding(building);
		Location loc = new Location(xLoc, yLoc);
		//System.out.println("TTTTT: "+ temp);
		dir.add(name, EntryType.Restaurant, loc, temp);
	}

	public void addRestaurantThree(String name, int xLoc, int yLoc) {
		RestaurantThreeSystem temp = new RestaurantThreeSystem(simcity);
		temp.setName(name);
		restaurantThrees.add(temp);
		BuildingGui building = new BuildingGui(temp, "RestaurantThree", xLoc, yLoc);
		world.getAnimationPanel().addBuilding(building);
		Location loc = new Location(xLoc, yLoc);

		dir.add(name, EntryType.Restaurant, loc, temp);


	}
	
	public void addRestaurantFour(String name, int x, int y) {
		RestaurantFourSystem temp = new RestaurantFourSystem(simcity);
		temp.setName(name);
		restaurantFours.add(temp);
		BuildingGui b = new BuildingGui(temp, "RestaurantFour", x, y);
		world.getAnimationPanel().addBuilding(b);
		Location location = new Location(x, y);
		dir.add(name, EntryType.Restaurant, location, temp);
	}

	public void addRestaurantFive(String name, int x, int y) {
		RestaurantFiveSystem temp = new RestaurantFiveSystem(simcity);
		temp.setName(name);
		restaurantFives.add(temp);
		BuildingGui b = new BuildingGui(temp, "RestaurantFive", x, y);
		world.getAnimationPanel().addBuilding(b);
		Location location = new Location(x, y);
		dir.add(name, EntryType.Restaurant, location, temp);
	}

	public void addRestaurantSix(String name, int xLoc, int yLoc) {
		RestaurantSixSystem temp = new RestaurantSixSystem(simcity);
		temp.setName(name);
		restaurantSixes.add(temp);

		BuildingGui building = new BuildingGui(temp, "RestaurantSix", xLoc, yLoc);
		world.getAnimationPanel().addBuilding(building);
		Location loc = new Location(xLoc, yLoc);
		dir.add(name, EntryType.Restaurant, loc, temp);	
	}

	public void setHome(String person, String home) {
		//ApartmentSystem apt;
		//HouseSystem house;
		PersonAgent resident = null;

		resident = getPersonFromName(person);
		
		//System.out.println("setHome is being called, home is "+home+" and resident is: "+resident.getName());
		resident.addHome(home);		

		if (person == "Hungry Antoine") {
			resident.setLowFood();
		}
	}


	public MarketSystem getMarket(int i) {
		return markets.get(i);
	}

	public BankSystem getBank(int i) {
		return banks.get(i);
	}

	public HouseSystem getHouse(int i) {
		return houses.get(i);
	}

	public RestaurantOneSystem getRestaurantOne(int i) {
		return restaurantOnes.get(i);
	}

	public RestaurantTwoSystem getRestaurantTwo(int i) {
		return restaurantTwos.get(i);
	}

	public RestaurantThreeSystem getRestaurantThree(int i) {
		return restaurantThrees.get(i);
	}

	public RestaurantFourSystem getRestaurantFour(int i) {
		return restaurantFours.get(i);
	}

	public RestaurantFiveSystem getRestaurantFive(int i) {
		return restaurantFives.get(i);
	}

	public RestaurantSixSystem getRestaurantSix(int i) {
		return restaurantSixes.get(i);
	}

	public WorldSystem getWorld() {
		return world;
	}

	public void clearDetailPane() {
		simcity.clearDetailPane();
	}




	/************************ MARKET FUNCTIONS ***********************/

	/**
	 * addMarketTruck - Helper function to create a market truck agent
	 * @param market - Name of market the truck works for
	 */
	public void addMarketTruck(String market) {
		MarketTruckAgent t = new MarketTruckAgent(Directory.getSystem(market));
		((MarketSystem) Directory.getSystem(market)).addTruck(t);
		t.startThread();
		world.getAnimationPanel().addGui(t.getGui());
	}

	/**
	 * addMarketCashier - Helper function to create person who works as a market cashier
	 * @param name - Name of person to be created
	 * @param market - Name of market they will work at
	 
	public void addMarketCashier(String name, String market) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role cashier = new MarketCashierRole(person);
		person.addWork(cashier, market);
		people.add(person);
		person.startThread();
	}
	*/

	public void setWorkMarketCashier(String name, String market) {
		PersonAgent person = getPersonFromName(name);
		Role cashier = new MarketCashierRole(person);
		person.addWork(cashier, market);
	}
	
	public void setWorkMarketWorker(String name, String market) {
		PersonAgent person = getPersonFromName(name);
		Role worker = new MarketWorkerRole(person);
		person.addWork(worker, market);
	}


	/**
	 * addMarketWorker - Helper function to create a person who works as a market worker
	 * @param name - Name of the person created
	 * @param market - Name of the market they work at
	 
	public void addMarketWorker(String name, String market) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role worker = new MarketWorkerRole(person);
		person.addWork(worker, market);
		people.add(person);
		person.startThread();
	}
	*/
	
	public PersonAgent getPersonFromName(String name) {
		PersonAgent person = null;

		for (PersonAgent p : people) {
			if (p.getName().equals(name)) {
				person = p;
			}
		}
		return person;
	}

	/****************** End of Market functions **********************/




	public void addBankHostHack(String name, String bank) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role bankHost = new BankHostRole(person);;
		person.addWork(bankHost, bank);
		people.add(person);
		person.startThread();
	}
	public void addBankTellerHack(String name, String bank) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role bankTeller = new BankTellerRole(person, banks.get(0));
		person.addWork(bankTeller, bank);
		people.add(person);
		person.startThread();
	}

	public void addHackedBankAccount(int accountNumber, double accountBalance, double amountOwed, String password) {
		banks.get(0).getBankComputer().addHackedBankAccount(accountNumber, accountBalance, amountOwed, password);
	}

	/*************** RESTAURANT ONE FUNTIONS DOE ****************/
	public void addRestaurantOneHost(String name, String rest) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role r1Host = new RestaurantOneHostRole(person);
		person.addWork(r1Host, rest);
		people.add(person);
		person.startThread();

	}	

	public void addRestaurantOneCashier(String name, String rest) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role r1Cashier = new RestaurantOneCashierRole(person);
		person.addWork(r1Cashier, rest);
		people.add(person);
		person.startThread();

	}

	public void addRestaurantOneWaiter(String name, String rest) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role r1waiter = new RestaurantOneWaiterRole(person);
		person.addWork(r1waiter, rest);
		people.add(person);
		person.startThread();
	}	

	public void addRestaurantOneCustomer(String name, String rest) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role r1customer = new RestaurantOneCustomerRole(person);
		person.addWork(r1customer, rest);
		people.add(person);
		person.startThread();
	}

	/*************** END OF RESTAURANT ONE FUNCTIONS ***********/

	/*************** RESTAURANT TWO FUNCTIONS *******************/

	public void addRestaurantTwoHostHack(String name, String rest) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role r2Host = new RestaurantTwoHostRole(person,restaurantTwos.get(0));
		person.addWork(r2Host, rest);
		people.add(person);
		person.startThread();

	}
	public void addRestaurantTwoCashierHack(String name, String rest) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role r2Cashier = new RestaurantTwoCashierRole(person,restaurantTwos.get(0),R2comp);
		person.addWork(r2Cashier, rest);
		people.add(person);
		person.startThread();

	}
	public void addRestaurantTwoCookHack(String name, String rest) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role r2Cook = new RestaurantTwoCookRole(person,restaurantTwos.get(0),R2comp);
		person.addWork(r2Cook, rest);
		people.add(person);
		person.startThread();

	}
	public void addRestaurantTwoWaiterHack(String name, String rest) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role r2Waiter = new RestaurantTwoWaiterRole(person,restaurantTwos.get(0),R2comp);
		person.addWork(r2Waiter, rest);
		people.add(person);
		person.startThread();
	}
	public void addRestaurantTwoSharedDataWaiterHack(String name, String rest) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role r2Waiter = new RestaurantTwoSharedDataWaiterRole(person,restaurantTwos.get(0),R2comp);
		person.addWork(r2Waiter, rest);
		people.add(person);
		person.startThread();
	}
	public void hacker(){
		restaurantTwos.get(0).hackr2();

	}
	
	/*************** RESTAURANT THREE FUNCTIONS *******************/

	public void addRestaurantThreeHost(String name, String rest) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role r3Host = new RestaurantThreeHostRole(person);
		person.addWork(r3Host, rest);
		people.add(person);
		person.startThread();
	}

	public void addRestaurantThreeCashier(String name, String rest) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role r3Cashier = new RestaurantThreeCashierRole(person);
		person.addWork(r3Cashier, rest);
		people.add(person);
		person.startThread();
	}
	public void addRestaurantThreeWaiter(String name, String rest) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role r3Waiter = new RestaurantThreeWaiterRole(person);
		person.addWork(r3Waiter, rest);
		people.add(person);
		person.startThread();
	}

	public void addRestaurantThreeCook(String name, String rest) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role r3Cook = new RestaurantThreeCookRole(person);
		person.addWork(r3Cook, rest);
		people.add(person);
		person.startThread();
	}

	/**************** End of Restaurant Three functions ***************/
	
	/**************** RESTAURANT FOUR FUNCTIONS ******************/
	
	public void addRestaurantFourHost(String name, String rest) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role host = new RestaurantFourHostRole(person);
		person.addWork(host, rest);
		people.add(person);
		person.startThread();
	}
	
	public void addRestaurantFourWaiter(String name, String rest) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role host = new RestaurantFourWaiterRole(person);
		person.addWork(host, rest);
		people.add(person);
		person.startThread();
	}
	
	/**************** End of Restaurant Four functions ****************/
	
	/*************** RESTAURANT FIVE FUNCTIONS *******************/

	public void addRestaurantFiveHost(String name, String rest) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role host = new RestaurantFiveHostRole(person);
		person.addWork(host, rest);
		people.add(person);
		person.startThread();
	}

	public void addRestaurantFiveWaiter(String name, String rest) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role waiter = new RestaurantFiveWaiterRole(person);
		person.addWork(waiter, rest);
		people.add(person);
		person.startThread();
	}

	/**************** End of Restaurant Five functions ***************/

	/*************** RESTAURANT SIX FUNCTIONS *******************/

	public void addRestaurantSixHost(String name, String rest) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role host = new RestaurantSixHostRole(person);
		person.addWork(host, rest);
		people.add(person);
		person.startThread();
	}

	public void addRestaurantSixWaiter(String name, String rest) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role waiter = new RestaurantSixWaiterRole(person);
		person.addWork(waiter, rest);
		people.add(person);
		person.startThread();
	}

	public void addRestaurantSixCook(String name, String rest) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role cook = new RestaurantSixCookRole(person);
		person.addWork(cook, rest);
		people.add(person);
		person.startThread();
	}

	public void addRestaurantSixCashier(String name, String rest) {
		PersonAgent person = new PersonAgent(name);
		world.getAnimationPanel().addGui(person.getIdleGui());
		Role cashier = new RestaurantSixCashierRole(person);
		person.addWork(cashier, rest);
		people.add(person);
		person.startThread();
	}

	public BusAgent getBus() {
		return bus;
	}

	public Directory getDirectory() {
		return dir;
	}



}
