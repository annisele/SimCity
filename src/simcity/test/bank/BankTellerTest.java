package simcity.test.bank;

import junit.framework.*;
import simcity.PersonAgent;
import simcity.buildings.bank.BankSystem;
import simcity.buildings.bank.BankTellerRole;
import simcity.gui.SimCityGui;
import simcity.test.bank.mock.MockBankCustomer;
import simcity.test.bank.mock.MockBankHost;

public class BankTellerTest extends TestCase {
	BankTellerRole bt;
	MockBankHost bh;
	MockBankCustomer bc;
	PersonAgent p = new PersonAgent("PersonAgent");
	SimCityGui scg = new SimCityGui();
	BankSystem bank = new BankSystem(scg);
	
	
	public void setUp() throws Exception {
		super.setUp();
		bank.setName("BANK1");
		bt = new BankTellerRole(p, bank);
		bh = new MockBankHost("bank host");
		bc = new MockBankCustomer("bank customer");		
	}
	public void testOneCustomerOpenAccount() {
		System.out.println("TEST ONE CUSTOMER OPEN ACCOUNT");
		assertTrue("Bank teller should have 0 customer in it. It doesn't ", 
				bt.getCustomers().isEmpty());
		assertEquals("Bank teller should have an empty event log before the Bank Teller's HereIsAccountInfo is called. Instead the Bank teller's event log reads : "
				+ bt.log.toString(), 0, bt.log.size());
		assertEquals("Bank customer should have an empty event log before the Bank Teller's HereIsAccountInfo is called. Instead the Bank teller's event log reads : "
				+ bc.log.toString(), 0, bc.log.size());
		bt.msgWantToOpenAccount(bc, 500);
		assertTrue(
                "Bank teller's scheduler should have returned true because the bank customer list is not empty, but didn't.",
                bt.pickAndExecuteAnAction());
		assertFalse("Bank teller should have 1 customer in the list because the account is opened. It doesn't.",
				bt.getCustomers().isEmpty());
		assertFalse(bt.getCustomers().isEmpty());
		System.out.println("");
	}
	public void testOneCustomerDepositMoney() {
		System.out.println("TEST ONE CUSTOMER DEPOSIT MONEY");
		assertTrue("Bank teller should have 0 customer in it. It doesn't ", bt.getCustomers().isEmpty());
		assertEquals("Bank teller should have an empty event log before the Bank Teller's MoneyIsDeposited is called. Instead the Bank teller's event log reads : "
				+ bt.log.toString(), 0, bt.log.size());
		assertEquals("Bank customer should have an empty event log before the Bank Teller's MoneyIsDeposited is called. Instead the Bank teller's event log reads : "
				+ bc.log.toString(), 0, bc.log.size());
		bt.msgWantToDeposit(bc, 100);
		assertTrue(
                "Bank teller's scheduler should have returned true because the bank customer list is not empty, but didn't.",
                bt.pickAndExecuteAnAction());
		assertFalse("Bank teller should have 1 customer in the list because the money is deposited. It doesn't.",
				bt.getCustomers().isEmpty());
		assertFalse(bt.getCustomers().isEmpty());
		System.out.println("");
	}
	public void testOneCustomerWithdrawMoney() {
		System.out.println("TEST ONE CUSTOMER WITHDRAW MONEY");
		assertTrue("Bank teller should have 0 customer in it. It doesn't ", bt.getCustomers().isEmpty());
		assertEquals("Bank teller should have an empty event log before the Bank Teller's MoneyIsWithdraw is called. Instead the Bank teller's event log reads : "
				+ bt.log.toString(), 0, bt.log.size());
		assertEquals("Bank customer should have an empty event log before the Bank Teller's MoneyIsWithdraw is called. Instead the Bank teller's event log reads : "
				+ bc.log.toString(), 0, bc.log.size());
		bt.msgWantToWithdraw(bc, 100);
		assertTrue(
                "Bank teller's scheduler should have returned true because the bank customer list is not empty, but didn't.",
                bt.pickAndExecuteAnAction());
		assertFalse("Bank teller should have 1 customer in the list because the money is withdrawn. It doesn't.",
				bt.getCustomers().isEmpty());
		assertFalse(bt.getCustomers().isEmpty());
		System.out.println("");
	}
	public void testOneCustomerGetLoan() {
		System.out.println("TEST ONE CUSOTMER GET LOAN");
		assertTrue("Bank teller should have 0 customer in it. It doesn't ", bt.getCustomers().isEmpty());
		assertEquals("Bank teller should have an empty event log before the Bank Teller's LoanIsApproved is called. Instead the Bank teller's event log reads : "
				+ bt.log.toString(), 0, bt.log.size());
		assertEquals("Bank customer should have an empty event log before the Bank Teller's LoanIsApproved is called. Instead the Bank teller's event log reads : "
				+ bc.log.toString(), 0, bc.log.size());
		bt.msgWantALoan(bc, 100);
		assertTrue(
                "Bank teller's scheduler should have returned true because the bank customer list is not empty, but didn't.",
                bt.pickAndExecuteAnAction());
		assertFalse("Bank teller should have 1 customer in the list because the a customer is getting loan. It doesn't.",
				bt.getCustomers().isEmpty());
		assertFalse(bt.getCustomers().isEmpty());
		System.out.println("");
	}
	public void testOneCustomerPayLoan() {
		System.out.println("TEST ONE CUSOTMER PAY LOAN");
		assertTrue("Bank teller should have 0 customer in it. It doesn't ", bt.getCustomers().isEmpty());
		assertEquals("Bank teller should have an empty event log before the Bank Teller's LoanIsFullyPaid is called. Instead the Bank teller's event log reads : "
				+ bt.log.toString(), 0, bt.log.size());
		assertEquals("Bank customer should have an empty event log before the Bank Teller's LoanIsFullyPaid is called. Instead the Bank teller's event log reads : "
				+ bc.log.toString(), 0, bc.log.size());
		bt.msgWantToPayLoan(bc, 100);
		assertTrue(
                "Bank teller's scheduler should have returned true because the bank customer list is not empty, but didn't.",
                bt.pickAndExecuteAnAction());
		assertEquals("Bank teller should have an empty event log before the Bank Teller's LoanIsFullyPaid is called. Instead the Bank teller's event log reads : "
				+ bt.log.toString(), 0, bt.log.size());
		assertFalse(bt.getCustomers().isEmpty());
		System.out.println("");
	}
	public void testOneCustomerPayRent() {
		System.out.println("TEST ONE CUSTOMER PAY RENT");
		assertTrue("Bank teller should have 0 customer in it. It doesn't ", bt.getCustomers().isEmpty());
		assertEquals("Bank teller should have an empty event log before the Bank Teller's RentIsPaid is called. Instead the Bank teller's event log reads : "
				+ bt.log.toString(), 0, bt.log.size());
		assertEquals("Bank customer should have an empty event log before the Bank Teller's RentIsPaid is called. Instead the Bank teller's event log reads : "
				+ bc.log.toString(), 0, bc.log.size());
		bt.msgWantToPayRent(bc, 100);
		assertFalse(
                "Bank teller's scheduler should have returned false because the bank customer list is empty, but didn't.",
                bt.pickAndExecuteAnAction());
		assertEquals("Bank teller should have an empty event log before the Bank Teller's RentIsPaid is called. Instead the Bank teller's event log reads : "
				+ bt.log.toString(), 0, bt.log.size());
		assertFalse(bt.getCustomers().isEmpty());
		System.out.println("");
	}
	
	
}