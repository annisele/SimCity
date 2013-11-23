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
	private String name;
	private List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());		// list of customers
	private List<MyCustomerInDebt> debtCustomers = Collections.synchronizedList(new ArrayList<MyCustomerInDebt>()); // list of bank customers in debt
	BankComputer bank;	// bank system that contains account info for people
	private Semaphore atBank = new Semaphore(0, true);
	public enum transactionType {none, openAccount, depositMoney, withdrawMoney, loanMoney};	// type of transaction from customer
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
	public void atBank() {
    	atBank.release();
    }
	// messages

	public void msgWantToOpenAccount(BankCustomerRole bc, double amountToProcess) {
		System.out.println("I want to open an account");
    	customers.add(new MyCustomer(bc, bc.getAccountNumber(), amountToProcess, transactionType.openAccount));
    	stateChanged();
	}

	public void msgWantToDeposit(BankCustomerRole bc, double amountToProcess) {
		System.out.println("I want to deposit money");
    	customers.add(new MyCustomer(bc, bc.getAccountNumber(), amountToProcess, transactionType.depositMoney));
    	stateChanged();
	}

	public void msgWantToWithdraw(BankCustomerRole bc, double amountToProcess) {
		System.out.println("I want to withdraw money");
    	customers.add(new MyCustomer(bc, bc.getAccountNumber(), amountToProcess, transactionType.withdrawMoney));
    	stateChanged();
	}

	public void msgWantALoan(BankCustomerRole bc, double amountToProcess) {
		System.out.println("I want to get a loan");
    	customers.add(new MyCustomer(bc, bc.getAccountNumber(), amountToProcess, transactionType.loanMoney));
    	stateChanged();
	}

	// scheduler

	public boolean pickAndExecuteAnAction() {
		synchronized(customers) {
			if (!customers.isEmpty()) {
				if (customers.get(0).getTransactionState() == transactionState.none) {

					if (customers.get(0).getTransactionType() == transactionType.openAccount) {
						customers.get(0).setTransactionState(transactionState.processing);
						int tempAccountNumber = bank.addAccountAndReturnNumber(customers.get(0).getBankCustomer(), customers.get(0).getAmountToProcess());
						BankAccount account = bank.accountLookup(tempAccountNumber);
						AddAccount(customers.get(0), account);
						return true;
					}

					else if (customers.get(0).getTransactionType() == transactionType.depositMoney) {
						customers.get(0).setTransactionState(transactionState.processing);
						BankAccount account = bank.accountLookup(customers.get(0).getAccountNumber());
						account.setAccountBalance(account.getAccountBalance() + customers.get(0).getAmountToProcess());
						bank.updateSystemAccount(account);
						DepositMoney(customers.get(0), account);
						return true;
					}

					else if (customers.get(0).getTransactionType() == transactionType.withdrawMoney) {
						customers.get(0).setTransactionState(transactionState.processing);
						BankAccount account = bank.accountLookup(customers.get(0).getAccountNumber());
						account.setAccountBalance(account.getAccountBalance() - customers.get(0).getAmountToProcess());
						bank.updateSystemAccount(account);
						WithdrawMoney(customers.get(0), account);
						return true;
					}

					else if (customers.get(0).getTransactionType() == transactionType.withdrawMoney) {
						customers.get(0).setTransactionState(transactionState.processing);
						BankAccount account = bank.accountLookup(customers.get(0).getAccountNumber());
						if (account.getAccountNumber() > 0.5 * customers.get(0).getAmountToProcess()) {	// RULE FOR LOAN: Loan is at max twice of account
							account.setAmountOwed(account.getAmountOwed() + customers.get(0).getAmountToProcess());
							bank.updateSystemAccount(account);
							CanGrantLoan(customers.get(0), account);
							return true;
						}
						else {
							CannotGrantLoan(customers.get(0), account);
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	// actions

	private void AddAccount(MyCustomer customer, BankAccount account) {
		System.out.println("I've opened an account for you");
		account.getBankCustomer().msgHereIsAccountInfo(account.getBankCustomer(), account.getAccountNumber(), account.getAccountBalance());
		customers.remove(customer);
	}

	private void DepositMoney(MyCustomer customer, BankAccount account) {
		System.out.println("I've deposited your money");
		account.getBankCustomer().msgMoneyIsDeposited(account.getBankCustomer(), account.getAccountNumber(), account.getAccountBalance(), 
			customer.getAmountToProcess());
		customers.remove(customer);
	}

	private void WithdrawMoney(MyCustomer customer, BankAccount account) {
		System.out.println("I've withdrawn your money");
		account.getBankCustomer().msgHereIsMoney(account.getBankCustomer(), account.getAccountNumber(), account.getAccountBalance(),
			customer.getAmountToProcess());
		customers.remove(customer);
	}

	private void CanGrantLoan(MyCustomer customer, BankAccount account) {
		System.out.println("Your loan is approved");
		account.getBankCustomer().msgHereIsYourLoan(account.getBankCustomer(), account.getAccountNumber(), account.getAmountOwed(),
			customer.getAmountToProcess());
		customers.remove(customer);
	}

	private void CannotGrantLoan(MyCustomer customer, BankAccount account) {
		System.out.println("Your loan is not approved");
		account.getBankCustomer().msgCannotGrantLoan(account.getBankCustomer(), account.getAccountNumber(), account.getAmountOwed(),
			customer.getAmountToProcess());
		customers.remove(customer);
	}

	// animation DoXYZ

	// utility classes
	public class MyCustomer {
		BankCustomerRole bc;
		int accountNumber;
		double amountToProcess;
		transactionType tt;
		transactionState ts;

		MyCustomer(BankCustomerRole bc, int accountNumber, double amountToProcess, transactionType tt) {
			this.bc = bc;
			this.accountNumber = accountNumber;
			this.amountToProcess = amountToProcess;
			this.tt = tt;
			this.ts = transactionState.none;
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

	@Override
	public void msgExitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgEnterBuilding() {
		// TODO Auto-generated method stub
		
	}

}
