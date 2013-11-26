package simcity.buildings.bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Semaphore;

import simcity.test.mock.EventLog;
import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.buildings.bank.BankHostRole.BankWindow;
import simcity.gui.bank.BankHostGui;
import simcity.interfaces.bank.BankCustomer;
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
	public  EventLog log = new EventLog();
	private List<BankTeller> waitingBankTellers = Collections.synchronizedList(new ArrayList<BankTeller>());
	private List<BankCustomer> customers = Collections.synchronizedList(new ArrayList<BankCustomer>());

	
	// utility variables
	private Semaphore atBank = new Semaphore(0, true);
	Timer timer = new Timer();
	public List getCustomers() {
		return customers;
	}
	// constructor
	public BankHostRole (PersonAgent p) {
		person = p;
		this.gui = new BankHostGui(this);
		//hack
		computer = new BankComputer();
	}

	// utility class: BankWindow
	public static class BankWindow {
		public BankCustomer occupiedBy;
		public BankTeller bankTeller;
		public int windowNum;
		public boolean occupied;
		public boolean bankTellerPresent;

		public BankWindow(int windowNum) {				// constructor
			this.windowNum = windowNum;
			this.occupied = false;
			this.bankTellerPresent = false;	// HACKHACKHACK
		}

		public BankCustomer getOccupant() {
			return occupiedBy;
		}

		public void setOccupant(BankCustomer bc) {
			occupiedBy = bc;
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

		public void removeBankTeller() {
			this.bankTeller = null;
			bankTellerPresent = false;
		}
		
		public boolean isOccupied() {
			return occupied;
		}

		public boolean isReadyToServe() {
			return bankTellerPresent;
		}
	}

	
	//messages
	public void msgEnteringBank(BankCustomer bc) {
		person.Do("Bank customer is entering the bank");
		customers.add(bc);
		stateChanged();
	}
	
	public void msgLeavingBank(int windowNumber) {
		person.Do("Bank customer is leaving the bank");
		bank.setWindowAvailable(windowNumber);
		stateChanged();
	}
	
	public void msgImReadyToWork(BankTeller bt) {
		waitingBankTellers.add(bt);

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
		synchronized(waitingBankTellers) {
			if (!waitingBankTellers.isEmpty()) {
				tellTellerToGoToAppropriateWindow(waitingBankTellers.get(0));
				return true;
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
				return true;
			}
		}
		return false;
	}
	
	//actions

	private void tellTellerToGoToAppropriateWindow(BankTeller bt) {
		bank.findUnreadyWindowAndSendBankTeller(bt);
		int tempWindowNumber = bank.getTellerWindow();
		System.out.println("tempWindowNumber = " + tempWindowNumber);
		bt.msgGoToThisWindow(tempWindowNumber);
		bank.reinitializeTellerWindow();
		waitingBankTellers.remove(bt);
	}
	
	private void tellCustomerToGoToWindow(BankCustomer bc, BankWindow window) {
		
			person.Do("Please go to the available window");
			((BankCustomer) customers.get(0)).msgGoToWindow(window.getWindowNumber(), window.getBankTeller());
			
			window.setOccupant(bc);
		
		customers.remove(bc);
	}
	
	// utility functions
	public void exitBuilding() {
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
	public void enterBuilding(SimSystem s) {
		bank = (BankSystem)s;
		((BankHostGui)gui).DoGoToHostPosition();
	}
	
	public void addBankTeller(BankTeller b) {
		waitingBankTellers.add(b);
		((BankTellerRole) b).setHost(this);
	}

	@Override
	public void atDestination() {
		atBank.release();
	}
}
