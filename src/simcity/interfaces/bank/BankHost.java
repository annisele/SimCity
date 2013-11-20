package simcity.interfaces.bank;

import simcity.buildings.bank.BankCustomerRole;

public interface BankHost {
	public abstract void msgEnteringBank(BankCustomerRole bc);
	public abstract void msgLeavingBank(int windowNumber);
}
