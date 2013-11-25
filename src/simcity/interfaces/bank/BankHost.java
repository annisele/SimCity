package simcity.interfaces.bank;

import simcity.buildings.bank.BankCustomerRole;
import simcity.buildings.bank.BankTellerRole;
import simcity.gui.bank.BankHostGui;

public interface BankHost {
	public abstract void msgEnteringBank(BankCustomerRole bc);
	public abstract void msgLeavingBank(int windowNumber);
	public abstract void msgImReadyToWork(BankTellerRole bt);
	
	public String getName();
	public void setName(String name);
	public void msgExitBuilding();
	public void msgEnterBuilding();
	public void addBankTeller(BankTeller b);
}
