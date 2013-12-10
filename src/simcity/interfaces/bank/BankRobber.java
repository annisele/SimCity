package simcity.interfaces.bank;

import simcity.SimSystem;
import simcity.buildings.bank.BankSystem;
import simcity.buildings.bank.BankCustomerRole.Event;
import simcity.buildings.bank.BankCustomerRole.State;
import simcity.buildings.bank.BankCustomerRole.TransactionType;
import simcity.gui.bank.BankCustomerGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.interfaces.GuiPartner;

public abstract interface BankRobber extends GuiPartner {
    public abstract void atDestination();
	public abstract String getCustomerName() ;
	
	
	
	public abstract BankSystem getBankSystem();
	public abstract void setBankSystem(BankSystem bank);
	
	public abstract BankHost getBankHost() ;
	public abstract void setBankHost(BankHost bh);
	
	public abstract BankTeller getBankTeller();
	public abstract void setBankTeller(BankTeller bt);
	//messages 
	public abstract void msgArrivedAtBank() ;
	//scheduler;;
	   // actions
	

		public abstract void exitBuilding() ;

		public abstract void enterBuilding(SimSystem s);
		public abstract void hackRobBank(BankSystem bankSystem);
		public abstract void msgDontRobBank(int windowNumber,
				BankTeller bankTeller);
		public abstract void msgHereIsYourMoney(double robbery_money);
}