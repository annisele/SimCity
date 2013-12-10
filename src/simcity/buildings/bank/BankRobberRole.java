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
import simcity.buildings.bank.BankCustomerRole.Event;
import simcity.buildings.bank.BankCustomerRole.TransactionType;
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
	
	public enum Event{none, arrivedAtBank, robBank, doneRobbing, left};
    private Event event = Event.none;
   


    
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
	public void hackRobBank(BankSystem b) {
		bank = b;
		AlertLog.getInstance().logMessage(AlertTag.valueOf(bank.getName()), "BankRobber: " + person.getName(), "Im here to rob dis bank!");	

	}
	
	//messages 
	public void msgArrivedAtBank() { // from gui
		//person.Do("I'm at bank");
		event = Event.arrivedAtBank;
		stateChanged();
	}

	public void msgDontRobBank(int windowNumber, BankTeller bt) {
		//person.Do("I'm going to the window to perform bank transaction");
		
		this.windowNumber = windowNumber;
		this.bt = bt;
		event = Event.robBank;
		stateChanged();
	}
	public void msgHereIsYourMoney(double robbery_money) {
		person.addMoney(robbery_money);
		event = Event.doneRobbing;
		stateChanged();
	}
	 public void msgLeftTheBank() {
		 event = Event.left;
		 stateChanged();
	 }
	//scheduler
	public boolean pickAndExecuteAnAction() {
		//person.Do("In sched, state is: "+state);
		//person.Do("event is: "+event);
	 	// person isn't doing anything, then arrives at bank
		if (event == Event.arrivedAtBank){
			InformBankHostOfArrival();
			return true;
		}

		// person is waiting at bank, then gets directed to window
		if (event == Event.robBank ) {
			TakeMoney(0);
		
		    return true;
		}

		// person leaves the bank after completing bank transaction
		if ( event == Event.doneRobbing ) {
		    InformBankHostOfDeparture();
		    return true;
		}
		
		// person is at the door and about to go outside
		if (event == Event.left ) {
			
			exitBuilding();
			return true;
		}
		
		return false;
	}

	   // actions
	    private void InformBankHostOfArrival() {
	    	((BankRobberGui)gui).DoGoToHost();
	    	try {
	    		atDest.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
	    	AlertLog.getInstance().logMessage(AlertTag.valueOf(bank.getName()), "BankRobber: " + person.getName(), "I'd like to rob a yo bank!");
	    	bank.getBankHost().msgRobBank(this);
	    	event=Event.none;
		}
	    private void TakeMoney(int n){
	    	((BankRobberGui)gui).DoGoToBankTeller(1);
	    	try {
	    		atDest.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
	    	bt.msgShowMeTheMoney(this,500);
	    }
	    private void     InformBankHostOfDeparture(){
	    	((BankRobberGui)gui).DoExitBuilding();
			try {
	    		atDest.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
		   
			AlertLog.getInstance().logMessage(AlertTag.valueOf(bank.getName()), "BankRobber: " + person.getName(), "I'm leaving the bank");	
		    msgLeftTheBank();
	    }

	

		public void exitBuilding() {
			event = Event.none;
		bank.exitBuilding(this);
		person.roleFinished();
			
		}

		public void enterBuilding(SimSystem s) {
			bank = (BankSystem)s;
			bh = bank.getBankHost();
			msgArrivedAtBank();
			AlertLog.getInstance().logMessage(AlertTag.valueOf(bank.getName()), "BankRobber: " + person.getName(), "I've arrived");	

			
		}

		
		
}
