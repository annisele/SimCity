package simcity.buildings.bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.buildings.bank.BankHostRole.BankWindow;
import simcity.gui.bank.BankHostGui;
import simcity.interfaces.bank.BankHost;
import simcity.interfaces.bank.BankTeller;

public class BankHostRole extends Role implements BankHost {

	// data
	// from PersonAgent
	private BankSystem bank;
	private BankComputer computer;
	// set in Bank
	//private List<BankWindow> windows = Collections.synchronizedList(new ArrayList<BankWindow>());
	private BankWindow availableWindow;
	private List<BankTeller> bankTellers = Collections.synchronizedList(new ArrayList<BankTeller>());
	private List<BankCustomerRole> customers = Collections.synchronizedList(new ArrayList<BankCustomerRole>());
	
	// utility variables
	private Semaphore atBank = new Semaphore(0, true);
	Timer timer = new Timer();
	
	// constructor
	public BankHostRole (PersonAgent p) {
		person = p;
		this.gui = new BankHostGui(this);
		//hack
		computer = new BankComputer();
	}

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
			this.bankTellerPresent = false;	// HACKHACKHACK
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

	
	//messages
	public void msgEnteringBank(BankCustomerRole bc) {
		person.Do("Bank customer is entering the bank");
		customers.add(bc);
		stateChanged();
	}
	
	public void msgLeavingBank(int windowNumber) {
		person.Do("Bank customer is leaving the bank");
		bank.setWindowAvailable(windowNumber);
		stateChanged();
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
				while (bank.getAvailableWindow() == null) {
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
			
			window.setOccupant(bc);
		
		customers.remove(bc);
	}
	
	// utility functions
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

		
	}

	@Override
	public void msgEnterBuilding(SimSystem s) {
		bank = (BankSystem)s;
		((BankHostGui)gui).DoGoToHostPosition();
	}
	
	public void addBankTeller(BankTeller b) {
		bankTellers.add(b);
		((BankTellerRole) b).setHost(this);
	}

	@Override
	public void atDestination() {
		atBank.release();
	}
}
