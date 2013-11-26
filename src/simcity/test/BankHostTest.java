package simcity.test;

import simcity.buildings.bank.BankHostRole;
import simcity.test.mock.*;
import simcity.test.mock.bank.MockBankTeller;
import simcity.test.mock.bank.MockBankCustomer;
import junit.framework.*;

public class BankHostTest extends TestCase {
	BankHostRole host;
	MockBankTeller bankTeller;
	MockBankCustomer bc;
	
	public void setUp() throws Exception {
		super.setUp();
		host = new BankHostRole(null);
		bankTeller = new MockBankTeller("bank teller");
		bc = new MockBankCustomer("bank customer");		
	}
	
	public void testOneCustomerEnterBank() {
		System.out.println("TEST ONE CUSTOMER ENTER BANK");

		assertTrue("Cashier should have 0 bills in it. It doesn't.",host.getCustomers().isEmpty());		
		host.msgEnteringBank(bc);
	}
	public void testOneBankTellerEnterBank() {
		System.out.println("TEST ONE BANK TELLER ENTER BANK");
	}
	public void testOneCustomerExitBank() {
		System.out.println("TEST ONE CUSTOMER EXIT BANK");
	}
}