package simcity;

import agent.Agent;

public abstract class Role  {
	public PersonAgent person;
	
	 public void stateChanged() { person.stateChanged(); }
	 public void setPerson(PersonAgent p) { person = p; }
	public abstract boolean pickAndExecuteAnAction();
	public boolean active;
}
