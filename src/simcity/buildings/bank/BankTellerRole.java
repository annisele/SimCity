package simcity.buildings.bank;

import java.util.*;

import simcity.interfaces.bank.BankCustomer;
import simcity.buildings.bank.BankComputer.BankAccount;

public class BankTellerRole implements simcity.interfaces.bank.BankTeller {

	// data
	private List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());		// list of customers
	BankComputer bank;	// bank system that contains account info for people

	public enum transactionType {none, openAccount, depositMoney, withdrawMoney, loanMoney};	// type of transaction from customer
	public enum transactionState {none, processing};											// transaction state

	// constructor
	public BankTellerRole(BankComputer bank) {
		setBankSystem(bank);
	}

	// messages

	public void msgWantToOpenAccount(BankCustomerRole bc, double amountToProcess) {
    	customers.add(new MyCustomer(bc, bc.getAccountNumber(), amountToProcess, transactionType.openAccount));
    	//stateChanged();
	}

	public void msgWantToDeposit(BankCustomerRole bc, double amountToProcess) {
    	customers.add(new MyCustomer(bc, bc.getAccountNumber(), amountToProcess, transactionType.depositMoney));
    	// stateChanged();
	}

	public void msgWantToWithdraw(BankCustomerRole bc, double amountToProcess) {
    	customers.add(new MyCustomer(bc, bc.getAccountNumber(), amountToProcess, transactionType.withdrawMoney));
    	// stateChanged();
	}

	public void msgWantALoan(BankCustomerRole bc, double amountToProcess) {
    	customers.add(new MyCustomer(bc, bc.getAccountNumber(), amountToProcess, transactionType.loanMoney));
    	// stateChanged();
	}

	// scheduler

	protected boolean pickAndExecuteAnAction() {
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
		account.getBankCustomer().msgHereIsAccountInfo(account.getBankCustomer(), account.getAccountNumber(), account.getAccountBalance());
		customers.remove(customer);
	}

	private void DepositMoney(MyCustomer customer, BankAccount account) {
		account.getBankCustomer().msgMoneyIsDeposited(account.getBankCustomer(), account.getAccountNumber(), account.getAccountBalance(), 
			customer.getAmountToProcess());
		customers.remove(customer);
	}

	private void WithdrawMoney(MyCustomer customer, BankAccount account) {
		account.getBankCustomer().msgHereIsMoney(account.getBankCustomer(), account.getAccountNumber(), account.getAccountBalance(),
			customer.getAmountToProcess());
		customers.remove(customer);
	}

	private void CanGrantLoan(MyCustomer customer, BankAccount account) {
		account.getBankCustomer().msgHereIsYourLoan(account.getBankCustomer(), account.getAccountNumber(), account.getAmountOwed(),
			customer.getAmountToProcess());
		customers.remove(customer);
	}

	private void CannotGrantLoan(MyCustomer customer, BankAccount account) {
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

	// utility functions

	public BankComputer getBankSystem() {
		return bank;
	}

	public void setBankSystem(BankComputer bank) {
		this.bank = bank;
	}

}
