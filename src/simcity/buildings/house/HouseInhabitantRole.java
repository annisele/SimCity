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

import simcity.Clock;
import simcity.PersonAgent;
import simcity.PersonAgent.EventType;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.house.HouseInhabitantGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;

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
	private Map <String, Integer > foodCookTimes = Collections.synchronizedMap(new HashMap<String,Integer>());
	private boolean marketScheduled = false;

	private Semaphore atDest = new Semaphore(0, true);

	private final int COOKTIME = 6000;
	private final int EATTIME = 4000;
	private final int TIMEBWMEALS = Clock.hoursInMillis(5);
	//private final int FOODSTOCK = 3;
	private final Integer FOODTHRESHOLD = 2;
	private final Integer FOODRESTOCK = 4;


	public HouseInhabitantRole(PersonAgent p){
		this.person = p;
		this.gui = new HouseInhabitantGui(this);
		synchronized (foodStock) {
			foodStock.put("steak", 1);
			foodStock.put("chicken", 2);
			foodStock.put("pizza", 2);
			foodStock.put("salad", 1);

		}
		synchronized (foodToBuy) {
			foodToBuy.put("steak", 0);
			foodToBuy.put("chicken", 0);
			foodToBuy.put("pizza", 0);
			foodToBuy.put("salad", 0);

		}
		synchronized (foodCookTimes) {
			foodCookTimes.put("steak", 4000);
			foodCookTimes.put("chicken", 2000);
			foodCookTimes.put("pizza", 0);
			foodCookTimes.put("salad", 500);
		}
	}

	@Override
	public void atDestination() {
		atDest.release();
	}

	public void marketDone() {
		marketScheduled = false;
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
		AlertLog.getInstance().logDebug(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
				"Entering the House Scheduler: " + person.getCurrentEvent());						

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
		AlertLog.getInstance().logMessage(AlertTag.valueOf(house.getName()), "HouseInhabitant: " + person.getName(), 
				"I'm hungry, I should eat");
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

				if (quantity <= FOODTHRESHOLD && !marketScheduled) {
					needToBuy = true;
				}
			} else {
				choice = "";
				for (String k : keys) {
					if (foodStock.get(k) > 0) {
						// Pick this food
						foodStock.put(choice, (quantity-1));
						if (quantity == 0) {
							foodStock.put(choice, 0);
						}
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
				AlertLog.getInstance().logMessage(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
						"I just scheduled an event to go to the market");				
			else
				AlertLog.getInstance().logMessage(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
						"There aren't any markets to get food from.  Oh well.");				
			marketScheduled = true;
		}

		AlertLog.getInstance().logMessage(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
				"I'm going to eat "+choice);				

		((HouseInhabitantGui)gui).DoHoldFood();
		DoGoToStove();
		((HouseInhabitantGui)gui).DoFoodOnStove();

		AlertLog.getInstance().logMessage(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
				"Wow cooking is so much fun ");				

		timer.schedule(new TimerTask(){            
			public void run() {
				Eat();
				//stateChanged(); 
			}
		}, COOKTIME + foodCookTimes.get(choice));

		house.updateFoodDisplay(this);

	}

	private void Eat() {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
				"Food looks ready!");				
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
		AlertLog.getInstance().logMessage(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
				"That was great. Wow. Such noms");				
		//Do("That was great. Wow. Such noms");
		DoGetUpFromTable();

		state = HouseInhabitantState.Eating;
		event = HouseInhabitantEvent.None;

		exitBuilding();
		//person.roleFinished();
	}

	private void LeaveForRestaurant() {
		// TODO Auto-generated method stub

	}

	private void Sleep() {
		DoGoToBed();
		AlertLog.getInstance().logMessage(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
				"I'm going to sleep...");		
		//AlertLog.getInstance().logMessage(AlertTag.valueOf(market.getName()), "MarketCustomer: " + person.getName(), "Here is my order.");

		//person.Do("I'm going to sleep...");
		//state = HouseInhabitantState.Sleeping;
		AlertLog.getInstance().logDebug(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
				"Event Duration is: "+person.getCurrentEventDuration());		

		timer.schedule(new TimerTask(){            
			public void run() {
				WakeUp();
				//stateChanged();
			}
		}, Clock.tenMinutesInMillis(person.getCurrentEventDuration())); //or whatever time is fine
	}

	private void WakeUp(){
		//gui leaves bed

		AlertLog.getInstance().logMessage(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
				"I'm up! I'm awake!");						
		//Do("I'm up! I'm awake!");
		person.scheduleEvent(EventType.Sleep);
		AlertLog.getInstance().logDebug(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
				"Scheduling a sleep event!");						

		state = HouseInhabitantState.Bored;
		//event = HouseInhabitantEvent.Hungry;
		event = HouseInhabitantEvent.None;


		// These are problems....  We're going to just make him always be hungry as he wakes up,
		// instead of using the REAL scheduleEvent method to make him hungry.

		// When you wake up, you need to eat
		AlertLog.getInstance().logDebug(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
				"Scheduling first eat event.");						
		//	person.scheduleEvent(EventType.EatAtHome);

		timer.schedule(new TimerTask(){            
			public void run() {
				AlertLog.getInstance().logDebug(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
						"Scheduling next eat event.");						

				person.scheduleEvent(EventType.EatAtHome);
				stateChanged();
			}
		}, 10);

		timer.schedule(new TimerTask(){            
			public void run() {
				AlertLog.getInstance().logDebug(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
						"Scheduling next eat event.");						

				person.scheduleEvent(EventType.EatAtHome);
				stateChanged();
			}
		}, TIMEBWMEALS);
		DoGoToLiving();
		//person.roleFinished();
		exitBuilding();
		stateChanged();
	}

	public Map<String, Integer> getListToBuy() {

		Map<String, Integer> list = new HashMap<String, Integer>();
		synchronized (foodToBuy) {
			synchronized (foodStock) {
				if(house != null) {
					AlertLog.getInstance().logDebug(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
							"foodStock has size:  "+foodStock.size());
				}
				foodToBuy.putAll(foodStock);
				if(house != null) {
					AlertLog.getInstance().logDebug(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
							"foodToBuy has size:  "+foodToBuy.size());
				}
				for (String key : foodToBuy.keySet()) {
					Integer value = foodToBuy.get(key);

					if (value > FOODTHRESHOLD) {
						if(house != null) {
							AlertLog.getInstance().logDebug(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
									"We have enough of "+key);
						}
						foodToBuy.put(key, 0);
					} else {
						foodToBuy.put(key, FOODRESTOCK - foodToBuy.get(key));
						if (foodToBuy.get(key) < 0)
							foodToBuy.put(key,  0);
						if(house != null) {
							AlertLog.getInstance().logDebug(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
									"foodToBuy we added: " + key + " " + foodToBuy.get(key));

						}
					}
					if(house != null) {
						AlertLog.getInstance().logDebug(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
								"For  "+key+" "+value);
					}
				}
			}		
			list.keySet().removeAll(foodToBuy.keySet());
			list.putAll(foodToBuy);
			if(house != null) {
				AlertLog.getInstance().logDebug(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
						"foodToBuy has a size of "+ foodToBuy.size() + " before clearing");
			}
			foodToBuy.clear();
		}
		if(house != null) {
			AlertLog.getInstance().logDebug(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
					"getListToBuy made a list of "+ list.size() + " total foods");
		}
		return list;
	}

	public void addItems(Map<String, Integer> items) {
		synchronized (foodStock) {
			Map<String, Integer> newList = new HashMap<String, Integer>();
			newList.putAll(foodStock);
			//AlertLog.getInstance().logDebug(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
			//"Adding items from market, we bought "+ items.size() + " total foods");						
			if(house != null) {
				AlertLog.getInstance().logDebug(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
						"NewList has size: "+ items.size() + " total foods");						
			}

			for (String key : items.keySet()) {
				Integer value = items.get(key);
				if (value != null) {
					Integer newValue = foodStock.get(key) + items.get(key);
					newList.put(key, newValue);
					if(house != null) {
						AlertLog.getInstance().logDebug(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
								"for "+key+", had: "+foodStock.get(key)+", plus: "+ items.get(key) + ", total = " + newList.get(key));						
					}
				} else {
					newList.put(key, items.get(key));
					if(house != null) {
						AlertLog.getInstance().logDebug(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
								"for "+key+", we didn't have any, but are adding: "+ items.get(key));						
					}
				}
			}
			foodStock = newList;
			if(house != null) {
				house.updateFoodDisplay(this);
			}
		}
	}

	public void setLowFood() {
		// This is a function to force the person to buy some food
		synchronized (foodStock) {
			foodStock.put("steak", 2);
			foodStock.put("chicken", 2);
			foodStock.put("pizza", 2);
			foodStock.put("salad", 2);
			house.updateFoodDisplay(this);
		}
	}

	public Map<String, Integer> getFoodStock() { // this is called by the controlpanel to update the food display
		return foodStock;
	}

	// Animation
	public void DoGoToBed() {
		((HouseInhabitantGui)gui).DoGoToLiving();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		((HouseInhabitantGui)gui).DoGoToBedroom();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		((HouseInhabitantGui)gui).DoGoToBed();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}

	public void DoGoToKitchen() {
		((HouseInhabitantGui)gui).DoGoToLiving();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		((HouseInhabitantGui)gui).DoGoToKitchen();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}

	public void DoGoToFridge() {
		((HouseInhabitantGui)gui).DoGoToFridge();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}

	public void DoGoToStove() {
		((HouseInhabitantGui)gui).DoGoToStove();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}

	public void DoGoToTable() {
		((HouseInhabitantGui)gui).DoGoToKitchen();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		((HouseInhabitantGui)gui).DoGoToDining();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		((HouseInhabitantGui)gui).DoGoToNearTable();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		((HouseInhabitantGui)gui).DoGoToTable();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}

	public void DoGetUpFromTable() {
		((HouseInhabitantGui)gui).DoGoToNearTable();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		((HouseInhabitantGui)gui).DoGoToLiving();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}

	public void DoGoToLiving() {
		((HouseInhabitantGui)gui).DoGoToLiving();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}

	}

	@Override
	public void exitBuilding() {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
				"Leaving the house.");						
		//person.Do("Leaving the house.");
		gui.DoExitBuilding();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		house.exitBuilding(this);
		person.roleFinished();
		//stateChanged();
	}

	@Override
	public void enterBuilding(SimSystem s) {
		house = (HouseSystem)s;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
				"Entering the house.");
		AlertLog.getInstance().logDebug(AlertTag.valueOf(house.getName()), "HouseInhabitant: "+person.getName(), 
				" My event is: "+person.getCurrentEvent());						

		((HouseInhabitantGui)gui).DoGoToLiving();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}

		if (person.getCurrentEvent() == "EatAtHome") {
			event = HouseInhabitantEvent.Hungry;
		} else if (person.getCurrentEvent() == "Sleep") {
			event = HouseInhabitantEvent.ReadyToSleep;
		}
		state = HouseInhabitantState.Bored;

	}

	@Override
	public void clear() {

		timer.cancel();
		timer.purge();
	}

}
