package simcity.buildings.bank;

import java.util.*;

import simcity.interfaces.bank.BankCustomer;
import simcity.buildings.bank.BankSystem.BankAccount;

public class BankTellerRole implements simcity.interfaces.bank.BankTeller {

	// data
	private List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());		// list of customers
	BankSystem bank;	// bank system that contains account info for people

	public enum transactionType {none, openAccount, depositMoney, withdrawMoney, loanMoney};	// type of transaction from customer
	public enum transactionState {none, processing};											// transaction state

	// constructor

	// messages

	public void msgWantToOpenAccount(BankCustomerRole bc, double amountToProcess) {
    	customers.add(new MyCustomer(bc, amountToProcess, transactionType.openAccount));
    	//stateChanged();
	}

	public void msgWantToDeposit(BankCustomerRole bc, double amountToProcess) {
    	customers.add(new MyCustomer(bc, amountToProcess, transactionType.depositMoney));
    	// stateChanged();
	}

	public void msgWantToWithdraw(BankCustomerRole bc, double amountToProcess) {
    	customers.add(new MyCustomer(bc, amountToProcess, transactionType.withdrawMoney));
    	// stateChanged();
	}

	public void msgWantALoan(BankCustomerRole bc, double amountToProcess) {
    	customers.add(new MyCustomer(bc, amountToProcess, transactionType.loanMoney));
    	// stateChanged();
	}

	// scheduler

	protected boolean pickAndExecuteAnAction() {
		synchronized(customers) {
			if (!customers.isEmpty()) {
				if (customers.get(0).getTransactionState() == transactionState.none) {

					if (customers.get(0).getTransactionType() == transactionType.openAccount) {
						customers.get(0).setTransactionState(transactionState.processing);
						bank.addAccount(customers.get(0).getBankCustomer(), customers.get(0).getAmountToProcess());
						synchronized(bank.getBankAccounts()) {
							for (BankAccount account : bank.getBankAccounts()) {
								if (account.getBankCustomer() == customers.get(0).getBankCustomer() && account.getAccountBalance() == 
									customers.get(0).getAmountToProcess()) {
									AddAccount(customers.get(0), account);
									return true;
								}
							}
						}
					}

					else if (customers.get(0).getTransactionType() == transactionType.depositMoney) {
						customers.get(0).setTransactionState(transactionState.processing);
						synchronized(bank.getBankAccounts()) {
							for (BankAccount account : bank.getBankAccounts()) {
								if (account.getAccountNumber() == customers.get(0).getBankCustomer().getAccountNumber()) {
									account.setAccountBalance(account.getAccountBalance() + customers.get(0).getAmountToProcess());
									DepositMoney(customers.get(0), account);
									return true;
								}
							}
						}
					}
					else if (customers.get(0).getTransactionType() == transactionType.withdrawMoney) {
						customers.get(0).setTransactionState(transactionState.processing);
						synchronized(bank.getBankAccounts()) {
							for (BankAccount account : bank.getBankAccounts()) {
								if (account.getAccountNumber() == customers.get(0).getBankCustomer().getAccountNumber()) {
									account.setAccountBalance(account.getAccountBalance() - customers.get(0).getAmountToProcess());
									WithdrawMoney(customers.get(0), account);
									return true;
								}
							}
						}
					}
					else if (customers.get(0).getTransactionType() == transactionType.loanMoney) {
						customers.get(0).setTransactionState(transactionState.processing);
						return true;
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
	}

	// animation DoXYZ

	// utility classes
	public class MyCustomer {
		BankCustomer bc;
		double amountToProcess;
		transactionType tt;
		transactionState ts;

		MyCustomer(BankCustomer bc, double amountToProcess, transactionType tt) {
			this.bc = bc;
			this.amountToProcess = amountToProcess;
			this.tt = tt;
			this.ts = transactionState.none;
		}

		public BankCustomer getBankCustomer() {
			return bc;
		}

		public void setBankCustomer(BankCustomer bc) {
			this.bc = bc;
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

	public BankSystem getBankSystem() {
		return bank;
	}

	public void setBankSystem(BankSystem bank) {
		this.bank = bank;
	}

}
