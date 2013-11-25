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
import simcity.gui.market.MarketCustomerGui;
import simcity.interfaces.bank.*;

public class BankCustomerRole extends Role implements simcity.interfaces.bank.BankCustomer {
	
	// Data

	// from PersonAgent
	private int accountNumber;
	private String accountPassword;
	private double cashOnHand;
	private double amountToProcess;
	private int landlordAccountNumber;		// when customer wants to pay rent, he gives this accountNumber instead

	// set inside bank
	private BankHost bh;  
	private BankTeller bt;
	private int windowNumber;
	
	private BankSystem bank;

	
	// utility variables
	private Semaphore atDest = new Semaphore(0, true);
	Timer timer = new Timer();
	
	public enum TransactionType{none, openAccount, depositMoney, withdrawMoney, loanMoney, payLoan, payRent};
	private TransactionType transactionType = TransactionType.none;
	
	public enum State{none, waitingAtBank, goingToWindow, leaving};
    private State state = State.none;

	public enum Event{none, arrivedAtBank, directedToWindow, transactionProcessed};
    private Event event = Event.none;
    
	// Constructor
	public BankCustomerRole(PersonAgent person) {
		this.person = person;
		this.gui = new BankCustomerGui(this);
	}

    public void atDestination() {
    	atDest.release();
    }
    
    // utility functions
	
	public String getCustomerName() {
		return person.getName();
	}     
	
	public int getAccountNumber() {
		return accountNumber;
	}
	
	public String getPassword() {
		return accountPassword;
	}
	
	public int getLandlordAccountNumber() {
		return landlordAccountNumber;
	}
	
	//messages from personagent
	public void msgDepositMoney(BankSystem b) {
		System.out.println("I need to open an account and deposit money");
		cashOnHand = 50;
		accountPassword = "abcdef";
		amountToProcess = 20;
		transactionType = TransactionType.openAccount;
		bank = b;
		//stateChanged();
	}
	
	//messages 
	public void msgArrivedAtBank() { // from gui
		System.out.println("I'm at bank");
		event = Event.arrivedAtBank;
		stateChanged();
	}

	public void msgGoToWindow(int windowNumber, BankTeller bt) {
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
		event = Event.transactionProcessed;
		stateChanged();
	}

	// bank teller sends this message to customer if not enough money is withdrawn
	public void msgNotEnoughMoneyToWithdraw(BankCustomerRole bc, int accountNumber, double accountBalance, double amountProcessed) {
		System.out.println("Not enough money to withdraw amount");
		event = Event.transactionProcessed;
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

	 // bank teller sends this message when the loan is completely repaid
	 public void msgLoanIsCompletelyRepaid(BankCustomerRole bc, int accountNumber, double amountOwed, double amountProcessed, 
			 double actualPaid) {
		 System.out.println("Loan repaid!");
		 cashOnHand = cashOnHand - actualPaid;
		 event = Event.transactionProcessed;
		 stateChanged();
	 }
	 
	 // bank teller sends this message when the loan is only partially repaid
	 public void msgLoanIsPartiallyRepaid(BankCustomerRole bc, int accountNumber, double amountOwed, double amountProcessed) {
		 System.out.println("Loan partially repaid");
		 cashOnHand = cashOnHand - amountProcessed;
		 event = Event.transactionProcessed;
		 stateChanged();
	 }
	 
	 // bank teller sends this message when rent is successfully paid
	 public void msgRentIsPaid(BankCustomerRole bc, int accountNumber, double amountProcessed) {
		 System.out.println("Rent paid");
		 cashOnHand = cashOnHand - amountProcessed;
		 event = Event.transactionProcessed;
		 stateChanged();
	 }
	 
	 //bank teller sends this message to customer verification failed
	 public void msgVerificationFailed() {
		 System.out.println("Verification failed");
		 event = Event.transactionProcessed;
		 stateChanged();
	 }
	 
	//scheduler
	public boolean pickAndExecuteAnAction() {

		person.Do("In sched, state is: "+state);
		person.Do("event is: "+event);
	 	// person isn't doing anything, then arrives at bank
		if (state == State.none && event == Event.arrivedAtBank){
			InformBankHostOfArrival();
			state = State.waitingAtBank;
			return true;
		}

		// person is waiting at bank, then gets directed to window
		if (state == State.waitingAtBank && event == Event.directedToWindow) {
			state = State.goingToWindow;
		    if (transactionType == TransactionType.openAccount) {
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
		    
		    return true;
		}

		// person receives
		if (state == State.goingToWindow && event == Event.transactionProcessed) {
			state = State.leaving;
		    InformBankHostOfDeparture();
		    return true;
		}
		
		System.out.println("No scheduler rule fired, should not happen in FSM, event="+event+" state="+ state);

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
	    	
	    	System.out.println("I'm here for bank transaction, host is: "+bank.getBankHost());
	    	bank.getBankHost().msgEnteringBank(this);
	    	/*
	    	((BankCustomerGui)gui).DoGoToBankTeller(3);

			try {
	    		atDest.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}*/
		}

		private void OpenAccount() {
			((BankCustomerGui)gui).DoGoToBankTeller(windowNumber);
			try {
	    		atDest.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
		    bt.msgWantToOpenAccount(this, amountToProcess);
		    System.out.println("Bank customer wants to open account");
		    System.out.println("PLease deposit $100 if you want to open account");
		}

		private void DepositMoney() {
			((BankCustomerGui)gui).DoGoToBankTeller(windowNumber);
			try {
	    		atDest.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
		    bt.msgWantToDeposit(this, amountToProcess);
		    System.out.println("Bank customer wants to deposit money");
		}

		private void WithdrawMoney() {
			((BankCustomerGui)gui).DoGoToBankTeller(windowNumber);
			try {
	    		atDest.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
		    bt.msgWantToWithdraw(this, amountToProcess);
		    System.out.println("Bank customer wants to withdraw money");
		}
		
		private void LoanMoney() {
			((BankCustomerGui)gui).DoGoToBankTeller(windowNumber);
			try {
	    		atDest.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
		    bt.msgWantALoan(this, amountToProcess);
		    System.out.println("Bank customer wants to get loan");
		}

		private void PayLoan() {
			((BankCustomerGui)gui).DoGoToBankTeller(windowNumber);
			try {
	    		atDest.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
		    bt.msgWantToPayLoan(this, amountToProcess);
		    System.out.println("Bank customer wants to get loan");
		}
		
		private void PayRent() {
			((BankCustomerGui)gui).DoGoToBankTeller(windowNumber);
			try {
	    		atDest.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
		    bt.msgWantToPayRent(this, amountToProcess);
		    System.out.println("Bank customer wants to get loan");
		}
		
		private void InformBankHostOfDeparture() {
			((BankCustomerGui)gui).DoExitBuilding();
			try {
	    		atDest.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
		    bank.getBankHost().msgLeavingBank(windowNumber);
		    System.out.println("Bank host, I'm leaving the bank now");
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
		public void msgEnterBuilding(SimSystem s) {
			bank = (BankSystem)s;
			bh = bank.getBankHost();
			msgArrivedAtBank();
		}

		public void msgWithdrawMoney(BankSystem b) {
			System.out.println("I need to withdraw money");
			cashOnHand = 50;
			accountPassword = "abcdef";
			amountToProcess = 20;
			transactionType = TransactionType.openAccount;
			bank = b;
			stateChanged();
			
		}
}
