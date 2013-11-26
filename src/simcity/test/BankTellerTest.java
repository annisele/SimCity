package simcity.test;

import simcity.buildings.bank.BankTellerRole;
import simcity.test.mock.*;
import junit.framework.*;
/**
 * 
 * This class is a JUnit test class to unit test the BankHostRole's basic interaction
 * with waiters, customers, and the host.
 *
 * @author Levonne Key
 */
public class BankTellerTest extends TestCase {
	BankTellerRole bankTeller;
	//MockBankHost bankHost;
	//MockBankCustomer bankCustomer;
	
	/**
	 * This method is run before each test. You can use it to instantiate the class variables
	 * for your role and mocks, etc.
	 */
	@Override
	public void setUp() throws Exception{
		super.setUp();
		//bankTeller = new BankTellerRole();
		//bankHost = new MockBankHost();
		//bankCustomer = new MockBankCustomer();	
	}
	
	public void testMsgWatToOpenAccount() {
		System.out.println("TEST ONE CUSTOMER OPEN ACCOUNT");
		
	}
	public void testOneNormalDepositMoneyForCustomerScenario() {
		System.out.println("TEST ONE CUSTOMER DEPOSIT MONEY");
	}
	public void testOneNormalWithdrawMoneyForCustomerScenario() {
		System.out.println("TEST ONE CUSTOMER WITHDRAW MONEY");
	}
	public void testOneNormalCustomerPayRentScenario() {
		System.out.println("TEST ONE CUSTOMER PAY RENT");
	}
	public void testOneNormalGiveLoanToCustomerScenario() {
		System.out.println("TEST ONE CUSTOMER GIVE LOAN");
	}
}