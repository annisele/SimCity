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
	// from PersonAgent
	private BankSystem bank;
	private String name;
	private BankComputer computer;
	// set in Bank
	//private List<BankWindow> windows = Collections.synchronizedList(new ArrayList<BankWindow>());
	private BankWindow availableWindow;
	private List<BankTeller> bankTellers = Collections.synchronizedList(new ArrayList<BankTeller>());
	private List<BankCustomerRole> customers = Collections.synchronizedList(new ArrayList<BankCustomerRole>());
	
	// utility variables
	private Semaphore atDest = new Semaphore(0, true);
	Timer timer = new Timer();
	

	// utility class: BankWindow
	public static class BankWindow {
		public BankCustomerRole occupiedBy;
		public BankTeller bankTeller;
		public int windowNum;
		public boolean occupied;
		public boolean bankTellerPresent;

		public BankWindow(int windowNum) {				// constructor
			this.windowNum = windowNum;
			this.occupied = false;
			this.bankTellerPresent = true;	// HACKHACKHACK
		}

		public BankCustomerRole getOccupant() {
			return occupiedBy;
		}

		public void setOccupant(BankCustomerRole cust) {
			occupiedBy = cust;
			occupied = true;
		}

		public void setUnoccupied() {
			occupiedBy = null;
			occupied = false;
		}

		public int getWindowNumber() {
			return windowNum;
		}

		public BankTeller getBankTeller() {
			return bankTeller;
		}	

		public void setBankTeller(BankTeller bankTeller) {
			this.bankTeller = bankTeller;
			bankTellerPresent = true;
		}	

		public boolean isOccupied() {
			return occupied;
		}

		public boolean isReadyToServe() {
			return bankTellerPresent;
		}
	}
	
	// constructor
	public BankHostRole (PersonAgent p, BankSystem bank) {
		person = p;
		this.gui = new BankHostGui(this);
		this.bank = bank;
		//hack
		computer = new BankComputer();
	}

	// utility functions
	public void atDestination() {
    	atDest.release();
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
	
	public void msgImReadyToWork(BankTellerRole bt) {
		bankTellers.add(bt);
		stateChanged();
	}
	
	//scheduler
	public boolean pickAndExecuteAnAction() {
		/*
		synchronized(windows){
			if (!customers.isEmpty()) {
				for (BankWindow window : windows) {
					if (!window.isOccupied() && window.isReadyToServe()) {
						tellCustomerToGoToWindow(customers.get(0), window);
						return true;
					}
				}
			}
		}
		*/
		
		synchronized(bankTellers) {
			if (!bankTellers.isEmpty()) {
				bank.findUnreadyWindowAndSendBankTeller(bankTellers.get(0));
			}
		}
		
		synchronized(customers) {
			if (!customers.isEmpty()) {
				while (availableWindow == null) {
					bank.findAvailableWindow();
					availableWindow = bank.getAvailableWindow();
				}
				bank.reinitializeAvailableWindow();
				tellCustomerToGoToWindow(customers.get(0), availableWindow);
				availableWindow = null;
			}
		}
		return false;
	}
	
	//actions
	private void tellCustomerToGoToWindow(BankCustomerRole bc, BankWindow window) {
		
			person.Do("Please go to the available window");
			((BankCustomerRole) customers.get(0)).msgGoToWindow(window.getWindowNumber(), window.getBankTeller());
			//bc.msgGoToWindow(window.getWindowNumber(), window.getBankTeller());
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

	public void msgExitBuilding() {
		person.Do("Leaving bank");
		gui.DoExitBuilding();
		try {
			atDest.acquire();
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
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public void addBankTeller(BankTeller b) {
		bankTellers.add(b);
		((BankTellerRole) b).setHost(this);
	}
}
