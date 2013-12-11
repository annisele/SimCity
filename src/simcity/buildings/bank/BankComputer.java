package simcity.buildings.bank;

import java.util.HashMap;
import java.util.Map;

import simcity.SimSystem;
import simcity.interfaces.bank.BankCustomer;

public class BankComputer {

	// variables
	int MAX_ACCOUNTS = 100;
	int numAccounts = 0;
	double loanableFunds;
	double cashInBank;
	Map<Integer,BankCustomer> customerAccounts = new HashMap<Integer,BankCustomer>(MAX_ACCOUNTS);
	Map<Integer,String> passwordAccounts = new HashMap<Integer,String>(MAX_ACCOUNTS);
	Map<Integer,Double> balanceAccounts = new HashMap<Integer,Double>(MAX_ACCOUNTS);
	Map<Integer,Double> owedAccounts = new HashMap<Integer,Double>(MAX_ACCOUNTS);
	
	BankAccount account = new BankAccount(); // temporary account for purposes of lookup
	Map<Integer,SystemBankAccount> systemAccounts = new HashMap<Integer,SystemBankAccount>();

	// constructor
	BankComputer() {
		setLoanableFunds(500000);
		setCashInBank(500000);
	}
	
	// functions
	public int addAccountAndReturnNumber(BankCustomer bc, String password, double amountToProcess) {
		numAccounts++;
		customerAccounts.put(numAccounts, bc);
		passwordAccounts.put(numAccounts, password);
		balanceAccounts.put(numAccounts, amountToProcess);
		owedAccounts.put(numAccounts, 0.0);
		System.out.println("numAccounts " + numAccounts);
		return numAccounts;
	}
	
	public boolean verifyAccount(int accountNumber, String password) {
		String passwordLookup = passwordAccounts.get(accountNumber);
		if (password == passwordLookup) {
			return true;
		}
		return false;
	}
	
	public BankAccount accountLookup(int accountNumber) {
		BankCustomer bc = customerAccounts.get(accountNumber);
		double accountBalance = balanceAccounts.get(accountNumber);
		double amountOwed = owedAccounts.get(accountNumber);
		account.setBankCustomer(bc);
		account.setAccountBalance(accountBalance);
		account.setAmountOwed(amountOwed);
		account.setAccountNumber(accountNumber);
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
	
	public void addBankAccount(int accountNumber, double accountBalance, double amountOwed, String password) {
		customerAccounts.put(accountNumber, null);
		passwordAccounts.put(accountNumber, password);
		balanceAccounts.put(accountNumber, accountBalance);
		owedAccounts.put(accountNumber, amountOwed);
	}
	
	public void depositIntoSystemAccount(int systemAccountNumber, double deposit) {
		SystemBankAccount temp = systemAccounts.get(systemAccountNumber);
		temp.setAccountBalance(temp.getAccountBalance() + deposit);
		systemAccounts.remove(systemAccountNumber);
		systemAccounts.put(systemAccountNumber, temp);
	}
	
	public void withdrawIntoSystemAccount(int systemAccountNumber, double withdrawal) {
		SystemBankAccount temp = systemAccounts.get(systemAccountNumber);
		temp.setAccountBalance(temp.getAccountBalance() - withdrawal);
		systemAccounts.remove(systemAccountNumber);
		systemAccounts.put(systemAccountNumber, temp);
	}
	
	// utility classes
	public class BankAccount {
		private int accountNumber;
		private String accountPassword;
		private BankCustomer bc;
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

	public class SystemBankAccount {
		private int accountNumber;
		private String accountPassword;
		private SimSystem simSystem;
		private double accountBalance;
		private double amountOwed;

		SystemBankAccount() {
			
		}

		public void reinitialize() {
			this.accountNumber = 0;
			this.accountPassword = "";
			this.simSystem = null;
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
		
		public SimSystem getSystem() {
			return simSystem;
		}

		public void setSystem(SimSystem simSystem) {
			this.simSystem = simSystem;
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
	public void stealCashInBank(double amount) {
		this.cashInBank = cashInBank-amount;
	}
	public Map<Integer,BankCustomer> getCustomerAccounts() {
		return customerAccounts;
	}
	
	public Map<Integer,String> getPasswordAccounts() {
		return passwordAccounts;
	}
	
	public Map<Integer,Double> getBalanceAccounts() {
		return balanceAccounts;
	}
	
	public Map<Integer,Double> getOwedAccounts() {
		return owedAccounts;
	}
}
