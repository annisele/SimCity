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
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;

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
		bankSystem.setBankHost(bankHost);
		bankSystem.setName("banksystem");
	}
	
	public void testOpenAccount() {
		// setup
		bankCustomer.setPassword("abcde");
		bankCustomer.setCashOnHand(100.00);
		bankCustomer.setAmountToProcess(100.00);
		bankCustomer.setTransactionType(TransactionType.openAccount);
		person.setCurrentRole(bankCustomer);
	
		// check setup postconditions
		assertEquals("BankCustomer password should be abcde", bankCustomer.getPassword(), "abcde");
		assertEquals("BankCustomer cash on hand should be 100", bankCustomer.getCashOnHand(), 100.00);
		assertEquals("BankCustomer amount to process should be 100", bankCustomer.getAmountToProcess(), 100.00);
		assertEquals("BankCustomer transaction type should be openAccount", bankCustomer.getTransactionType(), TransactionType.openAccount);
		assertEquals("BankCustomer state should be none", bankCustomer.getState(), State.none);
		assertEquals("BankCustomer event should be none", bankCustomer.getEvent(), Event.none);
		assertEquals("PersonAgent current role should be BankCustomerRole", person.getCurrentRole(), bankCustomer);
		
		// step 1 - customer enters building, sets its banksystem, then messages itself that it has arrived
		bankCustomer.enterBuilding(bankSystem);
		
		// check step1 postconditions
		assertEquals("BankCustomer state should be none", bankCustomer.getState(), State.none);
		assertEquals("BankCustomer event should be arrivedAtBank", bankCustomer.getEvent(), Event.arrivedAtBank);
		assertEquals("BankCustomer bank system should be bankSystem", bankCustomer.getBankSystem(), bankSystem);
		
		// step 2 - call scheduler
		bankCustomer.stateChanged();
		assertTrue("BankCustomer scheduler should return true", person.pickAndExecuteAnAction());
		
		// check step2 postconditions
		assertEquals("BankCustomer state should be waitingAtBank", bankCustomer.getState(), State.waitingAtBank);
		assertEquals("BankCustomer event should be arrivedAtBank", bankCustomer.getEvent(), Event.arrivedAtBank);
		assertEquals("BankCustomer ");
		
	}
	
}

/*
        public void testOpenAccount() {
        	// setup
        	customer1.waiter = waiter;
        	customer1.cashier = cashier;
        	waiter.customer = customer1;
        	waiter.cashier = cashier;
        	
        	// check step 1 preconditions
            assertEquals("Cashier should have 0 bills in it. It doesn't.", cashier.bills.size(), 0);
            assertEquals("Cashier should have $10.99 cash on hand. It doesn't.", cashier.getCashOnHand(), 10.99);
            
            // step 1: ask cashier for customer bill
            cashier.msgCustomerWantsBill(customer1, waiter, "Steak");
            
            // check step 1 postconditions and step 2 preconditions
            assertEquals("Mockwaiter should have an empty log. It doesn't.", 0, waiter.log.size());
            assertEquals("Mockcustomer1 should have an empty log. It doesn't.", 0, customer1.log.size());
            assertEquals("Cashier should have 1 bill in it. It doesn't.", cashier.bills.size(), 1);
            assertEquals("Cashier bill should know who the customer is. It doesn't.", cashier.bills.get(0).getCustomer()
            		.getName(), "mockcustomer1");
            assertEquals("Cashier bill should know who the waiter is. It doesn't.", cashier.bills.get(0).getWaiter().getName(),
            		"mockwaiter");
            assertEquals("Cashier bill should know what the order is. It doesn't.", cashier.bills.get(0).getFood(),
            		"Steak");
            assertEquals("Cashier bill should know what the price is. It doesn't.", cashier.bills.get(0).getPrice(),
            		15.99);
            assertEquals("Cashier bill state should be notified. It is not.", cashier.bills.get(0).getState(),
            		billState.notified);
            
            // step 2: call cashier scheduler and send bill to waiter
            assertTrue("Cashier's schedule should have returned true. It didn't.", cashier.pickAndExecuteAnAction());
            
            // check step 2 postconditions and step 3 preconditions
            assertEquals("Cashier bill state should be processing. It is not.", cashier.bills.get(0).getState(),
            		billState.processing);
            assertTrue("Mockwaiter should have gotten a bill. It didn't.", waiter.log.containsString("Received Bill " + 15.99));
            assertTrue("Mockcustomer1 should have gotten a bill. It didn't.", customer1.log.
            		containsString("Received bill from waiter " + 15.99));

            // step 3: customer pays cashier
            cashier.msgHereIsPayment(customer1, 15.99);
            
            // check step 3 postconditions and step 4 preconditions
            assertEquals("Cash on hand have increased to 10.99 + 15.99. It did not", cashier.getCashOnHand(), 26.98);
            assertEquals("Cashier bill state should be paid", cashier.bills.get(0).getState(),
            		billState.paid);
            assertEquals("Mockwaiter should have only 1 logged event. It doesn't.", waiter.log.size(), 1);
            assertEquals("Mockcustomer1 should have 1 logged event. It doesn't.", customer1.log.size(), 1);
            
            // step 4: call cashier schedule and thank customer
            assertTrue("Cashier's scheduler should have returned true. It didn't.", cashier.pickAndExecuteAnAction());
            
            // check step 4 postconditions
            assertTrue("Mockcustomer1 should have gotten a goodbye message. It didn't.", customer1.log
            		.containsString("Leaving."));
            assertEquals("Mockwaiter should have only 1 logged event. It doesn't.", waiter.log.size(), 1);
            assertTrue("Cashier should have no more bills.", cashier.bills.isEmpty());
            assertEquals("Cashier should have a total of 26.98", cashier.getCashOnHand(), 26.98);
            assertFalse("Cashier scheduler should return false. It didn't.", cashier.pickAndExecuteAnAction());
            System.out.println("testOneNormalCustomerScenario finished.");
            System.out.println("");
        }	//end one normal customer scenario
       */