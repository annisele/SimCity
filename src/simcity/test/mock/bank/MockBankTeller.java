package simcity.test.mock.bank;

import simcity.SimSystem;
import simcity.buildings.bank.BankHostRole;
import simcity.interfaces.*;
import simcity.interfaces.bank.BankCustomer;
import simcity.interfaces.bank.BankTeller;
import simcity.test.mock.EventLog;
import simcity.test.mock.Mock;

public class MockBankTeller extends Mock implements BankTeller {

	public MockBankTeller(String name) {
		super(name);
	}
	public EventLog log = new EventLog();
	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgWantToOpenAccount(BankCustomer bc, double amountToProcess) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgWantToDeposit(BankCustomer bc, double amountToProcess) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgWantToWithdraw(BankCustomer bc, double amountToProcess) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgWantALoan(BankCustomer bc, double amountToProcess) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgWantToPayLoan(BankCustomer bc, double amountToProcess) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgWantToPayRent(BankCustomer bc, double amountToProcess) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgTransactionProcessed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setName(String name) {
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
	public void setHost(BankHostRole b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

