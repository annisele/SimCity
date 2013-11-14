package simcity.buildings.bank;

import java.util.*;


import simcity.interfaces.bank.BankCustomer;

public class BankSystem {

	// variables
	double loanableFunds;
	private List<BankAccount> accounts = Collections.synchronizedList(new ArrayList<BankAccount>());

	// constructor
	BankSystem() {
		setLoanableFunds(500);
	}

	// functions
	public void addAccount(BankCustomer bc, double amountToProcess) {
		accounts.add(bc, amountToProcess, accounts.size()+1);
	}

	// utility classes
	public class BankAccount {
		int accountNumber;
		BankCustomer bc;
		double accountBalance;
		double amountOwed;

		BankAccount(BankCustomer bc, double amountToProcess, int accountNumber) {
			setBankCustomer(bc);
			setAccountBalance(amountToProcess);
			setAccountNumber(accountNumber);
			setAmountOwed(0);
		}

		public int getAccountNumber() {
			return accountNumber;
		}

		public void setAccountNumber(int accountNumber) {
			this.accountNumber = accountNumber;
		}

		public BankCustomer getBankCustomer() {
			return bc;
		}

		public void setBankCustomer(BankCustomer bc) {
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
