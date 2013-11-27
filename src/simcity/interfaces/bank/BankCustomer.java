package simcity.interfaces.bank;

import simcity.SimSystem;
import simcity.buildings.bank.BankSystem;
import simcity.buildings.bank.BankCustomerRole.Event;
import simcity.buildings.bank.BankCustomerRole.State;
import simcity.buildings.bank.BankCustomerRole.TransactionType;
import simcity.gui.bank.BankCustomerGui;
import simcity.interfaces.GuiPartner;

public abstract interface BankCustomer extends GuiPartner {
	
    public abstract void atDestination();
	public abstract String getCustomerName();
	public abstract int getAccountNumber();
	public abstract void setAccountNumber(int accountNumber);
	public abstract String getPassword();
	public abstract double getCashOnHand();
	public abstract void setCashOnHand(double cash);
	public abstract double getAmountToProcess();
	public abstract void setAmountToProcess(double amount);
	public abstract int getLandlordAccountNumber();
	public abstract void setLandlordAccountNumber(int landlordNumber);
	public abstract TransactionType getTransactionType();
	public abstract void setTransactionType(TransactionType tt);
	public abstract State getState();
	public abstract Event getEvent();
	public abstract BankSystem getBankSystem();
	public abstract BankHost getBankHost();
	public abstract void setBankHost(BankHost bh);
	public abstract void msgDepositMoney(BankSystem b);
	public abstract void msgArrivedAtBank();
	public abstract void msgGoToWindow(int windowNumber, BankTeller bt);
	public abstract void msgHereIsAccountInfo(BankCustomer bc, int accountNumber, double accountBalance);
	public abstract void msgHereIsMoney(BankCustomer bc, int accountNumber, double accountBalance, double amountProcessed);
	public abstract void msgNotEnoughMoneyToWithdraw(BankCustomer bc, int accountNumber, double accountBalance, double amountProcessed);
	public abstract void msgMoneyIsDeposited(BankCustomer bc, int accountNumber, double accountBalance, double amountProcessed);
	public abstract void msgHereIsYourLoan(BankCustomer bc, int accountNumber, double accountBalance, double amountProcessed);
	public abstract void msgCannotGrantLoan(BankCustomer bc, int accountNumber, double accountBalance, double loanAmount);
	public abstract void msgLoanIsCompletelyRepaid(BankCustomer bc, int accountNumber, double amountOwed, double amountProcessed, 
			 double actualPaid);
	public abstract void msgLoanIsPartiallyRepaid(BankCustomer bc, int accountNumber, double amountOwed, double amountProcessed);
	public abstract void msgRentIsPaid(BankCustomer bc, int accountNumber, double amountProcessed);
	public abstract void msgVerificationFailed();
	public abstract void exitBuilding();
	public abstract void enterBuilding(SimSystem s);
	public abstract void msgWithdrawMoney(BankSystem b);
	public abstract void msgPayRent(BankSystem b);
	public abstract void msgGetLoan(BankSystem b);
	
}
