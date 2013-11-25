package simcity.interfaces.bank;

import simcity.buildings.bank.BankComputer;
import simcity.buildings.bank.BankCustomerRole;
import simcity.buildings.bank.BankHostRole;
import simcity.buildings.bank.BankTellerRole.MyCustomer;
import simcity.buildings.bank.BankTellerRole.transactionType;
import simcity.gui.bank.BankTellerGui;

public interface BankTeller {
	public abstract void msgWantToOpenAccount(BankCustomer bc, double amountToProcess);
	public abstract void msgWantToDeposit(BankCustomer bc, double amountToProcess);
	public abstract void msgWantToWithdraw(BankCustomer bc, double amountToProcess);
	public abstract void msgWantALoan(BankCustomer bc, double amountToProcess);
	public abstract void msgWantToPayLoan(BankCustomer bc, double amountToProcess);
	public abstract void msgWantToPayRent(BankCustomer bc, double amountToProcess);
	
	public String getName();
	public void setName(String name);
	public BankComputer getBankSystem();
	public void setBankSystem(BankComputer bank);
	public void msgExitBuilding();
	public void msgEnterBuilding();
	public void setHost(BankHostRole b);
}
