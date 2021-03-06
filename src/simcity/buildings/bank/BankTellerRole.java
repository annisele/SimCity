package simcity.buildings.bank;

import java.util.*;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.PersonAgent.EventType;
import simcity.buildings.bank.BankComputer.BankAccount;
import simcity.gui.Gui;
import simcity.gui.bank.BankCustomerGui;
import simcity.gui.bank.BankTellerGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.bank.*;
import simcity.test.mock.EventLog;

public class BankTellerRole extends Role implements simcity.interfaces.bank.BankTeller {

	// data
	// from PersonAgent
	private String name;
	boolean stopWorking = false;
	private double robbery_money;
	// set in Bank
	//private BankComputer bank;	// bank system that contains account info for people
	private BankHost bankHost;
	private BankRobber robber;
	private BankSystem bankSystem;
	private List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());		// list of customers
	private List<MyCustomerInDebt> debtCustomers = Collections.synchronizedList(new ArrayList<MyCustomerInDebt>()); // list of bank customers in debt
	private int windowNumber;
	
	// utility variables
	private Semaphore atDest = new Semaphore(0, true);
	private Timer timer = new Timer();
	public  EventLog log = new EventLog();
	public enum tellerWindowState {none, directed, atWindow};
	public enum transactionType {none, openAccount, depositMoney, withdrawMoney, loanMoney, payLoan, payRent};	// type of transaction from customer
	public enum transactionState {none, processing, processed, done};											// transaction state
	private tellerWindowState tWindowState = tellerWindowState.none;
	
	public BankTellerRole(PersonAgent person, BankSystem bank) {
		this.person = person;
		this.gui = new BankTellerGui(this);
		this.bankSystem = bank;
		this.robbery_money=0;
	}
	public List getCustomers() {
		return customers;
	}
	public List getCustomerInDebt() {
		return debtCustomers;
	}
	// utility function
	public void atDestination() {
    	atDest.release();
    }
	// messages

	public void msgGoToThisWindow(int windowNumber) {
		this.windowNumber = windowNumber;
		tWindowState = tellerWindowState.directed;
		stateChanged();
	}
	
	public void msgWantToOpenAccount(BankCustomer bc, double amountToProcess) {
		//AlertLog.getInstance().logMessage(AlertTag.valueOf(bankSystem.getName()), "BankCustomer: " + person.getName(), "I want to open an account");		
    	customers.add(new MyCustomer(bc, bc.getPassword(), amountToProcess, transactionType.openAccount));
    	stateChanged();
	}

	public void msgWantToDeposit(BankCustomer bc, double amountToProcess) {
		//AlertLog.getInstance().logMessage(AlertTag.valueOf(bankSystem.getName()), "BankCustomer: " + person.getName(), "I want to deposit money");		

    	customers.add(new MyCustomer(bc, bc.getAccountNumber(), bc.getPassword(), amountToProcess, transactionType.depositMoney));
    	stateChanged();
	}

	public void msgWantToWithdraw(BankCustomer bc, double amountToProcess) {
		//AlertLog.getInstance().logMessage(AlertTag.valueOf(bankSystem.getName()), "BankCustomer: " + person.getName(), "I want to withdraw money");		

    	customers.add(new MyCustomer(bc, bc.getAccountNumber(), bc.getPassword(), amountToProcess, transactionType.withdrawMoney));
    	stateChanged();
	}

	public void msgWantALoan(BankCustomer bc, double amountToProcess) {
		//AlertLog.getInstance().logMessage(AlertTag.valueOf(bankSystem.getName()), "BankCustomer: " + person.getName(), "I want to get a loan.  I mean, I need to.  It's a sticky situation");		
    	customers.add(new MyCustomer(bc, bc.getAccountNumber(), bc.getPassword(), amountToProcess, transactionType.loanMoney));
    	stateChanged();
	}

	public void msgWantToPayLoan(BankCustomer bc, double amountToProcess) {
		//AlertLog.getInstance().logMessage(AlertTag.valueOf(bankSystem.getName()), "BankCustomer: " + person.getName(), "I'd like to pay off this loan");		
		customers.add(new MyCustomer(bc, bc.getAccountNumber(), bc.getPassword(), amountToProcess, transactionType.payLoan));
		stateChanged();
	}
	
	public void msgWantToPayRent(BankCustomer bc, double amountToProcess) {
		//System.out.println("I want to pay rent");
		customers.add(new MyCustomer(bc, amountToProcess, bc.getLandlordAccountNumber(), transactionType.payRent));
		stateChanged();
	}
	
	public void msgTransactionProcessed() {
		customers.get(0).setTransactionState(transactionState.processed);
		stateChanged();
	}
	public void msgShowMeTheMoney(BankRobber br,int money){
		this.robber=br;
		robbery_money=money;
		stateChanged();
	}
	
	// scheduler

	public boolean pickAndExecuteAnAction() {
		
		if (tWindowState == tellerWindowState.directed) {
			tWindowState = tellerWindowState.atWindow;
			GoToWindow();
			return true;
		}
		if(robbery_money>0){
			ProcessBankRobbery();
			return false;
		}
		synchronized(customers) {
			if (!customers.isEmpty()) {

				// if customer hasn't been processed
				if (customers.get(0).getTransactionState() == transactionState.none) {
					customers.get(0).setTransactionState(transactionState.processing);
					ProcessTransaction(customers.get(0));
					return true;
				}
					
				if (customers.get(0).getTransactionState() == transactionState.processed) {
					// customer wants to open an account
					if (customers.get(0).getTransactionType() == transactionType.openAccount) {
						customers.get(0).setTransactionState(transactionState.done);
						AddAccount(customers.get(0));
						return true;
					}

					// customer wants to deposit money into his own account
					else if (customers.get(0).getTransactionType() == transactionType.depositMoney) {
						customers.get(0).setTransactionState(transactionState.done);
						DepositMoney(customers.get(0));
						return true;
					}

					// customer wants to withdraw money
					else if (customers.get(0).getTransactionType() == transactionType.withdrawMoney) {
						customers.get(0).setTransactionState(transactionState.done);
						WithdrawMoney(customers.get(0));
						return true;
					}

					// customer wants a loan
					else if (customers.get(0).getTransactionType() == transactionType.loanMoney) {
						customers.get(0).setTransactionState(transactionState.done);
						LoanMoney(customers.get(0));
						return true;
					}

					// customer wants to pay loan
					else if (customers.get(0).getTransactionType() == transactionType.payLoan) {
						customers.get(0).setTransactionState(transactionState.done);
						PayLoan(customers.get(0));
						return true;
					}
					
					// customer wants to pay rent
					else if (customers.get(0).getTransactionType() == transactionType.payRent) {
						customers.get(0).setTransactionState(transactionState.done);
						PayRent(customers.get(0));
						return true;
					}
				}
			}
		}

		return false;
	}

	// actions

	private void GoToWindow() {
		((BankTellerGui)gui).DoGoToCorridor();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		((BankTellerGui)gui).DoGoToWindow(windowNumber);
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void ProcessTransaction(MyCustomer customer) {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(bankSystem.getName()), "BankTeller: " + person.getName(), "I'm processing your transaction.  One moment, please");	
		timer.schedule(new TimerTask() {
			public void run() {
				msgTransactionProcessed();
			}
		},
		2000);
	}
	
	private void AddAccount(MyCustomer customer) {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(bankSystem.getName()), "BankTeller: " + person.getName(), "I've made you an account!");	
		int tempAccountNumber = bankSystem.getBankComputer().addAccountAndReturnNumber(customer.getBankCustomer(), customer.getPassword(), 
				customer.getAmountToProcess());
		BankAccount account = bankSystem.getBankComputer().accountLookup(tempAccountNumber);
		customer.getBankCustomer().msgHereIsAccountInfo(account.getBankCustomer(), account.getAccountNumber(), 
				account.getAccountBalance());
		customers.remove(customer);
		bankSystem.getBankComputer().reinitializeTempAccount();
	}

	private void DepositMoney(MyCustomer customer) {
		if(bankSystem.getBankComputer().verifyAccount(customer.getAccountNumber(), customer.getPassword())){
			AlertLog.getInstance().logMessage(AlertTag.valueOf(bankSystem.getName()), "BankTeller: " + person.getName(), "I've deposited your money");				
			BankAccount account = bankSystem.getBankComputer().accountLookup(customer.getAccountNumber());
			account.setAccountBalance(account.getAccountBalance() + customer.getAmountToProcess());
			bankSystem.getBankComputer().updateSystemAccount(account);
			customer.getBankCustomer().msgMoneyIsDeposited(account.getBankCustomer(), account.getAccountNumber(), 
					account.getAccountBalance(), customer.getAmountToProcess());
		}
		else {
			customer.getBankCustomer().msgVerificationFailed();
		}
		bankSystem.getBankComputer().reinitializeTempAccount();
		customers.remove(customer);
	}

	private void WithdrawMoney(MyCustomer customer) {
		if(bankSystem.getBankComputer().verifyAccount(customer.getAccountNumber(), customer.getPassword())){
			

			BankAccount account = bankSystem.getBankComputer().accountLookup(customer.getAccountNumber());
			
			if (account.getAccountBalance() >= customer.getAmountToProcess()) {	// customer has enough money to withdraw
				AlertLog.getInstance().logMessage(AlertTag.valueOf(bankSystem.getName()), "BankTeller: " + person.getName(), "Your money is withdrawn, here it is");	
				account.setAccountBalance(account.getAccountBalance() - customer.getAmountToProcess()); bankSystem.getBankComputer().updateSystemAccount(account);
				customer.getBankCustomer().msgHereIsMoney(account.getBankCustomer(), account.getAccountNumber(), 
						account.getAccountBalance(), customer.getAmountToProcess());
			}
			else {	// customer does not have enough money to withdraw
				AlertLog.getInstance().logMessage(AlertTag.valueOf(bankSystem.getName()), "BankTeller: " + person.getName(), "You don't have that much.  Have you thought of getting a job?");	
				customer.getBankCustomer().msgNotEnoughMoneyToWithdraw(account.getBankCustomer(), account.getAccountNumber(), 
						account.getAccountBalance(), customer.getAmountToProcess());
			}
			
		}
		else { // customer fails to verify the account with password
			AlertLog.getInstance().logMessage(AlertTag.valueOf(bankSystem.getName()), "BankTeller: " + person.getName(), "That password is wrong.  You should leave");	
			customer.getBankCustomer().msgVerificationFailed();
		}
		bankSystem.getBankComputer().reinitializeTempAccount();
		customers.remove(customer);
	}

	private void LoanMoney(MyCustomer customer) {
		if(bankSystem.getBankComputer().verifyAccount(customer.getAccountNumber(), customer.getPassword())){
			BankAccount account = bankSystem.getBankComputer().accountLookup(customer.getAccountNumber());
			
			// RULE FOR LOAN: Loan is at max twice of account balance
			if (account.getAccountBalance() > customer.getAmountToProcess() * 0.5) { 
				AlertLog.getInstance().logMessage(AlertTag.valueOf(bankSystem.getName()), "BankTeller: " + person.getName(), "Your loan is approved!");	
				account.setAmountOwed(account.getAmountOwed() + customer.getAmountToProcess());
				bankSystem.getBankComputer().updateSystemAccount(account);
				bankSystem.getBankComputer().setLoanableFunds(bankSystem.getBankComputer().getLoanableFunds() - customer.getAmountToProcess());
				customer.getBankCustomer().msgHereIsYourLoan(account.getBankCustomer(), account.getAccountNumber(), 
						account.getAccountBalance(), customer.getAmountToProcess());
			}
			else {
				AlertLog.getInstance().logMessage(AlertTag.valueOf(bankSystem.getName()), "BankTeller: " + person.getName(), "We can't give you that loan.  Work on your credit");	
				customer.getBankCustomer().msgCannotGrantLoan(account.getBankCustomer(), account.getAccountNumber(), 
						account.getAmountOwed(), customer.getAmountToProcess());
			}

		}
		
		else {
			AlertLog.getInstance().logMessage(AlertTag.valueOf(bankSystem.getName()), "BankTeller: " + person.getName(), "That password is wrong.  You should leave");	
			customer.getBankCustomer().msgVerificationFailed();
		}
		
		bankSystem.getBankComputer().reinitializeTempAccount();
		customers.remove(customer);
	}

	private void PayLoan(MyCustomer customer) {
		if(bankSystem.getBankComputer().verifyAccount(customer.getAccountNumber(), customer.getPassword())){
			BankAccount account = bankSystem.getBankComputer().accountLookup(customer.getAccountNumber());
			
			if (account.getAmountOwed() <= customer.getAmountToProcess()) {	// paid successfully
				AlertLog.getInstance().logMessage(AlertTag.valueOf(bankSystem.getName()), "BankTeller: " + person.getName(), "You've paid back your loan!");	

				double actualPaid = account.getAmountOwed();	// in case customer pays too much
				account.setAmountOwed(0);
				bankSystem.getBankComputer().updateSystemAccount(account);
				bankSystem.getBankComputer().setLoanableFunds(bankSystem.getBankComputer().getLoanableFunds() + actualPaid);
				customer.getBankCustomer().msgLoanIsCompletelyRepaid(account.getBankCustomer(), account.getAccountNumber(), 
						account.getAmountOwed(), customer.getAmountToProcess(), actualPaid);
			}
			else if (account.getAmountOwed() > customer.getAmountToProcess()) { // not yet finished paying loan
				AlertLog.getInstance().logMessage(AlertTag.valueOf(bankSystem.getName()), "BankTeller: " + person.getName(), "You've made a payment on your loan.");	

				account.setAmountOwed(account.getAmountOwed() - customer.getAmountToProcess());
				bankSystem.getBankComputer().updateSystemAccount(account);
				bankSystem.getBankComputer().setLoanableFunds(bankSystem.getBankComputer().getLoanableFunds() + customer.getAmountToProcess());
				customer.getBankCustomer().msgLoanIsPartiallyRepaid(account.getBankCustomer(), account.getAccountNumber(), 
						account.getAmountOwed(), customer.getAmountToProcess());
			}

		}
		else {
			AlertLog.getInstance().logMessage(AlertTag.valueOf(bankSystem.getName()), "BankTeller: " + person.getName(), "That password is wrong.  You should leave");	

			customer.getBankCustomer().msgVerificationFailed();
		}
		bankSystem.getBankComputer().reinitializeTempAccount();
		customers.remove(customer);
	}
	
	private void PayRent(MyCustomer customer) { //bank customer pays rent to the landlord at Bank
		BankAccount account = bankSystem.getBankComputer().accountLookup(customer.getLandlordAccountNumber());
		account.setAccountBalance(account.getAccountBalance() + customer.getAmountToProcess());
		bankSystem.getBankComputer().updateSystemAccount(account);
		AlertLog.getInstance().logMessage(AlertTag.valueOf(bankSystem.getName()), "BankTeller: " + person.getName(), "You've paid your rent.  Your landlord sounds like a real hassle.  Seeya next month");	

		customer.getBankCustomer().msgRentIsPaid(account.getBankCustomer(), account.getAccountNumber(), 
				customer.getAmountToProcess());
		bankSystem.getBankComputer().reinitializeTempAccount();
		customers.remove(customer);
	}
	private void ProcessBankRobbery(){
		bankSystem.getBankComputer().stealCashInBank(robbery_money);
		robber.msgHereIsYourMoney(robbery_money);
	}
	
	// animation DoXYZ

	// utility classes
	public class MyCustomer {
		BankCustomer bc;
		int accountNumber;
		String accountPassword;
		double amountToProcess;
		transactionType tt;
		transactionState ts;
		int landlordAccountNumber;

		MyCustomer(BankCustomer bc, String accountPassword, double amountToProcess, transactionType tt) {
			this.bc = bc;
			this.accountPassword = accountPassword;
			this.amountToProcess = amountToProcess;
			this.tt = tt;
			this.ts = transactionState.none;
		}
		
		MyCustomer(BankCustomer bc, int accountNumber, String accountPassword, double amountToProcess, transactionType tt) {
			this.bc = bc;
			this.accountNumber = accountNumber;
			this.accountPassword = accountPassword;
			this.amountToProcess = amountToProcess;
			this.tt = tt;
			this.ts = transactionState.none;
		}
	
		MyCustomer(BankCustomer bc, double amountToProcess, int landlordAccountNumber, transactionType tt) {
			this.bc = bc;
			this.amountToProcess = amountToProcess;
			this.landlordAccountNumber = landlordAccountNumber;
			this.tt = tt;
		}
		
		public BankCustomer getBankCustomer() {
			return bc;
		}

		public void setBankCustomer(BankCustomer bc) {
			this.bc = bc;
		}

		public int getAccountNumber() {
			return accountNumber;
		}
		
		public void setAccountNumber(int accountNumber) {
			this.accountNumber = accountNumber;
		}
		
		public String getPassword() {
			return accountPassword;
		}
		
		public void setPassword(String accountPassword) {
			this.accountPassword = accountPassword;
		}
		
		public double getAmountToProcess() {
			return amountToProcess;
		}

		public void setAmountToProcess(double amountToProcess) {
			this.amountToProcess = amountToProcess;
		}

		public transactionType getTransactionType() {
			return tt;
		}

		public void setTransactionType(transactionType tt) {
			this.tt = tt;
		}

		public transactionState getTransactionState() {
			return ts;
		}

		public void setTransactionState(transactionState ts) {
			this.ts = ts;
		}

		public int getLandlordAccountNumber() {
			return landlordAccountNumber;
		}
		
		public void setLandlordAccountNumber(int landlordAccountNumber) {
			this.landlordAccountNumber = landlordAccountNumber;
		}
		
	}
	public class MyCustomerInDebt {
		BankCustomer bc;
		int accountNumber;
		double amountOwed;
		double amountPaid;
		
		MyCustomerInDebt (BankCustomer bc, int accountNumber, double amountOwed, double amountPaid) {
			this.bc = bc;
			this.accountNumber = accountNumber;
			this.amountOwed = amountOwed;
			this.amountPaid = amountPaid;
		}
	}
	// utility functions
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	/*
	private void InformBankHostOfDeparture() {
		((BankCustomerGui)gui).DoExitBuilding();
		try {
    		atDest.acquire();
    	} catch (InterruptedException e) {
    		e.printStackTrace();
    	}
	    bankSystem.getBankHost().msgLeavingBank(3);
	    System.out.println("Bank host, I'm leaving the bank now");
	    msgExitBuilding();
	}
	*/
	public void exitBuilding() {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(bankSystem.getName()), "BankTeller: " + person.getName(), "Leaving the bank");	
		gui.DoExitBuilding();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		bankSystem.exitBuilding(this);
		person.roleFinished();
		
	}
	public void enterBuilding(SimSystem s) {
		AlertLog.getInstance().logMessage(AlertTag.valueOf(bankSystem.getName()), "BankTeller: " + person.getName(), "Entering the bank");	
		
		bankSystem = (BankSystem) s;
		((BankTellerGui)gui).DoGoToHost();
		try {
			atDest.acquire();
		} catch (InterruptedException e) {
			
		}
		bankSystem.getBankHost().msgImReadyToWork(this);
	}
	public void setHost(BankHostRole b) {
		bankHost = b;
	}
	@Override
	public void msgFinishWorking() {
		stopWorking = true;
		person.scheduleEvent(EventType.Work);
		stateChanged();
	}

}
