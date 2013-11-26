package simcity.test;

import simcity.buildings.bank.BankTellerRole;
import simcity.test.mock.*;
import simcity.test.mock.bank.MockBankHost;
import simcity.test.mock.bank.MockBankCustomer;
import junit.framework.*;

public class BankTellerTest extends TestCase {
	BankTellerRole bt;
	MockBankHost bh;
	MockBankCustomer bc;
	
	public void setUp() throws Exception {
		super.setUp();
		bt = new BankTellerRole(null, null);
		bh = new MockBankHost("bank host");
		bc = new MockBankCustomer("bank customer");		
	}
	
	public void testOneCustomerEnterBank() {
		System.out.println("TEST ONE CUSTOMER ENTER BANK");

		assertTrue("Bank host should have 0 customer in it. It doesn't.",host.getCustomers().isEmpty());		
		bt.msgEnteringBank(bc);
		bt.pickAndExecuteAnAction();
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