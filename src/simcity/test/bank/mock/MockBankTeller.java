package simcity.test.bank.mock;

import java.util.List;

import simcity.SimSystem;
import simcity.buildings.bank.BankHostRole;
import simcity.interfaces.*;
import simcity.interfaces.bank.BankCustomer;
import simcity.interfaces.bank.BankRobber;
import simcity.interfaces.bank.BankTeller;
import simcity.test.mock.EventLog;
import simcity.test.mock.LoggedEvent;
import simcity.test.mock.Mock;

public class MockBankTeller extends Mock implements BankTeller {

	public MockBankTeller(String name) {
		super(name);
	}
	public EventLog log = new EventLog();
	@Override
	public void atDestination() {
		
	}

	@Override
	public void msgWantToOpenAccount(BankCustomer bc, double amountToProcess) {
		log.add(new LoggedEvent("Received message msgWantToOpenAccount from bank customer opening account and deposit" + amountToProcess));
	}

	@Override
	public void msgWantToDeposit(BankCustomer bc, double amountToProcess) {
		log.add(new LoggedEvent("Received message msgWantToDeposit from bank customer " + amountToProcess));
	}

	@Override
	public void msgWantToWithdraw(BankCustomer bc, double amountToProcess) {
		log.add(new LoggedEvent("Received message msgWantToWithdraw from bank customer " + amountToProcess));
	}

	@Override
	public void msgWantALoan(BankCustomer bc, double amountToProcess) {
		log.add(new LoggedEvent("Received message msgWantALoan from bank customer " + amountToProcess));
	}

	@Override
	public void msgWantToPayLoan(BankCustomer bc, double amountToProcess) {
		log.add(new LoggedEvent("Received message msgWantToPayLoan from bank customer " + amountToProcess));
	}

	@Override
	public void msgWantToPayRent(BankCustomer bc, double amountToProcess) {
		log.add(new LoggedEvent("Received message msgWantToPayRent from bank customer " + amountToProcess));
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
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterBuilding(SimSystem s) {
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

	@Override
	public void msgGoToThisWindow(int windowNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List getCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getCustomerInDebt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void msgShowMeTheMoney(BankRobber br, int money) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgFinishWorking() {
		// TODO Auto-generated method stub
		
	}
	
	
}

