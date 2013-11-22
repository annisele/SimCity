package simcity.buildings.bank;

import java.util.*;

import simcity.interfaces.bank.BankCustomer;

public class BankComputer {

	// variables
	private int MAX_ACCOUNTS = 100;
	private int numAccounts = 0;
	private double loanableFunds;
	private double cashInBank;
	private BankAccount account = new BankAccount();
	
	private Map<Integer,BankCustomerRole> customerAccounts = new HashMap<Integer,BankCustomerRole>(MAX_ACCOUNTS);
	private Map<Integer,String> customerPasswords = new HashMap<Integer,String>(MAX_ACCOUNTS);
	private Map<Integer,Double> balanceAccounts = new HashMap<Integer,Double>(MAX_ACCOUNTS);
	private Map<Integer,Double> owedAccounts = new HashMap<Integer,Double>(MAX_ACCOUNTS);

	// constructor
	BankComputer() {
		setLoanableFunds(500000);
		setCashInBank(500000);
	}

	// functions
	public int addAccountAndReturnNumber(BankCustomerRole bc, String password, double amountToProcess) {
		numAccounts++;
		customerAccounts.put(numAccounts, bc);
		customerPasswords.put(numAccounts, password);
		balanceAccounts.put(numAccounts, amountToProcess);
		owedAccounts.put(numAccounts, 0.0);
		return numAccounts;
	}
	
	public void accountLookup(int accountNumber, String password) {
		if (customerPasswords.get(accountNumber) == password) {
			account.setAccountNumber(accountNumber);
			account.setBankCustomer(customerAccounts.get(accountNumber));
			account.setAccountBalance(balanceAccounts.get(accountNumber));
			account.setAmountOwed(owedAccounts.get(accountNumber));
		}
		else {
			account.setAccountNumber(0);
			account.setBankCustomer(null);
			account.setAccountBalance(0);
			account.setAmountOwed(0);
		}
	}
	
	public BankAccount getBankAccount() {
		return account;
	}
	
	public void updateSystemAccount(BankAccount account) {
		customerAccounts.remove(account.getAccountNumber());
		customerAccounts.put(account.getAccountNumber(), account.getBankCustomer());
		balanceAccounts.remove(account.getAccountNumber());
		balanceAccounts.put(account.getAccountNumber(), account.getAccountBalance());
		owedAccounts.remove(account.getAccountNumber());
		owedAccounts.put(account.getAccountNumber(), account.getAmountOwed());
	}

	// utility classes
	public class BankAccount {
		int accountNumber;
		BankCustomerRole bc;
		double accountBalance;
		double amountOwed;

		public BankAccount() {
			
		}
		
		public BankAccount(BankCustomerRole bc, double amountToProcess, int accountNumber, double amountOwed) {
			setBankCustomer(bc);
			setAccountBalance(amountToProcess);
			setAccountNumber(accountNumber);
			setAmountOwed(amountOwed);
		}

		public int getAccountNumber() {
			return accountNumber;
		}

		public void setAccountNumber(int accountNumber) {
			this.accountNumber = accountNumber;
		}

		public BankCustomerRole getBankCustomer() {
			return bc;
		}

		public void setBankCustomer(BankCustomerRole bc) {
			this.bc = bc;
		}

		public double getAccountBalance() {
			return accountBalance;
		}

		public void setAccountBalance(double accountBalance) {
			this.accountBalance = accountBalance;
		}

		public double getAmountOwed() {
			return amountOwed;
		}

		public void setAmountOwed(double amountOwed) {
			this.amountOwed = amountOwed;
		}

	}

	// utility functions
	public double getLoanableFunds() {
		return loanableFunds;
	}

	public void setLoanableFunds(double loanableFunds) {
		this.loanableFunds = loanableFunds;
	}
	public double getCashInBank() {
		return cashInBank;
	}
	public void setCashInBank(double cashInBank) {
		this.cashInBank = cashInBank;
	}
}
