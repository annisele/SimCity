package simcity.interfaces.bank;

import simcity.SimSystem;
import simcity.buildings.bank.BankComputer;
import simcity.buildings.bank.BankHostRole;
import simcity.buildings.bank.BankSystem;
import simcity.gui.bank.BankTellerGui;
import simcity.interfaces.GuiPartner;

public interface BankTeller extends GuiPartner {
	public abstract void msgWantToOpenAccount(BankCustomer bc, double amountToProcess);
	public abstract void msgWantToDeposit(BankCustomer bc, double amountToProcess);
	public abstract void msgWantToWithdraw(BankCustomer bc, double amountToProcess);
	public abstract void msgWantALoan(BankCustomer bc, double amountToProcess);
	public abstract void msgWantToPayLoan(BankCustomer bc, double amountToProcess);
	public abstract void msgWantToPayRent(BankCustomer bc, double amountToProcess);
	
	public abstract String getName();
	public abstract void setName(String name);
	public abstract BankComputer getBankSystem();
	public abstract void setBankSystem(BankComputer bank);
	public abstract void msgExitBuilding();
	public abstract void msgEnterBuilding(SimSystem s);
	public abstract void setHost(BankHostRole b);
}
