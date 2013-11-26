package simcity.interfaces.bank;

import simcity.SimSystem;
import simcity.buildings.bank.BankCustomerRole;
import simcity.buildings.bank.BankSystem;
import simcity.buildings.bank.BankTellerRole;
import simcity.buildings.bank.BankHostRole.BankWindow;
import simcity.gui.bank.BankHostGui;
import simcity.gui.bank.BankTellerGui;
import simcity.interfaces.GuiPartner;

public interface BankHost extends GuiPartner {
	
	public abstract void msgEnteringBank(BankCustomer bc);
	public abstract void msgLeavingBank(int windowNumber);
	public abstract void msgImReadyToWork(BankTeller bt);
	public abstract void exitBuilding();
	public abstract void enterBuilding(SimSystem s);
	public abstract void addBankTeller(BankTeller b);
	public abstract void atDestination();
	public abstract void msgExitBuilding();
	public abstract void msgEnterBuilding(SimSystem s);
}
