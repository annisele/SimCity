 package simcity;

import simcity.gui.Gui;
import agent.Agent;
import agent.StringUtil;

public abstract class Role  {
	public PersonAgent person;
	public Gui gui;
	
	public void stateChanged() { 
		person.stateChanged(); 
	}
	
	public void setPerson(PersonAgent p) { 
		person = p; 
	}
	
	public abstract boolean pickAndExecuteAnAction();
	
	public abstract void msgExitBuilding();
	public abstract void msgEnterBuilding();
	 
	public boolean active=false;

	public Gui getGui() { 
		return gui; 
	}
	
	protected void Do(String msg) {
	        print(msg, null);
	}
	
	protected void print(String msg, Throwable e) {
	        StringBuffer sb = new StringBuffer();
	        sb.append(person.getName());
	        sb.append(": ");
	        sb.append(msg);
	        sb.append("\n");
	        if (e != null) {
	            sb.append(StringUtil.stackTraceString(e));
	        }
	        System.out.print(sb.toString());
	}
	 
	 
	 
	 
	
}
