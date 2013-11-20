package simcity.interfaces.bank;

import simcity.buildings.bank.BankCustomerRole;

public interface BankTeller {
	public abstract void msgWantToOpenAccount(BankCustomerRole bc, double amountToProcess) ;
	public abstract void msgWantToDeposit(BankCustomerRole bc, double amountToProcess);
	public abstract void msgWantToWithdraw(BankCustomerRole bc, double amountToProcess);
	public abstract void msgWantALoan(BankCustomerRole bc, double amountToProcess);
}
