package simcity;

import agent.Agent;

public abstract class Role  {
	public PersonAgent person;
	
	 public void stateChanged() { person.stateChanged(); }
	 
	public abstract boolean pickAndExecuteAnAction();
	public boolean active() {
		// TODO Auto-generated method stub
		return false;
	}
}
