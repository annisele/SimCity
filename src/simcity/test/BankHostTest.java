package simcity.test;

import simcity.buildings.bank.BankHostRole;
import simcity.test.mock.*;
import simcity.test.mock.bank.MockBankTeller;
import simcity.test.mock.bank.MockBankCustomer;
import junit.framework.*;

public class BankHostTest extends TestCase {
	BankHostRole host;
	MockBankTeller bankTeller;
	MockBankCustomer bankCustomer;
	
	public void setUp() throws Exception {
		super.setUp();
		host = new BankHostRole(null);
		bankTeller = new MockBankTeller("bank teller");
		bankCustomer = new MockBankCustomer("bank customer");		
	}
	
	public void testOneCustomerEnterBank() {
		System.out.println("TEST ONE CUSTOMER ENTER BANK");
	}
	public void testOneBankTellerEnterBank() {
		System.out.println("TEST ONE BANK TELLER ENTER BANK");
	}
	public void testOneCustomerExitBank() {
		System.out.println("TEST ONE CUSTOMER EXIT BANK");
	}
}