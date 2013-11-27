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
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
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
	public List getBankTellers() {
		return waitingBankTellers;
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
		//person.Do("Bank customer is entering the bank");
		AlertLog.getInstance().logMessage(AlertTag.valueOf(bank.getName()), "BankHost: " + person.getName(), "Bank customer is entering the bank");
		customers.add(bc);
		stateChanged();
	}
	
	public void msgLeavingBank(int windowNumber) {
		//person.Do("Bank customer is leaving the bank");
		AlertLog.getInstance().logMessage(AlertTag.valueOf(bank.getName()), "BankHost: " + person.getName(), "Bank customer is leaving the bank");
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
		System.out.println("hi");
		int tempWindowNumber = bank.getTellerWindow();
		AlertLog.getInstance().logMessage(AlertTag.valueOf(bank.getName()), "BankHost: " + person.getName(), "Teller! Go to window "+tempWindowNumber);		
		bt.msgGoToThisWindow(tempWindowNumber);
		bank.reinitializeTellerWindow();
		waitingBankTellers.remove(bt);
	}
	
	private void tellCustomerToGoToWindow(BankCustomer bc, BankWindow window) {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(bank.getName()), "BankHost: " + person.getName(), "Please head to window "+window.getWindowNumber());		
		((BankCustomer) customers.get(0)).msgGoToWindow(window.getWindowNumber(), window.getBankTeller());
		System.out.println("hi");
		window.setOccupant(bc);
		
		customers.remove(bc);
	}
	
	// utility functions
	public void exitBuilding() {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(bank.getName()), "BankHost: " + person.getName(), "Leaving the bank");		
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
		System.out.println(AlertTag.valueOf(bank.getName()));
		AlertLog.getInstance().logMessage(AlertTag.valueOf(bank.getName()), "BankHost: " + person.getName(), "Entering the bank");	
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
	@Override
	public void msgExitBuilding() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void msgEnterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		
	}
}
