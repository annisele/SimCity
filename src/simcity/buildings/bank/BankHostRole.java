package simcity.buildings.bank;

import java.util.*;

import simcity.Role;

public class BankHostRole extends Role implements simcity.interfaces.bank.BankHost {
	
	// data
	private String name;
	Timer timer = new Timer();
	public List<BankWindow> windows = Collections.synchronizedList(new ArrayList<BankWindow>());
	public List<BankCustomerRole> customers = Collections.synchronizedList(new ArrayList<BankCustomerRole>());
	
	public BankHostRole(String name) {
		super();
		this.name = name;
	}

	//messages
	public void msgEnteringBank(BankCustomerRole bc) {
		System.out.println("Bank customer is entering the bank");
		customers.add(bc);
		stateChanged();
	}
	
	public void msgLeavingBank(int windowNumber) {
		System.out.println("Bank customer is leaving the bank");
		for (BankWindow window : windows) {
			if (windowNumber == window.getWindowNumber()) {
				window.setUnoccupied();
				stateChanged();
			}
		}
	}
	
	//scheduler
	public boolean pickAndExecuteAnAction() {
		if (!customers.isEmpty()) {
			for (BankWindow window : windows) {
				if (!window.isOccupied()) {
					tellCustomerToGoToWindow(customers.get(0), window);
					return true;
				}
			}
		}
		
		return false;
	}
	
	//actions
	private void tellCustomerToGoToWindow(BankCustomerRole bc, BankWindow window) {
		System.out.println("PLease go to the available window");
		bc.msgGoToWindow(window.getWindowNumber(), window.getBankTeller());
		window.setOccupant(bc);
		customers.remove(bc);
	}
	
	// utility functions
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	// utility class: BankWindow
		public static class BankWindow {
			
			public BankCustomerRole occupiedBy;
			public BankTellerRole bankTeller;
			public int windowNum;
			public boolean occupied;
			
			public BankWindow(int windowNum) {	// constructor
				this.windowNum = windowNum;
				this.occupied = false;
			}
			
			public boolean isOccupied() {
				return occupied;
			}
			
			public int getWindowNumber() {
				return windowNum;
			}
			
			public void setUnoccupied() {
				occupiedBy = null;
				occupied = false;
			}
			
			public void setOccupant(BankCustomerRole cust) {
				occupiedBy = cust;
				occupied = true;
			}
			
			public BankCustomerRole getOccupant() {
				return occupiedBy;
			}
			
			public BankTellerRole getBankTeller() {
				return bankTeller;
			}
			
			public void setBankTeller(BankTellerRole bankTeller) {
				this.bankTeller = bankTeller;
			}
			
		}

		@Override
		public void msgExitBuilding() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void msgEnterBuilding() {
			// TODO Auto-generated method stub
			
		}
}
