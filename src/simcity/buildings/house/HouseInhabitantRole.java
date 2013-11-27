package simcity.buildings.house;
import java.util.ArrayList;
import java.util.Collections;
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
import simcity.gui.trace.*;

public class HouseInhabitantRole extends Role implements simcity.interfaces.house.HouseInhabitant {

	HouseSystem house;
	Timer timer = new Timer();
	Random rand = new Random();
	enum HouseInhabitantState { Eating, Sleeping, Bored } 
	enum HouseInhabitantEvent { Hungry, ReadyToSleep, None } 
	HouseInhabitantState state = HouseInhabitantState.Bored;
	HouseInhabitantEvent event = HouseInhabitantEvent.None;
	private Map <String, Integer > foodStock = Collections.synchronizedMap(new HashMap<String,Integer>());
	private Map <String, Integer > foodToBuy = Collections.synchronizedMap(new HashMap<String,Integer>());
	private boolean marketScheduled = false;
	
	private Semaphore atDest = new Semaphore(0, true);
	
	private final int SLEEPTIME = 4000;
	private final int COOKTIME = 6000;
	private final int EATTIME = 4000;
	private final int TIMEBWMEALS = 12*8*1000;
	private final int FOODSTOCK = 3;
	private final int FOODTHRESHOLD = 2;
	
	
	public HouseInhabitantRole(PersonAgent p){
		this.person = p;
		this.gui = new HouseInhabitantGui(this);
		synchronized (foodStock) {
			foodStock.put("steak", 1);
			foodStock.put("chicken", 4);
			foodStock.put("pizza", 2);
			foodStock.put("salad", 4);
		}
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
	
	public void msgGoToBed() {
		event = HouseInhabitantEvent.ReadyToSleep;
	}
	
	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		
		if (event == HouseInhabitantEvent.Hungry && state == HouseInhabitantState.Bored){
			state = HouseInhabitantState.Eating;
			Cook();
		}
		else if (event == HouseInhabitantEvent.ReadyToSleep && state == HouseInhabitantState.Bored){
			state = HouseInhabitantState.Sleeping;
			Sleep();
		}
		return false;
	}	
	
	// Actions
	private void Cook() {
		event = HouseInhabitantEvent.None;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(house.getName()), "HouseInhabitant: " + person.getName(), "I'm hungry, I should eat");
		DoGoToKitchen();
		DoGoToFridge();
		
		String choice = "";
		boolean needToBuy = false;
		
		synchronized (foodStock) {
			List<String> keys = new ArrayList<String>(foodStock.keySet());
			choice = keys.get( rand.nextInt(keys.size()) );
			Integer quantity = foodStock.get(choice);
			
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
		}
		if (choice == "") {
			// We're out of all food
		}
		if (needToBuy == true && marketScheduled == false) {
			if (person.scheduleEvent(EventType.GoToMarket))
				AlertLog.getInstance().logMessage(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), "I just scheduled an event to go to the market");				
			else
				AlertLog.getInstance().logMessage(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), "There aren't any markets to get food from.  Oh well.");				
			marketScheduled = true;
		}
		
		AlertLog.getInstance().logMessage(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), "I'm going to eat "+choice);				
		
		((HouseInhabitantGui)gui).DoHoldFood();
		DoGoToStove();
		((HouseInhabitantGui)gui).DoFoodOnStove();
		
		AlertLog.getInstance().logMessage(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), "Wow cooking is so much fun");				
		
		timer.schedule(new TimerTask(){            
            public void run() {
                    Eat();
                    //stateChanged();
            }
		}, COOKTIME);
		
	}
	
	private void Eat() {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), "Food looks ready!");				
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
		AlertLog.getInstance().logMessage(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), "That was great. Wow. Such noms");				
		//Do("That was great. Wow. Such noms");
		DoGetUpFromTable();
		
		state = HouseInhabitantState.Eating;
		event = HouseInhabitantEvent.None;
		
		exitBuilding();
		
		//msgExitBuilding();
	}

	private void LeaveForRestaurant() {
		// TODO Auto-generated method stub
		
	}

	private void Sleep() {
		DoGoToBed();
		AlertLog.getInstance().logMessage(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), "I'm going to sleep...");		
		//AlertLog.getInstance().logMessage(AlertTag.valueOf(market.getName()), "MarketCustomer: " + person.getName(), "Here is my order.");

		//person.Do("I'm going to sleep...");
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

		AlertLog.getInstance().logMessage(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), "I'm up! I'm awake!");						
		//Do("I'm up! I'm awake!");
		state = HouseInhabitantState.Bored;
		event = HouseInhabitantEvent.None;
		
		// When you wake up, you need to eat
		person.scheduleEvent(EventType.Eat);
		timer.schedule(new TimerTask(){            
            public void run() {
            		// And you also need to eat in about 8 hours
                    person.scheduleEvent(EventType.Eat);
                    //stateChanged();
            }
		}, TIMEBWMEALS); //or whatever time is fine
		
		exitBuilding();
	}

	public Map<String, Integer> getListToBuy() {
		Map<String, Integer> list = null;
		synchronized (foodToBuy) {
			list = foodToBuy;
			foodToBuy.clear();
		}
		return list;
	}
	
	public void addItems(Map<String, Integer> items) {
		synchronized (foodStock) {
			Map<String, Integer> newList = new HashMap<String, Integer>();
	        newList.putAll(foodStock);
	        
	        for (String key : items.keySet()) {
	            Integer value = newList.get(key);
	            if (value != null) {
	                Integer newValue = value + items.get(key);
	                newList.put(key, newValue);
	            } else {
	                newList.put(key, items.get(key));
	            }
	        }
		}
	}
	
	public void setLowFood() {
		// This is a function to force the person to buy some food
		synchronized (foodStock) {
			foodStock.put("steak", 1);
			foodStock.put("chicken", 1);
			foodStock.put("pizza", 1);
			foodStock.put("salad", 1);
		}
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
	public void exitBuilding() {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), "Leaving the house.");						
		//person.Do("Leaving the house.");
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
	public void enterBuilding(SimSystem s) {
		house = (HouseSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), "Entering the house.");										
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
