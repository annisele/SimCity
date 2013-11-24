package simcity.buildings.bank;

import java.util.*;

import simcity.interfaces.bank.BankCustomer;

public class BankComputer {

	// variables
	int MAX_ACCOUNTS = 100;
	int numAccounts = 0;
	double loanableFunds;
	double cashInBank;
	Map<Integer,BankCustomerRole> customerAccounts = new HashMap<Integer,BankCustomerRole>(MAX_ACCOUNTS);
	Map<Integer,String> passwordAccounts = new HashMap<Integer,String>(MAX_ACCOUNTS);
	Map<Integer,Double> balanceAccounts = new HashMap<Integer,Double>(MAX_ACCOUNTS);
	Map<Integer,Double> owedAccounts = new HashMap<Integer,Double>(MAX_ACCOUNTS);
	
	BankAccount account = new BankAccount(); // temporary account for purposes of lookup

	// constructor
	BankComputer() {
		setLoanableFunds(500000);
		setCashInBank(500000);
		
	}
	// functions
	public int addAccountAndReturnNumber(BankCustomerRole bc, String password, double amountToProcess) {
		numAccounts++;
		customerAccounts.put(numAccounts, bc);
		passwordAccounts.put(numAccounts, password);
		balanceAccounts.put(numAccounts, amountToProcess);
		owedAccounts.put(numAccounts, 0.0);
		return numAccounts;
	}
	
	public boolean verifyAccount(int accountNumber, String password) {
		boolean verified = false;
		String passwordLookup = passwordAccounts.get(accountNumber);
		if (password == passwordLookup) {
			verified = true;
		}
		return verified;
	}
	
	public BankAccount accountLookup(int accountNumber) {
		BankCustomerRole bc = customerAccounts.get(accountNumber);
		double accountBalance = balanceAccounts.get(accountNumber);
		double amountOwed = owedAccounts.get(accountNumber);
		account.setBankCustomer(bc);
		account.setAccountBalance(accountBalance);
		account.setAmountOwed(amountOwed);
		return account;
	}
	
	public void updateSystemAccount(BankAccount account) {
		balanceAccounts.remove(account.getAccountNumber());
		balanceAccounts.put(account.getAccountNumber(), account.getAccountBalance());
		owedAccounts.remove(account.getAccountNumber());
		owedAccounts.put(account.getAccountNumber(), account.getAmountOwed());
	}

	public void reinitializeTempAccount() {
		account.reinitialize();
	}
	
	// utility classes
	public class BankAccount {
		private int accountNumber;
		private String accountPassword;
		private BankCustomerRole bc;
		private double accountBalance;
		private double amountOwed;

		BankAccount() {
			
		}

		public void reinitialize() {
			this.accountNumber = 0;
			this.accountPassword = "";
			this.bc = null;
			this.accountBalance = 0;
			this.amountOwed = 0;
		}
		
		public int getAccountNumber() {
			return accountNumber;
		}

		public void setAccountNumber(int accountNumber) {
			this.accountNumber = accountNumber;
		}

		public String getAccountPassword() {
			return accountPassword;
		}
		
		public void setAccountPassword(String accountPassword) {
			this.accountPassword = accountPassword;
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
