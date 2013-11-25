package simcity.interfaces.bank;

import simcity.SimSystem;
import simcity.buildings.bank.BankSystem;
import simcity.interfaces.GuiPartner;

public interface BankCustomer extends GuiPartner {
	
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
	
	public abstract void msgExitBuilding();
	public abstract void msgEnterBuilding(SimSystem s);
	public abstract void msgWithdrawMoney(BankSystem b);
	public abstract void msgDepositMoney(BankSystem b);
	public abstract void msgPayRent(BankSystem b);
	public abstract String getCustomerName();
	public abstract int getAccountNumber();
	public abstract String getPassword();
	public abstract int getLandlordAccountNumber();
	public abstract void msgGetLoan(BankSystem b);
	
}
