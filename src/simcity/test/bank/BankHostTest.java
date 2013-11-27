package simcity.test.bank;

import simcity.PersonAgent;
import simcity.buildings.bank.BankHostRole;
import simcity.buildings.bank.BankSystem;
import simcity.gui.SimCityGui;
import simcity.test.bank.mock.MockBankCustomer;
import simcity.test.bank.mock.MockBankTeller;
import simcity.test.mock.*;
import junit.framework.*;

public class BankHostTest extends TestCase {
	BankHostRole host;
	MockBankTeller bt;
	MockBankCustomer bc;
	PersonAgent p = new PersonAgent("PersonAgent");
	SimCityGui scg = new SimCityGui();
	BankSystem bank = new BankSystem(scg);

	
	public void setUp() throws Exception {
		super.setUp();
		bank.setName("BANK1");
		host = new BankHostRole(p);
		host.enterBuilding(bank);
		bt = new MockBankTeller("bank teller");
		bc = new MockBankCustomer("bank customer");
	
	}
	
	public void testOneCustomerEnterBank() {
		System.out.println("TEST ONE CUSTOMER ENTER BANK");
		
		assertTrue("Bank host should have 0 customer in it. It doesn't.",host.getCustomers().isEmpty());		
		assertEquals("Bank host should have an empty event log before the BankHost's GoToWindow is called. Instead, the Bank Host's event log reads:"
				+ host.log.toString(), 0, host.log.size());
		host.msgEnteringBank(bc);
		assertFalse("MockBankCustomer should have received msgGoToWindow.", bc.log.containsString("I'm going to the window to perform bank transaction"));
		
		//host.pickAndExecuteAnAction();
		assertFalse(host.getCustomers().isEmpty());
		
		assertEquals(
				"MockBankCustomer should have an empty event log after the BankHost's scheduler is called for the first time. Instead, the MockCustomer's event log reads: "
						+ bc.log.toString(), 0, bc.log.size());
				
		//assertTrue("MockBankCustomer should have received msgGoToWindow.", bc.log.containsString("I'm going to the window to perform bank transaction"));
		
	}
	public void testOneBankTellerEnterBank() {
		System.out.println("TEST ONE BANK TELLER ENTER BANK");
		
		assertTrue("Bank host should have 0 bank teller in it. It doesn't.",host.getBankTellers().isEmpty());		
		host.msgImReadyToWork(bt);
		host.pickAndExecuteAnAction();
		assertEquals("Bank host should have an empty event log before the BankHost's GoToWindow is called. Instead, the Bank Host's event log reads:"
				+ host.log.toString(), 0, host.log.size());
		assertTrue(host.getBankTellers().isEmpty());
	}
	
}