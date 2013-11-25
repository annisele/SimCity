package simcity.buildings.house;
import java.util.*;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.gui.house.HouseInhabitantGui;
import simcity.gui.market.MarketCustomerGui;

public class HouseInhabitantRole extends Role implements simcity.interfaces.house.HouseInhabitant {

	HouseSystem house;
	enum HouseInhabitantState { Hungry, ReadyToSleep, Sleeping, Bored } 
	HouseInhabitantState state;
	private Map <String , Integer > foodStock= new HashMap<String,Integer>();
	Timer sleeptimer= new Timer();
	Timer cooktimer= new Timer();
	
	private Semaphore atDest = new Semaphore(0, true);
	
	
	public HouseInhabitantRole(PersonAgent p){
		this.person = p;
		this.gui = new HouseInhabitantGui(this);
	}
	
	@Override
	public void atDestination() {
		atDest.release();
		
	}

	//Messages

	public void msgNeedToEat() { //From PersonAgent
		state = HouseInhabitantState.Hungry;
	}
	
	public void msgGoToBed() {//from universe/simGOD
		state = HouseInhabitantState.ReadyToSleep;
	}
	
	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		if (state == HouseInhabitantState.Hungry){
				Cook();
				return true;
		}
			if (state == HouseInhabitantState.ReadyToSleep){
				Sleep();
				return true;
			}

		return false;
	}	

	private void Cook() {
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
		int sleepTime;
		/*if (person.isDone()==true) {
			sleepTime = 8;
		} */
		sleeptimer.schedule(new TimerTask(){
            Object cookie = 1;
            
            public void run() {
                    
                    //Do("finished Sleeping");
                    WakeUp();
                    // add gui events to "got to the kitcen and make food" 
                    stateChanged();
            }
    }, 3000); //or whatever time is fine
		//personGui.msgGoToBed();

	}
	private void WakeUp(){
		//gui leaves bed
	}


	public Map<String, Integer> getListToBuy() {
		//clear list!!!!
		return null;
	}

	@Override
	public void msgExitBuilding() {
		person.Do("Leaving market.");
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

		
		
	}

}
