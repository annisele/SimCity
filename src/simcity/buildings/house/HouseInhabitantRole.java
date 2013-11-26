package simcity.buildings.house;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.PersonAgent.EventType;
import simcity.gui.house.HouseInhabitantGui;

public class HouseInhabitantRole extends Role implements simcity.interfaces.house.HouseInhabitant {

	HouseSystem house;
	Timer timer = new Timer();
	Random rand = new Random();
	enum HouseInhabitantState { Eating, Sleeping, Bored } 
	enum HouseInhabitantEvent { Hungry, ReadyToSleep, None } 
	HouseInhabitantState state = HouseInhabitantState.Bored;
	HouseInhabitantEvent event = HouseInhabitantEvent.None;
	private Map <String, Integer > foodStock = new HashMap<String,Integer>();
	private Map <String, Integer > foodToBuy = new HashMap<String,Integer>();
	private boolean marketScheduled = false;
	
	private Semaphore atDest = new Semaphore(0, true);
	
	private final int SLEEPTIME = 4000;
	private final int COOKTIME = 6000;
	private final int EATTIME = 4000;
	private final int FOODSTOCK = 3;
	private final int FOODTHRESHOLD = 2;
	
	
	public HouseInhabitantRole(PersonAgent p){
		this.person = p;
		this.gui = new HouseInhabitantGui(this);
		foodStock.put("steak", 1);
		foodStock.put("chicken", 1);
		foodStock.put("pizza", 1);
		foodStock.put("salad", 1);
	}
	
	@Override
	public void atDestination() {
		atDest.release();
	}

	//Messages

	public void msgNeedToEat() { //From PersonAgent
		event = HouseInhabitantEvent.Hungry;
		stateChanged();
	}
	
	public void msgGoToBed() {//from universe/simGOD
		event = HouseInhabitantEvent.ReadyToSleep;
	}
	
	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		if (event == HouseInhabitantEvent.Hungry){
			Cook();
		}
		else if (event == HouseInhabitantEvent.ReadyToSleep){
			Sleep();
		}
		return false;
	}	
	
	// Actions
	private void Cook() {
		event = HouseInhabitantEvent.None;
		person.Do("I'm hungry, I should eat");
		DoGoToKitchen();
		DoGoToFridge();
		
		List<String> keys = new ArrayList<String>(foodStock.keySet());
		String choice = keys.get( rand.nextInt(keys.size()) );
		Integer quantity = foodStock.get(choice);
		boolean needToBuy = false;
		
		if (quantity > 0) {
			// Pick your random food if you have any
			foodStock.put(choice, (quantity-1));
			if (quantity < FOODTHRESHOLD && !marketScheduled) 
				needToBuy = true;
		} else {
			choice = "";
			for (String k : keys) {
				if (foodStock.get(k) > 0) {
					// Pick this food
					foodStock.put(choice, (quantity-1));
					choice = k;
				}
				if (foodStock.get(k) < FOODTHRESHOLD && !marketScheduled) {
					// Add them to a list of things to buy
					needToBuy = true;
				}
			}
		}
		if (choice == "") {
			// We're out of all food
		}
		if (needToBuy == true && marketScheduled == false) {
			person.Do("I just scheduled an event to go to the market");
			person.scheduleEvent(EventType.GoToMarket);
			marketScheduled = true;
		}
		
		person.Do("I'm going to eat "+choice);
		
		((HouseInhabitantGui)gui).DoHoldFood();
		DoGoToStove();
		((HouseInhabitantGui)gui).DoFoodOnStove();
		
		person.Do("Wow cooking is so much fun");
		
		timer.schedule(new TimerTask(){            
            public void run() {
                    Eat();
                    //stateChanged();
            }
		}, COOKTIME); //or whatever time is fine
		
	}
	
	private void Eat() {
		Do("Food looks ready!");
		((HouseInhabitantGui)gui).DoHoldFood();
		DoGoToTable();
		((HouseInhabitantGui)gui).DoFoodOnTable();
		
		timer.schedule(new TimerTask(){            
            public void run() {
                    DoneEating();
            }
		}, EATTIME); //or whatever time is fine
	}
	
	private void DoneEating() {
		((HouseInhabitantGui)gui).DoEatFood();
		Do("That was great. Wow. Such noms");
		DoGetUpFromTable();
		msgExitBuilding();
		
		//msgExitBuilding();
	}

	private void LeaveForRestaurant() {
		// TODO Auto-generated method stub
		
	}

	private void Sleep() {
		DoGoToBed();
		person.Do("I'm going to sleep...");
		//state = HouseInhabitantState.Sleeping;
		
		timer.schedule(new TimerTask(){            
            public void run() {
                    WakeUp();
                    //stateChanged();
            }
		}, SLEEPTIME); //or whatever time is fine
	}
	
	private void WakeUp(){
		//gui leaves bed
		Do("I'm up! I'm awake!");
		state = HouseInhabitantState.Bored;
		if (event == HouseInhabitantEvent.ReadyToSleep)
			event = HouseInhabitantEvent.None;
		msgNeedToEat();
	}

	public Map<String, Integer> getListToBuy() {
		Map<String, Integer> list = foodToBuy;
		foodToBuy.clear();
		return list;
	}
	
	// Animation
	public void DoGoToBed() {
		((HouseInhabitantGui)gui).DoGoToLiving();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		((HouseInhabitantGui)gui).DoGoToBedroom();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		((HouseInhabitantGui)gui).DoGoToBed();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void DoGoToKitchen() {
		((HouseInhabitantGui)gui).DoGoToLiving();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		((HouseInhabitantGui)gui).DoGoToKitchen();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void DoGoToFridge() {
		((HouseInhabitantGui)gui).DoGoToFridge();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void DoGoToStove() {
		((HouseInhabitantGui)gui).DoGoToStove();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void DoGoToTable() {
		((HouseInhabitantGui)gui).DoGoToKitchen();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		((HouseInhabitantGui)gui).DoGoToDining();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		((HouseInhabitantGui)gui).DoGoToNearTable();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		((HouseInhabitantGui)gui).DoGoToTable();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void DoGetUpFromTable() {
		((HouseInhabitantGui)gui).DoGoToNearTable();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		((HouseInhabitantGui)gui).DoGoToLiving();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void msgExitBuilding() {
		person.Do("Leaving the house.");
		gui.DoExitBuilding();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		house.exitBuilding(this);
		person.roleFinished();
		//stateChanged();
	}

	@Override
	public void msgEnterBuilding(SimSystem s) {
		house = (HouseSystem)s;
		((HouseInhabitantGui)gui).DoGoToLiving();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		state = HouseInhabitantState.Bored;
		
	}
	
	@Override
	public void clear() {
		
		timer.cancel();
		timer.purge();
	}

}
