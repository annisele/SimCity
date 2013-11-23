package simcity.buildings.bank;

import java.util.*;

import simcity.Role;
import simcity.buildings.bank.BankComputer.BankAccount;
import simcity.buildings.bank.BankTransaction.transactionType;

public class BankTellerRole extends Role implements simcity.interfaces.bank.BankTeller {

	// data
	private String name;
	private List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());		// list of customers
	private List<MyCustomerInDebt> debtCustomers = Collections.synchronizedList(new ArrayList<MyCustomerInDebt>()); // list of bank customers in debt
	BankComputer bank;	// bank system that contains account info for people

	public enum transactionState {none, processing};											// transaction state

	// constructor
	public BankTellerRole(BankComputer bank) {
		setBankSystem(bank);
	}

	// messages

	public void msgINeedTransaction(BankCustomerRole bc, BankTransaction bt) {
		customers.add(new MyCustomer(bc, bt));
	}

	// scheduler

	public boolean pickAndExecuteAnAction() {
		synchronized(customers) {
			if (!customers.isEmpty()) {
				if (customers.get(0).getTransactionState() == transactionState.none) {
					
					if (customers.get(0).getBankTransaction().getTransactionType() == transactionType.openAccount) {
						customers.get(0).setTransactionState(transactionState.processing);
						int tempAccountNumber = bank.addAccountAndReturnNumber(customers.get(0).getBankCustomer(),
								customers.get(0).getBankTransaction().getPinCode(),
								customers.get(0).getBankTransaction().getAmountProcessed());
						BankAccount account = bank.account(tempAccountNumber);
						AddAccount(customers.get(0), account);
						return true;
					}

					else if (customers.get(0).getBankTransaction().getTransactionType() == transactionType.depositMoney) {
						customers.get(0).setTransactionState(transactionState.processing);
						BankAccount account = bank.accountLookup(customers.get(0).getBankTransaction().getAccountID());
						account.setAccountBalance(account.getAccountBalance() + 
								customers.get(0).getBankTransaction().getAmountProcessed());
						bank.updateSystemAccount(account);
						DepositMoney(customers.get(0), account);
						return true;
					}

					else if (customers.get(0).getBankTransaction().getTransactionType() == transactionType.withdrawMoney) {
						customers.get(0).setTransactionState(transactionState.processing);
						BankAccount account = bank.accountLookup(customers.get(0).getBankTransaction().getAccountID());
						account.setAccountBalance(account.getAccountBalance() - 
								customers.get(0).getBankTransaction().getAmountProcessed());
						bank.updateSystemAccount(account);
						WithdrawMoney(customers.get(0), account);
						return true;
					}

					else if (customers.get(0).getBankTransaction().getTransactionType() == transactionType.withdrawMoney) {
						customers.get(0).setTransactionState(transactionState.processing);
						BankAccount account = bank.accountLookup(customers.get(0).getBankTransaction().getAccountID());
						if (account.getAccountNumber() > 0.5 * 
								customers.get(0).getBankTransaction().getAmountProcessed()) {	// RULE FOR LOAN: Loan is at max twice of account
							account.setAmountOwed(account.getAmountOwed() + 
									customers.get(0).getBankTransaction().getAmountProcessed());
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
		account.getBankCustomer().msgMoneyIsDeposited(account.getBankCustomer(), account.getAccountNumber(), 
				account.getAccountBalance(), customer.getBankTransaction().getAmountProcessed());
		customers.remove(customer);
	}

	private void WithdrawMoney(MyCustomer customer, BankAccount account) {
		System.out.println("I've withdrawn your money");
		account.getBankCustomer().msgHereIsMoney(account.getBankCustomer(), account.getAccountNumber(), account.getAccountBalance(),
			customer.getBankTransaction().getAmountProcessed());
		customers.remove(customer);
	}

	private void CanGrantLoan(MyCustomer customer, BankAccount account) {
		System.out.println("Your loan is approved");
		account.getBankCustomer().msgHereIsYourLoan(account.getBankCustomer(), account.getAccountNumber(), account.getAmountOwed(),
			customer.getBankTransaction().getAmountProcessed());
		customers.remove(customer);
	}

	private void CannotGrantLoan(MyCustomer customer, BankAccount account) {
		System.out.println("Your loan is not approved");
		account.getBankCustomer().msgCannotGrantLoan(account.getBankCustomer(), account.getAccountNumber(), account.getAmountOwed(),
			customer.getBankTransaction().getAmountProcessed());
		customers.remove(customer);
	}

	// animation DoXYZ

	// utility classes
	public class MyCustomer {
		BankCustomerRole bc;
		BankTransaction bt;
		transactionState ts;

		MyCustomer(BankCustomerRole bc, BankTransaction bt) {
			this.bc = bc;
			this.bt = bt;
			this.ts = transactionState.none;
		}

		public BankCustomerRole getBankCustomer() {
			return bc;
		}

		public void setBankCustomer(BankCustomerRole bc) {
			this.bc = bc;
		}

		public BankTransaction getBankTransaction() {
			return bt;
		}
		
		public void setBankTransaction(BankTransaction bt) {
			this.bt = bt;
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
	public void msgWantToOpenAccount(BankCustomerRole bc, double amountToProcess) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgWantToDeposit(BankCustomerRole bc, double amountToProcess) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgWantToWithdraw(BankCustomerRole bc, double amountToProcess) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgWantALoan(BankCustomerRole bc, double amountToProcess) {
		// TODO Auto-generated method stub
		
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
