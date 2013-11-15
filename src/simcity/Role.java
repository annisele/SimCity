package simcity;

import agent.Agent;

public abstract class Role  {
	public PersonAgent person;
	public abstract boolean pickAndExectuteAnAction();
	public boolean active() {
		// TODO Auto-generated method stub
		return false;
	}
}
