package simcity.test;

import junit.framework.TestCase;
import simcity.PersonAgent;
import simcity.buildings.bank.BankSystem;
import simcity.buildings.bank.BankTellerRole;
import simcity.gui.SimCityGui;
import simcity.test.mock.bank.MockBankCustomer;
import simcity.test.mock.bank.MockBankHost;

public class BankTellerTest extends TestCase {
	BankTellerRole bt;
	MockBankHost bh;
	MockBankCustomer bc;
	PersonAgent p = new PersonAgent("PersonAgent");
	SimCityGui scg = new SimCityGui();
	BankSystem bank = new BankSystem(scg);
	
	
	public void setUp() throws Exception {
		super.setUp();
		bt = new BankTellerRole(p, bank);
		bh = new MockBankHost("bank host");
		bc = new MockBankCustomer("bank customer");		
	}
	public void testOneCustomerOpenAccount() {
		System.out.println("TEST ONE CUSTOMER OPEN ACCOUNT");
		assertTrue("Bank teller should have 0 customer in it. It doesn't ", bt.getCustomers().isEmpty());
		bt.msgWantToOpenAccount(bc, 500);
		bt.pickAndExecuteAnAction();
		assertEquals("Bank teller should have an empty event log before the Bank Teller's HereIsAccountInfo is called. Instead the Bank teller's event log reads : "
				+ bt.log.toString(), 0, bt.log.size());
		assertFalse(bt.getCustomers().isEmpty());
	}
	public void testOneCustomerDepositMoney() {
		System.out.println("TEST ONE CUSTOMER DEPOSIT MONEY");
		assertTrue("Bank teller should have 0 customer in it. It doesn't ", bt.getCustomers().isEmpty());
		bt.msgWantToDeposit(bc, 100);
		bt.pickAndExecuteAnAction();
		assertEquals("Bank teller should have an empty event log before the Bank Teller's HereIsAccountInfo is called. Instead the Bank teller's event log reads : "
				+ bt.log.toString(), 0, bt.log.size());
		assertFalse(bt.getCustomers().isEmpty());
	}
	public void testOneCustomerWithdrawMoney() {
		System.out.println("TEST ONE CUSTOMER WITHDRAW MONEY");
		assertTrue("Bank teller should have 0 customer in it. It doesn't ", bt.getCustomers().isEmpty());
		bt.msgWantToWithdraw(bc, 100);
		bt.pickAndExecuteAnAction();
		assertEquals("Bank teller should have an empty event log before the Bank Teller's HereIsAccountInfo is called. Instead the Bank teller's event log reads : "
				+ bt.log.toString(), 0, bt.log.size());
		assertFalse(bt.getCustomers().isEmpty());
	}
	public void testOneCustomerGetLoan() {
		System.out.println("TEST ONE CUSOTMER GET LOAN");
		assertTrue("Bank teller should have 0 customer in it. It doesn't ", bt.getCustomers().isEmpty());
		bt.msgWantALoan(bc, 100);
		bt.pickAndExecuteAnAction();
		assertEquals("Bank teller should have an empty event log before the Bank Teller's HereIsAccountInfo is called. Instead the Bank teller's event log reads : "
				+ bt.log.toString(), 0, bt.log.size());
		assertFalse(bt.getCustomers().isEmpty());
	}
	public void testOneCustomerPayLoan() {
		System.out.println("TEST ONE CUSOTMER PAY LOAN");
		assertTrue("Bank teller should have 0 customer in it. It doesn't ", bt.getCustomers().isEmpty());
		bt.msgWantToPayLoan(bc, 100);
		bt.pickAndExecuteAnAction();
		assertEquals("Bank teller should have an empty event log before the Bank Teller's HereIsAccountInfo is called. Instead the Bank teller's event log reads : "
				+ bt.log.toString(), 0, bt.log.size());
		assertFalse(bt.getCustomers().isEmpty());
	}
	public void testOneCustomerPayRent() {
		System.out.println("TEST ONE CUSTOMER PAY RENT");
		assertTrue("Bank teller should have 0 customer in it. It doesn't ", bt.getCustomers().isEmpty());
		bt.msgWantToPayRent(bc, 100);
		bt.pickAndExecuteAnAction();
		assertEquals("Bank teller should have an empty event log before the Bank Teller's HereIsAccountInfo is called. Instead the Bank teller's event log reads : "
				+ bt.log.toString(), 0, bt.log.size());
		assertFalse(bt.getCustomers().isEmpty());
	}
	
	
}