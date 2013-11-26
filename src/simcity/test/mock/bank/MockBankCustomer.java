package simcity.test.mock.bank;

import simcity.SimSystem;
import simcity.buildings.bank.BankCustomerRole.Event;
import simcity.buildings.bank.BankCustomerRole.State;
import simcity.buildings.bank.BankCustomerRole.TransactionType;
import simcity.buildings.bank.BankSystem;
import simcity.interfaces.*;
import simcity.interfaces.bank.BankCustomer;
import simcity.interfaces.bank.BankTeller;
import simcity.test.mock.*;

public class MockBankCustomer extends Mock implements BankCustomer {

	public MockBankCustomer(String name) {
		super(name);
	}
	public EventLog log = new EventLog();
	@Override
	public void atDestination() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgArrivedAtBank() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgGoToWindow(int windowNumber, BankTeller bt) {
		log.add(new LoggedEvent("Received message msgGoToWindow from bank host " + windowNumber));
		
	}

	@Override
	public void msgHereIsAccountInfo(BankCustomer bc, int accountNumber,
			double accountBalance) {
		
		log.add(new LoggedEvent("Received message msgHereIsAccountInfo from bank teller for account number " + accountNumber + " account balance" + accountBalance));
	}

	@Override
	public void msgHereIsMoney(BankCustomer bc, int accountNumber,
			double accountBalance, double amountProcessed) {
		log.add(new LoggedEvent("Received message msgHereIsMoney from bank teller for account number : " + accountNumber + " account balance : " + accountBalance + "amount processed : " + amountProcessed ));
	}

	@Override
	public void msgNotEnoughMoneyToWithdraw(BankCustomer bc, int accountNumber,
			double accountBalance, double amountProcessed) {
		log.add(new LoggedEvent("Received message msgNotEnoughMoneyToWithdraw from bank teller for account number : " + accountNumber + " account balance : " + accountBalance + "amount processed : " + amountProcessed ));
	}

	@Override
	public void msgMoneyIsDeposited(BankCustomer bc, int accountNumber,
			double accountBalance, double amountProcessed) {
		log.add(new LoggedEvent("Received message msgMoneyIsDeposited from bank teller for account number : " + accountNumber + " account balance : " + accountBalance + "amount processed : " + amountProcessed ));
	}

	@Override
	public void msgHereIsYourLoan(BankCustomer bc, int accountNumber,
			double accountBalance, double amountProcessed) {
		log.add(new LoggedEvent("Received message msgHereIsYourLoan from bank teller for account number : " + accountNumber + " account balance : " + accountBalance + "amount processed : " + amountProcessed ));
	}

	@Override
	public void msgCannotGrantLoan(BankCustomer bc, int accountNumber,
			double accountBalance, double loanAmount) {
		log.add(new LoggedEvent("Received message msgCannotGrantLoan from bank teller for account number : " + accountNumber + " loan amount " + loanAmount ));
	}

	@Override
	public void msgLoanIsCompletelyRepaid(BankCustomer bc, int accountNumber,
			double amountOwed, double amountProcessed, double actualPaid) {
		log.add(new LoggedEvent("Received message msgLoanIsCompletelyRepaid from bank teller for account number : " + accountNumber + " amount paid : " + actualPaid + "amount processed : " + amountProcessed ));
	}

	@Override
	public void msgLoanIsPartiallyRepaid(BankCustomer bc, int accountNumber,
			double amountOwed, double amountProcessed) {
		log.add(new LoggedEvent("Received message msgHereIsMoney from bank teller for account number : " + accountNumber + " amount owed : " + amountOwed + "amount processed : " + amountProcessed ));
	}

	@Override
	public void msgRentIsPaid(BankCustomer bc, int accountNumber,
			double amountProcessed) {
		log.add(new LoggedEvent("Received message msgRentIsPaid from bank teller for account number : " + accountNumber  + "amount processed : " + amountProcessed ));
	}

	@Override
	public void msgVerificationFailed() {
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
	public void msgWithdrawMoney(BankSystem b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgDepositMoney(BankSystem b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgPayRent(BankSystem b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCustomerName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAccountNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLandlordAccountNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void msgGetLoan(BankSystem b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TransactionType getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public State getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Event getEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTransactionType(TransactionType tt) {
		// TODO Auto-generated method stub
		
	}
	
}

