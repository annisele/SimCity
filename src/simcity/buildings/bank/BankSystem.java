package simcity.buildings.bank;

import java.util.*;

import simcity.interfaces.bank.BankCustomer;

public class BankSystem {

	// variables
	public static final int MAX_ACCOUNTS = 100;
	int numAccounts = 0;
	
	double loanableFunds;
	
	Map<Integer,BankCustomerRole> customerAccounts = new HashMap<Integer,BankCustomerRole>(MAX_ACCOUNTS);
	Map<Integer,Double> balanceAccounts = new HashMap<Integer,Double>(MAX_ACCOUNTS);
	Map<Integer,Double> owedAccounts = new HashMap<Integer,Double>(MAX_ACCOUNTS);
	
	//private List<BankAccount> accounts = Collections.synchronizedList(new ArrayList<BankAccount>());

	// constructor
	BankSystem() {
		setLoanableFunds(500);
	}

	// functions
	public int addAccountAndReturnNumber(BankCustomerRole bc, double amountToProcess) {
		numAccounts++;
		customerAccounts.put(numAccounts, bc);
		balanceAccounts.put(numAccounts, amountToProcess);
		owedAccounts.put(numAccounts, 0.0);
		return numAccounts;
	}
	
	public BankAccount accountLookup(int accountNumber) {
		BankCustomerRole bc = customerAccounts.get(accountNumber);
		double accountBalance = balanceAccounts.get(accountNumber);
		double amountOwed = owedAccounts.get(accountNumber);
		BankAccount account = new BankAccount(bc, accountBalance, accountNumber, amountOwed);
		return account;
	}
	
	// utility classes
	public class BankAccount {
		int accountNumber;
		BankCustomerRole bc;
		double accountBalance;
		double amountOwed;

		BankAccount(BankCustomerRole bc, double amountToProcess, int accountNumber, double amountOwed) {
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

}
