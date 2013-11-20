package simcity.buildings.house;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import simcity.PersonAgent;
import simcity.Role;

public class HouseInhabitantRole extends Role implements simcity.interfaces.house.HouseInhabitant {
	private Map <String , Integer > foodStock= new HashMap<String,Integer>();
	Timer sleeptimer;
	Timer cooktimer;
	PersonAgent person;
	enum HouseInhabitantState {hungry, readytosleep, sleeping, bored} 
	HouseInhabitantState state;
	HouseInhabitantRole(PersonAgent p){
		this.person=p;
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
			cooktimer.start(person.msgFinishedEating(), 8000); //or whatever time is fine
		else LeaveForRestaurant();

	}

	private void Sleep() {
		if () {
			int sleepTime = 8 hours;
		} else {
			int sleepTime = timeIneedtodosomething - timeToEatFood - timeNow;
		}
		sleeptimer.start(msgWakeUp(), sleepTime);
		//personGui.msgGoToBed();

	}

}
