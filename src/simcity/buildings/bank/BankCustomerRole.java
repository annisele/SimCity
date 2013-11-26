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
	
	public TransactionType getTransactionType() {
		return transactionType;
	}
	
	public void setTransactionType(TransactionType tt) {
		this.transactionType = tt;
	}
	
	public State getState() {
		return state;
	}
	
	public Event getEvent() {
		return event;
	}
	
	//messages from personagent
	public void msgDepositMoney(BankSystem b) {
		person.Do("I need to open an account and deposit money");
		cashOnHand = 50;
		accountPassword = "abcdef";
		amountToProcess = 20;
		transactionType = TransactionType.openAccount;
		bank = b;
	}
	
	//messages 
	public void msgArrivedAtBank() { // from gui
		person.Do("I'm at bank");
		event = Event.arrivedAtBank;
		stateChanged();
	}

	public void msgGoToWindow(int windowNumber, BankTeller bt) {
		person.Do("I'm going to the window to perform bank transaction");
		this.windowNumber = windowNumber;
		this.bt = bt;
		event = Event.directedToWindow;
		stateChanged();
	}
	
	// bank teller sends this message to customer after opening an account
	public void msgHereIsAccountInfo(BankCustomer bc, int accountNumber, double accountBalance) {
		person.Do("Here is your new account information");
		person.Do("Accountnumber: " + accountNumber);
		person.Do("Balance: " + accountBalance);
		cashOnHand = cashOnHand - accountBalance;
		event = Event.transactionProcessed;
		stateChanged();
	}

	//bank teller sends this message to customer after withdrawing money
	public void msgHereIsMoney(BankCustomer bc, int accountNumber, double accountBalance, double amountProcessed) {
		person.Do("Here is the money that you withdraw");
		person.Do("Accountnumber: " + accountNumber);
		person.Do("Balance: " + accountBalance);
		cashOnHand = cashOnHand + amountProcessed;
		event = Event.transactionProcessed;
		stateChanged();
	}

	// bank teller sends this message to customer if not enough money is withdrawn
	public void msgNotEnoughMoneyToWithdraw(BankCustomer bc, int accountNumber, double accountBalance, double amountProcessed) {
		person.Do("Not enough money to withdraw amount");
		event = Event.transactionProcessed;
		stateChanged();
	}
	
	//bank teller sends this message to customer after depositing money
	public void msgMoneyIsDeposited(BankCustomer bc, int accountNumber, double accountBalance, double amountProcessed) {
		person.Do("Here is the money that you deposited");
		cashOnHand = cashOnHand - amountProcessed;
		event = Event.transactionProcessed;
		stateChanged();
    }

	//bank teller sends this message to customer when the loan is approved
	 public void msgHereIsYourLoan(BankCustomer bc, int accountNumber, double accountBalance, double amountProcessed) {
		    System.out.println("Your loan has been approved");
		    cashOnHand = cashOnHand + amountProcessed;
		    event = Event.transactionProcessed;
			stateChanged(); 
	 }
	 
	//bank teller sends this message to customer when the loan is not approved
	 public void msgCannotGrantLoan(BankCustomer bc, int accountNumber, double accountBalance, double loanAmount) {
		 System.out.println("Your loan is not approved");
    	 event = Event.transactionProcessed;
	     stateChanged(); 
     }

	 // bank teller sends this message when the loan is completely repaid
	 public void msgLoanIsCompletelyRepaid(BankCustomer bc, int accountNumber, double amountOwed, double amountProcessed, 
			 double actualPaid) {
		 System.out.println("Loan repaid!");
		 cashOnHand = cashOnHand - actualPaid;
		 event = Event.transactionProcessed;
		 stateChanged();
	 }
	 
	 // bank teller sends this message when the loan is only partially repaid
	 public void msgLoanIsPartiallyRepaid(BankCustomer bc, int accountNumber, double amountOwed, double amountProcessed) {
		 System.out.println("Loan partially repaid");
		 cashOnHand = cashOnHand - amountProcessed;
		 event = Event.transactionProcessed;
		 stateChanged();
	 }
	 
	 // bank teller sends this message when rent is successfully paid
	 public void msgRentIsPaid(BankCustomer bc, int accountNumber, double amountProcessed) {
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

		//person.Do("In sched, state is: "+state);
		//person.Do("event is: "+event);
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

		// person leaves the bank after completing bank transaction
		if (state == State.goingToWindow && event == Event.transactionProcessed) {
			state = State.leaving;
		    InformBankHostOfDeparture();
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
	    	
	    	person.Do("I'm here for bank transaction, host is: "+ bank.getBankHost());
	    	bank.getBankHost().msgEnteringBank(this);
		}

		private void OpenAccount() {
			((BankCustomerGui)gui).DoGoToBankTeller(windowNumber);
			try {
	    		atDest.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
		    bt.msgWantToOpenAccount(this, amountToProcess);
		    person.Do("I want to open account");
		    person.Do("deposit $100 to open account");
		}

		private void DepositMoney() {
			((BankCustomerGui)gui).DoGoToBankTeller(windowNumber);
			try {
	    		atDest.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
		    bt.msgWantToDeposit(this, amountToProcess);
		    person.Do("I want to deposit money");
		}

		private void WithdrawMoney() {
			((BankCustomerGui)gui).DoGoToBankTeller(windowNumber);
			try {
	    		atDest.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
		    bt.msgWantToWithdraw(this, amountToProcess);
		    person.Do("I want to withdraw money");
		}
		
		private void LoanMoney() {
			((BankCustomerGui)gui).DoGoToBankTeller(windowNumber);
			try {
	    		atDest.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
		    bt.msgWantALoan(this, amountToProcess);
		    person.Do("I want to get loan");
		}

		private void PayLoan() {
			((BankCustomerGui)gui).DoGoToBankTeller(windowNumber);
			try {
	    		atDest.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
		    bt.msgWantToPayLoan(this, amountToProcess);
		    System.out.println("I want to get loan");
		}
		
		private void PayRent() {
			((BankCustomerGui)gui).DoGoToBankTeller(windowNumber);
			try {
	    		atDest.acquire();
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
		    bt.msgWantToPayRent(this, amountToProcess);
		    System.out.println("I want to pay $10 rent");
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
		    exitBuilding();
		}

		public void exitBuilding() {
			person.Do("Leaving bank");
			gui.DoExitBuilding();
			try {
				atDest.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			bank.exitBuilding(this);
			person.roleFinished();
			
		}
		public void enterBuilding(SimSystem s) {
			bank = (BankSystem)s;
			bh = bank.getBankHost();
			msgArrivedAtBank();
		}

		public void msgWithdrawMoney(BankSystem b) {
			System.out.println("I need to withdraw money");
			cashOnHand = 50;
			accountPassword = "abcdef";
			amountToProcess = 20;
			transactionType = TransactionType.withdrawMoney;
			bank = b;	
		}
		public void msgPayRent(BankSystem b) {
			System.out.println("I need to pay rent");
			cashOnHand = 50;
			accountPassword = "abcdef";
			amountToProcess = 10;
			transactionType = TransactionType.payRent;
			bank = b;	
		}
		public void msgGetLoan(BankSystem b) {
			System.out.println("I need to get a loan");
			cashOnHand = 50;
			accountPassword = "abcdef";
			amountToProcess = 100;
			transactionType = TransactionType.loanMoney;
			bank = b;
		}
		
}
