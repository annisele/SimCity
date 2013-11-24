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
import simcity.gui.Gui;
import simcity.gui.bank.BankCustomerGui;
import simcity.interfaces.bank.*;

public class BankCustomerRole extends Role implements simcity.interfaces.bank.BankCustomer {
	
	private String name;
	private BankHostRole bh;
	private Semaphore atBank = new Semaphore(0, true);
	private BankTellerRole bt;
	int windowNumber;
	
	int accountNumber;
	double amountToProcess;
	double cashOnHand;
	// Constructor
		public BankCustomerRole(PersonAgent person) {
			this.person = person;
			this.gui = new BankCustomerGui(this);
		}
	Timer timer = new Timer();
	
	public enum TransactionState{none, openAccount, depositMoney, withdrawMoney, loanMoney};
	private TransactionState transactionState = TransactionState.none;
	
	public enum Event{none, arrivedAtBank, directedToWindow, transactionProcessed};
    private Event event;  
    public void atBank() {
    	atBank.release();
    }
    public enum BankCustomerState{none, waitingAtBank, goingToWindow};
    private BankCustomerState customerState;
    
    // utility functions
	public void setBankHost(BankHostRole bh) {
		this.bh = bh;
	}
	public String getCustomerName() {
		return name;
	}     
	
	public int getAccountNumber() {
		return accountNumber;
	}
	
	//messages 
	
	//bank host sends this message to tell bank customer to go to bank window
	public void msgArrivedAtBank() { // from gui
		System.out.println("I'm at bank");
		event = Event.arrivedAtBank;
		stateChanged();
	}
	public void msgGoToWindow(int windowNumber, BankTellerRole bt) {
		System.out.println("I'm going to the window to perform bank transaction");
		this.windowNumber = windowNumber;
		this.bt = bt;
		event = Event.directedToWindow;
		stateChanged();
	}
	
	// bank teller sends this message to customer after opening an account
	public void msgHereIsAccountInfo(BankCustomerRole bc, int accountNumber, double accountBalance) {
		System.out.println("Here is your new account information");
		cashOnHand = cashOnHand - accountBalance;
		event = Event.transactionProcessed;
		stateChanged();
	}
	//bank teller sends this message to customer after withdrawing money
	public void msgHereIsMoney(BankCustomerRole bc, int accountNumber, double accountBalance, double amountProcessed) {
		System.out.println("Here is the money that you withdraw");
		cashOnHand = cashOnHand + amountProcessed;
		stateChanged();
	}
	//bank teller sends this message to customer after depositing money
	public void msgMoneyIsDeposited(BankCustomerRole bc, int accountNumber, double accountBalance, double amountProcessed) {
		System.out.println("Here is the money that you deposited");
		cashOnHand = cashOnHand - amountProcessed;
		event = Event.transactionProcessed;
		stateChanged();
    }
	//bank teller sends this message to customer when the loan is approved
	 public void msgHereIsYourLoan(BankCustomerRole bc, int accountNumber, double accountBalance, double amountProcessed) {
		    System.out.println("Your loan has been approved");
		    cashOnHand = cashOnHand + amountProcessed;
		    event = Event.transactionProcessed;
			stateChanged(); 
	 }
	//bank teller sends this message to customer when the loan is not approved
	 public void msgCannotGrantLoan(BankCustomerRole bc, int accountNumber, double accountBalance, double loanAmount) {
		 System.out.println("Your loan is not approved");
    	 event = Event.transactionProcessed;
	     stateChanged(); 
     }
	 //bank teller sends this message to customer when the rent is paid to the landlord
	 

	 //scheduler
	 public boolean pickAndExecuteAnAction() {
		 if (customerState == BankCustomerState.none){
			    if (event == Event.arrivedAtBank){
			    InformBankHostOfArrival();
			    customerState = BankCustomerState.waitingAtBank;
				return true;
			    }
			    
		 }
		 if (customerState == BankCustomerState.waitingAtBank && event == Event.directedToWindow) {
			 customerState = BankCustomerState.goingToWindow;
			    if (transactionState == transactionState.openAccount) {
			        OpenAccount();
			    }
			    else if (transactionState == transactionState.depositMoney) {
			        DepositMoney();
			    }
			    else if (transactionState == transactionState.withdrawMoney) {
			        WithdrawMoney();
			    }
			    else if (transactionState == transactionState.loanMoney) {
			        LoanMoney();
			    }
			    return true;
		 }
		 if (customerState == BankCustomerState.goingToWindow && event == Event.transactionProcessed) {
			    InformBankHostOfDeparture();
			    return true;
		  }
		
			System.out.println("No scheduler rule fired, should not happen in FSM, event="+event+" state="+ customerState);

		 return false;
	 }
	   //actions
	    private void InformBankHostOfArrival() {
	    	((BankCustomerGui)gui).DoGoToHost();
	    	try {
	    		atBank.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
		    bh.msgEnteringBank(this);
		    System.out.println("I'm here for bank transaction");
		}

		private void OpenAccount() {
			((BankCustomerGui)gui).DoGoToBankTeller();
			try {
	    		atBank.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
		    bt.msgWantToOpenAccount(this, amountToProcess);
		    System.out.println("Bank customer wants to open account");
		    System.out.println("PLease deposit $100 if you want to open account");
		}

		private void DepositMoney() {
			((BankCustomerGui)gui).DoGoToBankTeller();
			try {
	    		atBank.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
		    bt.msgWantToDeposit(this, amountToProcess);
		    System.out.println("Bank customer wants to deposit money");
		}

		private void WithdrawMoney() {
			((BankCustomerGui)gui).DoGoToBankTeller();
			try {
	    		atBank.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
		    bt.msgWantToWithdraw(this, amountToProcess);
		    System.out.println("Bank customer wants to withdraw money");
		}

		private void LoanMoney() {
			((BankCustomerGui)gui).DoGoToBankTeller();
			try {
	    		atBank.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
		    bt.msgWantALoan(this, amountToProcess);
		    System.out.println("Bank customer wants to get loan");
		}

		private void InformBankHostOfDeparture() {
			((BankCustomerGui)gui).DoExitBuilding();
			try {
	    		atBank.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
		    bh.msgLeavingBank(windowNumber);
		    System.out.println("Bank host, I'm leaving the bank now");
		}

		public void msgExitBuilding() {
			
		}
		public void msgEnterBuilding() {
		
		}	 

}
