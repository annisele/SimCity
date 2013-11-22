package simcity.buildings.house;
import java.util.*;

import simcity.PersonAgent;
import simcity.Role;

public class HouseInhabitantRole extends Role implements simcity.interfaces.house.HouseInhabitant {
	private Map <String , Integer > foodStock= new HashMap<String,Integer>();
	Timer sleeptimer= new Timer();
	Timer cooktimer= new Timer();
	PersonAgent person;
	enum HouseInhabitantState {hungry, readytosleep, sleeping, bored} 
	HouseInhabitantState state;
	
	
	public HouseInhabitantRole(PersonAgent p){
		this.person = p;
	}

	//Messages

	public void msgNeedToEat() { //From PersonAgent
		state = HouseInhabitantState.hungry;
	}
	
	public void msgGoToBed() {//from universe/simGOD
		state = HouseInhabitantState.readytosleep;
	}
	
	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		if (state == HouseInhabitantState.hungry){
				Cook();
				return true;
		}
			if (state == HouseInhabitantState.readytosleep){
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
                         
                         Do("cooking");
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
		if (person.isDone()==true) {
			sleepTime = 8;
		} 
		sleeptimer.schedule(new TimerTask(){
            Object cookie = 1;
            
            public void run() {
                    
                    Do("finished sleeping");
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

}
