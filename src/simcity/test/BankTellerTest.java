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
	
	
}