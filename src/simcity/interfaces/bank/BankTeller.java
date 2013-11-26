package simcity.interfaces.bank;

import simcity.SimSystem;
import simcity.buildings.bank.BankComputer;
import simcity.buildings.bank.BankHostRole;
import simcity.buildings.bank.BankSystem;
import simcity.buildings.bank.BankTellerRole.MyCustomer;
import simcity.buildings.bank.BankTellerRole.transactionType;
import simcity.gui.bank.BankTellerGui;
import simcity.interfaces.GuiPartner;

public interface BankTeller extends GuiPartner {
	public abstract void atDestination();
	public abstract void msgWantToOpenAccount(BankCustomer bc, double amountToProcess);
	public abstract void msgWantToDeposit(BankCustomer bc, double amountToProcess);
	public abstract void msgWantToWithdraw(BankCustomer bc, double amountToProcess);
	public abstract void msgWantALoan(BankCustomer bc, double amountToProcess);
	public abstract void msgWantToPayLoan(BankCustomer bc, double amountToProcess);
	public abstract void msgWantToPayRent(BankCustomer bc, double amountToProcess);
	public abstract void msgTransactionProcessed();
	
	public abstract String getName();
	public abstract void setName(String name);
	public abstract void exitBuilding();
	public abstract void enterBuilding(SimSystem s);
	public abstract void setHost(BankHostRole b);
}
