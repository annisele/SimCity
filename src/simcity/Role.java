package simcity;

import simcity.gui.Gui;
import agent.Agent;

public abstract class Role  {
	public PersonAgent person;
	public Gui gui;
	
	public void stateChanged() { person.stateChanged(); }
	public void setPerson(PersonAgent p) { person = p; }
	public abstract boolean pickAndExecuteAnAction();

	public boolean active=false;

	public boolean active;
	public Gui getGui() { return gui; }
}
