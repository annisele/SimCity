package simcity.buildings.house;
import java.util.*;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.gui.house.HouseInhabitantGui;
import simcity.gui.market.MarketCustomerGui;

public class HouseInhabitantRole extends Role implements simcity.interfaces.house.HouseInhabitant {

	HouseSystem house;
	enum HouseInhabitantState { Eating, Sleeping, Bored } 
	enum HouseInhabitantEvent { Hungry, ReadyToSleep, None } 
	HouseInhabitantState state;
	HouseInhabitantEvent event;
	private Map <String , Integer > foodStock= new HashMap<String,Integer>();
	private int sleepTime;
	Timer sleeptimer= new Timer();
	Timer cooktimer= new Timer();
	
	private Semaphore atDest = new Semaphore(0, true);
	
	
	public HouseInhabitantRole(PersonAgent p){
		this.person = p;
		this.gui = new HouseInhabitantGui(this);
		sleepTime = 4000;
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
		if (state == HouseInhabitantState.Bored && event == HouseInhabitantEvent.Hungry){
			Cook();
			return true;
		}
		else if (state == HouseInhabitantState.Bored && event == HouseInhabitantEvent.ReadyToSleep){
			Sleep();
			return false;
		}

		return false;
	}	

	private void Cook() {
		person.Do("I'm hungry, I should eat");
		DoGoToKitchen();
		DoGoToFridge();
		DoGoToStove();
		DoGoToTable();
		if (person.money < 100)
		     cooktimer.schedule(new TimerTask(){
                 Object cookie = 1;
                 
                 public void run() {
                         
                         //("cooking");
                         // add gui events to "got to the kitcen and make food" 
                         stateChanged();
                 }
         }, 3000); //or whatever time is fine
		else LeaveForRestaurant();

	}

	private void LeaveForRestaurant() {
		// TODO Auto-generated method stub
		
	}


	private void Sleep() {
		DoGoToBed();
		person.Do("I'm going to sleep...");
		state = HouseInhabitantState.Sleeping;
		
		sleeptimer.schedule(new TimerTask(){            
            public void run() {
                    WakeUp();
                    stateChanged();
            }
		}, sleepTime); //or whatever time is fine
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
		//clear list!!!!
		return null;
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
		person.isIdle();
		
	}

	@Override
	public void msgEnterBuilding() {
		((HouseInhabitantGui)gui).DoGoToLiving();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		state = HouseInhabitantState.Bored;
	}

}
