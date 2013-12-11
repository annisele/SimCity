package simcity.test.bank.mock;

import simcity.SimSystem;
import simcity.buildings.bank.BankCustomerRole;
import simcity.buildings.bank.BankTellerRole;
import simcity.interfaces.bank.BankCustomer;
import simcity.interfaces.bank.BankHost;
import simcity.interfaces.bank.BankRobber;
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
	public void msgLeavingBank(int windowNumber) {
		log.add(new LoggedEvent("Received message msgLeavingBank from bank customer " + windowNumber));
		
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

	@Override
	public void msgEnteringBank(BankCustomer bc) {
		log.add(new LoggedEvent("Received message msgEnteringBank from bank customer " + bc));
	}

	@Override
	public void msgImReadyToWork(BankTeller bt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgExitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgEnterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgRobBank(BankRobber bankRobber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgLeaveWork() {
		// TODO Auto-generated method stub
		
	}
	
	
}