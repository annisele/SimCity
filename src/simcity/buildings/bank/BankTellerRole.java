package simcity.buildings.bank;

import java.util.*;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.buildings.bank.BankComputer.BankAccount;
import simcity.gui.Gui;
import simcity.gui.bank.BankTellerGui;
import simcity.interfaces.bank.*;

public class BankTellerRole extends Role implements simcity.interfaces.bank.BankTeller {

	// data
	// from PersonAgent
	private String name;

	// set in Bank
	private BankComputer bank;	// bank system that contains account info for people
	private BankHost bankHost;
	private BankSystem bankSystem;
	private List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());		// list of customers
	private List<MyCustomerInDebt> debtCustomers = Collections.synchronizedList(new ArrayList<MyCustomerInDebt>()); // list of bank customers in debt

	// utility variables
	private Semaphore atBank = new Semaphore(0, true);

	public enum transactionType {none, openAccount, depositMoney, withdrawMoney, loanMoney, payLoan, payRent};	// type of transaction from customer
	public enum transactionState {none, processing};											// transaction state

	// constructor
	/*
	public BankTellerRole(BankComputer bank) {
		setBankSystem(bank);
	}
	*/
	public BankTellerRole(PersonAgent person) {
		this.person = person;
		this.gui = new BankTellerGui(this);
	}

	// utility function
	public void atBank() {
    	atBank.release();
    }
	// messages

	public void msgWantToOpenAccount(BankCustomerRole bc, double amountToProcess) {
		System.out.println("I want to open an account");
    	customers.add(new MyCustomer(bc, bc.getPassword(), amountToProcess, transactionType.openAccount));
    	stateChanged();
	}

	public void msgWantToDeposit(BankCustomerRole bc, double amountToProcess) {
		System.out.println("I want to deposit money");
    	customers.add(new MyCustomer(bc, bc.getAccountNumber(), bc.getPassword(), amountToProcess, transactionType.depositMoney));
    	stateChanged();
	}

	public void msgWantToWithdraw(BankCustomerRole bc, double amountToProcess) {
		System.out.println("I want to withdraw money");
    	customers.add(new MyCustomer(bc, bc.getAccountNumber(), bc.getPassword(), amountToProcess, transactionType.withdrawMoney));
    	stateChanged();
	}

	public void msgWantALoan(BankCustomerRole bc, double amountToProcess) {
		System.out.println("I want to get a loan");
    	customers.add(new MyCustomer(bc, bc.getAccountNumber(), bc.getPassword(), amountToProcess, transactionType.loanMoney));
    	stateChanged();
	}

	public void msgWantToPayLoan(BankCustomerRole bc, double amountToProcess) {
		System.out.println("I want to pay off my loan");
		customers.add(new MyCustomer(bc, bc.getAccountNumber(), bc.getPassword(), amountToProcess, transactionType.payLoan));
		stateChanged();
	}
	
	public void msgWantToPayRent(BankCustomerRole bc, double amountToProcess) {
		System.out.println("I want to pay rent");
		customers.add(new MyCustomer(bc, amountToProcess, bc.getLandlordAccountNumber(), transactionType.payRent));
		stateChanged();
	}
	
	// scheduler

	public boolean pickAndExecuteAnAction() {
		synchronized(customers) {
			if (!customers.isEmpty()) {

				// if customer hasn't been processed
				if (customers.get(0).getTransactionState() == transactionState.none) {

					// customer wants to open an account
					if (customers.get(0).getTransactionType() == transactionType.openAccount) {
						customers.get(0).setTransactionState(transactionState.processing);
						AddAccount(customers.get(0));
						return true;
					}

					// customer wants to deposit money into his own account
					else if (customers.get(0).getTransactionType() == transactionType.depositMoney) {
						customers.get(0).setTransactionState(transactionState.processing);
						DepositMoney(customers.get(0));
						return true;
					}

					// customer wants to withdraw money
					else if (customers.get(0).getTransactionType() == transactionType.withdrawMoney) {
						customers.get(0).setTransactionState(transactionState.processing);
						WithdrawMoney(customers.get(0));
						return true;
					}

					// customer wants a loan
					else if (customers.get(0).getTransactionType() == transactionType.loanMoney) {
						customers.get(0).setTransactionState(transactionState.processing);
						LoanMoney(customers.get(0));
						return true;
					}

					// customer wants to pay loan
					else if (customers.get(0).getTransactionType() == transactionType.payLoan) {
						customers.get(0).setTransactionState(transactionState.processing);
						PayLoan(customers.get(0));
						return true;
					}
					
					// customer wants to pay rent
					else if (customers.get(0).getTransactionType() == transactionType.payRent) {
						customers.get(0).setTransactionState(transactionState.processing);
						PayRent(customers.get(0));
						return true;
					}
				}
			}
		}

		return false;
	}

	// actions

	private void AddAccount(MyCustomer customer) {
		System.out.println("I've opened an account for you");
		int tempAccountNumber = bank.addAccountAndReturnNumber(customer.getBankCustomer(), customer.getPassword(), 
				customer.getAmountToProcess());
		BankAccount account = bank.accountLookup(tempAccountNumber);
		customer.getBankCustomer().msgHereIsAccountInfo(account.getBankCustomer(), account.getAccountNumber(), 
				account.getAccountBalance());
		customers.remove(customer);
		bank.reinitializeTempAccount();
	}

	private void DepositMoney(MyCustomer customer) {
		if(bank.verifyAccount(customer.getAccountNumber(), customer.getPassword())){
			System.out.println("I've deposited your money");
			BankAccount account = bank.accountLookup(customer.getAccountNumber());
			account.setAccountBalance(account.getAccountBalance() + customer.getAmountToProcess());
			bank.updateSystemAccount(account);
			customer.getBankCustomer().msgMoneyIsDeposited(account.getBankCustomer(), account.getAccountNumber(), 
					account.getAccountBalance(), customer.getAmountToProcess());
		}
		else {
			customer.getBankCustomer().msgVerificationFailed();
		}
		bank.reinitializeTempAccount();
		customers.remove(customer);
	}

	private void WithdrawMoney(MyCustomer customer) {
		if(bank.verifyAccount(customer.getAccountNumber(), customer.getPassword())){
			System.out.println("I've withdrawn your money");
			BankAccount account = bank.accountLookup(customer.getAccountNumber());
			
			if (account.getAccountBalance() >= customer.getAmountToProcess()) {	// customer has enough money to withdraw
				account.setAccountBalance(account.getAccountBalance() - customer.getAmountToProcess());
				bank.updateSystemAccount(account);
				customer.getBankCustomer().msgHereIsMoney(account.getBankCustomer(), account.getAccountNumber(), 
						account.getAccountBalance(), customer.getAmountToProcess());
			}
			else {	// customer does not have enough money to withdraw
				customer.getBankCustomer().msgNotEnoughMoneyToWithdraw(account.getBankCustomer(), account.getAccountNumber(), 
						account.getAccountBalance(), customer.getAmountToProcess());
			}
			
		}
		else {
			customer.getBankCustomer().msgVerificationFailed();
		}
		bank.reinitializeTempAccount();
		customers.remove(customer);
	}

	private void LoanMoney(MyCustomer customer) {
		if(bank.verifyAccount(customer.getAccountNumber(), customer.getPassword())){
			BankAccount account = bank.accountLookup(customer.getAccountNumber());
			
			// RULE FOR LOAN: Loan is at max twice of account balance
			if (account.getAccountBalance() > customer.getAmountToProcess() * 0.5) { 
				System.out.println("Your loan is approved!");
				account.setAmountOwed(account.getAmountOwed() + customer.getAmountToProcess());
				bank.updateSystemAccount(account);
				bank.setLoanableFunds(bank.getLoanableFunds() - customer.getAmountToProcess());
				customer.getBankCustomer().msgHereIsYourLoan(account.getBankCustomer(), account.getAccountNumber(), 
						account.getAccountBalance(), customer.getAmountToProcess());
			}
			else {
				System.out.println("Your loan was not approved!");
				customer.getBankCustomer().msgCannotGrantLoan(account.getBankCustomer(), account.getAccountNumber(), 
						account.getAmountOwed(), customer.getAmountToProcess());
			}

		}
		
		else {
			customer.getBankCustomer().msgVerificationFailed();
		}
		
		bank.reinitializeTempAccount();
		customers.remove(customer);
	}

	private void PayLoan(MyCustomer customer) {
		if(bank.verifyAccount(customer.getAccountNumber(), customer.getPassword())){
			BankAccount account = bank.accountLookup(customer.getAccountNumber());
			
			if (account.getAmountOwed() <= customer.getAmountToProcess()) {	// paid successfully
				System.out.println("You've paid back all your loans!");
				double actualPaid = account.getAmountOwed();	// in case customer pays too much
				account.setAmountOwed(0);
				bank.updateSystemAccount(account);
				bank.setLoanableFunds(bank.getLoanableFunds() + actualPaid);
				customer.getBankCustomer().msgLoanIsCompletelyRepaid(account.getBankCustomer(), account.getAccountNumber(), 
						account.getAmountOwed(), customer.getAmountToProcess(), actualPaid);
			}
			else if (account.getAmountOwed() > customer.getAmountToProcess()) { // not yet finished paying loan
				System.out.println("You have paid a part of your loan.");
				account.setAmountOwed(account.getAmountOwed() - customer.getAmountToProcess());
				bank.updateSystemAccount(account);
				bank.setLoanableFunds(bank.getLoanableFunds() + customer.getAmountToProcess());
				customer.getBankCustomer().msgLoanIsPartiallyRepaid(account.getBankCustomer(), account.getAccountNumber(), 
						account.getAmountOwed(), customer.getAmountToProcess());
			}

		}
		else {
			customer.getBankCustomer().msgVerificationFailed();
		}
		bank.reinitializeTempAccount();
		customers.remove(customer);
	}
	
	private void PayRent(MyCustomer customer) {
		BankAccount account = bank.accountLookup(customer.getLandlordAccountNumber());
		account.setAccountBalance(account.getAccountBalance() + customer.getAmountToProcess());
		bank.updateSystemAccount(account);
		customer.getBankCustomer().msgRentIsPaid(account.getBankCustomer(), account.getAccountNumber(), 
				customer.getAmountToProcess());
		bank.reinitializeTempAccount();
		customers.remove(customer);
	}
	
	// animation DoXYZ

	// utility classes
	public class MyCustomer {
		BankCustomerRole bc;
		int accountNumber;
		String accountPassword;
		double amountToProcess;
		transactionType tt;
		transactionState ts;
		int landlordAccountNumber;

		MyCustomer(BankCustomerRole bc, String accountPassword, double amountToProcess, transactionType tt) {
			this.bc = bc;
			this.accountPassword = accountPassword;
			this.amountToProcess = amountToProcess;
			this.tt = tt;
			this.ts = transactionState.none;
		}
		
		MyCustomer(BankCustomerRole bc, int accountNumber, String accountPassword, double amountToProcess, transactionType tt) {
			this.bc = bc;
			this.accountNumber = accountNumber;
			this.accountPassword = accountPassword;
			this.amountToProcess = amountToProcess;
			this.tt = tt;
			this.ts = transactionState.none;
		}
	
		MyCustomer(BankCustomerRole bc, double amountToProcess, int landlordAccountNumber, transactionType tt) {
			this.bc = bc;
			this.amountToProcess = amountToProcess;
			this.landlordAccountNumber = landlordAccountNumber;
			this.tt = tt;
		}
		
		public BankCustomerRole getBankCustomer() {
			return bc;
		}

		public void setBankCustomer(BankCustomerRole bc) {
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
		BankCustomerRole bc;
		int accountNumber;
		double amountOwed;
		double amountPaid;
		
		MyCustomerInDebt (BankCustomerRole bc, int accountNumber, double amountOwed, double amountPaid) {
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
	
	public BankComputer getBankSystem() {
		return bank;
	}

	public void setBankSystem(BankComputer bank) {
		this.bank = bank;
	}

	public void msgExitBuilding() {
		person.Do("Leaving bank.");
		gui.DoExitBuilding();
		try {
			atBank.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		bankSystem.exitBuilding(this);
		person.roleFinished();
		person.isIdle();
		
	}
	public void msgEnterBuilding() {
		((BankTellerGui)gui).DoGoToHost();
		try {
			atBank.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	public void setHost(BankHostRole b) {
		bankHost = b;
	}

}
