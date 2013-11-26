package simcity.test;

import simcity.buildings.bank.BankHostRole;
import simcity.test.mock.*;
import simcity.test.mock.bank.MockBankTeller;
import simcity.test.mock.bank.MockBankCustomer;
import junit.framework.*;

public class BankHostTest extends TestCase {
	BankHostRole host;
	MockBankTeller bt;
	MockBankCustomer bc;
	
	public void setUp() throws Exception {
		super.setUp();
		host = new BankHostRole(null);
		bt = new MockBankTeller("bank teller");
		bc = new MockBankCustomer("bank customer");		
	}
	
	public void testOneCustomerEnterBank() {
		System.out.println("TEST ONE CUSTOMER ENTER BANK");
		
		assertTrue("Bank host should have 0 customer in it. It doesn't.",host.getCustomers().isEmpty());		
		host.msgEnteringBank(bc);
		host.pickAndExecuteAnAction();
		assertEquals("Bank host should have an empty event log before the BankHost's GoToWindow is called. Instead, the Bank Host's event log reads:"
				+ host.log.toString(), 0, host.log.size());
		assertFalse(host.getCustomers().isEmpty());
		
	}
	public void testOneBankTellerEnterBank() {
		System.out.println("TEST ONE BANK TELLER ENTER BANK");
		
		assertTrue("Bank host should have 0 bank teller in it. It doesn't.",host.getCustomers().isEmpty());		
		host.msgImReadyToWork(bt);
		host.pickAndExecuteAnAction();
		assertEquals("Bank host should have an empty event log before the BankHost's GoToWindow is called. Instead, the Bank Host's event log reads:"
				+ host.log.toString(), 0, host.log.size());
		assertFalse(host.getBankTellers().isEmpty());
	}
	
}