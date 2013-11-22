package simcity.interfaces.bank;

import simcity.buildings.bank.BankCustomerRole;
import simcity.buildings.bank.BankTellerRole;

public interface BankCustomer {
	
	public abstract void msgGoToWindow(int windowNumber, BankTellerRole bt);
	public abstract void msgHereIsAccountInfo(BankCustomerRole bc, int accountNumber, double accountBalance);
	public abstract void msgHereIsMoney(BankCustomerRole bc, int accountNumber, double accountBalance, double amountProcessed);
	public abstract void msgMoneyIsDeposited(BankCustomerRole bc, int accountNumber, double accountBalance, double amountProcessed) ;
	public abstract void msgHereIsYourLoan(BankCustomerRole bc, int accountNumber, double accountBalance, double amountProcessed);
	public abstract void msgCannotGrantLoan(BankCustomerRole bc, int accountNumber, double accountBalance, double loanAmount);
}
