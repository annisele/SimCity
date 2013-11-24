package simcity.buildings.bank;

import java.util.*;
import java.util.concurrent.Semaphore;

import simcity.gui.Gui;
import simcity.PersonAgent;
import simcity.Role;
import simcity.gui.bank.BankHostGui;
import simcity.interfaces.bank.*;

public class BankHostRole extends Role implements simcity.interfaces.bank.BankHost {	
	// data
	private BankSystem bank;
	private String name;
	Timer timer = new Timer();
	private List<BankWindow> windows = Collections.synchronizedList(new ArrayList<BankWindow>());
	// utility class: BankWindow
	public static class BankWindow {
		public BankCustomerRole occupiedBy;
		public BankTellerRole bankTeller;
		public int windowNum;
		public boolean occupied;
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
		public BankWindow(int windowNum) {				// constructor
			this.windowNum = windowNum;
			this.occupied = false;
		}
		public BankTellerRole getBankTeller() {
			return bankTeller;
		}	
		public void setBankTeller(BankTellerRole bankTeller) {
			this.bankTeller = bankTeller;
		}		
	}
	private List<BankCustomerRole> customers = Collections.synchronizedList(new ArrayList<BankCustomerRole>());
	private List<BankTeller> bankTellers = Collections.synchronizedList(new ArrayList<BankTeller>());
	private Semaphore atBank = new Semaphore(0, true);
	public BankHostRole (PersonAgent p) {
		person = p;
		this.gui = new BankHostGui(this);
	}
	public void atBank() {
    	atBank.release();
    }
	//messages
	public void msgEnteringBank(BankCustomerRole bc) {
		System.out.println("Bank customer is entering the bank");
		customers.add(bc);
		stateChanged();
	}
	
	public void msgLeavingBank(int windowNumber) {
		synchronized(windows){
		System.out.println("Bank customer is leaving the bank");
		for (BankWindow window : windows) {
			if (windowNumber == window.getWindowNumber()) {
				window.setUnoccupied();
				stateChanged();
			}
		}
		}
	}
	
	//scheduler
	public boolean pickAndExecuteAnAction() {
		synchronized(windows){
		if (!customers.isEmpty()) {
			for (BankWindow window : windows) {
				if (!window.isOccupied()) {
					tellCustomerToGoToWindow(customers.get(0), window);
					return true;
				}
			}
		}
		}
		return false;
	}
	
	//actions
	private void tellCustomerToGoToWindow(BankCustomerRole bc, BankWindow window) {
		if(bankTellers.isEmpty()) {
			System.out.println("No bank tellers to perform bank transaction.");
		}
		else {
			person.Do("Please go to the available window");
			bc.msgGoToWindow(window.getWindowNumber(), window.getBankTeller());
			window.setOccupant(bc);
		}
		customers.remove(bc);
	}
	
	// utility functions
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void msgExitBuilding() {
		person.Do("Leaving bank");
		gui.DoExitBuilding();
		try {
			atBank.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		bank.exitBuilding(this);
		person.roleFinished();
		person.isIdle();
		
	}

	@Override
	public void msgEnterBuilding() {
		((BankHostGui)gui).DoGoToHostPosition();
		try {
			atBank.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	public void addBankTeller(BankTeller b) {
		bankTellers.add(b);
		((BankTellerRole) b).setHost(this);
	}
}
