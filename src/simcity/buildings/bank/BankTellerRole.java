package simcity.buildings.bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import simcity.interfaces.bank.BankCustomer;

public class BankTellerRole implements simcity.interfaces.bank.BankTeller {

	// data
	private List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());		// list of customers
	BankSystem bank;	// bank system that contains account info for people

	public enum transactionType {none, openAccount, depositMoney, withdrawMoney, loanMoney};
	public enum transactionState {none, processing};

	// constructor

	// messages

	public void msgWantToOpenAccount(BankCustomer bc, double amountToProcess) {
    	customers.add(new MyCustomer(bc, amountToProcess, transactionType.openAccount));
    	//stateChanged();
	}

	public void msgWantToDeposit(BankCustomer bc, double amountToProcess) {
    	customers.add(new MyCustomer(bc, amountToProcess, transactionType.depositMoney));
    	// stateChanged();
	}

	public void msgWantToWithdraw(BankCustomer bc, double amountToProcess) {
    	customers.add(new MyCustomer(bc, amountToProcess, transactionType.withdrawMoney));
    	// stateChanged();
	}

	public void msgWantALoan(BankCustomer bc, double amountToProcess) {
    	customers.add(new MyCustomer(bc, amountToProcess, transactionType.loanMoney));
    	// stateChanged();
	}

	// scheduler

	// actions

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

}
