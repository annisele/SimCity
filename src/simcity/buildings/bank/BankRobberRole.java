/*******************
 * BankCustomerRole 
 * @author levonne key
 *
 */
package simcity.buildings.bank;

import java.util.*;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.Gui;
import simcity.gui.bank.BankCustomerGui;
import simcity.gui.bank.BankRobberGui;
import simcity.gui.market.MarketCustomerGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.bank.*;

public class BankRobberRole extends Role implements simcity.interfaces.bank.BankRobber {
	
	// Data

	// from PersonAgent
	// when customer wants to pay rent, he gives this accountNumber instead

	// set inside bank
	private BankHost bh;  
	private BankTeller bt;
	private int windowNumber; 
	
	private BankSystem bank;

	
	// utility variables
	private Semaphore atDest = new Semaphore(0, true);
	Timer timer = new Timer();
	

	public enum State{none, waitingAtBank, goingToWindow, leaving};
    private State state = State.none;


    
	// Constructor
	public BankRobberRole(PersonAgent person) {
		this.person = person;
		this.gui = new BankRobberGui(this);
	}

    public void atDestination() {
    	atDest.release();
    }
    
    // utility functions
	
	public String getCustomerName() {
		return person.getName();
	}     
	
	

	public BankSystem getBankSystem() {
		return bank;
	}
	
	public void setBankSystem(BankSystem bank) {
		this.bank = bank;
	}
	
	public BankHost getBankHost() {
		return bh;
	}
	
	public void setBankHost(BankHost bh) {
		this.bh = bh;
	}
	
	
	public BankTeller getBankTeller() {
		return bt;
	}
	
	public void setBankTeller(BankTeller bt) {
		this.bt = bt;
	}
	
	//messages 
	public void msgArrivedAtBank() { // from gui
		//person.Do("I'm at bank");
		//event = Event.arrivedAtBank;
		stateChanged();
	}

	public void msgGoToWindow(int windowNumber, BankTeller bt) {
		//person.Do("I'm going to the window to perform bank transaction");
		this.windowNumber = windowNumber;
		this.bt = bt;
		//event = Event.directedToWindow;
		stateChanged();
	}
	
	//scheduler
	public boolean pickAndExecuteAnAction() {

		//person.Do("In sched, state is: "+state);
		//person.Do("event is: "+event);
	 	// person isn't doing anything, then arrives at bank
		if (state == State.none){
			state = State.waitingAtBank;
			InformBankHostOfArrival();
			return true;
		}

		// person is waiting at bank, then gets directed to window
		if (state == State.waitingAtBank ) {
			state = State.goingToWindow;
		   /* if (transactionType == TransactionType.openAccount) {
		        OpenAccount();
		    }
		    else if (transactionType == TransactionType.depositMoney) {
		        DepositMoney();
		    }
		    else if (transactionType == TransactionType.withdrawMoney) {
		        WithdrawMoney();
		    }
		    else if (transactionType == TransactionType.loanMoney) {
		        LoanMoney();
		    }
		    else if (transactionType == TransactionType.payLoan) {
		    	PayLoan();
		    }
		    else if (transactionType == TransactionType.payRent) {
		    	PayRent();
		    }
		    */
		    return true;
		}

		// person leaves the bank after completing bank transaction
		if (state == State.goingToWindow ) {
			state = State.leaving;
		    InformBankHostOfDeparture();
		    return true;
		}
		
		// person is at the door and about to go outside
		if (state == State.leaving ) {
			state = State.none;
			exitBuilding();
			return true;
		}
		
		return false;
	}

	   // actions
	    private void InformBankHostOfArrival() {
	    	((BankCustomerGui)gui).DoGoToHost();
	    	try {
	    		atDest.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
	    	AlertLog.getInstance().logMessage(AlertTag.valueOf(bank.getName()), "BankCustomer: " + person.getName(), "I'd like to make a transaction!");
	    	//bank.getBankHost().msgEnteringBank(this);
		}
	    private void     InformBankHostOfDeparture(){
	    	
	    }

	

		@Override
		public void exitBuilding() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void enterBuilding(SimSystem s) {
			// TODO Auto-generated method stub
			
		}

		
		
}
