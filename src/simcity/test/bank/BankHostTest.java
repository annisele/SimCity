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
		assertEquals("Bank customer should have an empty event log before the Bank BankHost's scheduler is called. Instead the Bank teller's event log reads : "
				+ bc.log.toString(), 0, bc.log.size());
		host.msgEnteringBank(bc);
		assertFalse("MockBankCustomer should have received msgGoToWindow.", bc.log.containsString("I'm going to the window to perform bank transaction"));
		assertEquals("Bank host should have 1 bank customer in the list ", host.getCustomers().size(), 1);
		assertFalse(host.getCustomers().isEmpty());
		System.out.println("");		
	}
	public void testOneBankTellerEnterBank() {
		System.out.println("TEST ONE BANK TELLER ENTER BANK");
		
		assertTrue("Bank host should have 0 bank teller in it. It doesn't.",host.getBankTellers().isEmpty());		
		assertEquals("Bank host should have an empty event log before the BankHost's GoToWindow is called. Instead, the Bank Host's event log reads:"
				+ host.log.toString(), 0, host.log.size());
		assertEquals("Bank teller should have an empty event log before the Bank BankHost's scheduler is called. Instead the Bank teller's event log reads : "
				+ bt.log.toString(), 0, bt.log.size());
		host.msgImReadyToWork(bt);
		assertTrue(
                "Bank host's scheduler should have returned true because the bank customer list is empty, but didn't.",
                host.pickAndExecuteAnAction());
		assertEquals("Bank host should have 1 bank teller in the list ", host.getBankTellers().size(), 1);
		
		assertFalse(host.getBankTellers().isEmpty());
		System.out.println("");	
	}
	
}