package simcity.test.mock.bank;

import simcity.SimSystem;
import simcity.buildings.bank.BankCustomerRole;
import simcity.buildings.bank.BankTellerRole;
import simcity.interfaces.bank.BankHost;
import simcity.interfaces.bank.BankTeller;
import simcity.test.mock.EventLog;
import simcity.test.mock.LoggedEvent;
import simcity.test.mock.Mock;

public class MockBankHost extends Mock implements BankHost {

	public MockBankHost(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	public EventLog log = new EventLog();
	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgEnteringBank(BankCustomerRole bc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgLeavingBank(int windowNumber) {
		log.add(new LoggedEvent("Received message msgLeavingBank from bank customer " + windowNumber));
		
	}

	@Override
	public void msgImReadyToWork(BankTellerRole bt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addBankTeller(BankTeller b) {
		// TODO Auto-generated method stub
		
	}
	
	
}