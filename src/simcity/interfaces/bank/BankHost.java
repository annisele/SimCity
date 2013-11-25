package simcity.interfaces.bank;

import simcity.SimSystem;
import simcity.buildings.bank.BankCustomerRole;
import simcity.buildings.bank.BankSystem;
import simcity.buildings.bank.BankTellerRole;
import simcity.gui.bank.BankTellerGui;
import simcity.interfaces.GuiPartner;

public interface BankHost extends GuiPartner {
	public abstract void msgEnteringBank(BankCustomerRole bc);
	public abstract void msgLeavingBank(int windowNumber);
	public abstract void msgImReadyToWork(BankTellerRole bt);
	
	public abstract void msgExitBuilding();
	public abstract void msgEnterBuilding(SimSystem s);
	public abstract void addBankTeller(BankTeller b);
}
