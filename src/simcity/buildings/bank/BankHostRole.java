package simcity.buildings.bank;

import java.util.Collections;
import java.util.*;
public class BankHostRole implements simcity.interfaces.bank.BankHost {
	private class Window {
		public int windowNum;
		private boolean occupied = false;
		public boolean isOccupied() {
			return occupied;
		}
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
		if (!bc.isEmpty()) {
			for (Window window : windows) {
				if (!window.isOccupied()) {
					tellCustomerToGoToWindow(bc.get(0), window);
				}
			}
		}
		
		return false;
	}
	//actions
	private void tellCustomerToGoToWindow(BankCustomerRole bc, Window window) {
		
	}
}
