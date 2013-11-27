package simcity.test.bank;

import junit.framework.TestCase;
import simcity.PersonAgent;
import simcity.buildings.bank.BankCustomerRole;
import simcity.buildings.bank.BankCustomerRole.Event;
import simcity.buildings.bank.BankCustomerRole.State;
import simcity.buildings.bank.BankCustomerRole.TransactionType;
import simcity.buildings.bank.BankSystem;
import simcity.gui.SimCityGui;
import simcity.test.bank.mock.MockBankHost;
import simcity.test.bank.mock.MockBankTeller;

public class BankCustomerTest extends TestCase {
	
	BankCustomerRole bankCustomer;
	MockBankHost bankHost;
	MockBankTeller bankTeller;
	PersonAgent person;
	BankSystem bankSystem;
	SimCityGui scg;
	
	public void setUp() throws Exception {
		super.setUp();
		person = new PersonAgent("PersonAgent");
		bankCustomer = new BankCustomerRole(person);
		bankHost = new MockBankHost("mockbankhost");
		bankTeller = new MockBankTeller("mockbankteller");
		scg = new SimCityGui();
		bankSystem = new BankSystem(scg);
	}
	
	// one customer simply wants to open an account
	public void testOneCustomerOpenAccount() {
		System.out.println("testOneCustomerOpenAccount");
		System.out.println("");
		// setup
		bankCustomer.setPassword("abcde");
		bankCustomer.setCashOnHand(100.00);
		bankCustomer.setAmountToProcess(100.00);
		bankCustomer.setTransactionType(TransactionType.openAccount);
		bankCustomer.setBankHost(bankHost);
		bankCustomer.setBankSystem(bankSystem);
		bankSystem.setBankHost(bankHost);
		bankSystem.setName("BANK1");
		person.setCurrentRole(bankCustomer);
		
		// check setup postconditions
		assertEquals("BankCustomer password should be abcde", bankCustomer.getPassword(), "abcde");
		assertEquals("BankCustomer cash on hand should be 100", bankCustomer.getCashOnHand(), 100.00);
		assertEquals("BankCustomer amount to process should be 100", bankCustomer.getAmountToProcess(), 100.00);
		assertEquals("BankCustomer transaction type should be openAccount", bankCustomer.getTransactionType(), TransactionType.openAccount);
		assertEquals("BankCustomer bank host should be bankHost", bankCustomer.getBankHost(), bankHost);
		assertEquals("BankCustomer bank system should be bankSystem", bankCustomer.getBankSystem(), bankSystem);
		assertEquals("BankCustomer bank system host should be bankHost", bankCustomer.getBankSystem().getBankHost(), bankHost);
		assertEquals("BankCustomer bank system name should be banksystem", bankCustomer.getBankSystem().getName(), "BANK1");
		assertEquals("BankCustomer state should be none", bankCustomer.getState(), State.none);
		assertEquals("BankCustomer event should be none", bankCustomer.getEvent(), Event.none);
		
		// step 1 - customer arrives to bank
		bankCustomer.msgArrivedAtBank();
		
		// check step 1 postconditions
		assertEquals("BankCustomer state should be none", bankCustomer.getState(), State.none);
		assertEquals("BankCustomer event should be arrivedAtBank", bankCustomer.getEvent(), Event.arrivedAtBank);
		
		// step 2 - call scheduler, customer messages bankhost of arrival
		bankCustomer.atDestination();
		assertTrue("BankCustomer scheduler should return true", bankCustomer.pickAndExecuteAnAction());
		
		// check step 2 postconditions
		assertEquals("BankCustomer state should be waitingAtBank", bankCustomer.getState(), State.waitingAtBank);
		assertEquals("BankCustomer event should be arrivedAtBank", bankCustomer.getEvent(), Event.arrivedAtBank);
		assertTrue("Mockbankhost should have received msgEnteringBank from customer", bankHost.log.containsString("Received message msgEnteringBank from bank customer " + bankCustomer));
		
		// step 3 - bankhost messages customer to go to a certain window
		bankCustomer.msgGoToWindow(1, bankTeller);
		
		// check step 3 postconditions
		assertEquals("BankCustomer state should be waitingAtBank", bankCustomer.getState(), State.waitingAtBank);
		assertEquals("BankCustomer event should be directedToWindow", bankCustomer.getEvent(), Event.directedToWindow);
		assertEquals("BankCustomer window number should be 1", bankCustomer.getWindowNumber(), 1);
		assertEquals("BankCustomer bank teller should be bankTeller", bankCustomer.getBankTeller(), bankTeller);
		
		// step 4 - call scheduler, customer messages bank teller of transaction
		bankCustomer.atDestination();
		assertTrue("BankCustomer scheduler should return true", bankCustomer.pickAndExecuteAnAction());
		
		// check step 4 postconditions
		assertEquals("BankCustomer state should be goingToWindow", bankCustomer.getState(), State.goingToWindow);
		assertEquals("BankCustomer event should be directedToWindow", bankCustomer.getEvent(), Event.directedToWindow);
		assertTrue("Mockbankteller should have received msgWantToOpenAccount from customer", bankTeller.log.containsString("Received message msgWantToOpenAccount from bank customer opening account and deposit" + 100.00));
		
		// step 5 - bankteller messages customer when transaction is complete
		bankCustomer.msgHereIsAccountInfo(bankCustomer, 1, 100);
		
		// check step 5 postconditions
		assertEquals("BankCustomer state should be goingToWindow", bankCustomer.getState(), State.goingToWindow);
		assertEquals("BankCustomer event should be transactionProcessed", bankCustomer.getEvent(), Event.transactionProcessed);
		assertEquals("BankCustomer should now have a set account number to 1", bankCustomer.getAccountNumber(), 1);
		assertEquals("BankCustomer cash on hand should be 0 (because of deposit)", bankCustomer.getCashOnHand(), 0.00);
		
		// FINAL step 6 - call scheduler, customer informs bank host that he is leaving
		bankCustomer.atDestination();
		assertTrue("BankCustomer scheduler should return true", bankCustomer.pickAndExecuteAnAction());
		
		// FINAL check step 6 postconditions
		assertEquals("BankCustomer state should be leaving", bankCustomer.getState(), State.leaving);
		assertEquals("BankCustomer event should be left", bankCustomer.getEvent(), Event.left);
		assertTrue("Mockbankhost should have received msgLeavingBank from customer", bankHost.log.containsString("Received message msgLeavingBank from bank customer " + 1));
		assertEquals("BankCustomer cash on hand should be 0", bankCustomer.getCashOnHand(), 0.00);
		assertEquals("BankCustomer account number should be 1", bankCustomer.getAccountNumber(), 1);
		
		System.out.println("");
		System.out.println("testOneCustomerAddAccount done");
		System.out.println("");
	}
	
	// one customer simply wants to deposit money
	public void testOneCustomerDepositMoney() {
		System.out.println("testOneCustomerDepositMoney");
		System.out.println("");
		
		// setup
		bankCustomer.setAccountNumber(1);
		bankCustomer.setPassword("abcde");
		bankCustomer.setCashOnHand(100.00);
		bankCustomer.setAmountToProcess(100.00);
		bankCustomer.setTransactionType(TransactionType.depositMoney);
		bankCustomer.setBankHost(bankHost);
		bankCustomer.setBankSystem(bankSystem);
		bankSystem.setBankHost(bankHost);
		bankSystem.setName("BANK1");
		person.setCurrentRole(bankCustomer);
		bankSystem.getBankComputer().addHackedBankAccount(1, 50.00, 0.00, "abcde");
		
		// check setup postconditions
		assertEquals("BankCustomer account number should be 1", bankCustomer.getAccountNumber(), 1);
		assertEquals("BankCustomer password should be abcde", bankCustomer.getPassword(), "abcde");
		assertEquals("BankCustomer cash on hand should be 100", bankCustomer.getCashOnHand(), 100.00);
		assertEquals("BankCustomer amount to process should be 100", bankCustomer.getAmountToProcess(), 100.00);
		assertEquals("BankCustomer transaction type should be depositMoney", bankCustomer.getTransactionType(), TransactionType.depositMoney);
		assertEquals("BankCustomer bank host should be bankHost", bankCustomer.getBankHost(), bankHost);
		assertEquals("BankCustomer bank system should be bankSystem", bankCustomer.getBankSystem(), bankSystem);
		assertEquals("BankCustomer bank system host should be bankHost", bankCustomer.getBankSystem().getBankHost(), bankHost);
		assertEquals("BankCustomer bank system name should be banksystem", bankCustomer.getBankSystem().getName(), "BANK1");
		assertEquals("BankCustomer state should be none", bankCustomer.getState(), State.none);
		assertEquals("BankCustomer event should be none", bankCustomer.getEvent(), Event.none);
		assertFalse("BankSystem should have a bank account", bankSystem.getBankComputer().getCustomerAccounts().isEmpty());
		assertEquals("BankCustomer should have a bank account 1 with balance 50", bankSystem.getBankComputer().getBalanceAccounts().get(1), 50.00);
		assertEquals("BankCustomer should have a bank account 1 with owed 0", bankSystem.getBankComputer().getOwedAccounts().get(1), 0.00);
		assertEquals("BankCustomer should have a bank account 1 with password abcde", bankSystem.getBankComputer().getPasswordAccounts().get(1), "abcde");
		
		// step 1 - customer arrives to bank
		bankCustomer.msgArrivedAtBank();
		
		// check step 1 postconditions
		assertEquals("BankCustomer state should be none", bankCustomer.getState(), State.none);
		assertEquals("BankCustomer event should be arrivedAtBank", bankCustomer.getEvent(), Event.arrivedAtBank);
		
		// step 2 - call scheduler, customer messages bankhost of arrival
		bankCustomer.atDestination();
		assertTrue("BankCustomer scheduler should return true", bankCustomer.pickAndExecuteAnAction());
		
		// check step 2 postconditions
		assertEquals("BankCustomer state should be waitingAtBank", bankCustomer.getState(), State.waitingAtBank);
		assertEquals("BankCustomer event should be arrivedAtBank", bankCustomer.getEvent(), Event.arrivedAtBank);
		assertTrue("Mockbankhost should have received msgEnteringBank from customer", bankHost.log.containsString("Received message msgEnteringBank from bank customer " + bankCustomer));
		
		// step 3 - bankhost messages customer to go to a certain window
		bankCustomer.msgGoToWindow(1, bankTeller);
		
		// check step 3 postconditions
		assertEquals("BankCustomer state should be waitingAtBank", bankCustomer.getState(), State.waitingAtBank);
		assertEquals("BankCustomer event should be directedToWindow", bankCustomer.getEvent(), Event.directedToWindow);
		assertEquals("BankCustomer window number should be 1", bankCustomer.getWindowNumber(), 1);
		assertEquals("BankCustomer bank teller should be bankTeller", bankCustomer.getBankTeller(), bankTeller);
		
		// step 4 - call scheduler, customer messages bank teller of transaction
		bankCustomer.atDestination();
		assertTrue("BankCustomer scheduler should return true", bankCustomer.pickAndExecuteAnAction());
		
		// check step 4 postconditions
		assertEquals("BankCustomer state should be goingToWindow", bankCustomer.getState(), State.goingToWindow);
		assertEquals("BankCustomer event should be directedToWindow", bankCustomer.getEvent(), Event.directedToWindow);
		assertTrue("Mockbankteller should have received msgWantToDeposit from customer", bankTeller.log.containsString("Received message msgWantToDeposit from bank customer " + 100.00));
		
		// step 5 - bankteller messages customer when transaction is complete
		bankCustomer.msgMoneyIsDeposited(bankCustomer, 1, 150.00, 100.00);
		
		// check step 5 postconditions
		assertEquals("BankCustomer state should be goingToWindow", bankCustomer.getState(), State.goingToWindow);
		assertEquals("BankCustomer event should be transactionProcessed", bankCustomer.getEvent(), Event.transactionProcessed);
		assertEquals("BankCustomer cash on hand should be 0 (because of deposit)", bankCustomer.getCashOnHand(), 0.00);
		
		// FINAL step 6 - call scheduler, customer informs bank host that he is leaving
		bankCustomer.atDestination();
		assertTrue("BankCustomer scheduler should return true", bankCustomer.pickAndExecuteAnAction());
		
		// FINAL check step 6 postconditions
		assertEquals("BankCustomer state should be leaving", bankCustomer.getState(), State.leaving);
		assertEquals("BankCustomer event should be left", bankCustomer.getEvent(), Event.left);
		assertTrue("Mockbankhost should have received msgLeavingBank from customer", bankHost.log.containsString("Received message msgLeavingBank from bank customer " + 1));
		assertEquals("BankCustomer cash on hand should be 0", bankCustomer.getCashOnHand(), 0.00);
		assertEquals("BankCustomer account number should be 1", bankCustomer.getAccountNumber(), 1);
		
		System.out.println("");
		System.out.println("testOneCustomerDepositMoney done");
		System.out.println("");
	}
	
	// one customer simply wants to withdraw money, and successfully does so
	public void testOneCustomerWithdrawMoney() {
		System.out.println("testOneCustomerWithdrawMoney");
		System.out.println("");
		// setup
		bankCustomer.setAccountNumber(1);
		bankCustomer.setPassword("abcde");
		bankCustomer.setCashOnHand(100.00);
		bankCustomer.setAmountToProcess(100.00);
		bankCustomer.setTransactionType(TransactionType.withdrawMoney);
		bankCustomer.setBankHost(bankHost);
		bankCustomer.setBankSystem(bankSystem);
		bankSystem.setBankHost(bankHost);
		bankSystem.setName("BANK1");
		person.setCurrentRole(bankCustomer);
		bankSystem.getBankComputer().addHackedBankAccount(1, 150.00, 0.00, "abcde");
		
		// check setup postconditions
		assertEquals("BankCustomer account number should be 1", bankCustomer.getAccountNumber(), 1);
		assertEquals("BankCustomer password should be abcde", bankCustomer.getPassword(), "abcde");
		assertEquals("BankCustomer cash on hand should be 100", bankCustomer.getCashOnHand(), 100.00);
		assertEquals("BankCustomer amount to process should be 100", bankCustomer.getAmountToProcess(), 100.00);
		assertEquals("BankCustomer transaction type should be withdrawMoney", bankCustomer.getTransactionType(), TransactionType.withdrawMoney);
		assertEquals("BankCustomer bank host should be bankHost", bankCustomer.getBankHost(), bankHost);
		assertEquals("BankCustomer bank system should be bankSystem", bankCustomer.getBankSystem(), bankSystem);
		assertEquals("BankCustomer bank system host should be bankHost", bankCustomer.getBankSystem().getBankHost(), bankHost);
		assertEquals("BankCustomer bank system name should be banksystem", bankCustomer.getBankSystem().getName(), "BANK1");
		assertEquals("BankCustomer state should be none", bankCustomer.getState(), State.none);
		assertEquals("BankCustomer event should be none", bankCustomer.getEvent(), Event.none);
		assertFalse("BankSystem should have a bank account", bankSystem.getBankComputer().getCustomerAccounts().isEmpty());
		assertEquals("BankCustomer should have a bank account 1 with balance 150", bankSystem.getBankComputer().getBalanceAccounts().get(1), 150.00);
		assertEquals("BankCustomer should have a bank account 1 with owed 0", bankSystem.getBankComputer().getOwedAccounts().get(1), 0.00);
		assertEquals("BankCustomer should have a bank account 1 with password abcde", bankSystem.getBankComputer().getPasswordAccounts().get(1), "abcde");
		
		// step 1 - customer arrives to bank
		bankCustomer.msgArrivedAtBank();
		
		// check step 1 postconditions
		assertEquals("BankCustomer state should be none", bankCustomer.getState(), State.none);
		assertEquals("BankCustomer event should be arrivedAtBank", bankCustomer.getEvent(), Event.arrivedAtBank);
		
		// step 2 - call scheduler, customer messages bankhost of arrival
		bankCustomer.atDestination();
		assertTrue("BankCustomer scheduler should return true", bankCustomer.pickAndExecuteAnAction());
		
		// check step 2 postconditions
		assertEquals("BankCustomer state should be waitingAtBank", bankCustomer.getState(), State.waitingAtBank);
		assertEquals("BankCustomer event should be arrivedAtBank", bankCustomer.getEvent(), Event.arrivedAtBank);
		assertTrue("Mockbankhost should have received msgEnteringBank from customer", bankHost.log.containsString("Received message msgEnteringBank from bank customer " + bankCustomer));
		
		// step 3 - bankhost messages customer to go to a certain window
		bankCustomer.msgGoToWindow(1, bankTeller);
		
		// check step 3 postconditions
		assertEquals("BankCustomer state should be waitingAtBank", bankCustomer.getState(), State.waitingAtBank);
		assertEquals("BankCustomer event should be directedToWindow", bankCustomer.getEvent(), Event.directedToWindow);
		assertEquals("BankCustomer window number should be 1", bankCustomer.getWindowNumber(), 1);
		assertEquals("BankCustomer bank teller should be bankTeller", bankCustomer.getBankTeller(), bankTeller);
		
		// step 4 - call scheduler, customer messages bank teller of transaction
		bankCustomer.atDestination();
		assertTrue("BankCustomer scheduler should return true", bankCustomer.pickAndExecuteAnAction());
		
		// check step 4 postconditions
		assertEquals("BankCustomer state should be goingToWindow", bankCustomer.getState(), State.goingToWindow);
		assertEquals("BankCustomer event should be directedToWindow", bankCustomer.getEvent(), Event.directedToWindow);
		assertTrue("Mockbankteller should have received msgWantToWithdraw from customer", bankTeller.log.containsString("Received message msgWantToWithdraw from bank customer " + 100.00));
		
		// step 5 - bankteller messages customer when transaction is complete
		bankCustomer.msgHereIsMoney(bankCustomer, 1, 50.00, 100.00);
		
		// check step 5 postconditions
		assertEquals("BankCustomer state should be goingToWindow", bankCustomer.getState(), State.goingToWindow);
		assertEquals("BankCustomer event should be transactionProcessed", bankCustomer.getEvent(), Event.transactionProcessed);
		assertEquals("BankCustomer cash on hand should be 200.00 (because of withdrawal)", bankCustomer.getCashOnHand(), 200.00);
		
		// FINAL step 6 - call scheduler, customer informs bank host that he is leaving
		bankCustomer.atDestination();
		assertTrue("BankCustomer scheduler should return true", bankCustomer.pickAndExecuteAnAction());
		
		// FINAL check step 6 postconditions
		assertEquals("BankCustomer state should be leaving", bankCustomer.getState(), State.leaving);
		assertEquals("BankCustomer event should be left", bankCustomer.getEvent(), Event.left);
		assertTrue("Mockbankhost should have received msgLeavingBank from customer", bankHost.log.containsString("Received message msgLeavingBank from bank customer " + 1));
		assertEquals("BankCustomer cash on hand should be 200", bankCustomer.getCashOnHand(), 200.00);
		assertEquals("BankCustomer account number should be 1", bankCustomer.getAccountNumber(), 1);
		
		System.out.println("");
		System.out.println("testOneCustomerWithdrawMoney done");
		System.out.println("");
	}
	
	// one customer simply wants to get a loan, and successfully does so
	public void testOneCustomerLoanMoney() {
		System.out.println("testOneCustomerLoanMoney");
		System.out.println("");
		
		// setup
		bankCustomer.setAccountNumber(1);
		bankCustomer.setPassword("abcde");
		bankCustomer.setCashOnHand(100.00);
		bankCustomer.setAmountToProcess(100.00);
		bankCustomer.setTransactionType(TransactionType.loanMoney);
		bankCustomer.setBankHost(bankHost);
		bankCustomer.setBankSystem(bankSystem);
		bankSystem.setBankHost(bankHost);
		bankSystem.setName("BANK1");
		person.setCurrentRole(bankCustomer);
		bankSystem.getBankComputer().addHackedBankAccount(1, 100.00, 0.00, "abcde");
		
		// check setup postconditions
		assertEquals("BankCustomer account number should be 1", bankCustomer.getAccountNumber(), 1);
		assertEquals("BankCustomer password should be abcde", bankCustomer.getPassword(), "abcde");
		assertEquals("BankCustomer cash on hand should be 100", bankCustomer.getCashOnHand(), 100.00);
		assertEquals("BankCustomer amount to process should be 100", bankCustomer.getAmountToProcess(), 100.00);
		assertEquals("BankCustomer transaction type should be loanMoney", bankCustomer.getTransactionType(), TransactionType.loanMoney);
		assertEquals("BankCustomer bank host should be bankHost", bankCustomer.getBankHost(), bankHost);
		assertEquals("BankCustomer bank system should be bankSystem", bankCustomer.getBankSystem(), bankSystem);
		assertEquals("BankCustomer bank system host should be bankHost", bankCustomer.getBankSystem().getBankHost(), bankHost);
		assertEquals("BankCustomer bank system name should be banksystem", bankCustomer.getBankSystem().getName(), "BANK1");
		assertEquals("BankCustomer state should be none", bankCustomer.getState(), State.none);
		assertEquals("BankCustomer event should be none", bankCustomer.getEvent(), Event.none);
		assertFalse("BankSystem should have a bank account", bankSystem.getBankComputer().getCustomerAccounts().isEmpty());
		assertEquals("BankCustomer should have a bank account 1 with balance 100", bankSystem.getBankComputer().getBalanceAccounts().get(1), 100.00);
		assertEquals("BankCustomer should have a bank account 1 with owed 0", bankSystem.getBankComputer().getOwedAccounts().get(1), 0.00);
		assertEquals("BankCustomer should have a bank account 1 with password abcde", bankSystem.getBankComputer().getPasswordAccounts().get(1), "abcde");
		
		// step 1 - customer arrives to bank
		bankCustomer.msgArrivedAtBank();
		
		// check step 1 postconditions
		assertEquals("BankCustomer state should be none", bankCustomer.getState(), State.none);
		assertEquals("BankCustomer event should be arrivedAtBank", bankCustomer.getEvent(), Event.arrivedAtBank);
		
		// step 2 - call scheduler, customer messages bankhost of arrival
		bankCustomer.atDestination();
		assertTrue("BankCustomer scheduler should return true", bankCustomer.pickAndExecuteAnAction());
		
		// check step 2 postconditions
		assertEquals("BankCustomer state should be waitingAtBank", bankCustomer.getState(), State.waitingAtBank);
		assertEquals("BankCustomer event should be arrivedAtBank", bankCustomer.getEvent(), Event.arrivedAtBank);
		assertTrue("Mockbankhost should have received msgEnteringBank from customer", bankHost.log.containsString("Received message msgEnteringBank from bank customer " + bankCustomer));
		
		// step 3 - bankhost messages customer to go to a certain window
		bankCustomer.msgGoToWindow(1, bankTeller);
		
		// check step 3 postconditions
		assertEquals("BankCustomer state should be waitingAtBank", bankCustomer.getState(), State.waitingAtBank);
		assertEquals("BankCustomer event should be directedToWindow", bankCustomer.getEvent(), Event.directedToWindow);
		assertEquals("BankCustomer window number should be 1", bankCustomer.getWindowNumber(), 1);
		assertEquals("BankCustomer bank teller should be bankTeller", bankCustomer.getBankTeller(), bankTeller);
		
		// step 4 - call scheduler, customer messages bank teller of transaction
		bankCustomer.atDestination();
		assertTrue("BankCustomer scheduler should return true", bankCustomer.pickAndExecuteAnAction());
		
		// check step 4 postconditions
		assertEquals("BankCustomer state should be goingToWindow", bankCustomer.getState(), State.goingToWindow);
		assertEquals("BankCustomer event should be directedToWindow", bankCustomer.getEvent(), Event.directedToWindow);
		assertTrue("Mockbankteller should have received msgWantALoan from customer", bankTeller.log.containsString("Received message msgWantALoan from bank customer " + 100.00));
		
		// step 5 - bankteller messages customer when transaction is complete
		bankCustomer.msgHereIsYourLoan(bankCustomer, 1, 100.00, 100.00);
		
		// check step 5 postconditions
		assertEquals("BankCustomer state should be goingToWindow", bankCustomer.getState(), State.goingToWindow);
		assertEquals("BankCustomer event should be transactionProcessed", bankCustomer.getEvent(), Event.transactionProcessed);
		assertEquals("BankCustomer cash on hand should be 200.00 (because of loan)", bankCustomer.getCashOnHand(), 200.00);
		
		// FINAL step 6 - call scheduler, customer informs bank host that he is leaving
		bankCustomer.atDestination();
		assertTrue("BankCustomer scheduler should return true", bankCustomer.pickAndExecuteAnAction());
		
		// FINAL check step 6 postconditions
		assertEquals("BankCustomer state should be leaving", bankCustomer.getState(), State.leaving);
		assertEquals("BankCustomer event should be left", bankCustomer.getEvent(), Event.left);
		assertTrue("Mockbankhost should have received msgLeavingBank from customer", bankHost.log.containsString("Received message msgLeavingBank from bank customer " + 1));
		assertEquals("BankCustomer cash on hand should be 200", bankCustomer.getCashOnHand(), 200.00);
		assertEquals("BankCustomer account number should be 1", bankCustomer.getAccountNumber(), 1);
		
		System.out.println("");
		System.out.println("testOneCustomerLoanMoney done");
		System.out.println("");
	}
	
	// one customer simply wants to pay back part of a loan, and successfully does so
	public void testOneCustomerPayLoan() {
		System.out.println("testOneCustomerPayLoan");
		System.out.println("");
		
		// setup
		bankCustomer.setAccountNumber(1);
		bankCustomer.setPassword("abcde");
		bankCustomer.setCashOnHand(100.00);
		bankCustomer.setAmountToProcess(20.00);
		bankCustomer.setTransactionType(TransactionType.payLoan);
		bankCustomer.setBankHost(bankHost);
		bankCustomer.setBankSystem(bankSystem);
		bankSystem.setBankHost(bankHost);
		bankSystem.setName("BANK1");
		person.setCurrentRole(bankCustomer);
		bankSystem.getBankComputer().addHackedBankAccount(1, 100.00, 100.00, "abcde");
		
		// check setup postconditions
		assertEquals("BankCustomer account number should be 1", bankCustomer.getAccountNumber(), 1);
		assertEquals("BankCustomer password should be abcde", bankCustomer.getPassword(), "abcde");
		assertEquals("BankCustomer cash on hand should be 100", bankCustomer.getCashOnHand(), 100.00);
		assertEquals("BankCustomer amount to process should be 20", bankCustomer.getAmountToProcess(), 20.00);
		assertEquals("BankCustomer transaction type should be payLoan", bankCustomer.getTransactionType(), TransactionType.payLoan);
		assertEquals("BankCustomer bank host should be bankHost", bankCustomer.getBankHost(), bankHost);
		assertEquals("BankCustomer bank system should be bankSystem", bankCustomer.getBankSystem(), bankSystem);
		assertEquals("BankCustomer bank system host should be bankHost", bankCustomer.getBankSystem().getBankHost(), bankHost);
		assertEquals("BankCustomer bank system name should be banksystem", bankCustomer.getBankSystem().getName(), "BANK1");
		assertEquals("BankCustomer state should be none", bankCustomer.getState(), State.none);
		assertEquals("BankCustomer event should be none", bankCustomer.getEvent(), Event.none);
		assertFalse("BankSystem should have a bank account", bankSystem.getBankComputer().getCustomerAccounts().isEmpty());
		assertEquals("BankCustomer should have a bank account 1 with balance 100", bankSystem.getBankComputer().getBalanceAccounts().get(1), 100.00);
		assertEquals("BankCustomer should have a bank account 1 with owed 100", bankSystem.getBankComputer().getOwedAccounts().get(1), 100.00);
		assertEquals("BankCustomer should have a bank account 1 with password abcde", bankSystem.getBankComputer().getPasswordAccounts().get(1), "abcde");
		
		// step 1 - customer arrives to bank
		bankCustomer.msgArrivedAtBank();
		
		// check step 1 postconditions
		assertEquals("BankCustomer state should be none", bankCustomer.getState(), State.none);
		assertEquals("BankCustomer event should be arrivedAtBank", bankCustomer.getEvent(), Event.arrivedAtBank);
		
		// step 2 - call scheduler, customer messages bankhost of arrival
		bankCustomer.atDestination();
		assertTrue("BankCustomer scheduler should return true", bankCustomer.pickAndExecuteAnAction());
		
		// check step 2 postconditions
		assertEquals("BankCustomer state should be waitingAtBank", bankCustomer.getState(), State.waitingAtBank);
		assertEquals("BankCustomer event should be arrivedAtBank", bankCustomer.getEvent(), Event.arrivedAtBank);
		assertTrue("Mockbankhost should have received msgEnteringBank from customer", bankHost.log.containsString("Received message msgEnteringBank from bank customer " + bankCustomer));
		
		// step 3 - bankhost messages customer to go to a certain window
		bankCustomer.msgGoToWindow(1, bankTeller);
		
		// check step 3 postconditions
		assertEquals("BankCustomer state should be waitingAtBank", bankCustomer.getState(), State.waitingAtBank);
		assertEquals("BankCustomer event should be directedToWindow", bankCustomer.getEvent(), Event.directedToWindow);
		assertEquals("BankCustomer window number should be 1", bankCustomer.getWindowNumber(), 1);
		assertEquals("BankCustomer bank teller should be bankTeller", bankCustomer.getBankTeller(), bankTeller);
		
		// step 4 - call scheduler, customer messages bank teller of transaction
		bankCustomer.atDestination();
		assertTrue("BankCustomer scheduler should return true", bankCustomer.pickAndExecuteAnAction());
		
		// check step 4 postconditions
		assertEquals("BankCustomer state should be goingToWindow", bankCustomer.getState(), State.goingToWindow);
		assertEquals("BankCustomer event should be directedToWindow", bankCustomer.getEvent(), Event.directedToWindow);
		assertTrue("Mockbankteller should have received msgWantToPayLoan from customer", bankTeller.log.containsString("Received message msgWantToPayLoan from bank customer " + 20.00));
		
		// step 5 - bankteller messages customer when transaction is complete
		bankCustomer.msgLoanIsPartiallyRepaid(bankCustomer, 1, 80.00, 20.00);
		
		// check step 5 postconditions
		assertEquals("BankCustomer state should be goingToWindow", bankCustomer.getState(), State.goingToWindow);
		assertEquals("BankCustomer event should be transactionProcessed", bankCustomer.getEvent(), Event.transactionProcessed);
		assertEquals("BankCustomer cash on hand should be 80 (because of loan payment)", bankCustomer.getCashOnHand(), 80.00);
		
		// FINAL step 6 - call scheduler, customer informs bank host that he is leaving
		bankCustomer.atDestination();
		assertTrue("BankCustomer scheduler should return true", bankCustomer.pickAndExecuteAnAction());
		
		// FINAL check step 6 postconditions
		assertEquals("BankCustomer state should be leaving", bankCustomer.getState(), State.leaving);
		assertEquals("BankCustomer event should be left", bankCustomer.getEvent(), Event.left);
		assertTrue("Mockbankhost should have received msgLeavingBank from customer", bankHost.log.containsString("Received message msgLeavingBank from bank customer " + 1));
		assertEquals("BankCustomer cash on hand should be 80", bankCustomer.getCashOnHand(), 80.00);
		assertEquals("BankCustomer account number should be 1", bankCustomer.getAccountNumber(), 1);
		
		System.out.println("");
		System.out.println("testOneCustomerPayLoan done");
		System.out.println("");
	}
	
	// one customer simply wants to deposit money, but password verification fails
	public void testOneCustomerDepositMoneyButFailPasswordVerification() {
		System.out.println("testOneCustomerDepositMoneyButFailPasswordVerification");
		System.out.println("");
		
		// setup
		bankCustomer.setAccountNumber(1);
		bankCustomer.setPassword("abcde");
		bankCustomer.setCashOnHand(100.00);
		bankCustomer.setAmountToProcess(100.00);
		bankCustomer.setTransactionType(TransactionType.depositMoney);
		bankCustomer.setBankHost(bankHost);
		bankCustomer.setBankSystem(bankSystem);
		bankSystem.setBankHost(bankHost);
		bankSystem.setName("BANK1");
		person.setCurrentRole(bankCustomer);
		bankSystem.getBankComputer().addHackedBankAccount(1, 50.00, 0.00, "wrong");
		
		// check setup postconditions
		assertEquals("BankCustomer account number should be 1", bankCustomer.getAccountNumber(), 1);
		assertEquals("BankCustomer password should be abcde", bankCustomer.getPassword(), "abcde");
		assertEquals("BankCustomer cash on hand should be 100", bankCustomer.getCashOnHand(), 100.00);
		assertEquals("BankCustomer amount to process should be 100", bankCustomer.getAmountToProcess(), 100.00);
		assertEquals("BankCustomer transaction type should be depositMoney", bankCustomer.getTransactionType(), TransactionType.depositMoney);
		assertEquals("BankCustomer bank host should be bankHost", bankCustomer.getBankHost(), bankHost);
		assertEquals("BankCustomer bank system should be bankSystem", bankCustomer.getBankSystem(), bankSystem);
		assertEquals("BankCustomer bank system host should be bankHost", bankCustomer.getBankSystem().getBankHost(), bankHost);
		assertEquals("BankCustomer bank system name should be banksystem", bankCustomer.getBankSystem().getName(), "BANK1");
		assertEquals("BankCustomer state should be none", bankCustomer.getState(), State.none);
		assertEquals("BankCustomer event should be none", bankCustomer.getEvent(), Event.none);
		assertFalse("BankSystem should have a bank account", bankSystem.getBankComputer().getCustomerAccounts().isEmpty());
		assertEquals("BankCustomer should have a bank account 1 with balance 50", bankSystem.getBankComputer().getBalanceAccounts().get(1), 50.00);
		assertEquals("BankCustomer should have a bank account 1 with owed 0", bankSystem.getBankComputer().getOwedAccounts().get(1), 0.00);
		assertEquals("BankCustomer should have a bank account 1 with password wrong", bankSystem.getBankComputer().getPasswordAccounts().get(1), "wrong");
		
		// step 1 - customer arrives to bank
		bankCustomer.msgArrivedAtBank();
		
		// check step 1 postconditions
		assertEquals("BankCustomer state should be none", bankCustomer.getState(), State.none);
		assertEquals("BankCustomer event should be arrivedAtBank", bankCustomer.getEvent(), Event.arrivedAtBank);
		
		// step 2 - call scheduler, customer messages bankhost of arrival
		bankCustomer.atDestination();
		assertTrue("BankCustomer scheduler should return true", bankCustomer.pickAndExecuteAnAction());
		
		// check step 2 postconditions
		assertEquals("BankCustomer state should be waitingAtBank", bankCustomer.getState(), State.waitingAtBank);
		assertEquals("BankCustomer event should be arrivedAtBank", bankCustomer.getEvent(), Event.arrivedAtBank);
		assertTrue("Mockbankhost should have received msgEnteringBank from customer", bankHost.log.containsString("Received message msgEnteringBank from bank customer " + bankCustomer));
		
		// step 3 - bankhost messages customer to go to a certain window
		bankCustomer.msgGoToWindow(1, bankTeller);
		
		// check step 3 postconditions
		assertEquals("BankCustomer state should be waitingAtBank", bankCustomer.getState(), State.waitingAtBank);
		assertEquals("BankCustomer event should be directedToWindow", bankCustomer.getEvent(), Event.directedToWindow);
		assertEquals("BankCustomer window number should be 1", bankCustomer.getWindowNumber(), 1);
		assertEquals("BankCustomer bank teller should be bankTeller", bankCustomer.getBankTeller(), bankTeller);
		
		// step 4 - call scheduler, customer messages bank teller of transaction
		bankCustomer.atDestination();
		assertTrue("BankCustomer scheduler should return true", bankCustomer.pickAndExecuteAnAction());
		
		// check step 4 postconditions
		assertEquals("BankCustomer state should be goingToWindow", bankCustomer.getState(), State.goingToWindow);
		assertEquals("BankCustomer event should be directedToWindow", bankCustomer.getEvent(), Event.directedToWindow);
		assertTrue("Mockbankteller should have received msgWantToDeposit from customer", bankTeller.log.containsString("Received message msgWantToDeposit from bank customer " + 100.00));
		
		// step 5 - bankteller messages customer when transaction is complete, unfortunately password fails
		bankCustomer.msgVerificationFailed();
		
		// check step 5 postconditions
		assertEquals("BankCustomer state should be goingToWindow", bankCustomer.getState(), State.goingToWindow);
		assertEquals("BankCustomer event should be transactionProcessed", bankCustomer.getEvent(), Event.transactionProcessed);
		assertEquals("BankCustomer cash on hand should be 100.00 (because of failed deposit)", bankCustomer.getCashOnHand(), 100.00);
		
		// FINAL step 6 - call scheduler, customer informs bank host that he is leaving
		bankCustomer.atDestination();
		assertTrue("BankCustomer scheduler should return true", bankCustomer.pickAndExecuteAnAction());
		
		// FINAL check step 6 postconditions
		assertEquals("BankCustomer state should be leaving", bankCustomer.getState(), State.leaving);
		assertEquals("BankCustomer event should be left", bankCustomer.getEvent(), Event.left);
		assertTrue("Mockbankhost should have received msgLeavingBank from customer", bankHost.log.containsString("Received message msgLeavingBank from bank customer " + 1));
		assertEquals("BankCustomer cash on hand should be 100", bankCustomer.getCashOnHand(), 100.00);
		assertEquals("BankCustomer account number should be 1", bankCustomer.getAccountNumber(), 1);
		
		System.out.println("");
		System.out.println("testOneCustomerDepositMoneyButFailedVerification done");
		System.out.println("");
	}
	
	// one customer simply wants to withdraw money, but figures out that there is not enough money in the account
	
	// one customer simply wants to get a loan, but figures out that he may not due to bank loan policy
	
	// one customer wants to open an account, leaves, and comes back to withdraw money
	
	// one customer wants to get a loan, leaves, and comes back to pay back part of the loan
	
	// one customer wants to withdraw, leaves, then comes back to deposit money
}