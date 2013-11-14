package simcity.buildings.bank;

import java.util.Collections;
import java.util.*;
public class BankHostRole implements simcity.interfaces.bank.BankHost {
	private class Window {
		public int windowNum;
		public boolean occupied;
		public int getWindowNumber() {
			return windowNum;
		}
	}
	String name;
	Timer timer = new Timer();
	public List<Window> windows = Collections.synchronizedList(new ArrayList<Window>());
	public List<BankCustomerRole> bc = Collections.synchronizedList(new ArrayList<BankCustomerRole>());
	
	public BankHostRole(String name) {
		super();
		this.name = name;
	}

	//messages
	public void msgEnteringBank(BankCustomerRole bc) {
		
	}
	public void msgLeavingBank(int windowNumber) {
		
	}
	//scheduler
	protected boolean pickAndExecuteAnAction() {
		
		
		return false;
	}

}
