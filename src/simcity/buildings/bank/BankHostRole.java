package simcity.buildings.bank;

import java.util.*;

import simcity.Role;

public class BankHostRole extends Role implements simcity.interfaces.bank.BankHost {
	
	// utility class: BankWindow
	private class BankWindow {
		
		public BankCustomerRole occupiedBy;
		public int windowNum;
		public boolean occupied = false;
		
		public boolean isOccupied() {
			return occupied;
		}
		
		public int getWindowNumber() {
			return windowNum;
		}
		
		public void setUnoccupied() {
			occupiedBy = null;
		}
		
		public void setOccupant(BankCustomerRole cust) {
			occupiedBy = cust;
		}
		
		BankCustomerRole getOccupant() {
			return occupiedBy;
		}
		
		public BankWindow(int windowNum) {
			this.windowNum = windowNum;
			this.occupied = false;
		}
		
	}
	
	// data
	String name;
	public String getName() {
		return name;
	}
	Timer timer = new Timer();
	public List<BankWindow> windows = Collections.synchronizedList(new ArrayList<BankWindow>());
	public List<BankCustomerRole> bc = Collections.synchronizedList(new ArrayList<BankCustomerRole>());
	
	public BankHostRole(String name) {
		super();
		this.name = name;
	}

	//messages
	public void msgEnteringBank(BankCustomerRole bc) {
		System.out.println("Bank customer is entering the bank");
	}
	public void msgLeavingBank(int windowNumber) {
		System.out.println("Bank customer is leaving the bank");
		for (BankWindow window : windows) {
			if (windowNumber == window.getWindowNumber()) {
				window.setUnoccupied();
				
			}
		}
	}
	//scheduler
	protected boolean pickAndExecuteAnAction() {
		if (!bc.isEmpty()) {
			for (BankWindow window : windows) {
				if (!window.isOccupied()) {
					tellCustomerToGoToWindow(bc.get(0), window);
				}
			}
		}
		
		return false;
	}
	//actions
	private void tellCustomerToGoToWindow(BankCustomerRole bc, BankWindow window) {
		
		
	}
}
