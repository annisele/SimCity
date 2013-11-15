package simcity.buildings.bank;

import java.awt.Window;
import java.util.*;
public class BankCustomerRole implements simcity.interfaces.bank.BankCustomer {
	private BankHostRole bh;
	private String name;
	double amountToProcess;
	int accountNumber;
	double cashOnHand;
	private BankSystem bankSystem;
	Timer timer = new Timer();
	public enum TransactionState{none, openAccount, depositMoney, withdrawMoney, loanMoney};
	private TransactionState transactionState = TransactionState.none;
	public enum Event{none, arrivedAtBank, directedToWindow, transactionProcessed};
    Event event;  
  
    public enum BankCustomerState{none, waitingAtBank, goingToWindow};
    BankCustomerState customerState;
	private Window window;
	public void setBankHost(BankHostRole bh) {
		this.bh = bh;
	}
	public String getCustomerName() {
		return name;
	}     
	//messages 
	
	//bank host sends this message to tell bank customer to go to bank window
	public void msgArrivedAtBank() { // from gui
		event = Event.arrivedAtBank;
	}
	public void msgGoToWindow(Window window) {
		setWindow(window);
		event = Event.directedToWindow;
	}
	private void setWindow(Window window) {
		this.window = window;
	}
	// bank teller sends this message to customer after opening an account
	public void msgHereIsAccountInfo(BankCustomerRole bc, int accountNumber, double accountBalance) {
		System.out.println("Here is your new account information");
		cashOnHand = cashOnHand - accountBalance;
		event = Event.transactionProcessed;
		//stateChanged();
	}
	//bank teller sends this message to customer after withdrawing money
	public void msgHereIsMoney(BankCustomerRole bc, int accountNumber, double accountBalance, double amountProcessed) {
		System.out.println("Here is the money that you withdraw");
		cashOnHand = cashOnHand + amountProcessed;
		event = Event.transactionProcessed;
		//stateChanged(); 
	}
	//bank teller sends this message to customer after depositing money
	public void msgMoneyIsDeposited(BankCustomerRole bc, int accountNumber, double accountBalance, double amountProcessed) {
		System.out.println("Here is the money that you withdraw");
		cashOnHand = cashOnHand - amountProcessed;
		event = Event.transactionProcessed;
		//stateChanged();
    }
	//bank teller sends this message to customer when the loan is approved
	 public void msgHereIsYourLoan(BankCustomerRole bc, int accountNumber, double accountBalance, double amountProcessed) {
		    System.out.println("Your loan has been approved");
		    cashOnHand = cashOnHand + amountProcessed;
		    event = Event.transactionProcessed;
			//stateChanged(); 
	 }
	//bank teller sends this message to customer when the loan is not approved
	 public void msgCannotGrantLoan(int accountNumber, double accountBalance, double loanAmount) {
		 System.out.println("Your loan is not approved");
    	 event = Event.transactionProcessed;
	     //stateChanged(); 
     }
	
	 //scheduler
	 protected boolean pickAndExecuteAnAction() {
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
		    bh.msgEnteringBank(this);
		}

		private void OpenAccount() {
		    window.getBankTeller().msgWantToOpenAccountBalance(this, amountToProcess);
		    bankSystem.window.getBankTeller();
		}

		private void DepositMoney() {
		    window.getBankTeller().msgWantToDeposit(this, amountToProcess);
		}

		private void WithdrawMoney() {
		    window.getBankTeller().msgWantToWithdraw(this, amountToProcess);
		}

		private void LoanMoney() {
		    window.getBankTeller().msgWantALoan(this, amountToProcess);
		}

		private void InformBankHostOfDeparture() {
		    bh.msgLeavingBank(windowNumber);
		}	 
}
