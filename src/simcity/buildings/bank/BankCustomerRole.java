package simcity.buildings.bank;

import java.util.*;
public class BankCustomerRole implements simcity.interfaces.bank.BankCustomer {
	private BankHostRole bh;
	private BankTellerRole bt;
	private String name;
	Timer timer = new Timer();
	public enum AgentState{DoingNothing, WaitingInBank, AtWindow, TellerProcessingTransaction, RequestLoan};
	private AgentState state = AgentState.DoingNothing;//The start state
	public enum AgentEvent{haveBankTransaction, beingHelpedAtWindow, getAccountInfo, withdrawMoney, depositMoney, requestLoan, requestOpenAccount, loanApproved, loanUnapproved, doneTransaction};
    AgentEvent event;   
	//hack to establish connection to BankHostRole 
	public void setBankHost(BankHostRole bh) {
		this.bh = bh;
	}
	//hack to establish connection to BankTellerRole 
	public void setBankTeller(BankTellerRole bt) {
		this.bt = bt;
	}
	public String getCustomerName() {
		return name;
	}     
	//messages 
	
	//bank host sends this message to tell bank customer to go to bank window
	public void msgGoToWindow(int windowNumber, BankTellerRole bt) {
		this.bt = bt;
		event = AgentEvent.beingHelpedAtWindow;
		System.out.println("Please go to that window");
		//stateChanged();
	}
	// bank teller sends this message to customer after opening an account
	public void msgHereIsAccountInfo(int accountNumber, double accountBalance) {
		System.out.println("Here is your new account information");
		event = AgentEvent.getAccountInfo;
		//stateChanged();
	}
	//bank teller sends this message to customer after withdrawing money
	public void msgHerIsMoney(int accountNumber, double accountBalance, double withdrawAmount) {
		System.out.println("Here is the money that you withdraw");
		event = AgentEvent.withdrawMoney;
		//stateChanged(); 
	}
	//bank teller sends this message to customer after depositing money
	public void msgMoneyIsDeposited(int accountNumber, double accountBalance, double depositAmount) {
		System.out.println("Here is the money that you withdraw");
		event = AgentEvent.depositMoney;
		//stateChanged();
    }
	//bank teller sends this message to customer when the loan is approved
	 public void msgHereIsYourLoan(int accountNumber, double accountBalance, double loanAmount) {
		    System.out.println("Your loan has been approved");
		    event = AgentEvent.loanApproved;
			//stateChanged(); 
	 }
	//bank teller sends this message to customer when the loan is not approved
	 public void msgCannotGrantLoan(int accountNumber, double accountBalance, double loanAmount) {
		 System.out.println("Your loan is not approved");
    	 event = AgentEvent.loanUnapproved;
	     //stateChanged(); 
     }
	 //bank teller sends this message to customer when the transaction is completed
	 public void msgDoneTransaction() {
		    System.out.println("Your transaction is completed");
			event = AgentEvent.doneTransaction;
			//stateChanged(); 
	 }
	 //scheduler
	 protected boolean pickAndExecuteAnAction() {
		 if (state == AgentState.DoingNothing){
			    if (event == AgentEvent.haveBankTransaction){
				goingToBank();
				state = AgentState.WaitingInBank;
				return true;
			    }
			    
			}
			if (state == AgentState.WaitingInBank) {
			    if (event == AgentEvent.beingHelpedAtWindow) {
				makeTransactionChoice();
				state = AgentState.AtWindow;
				return true;
			    }
			}
			if (state == AgentState.AtWindow) {
			   
				if (event == AgentEvent.withdrawMoney)	{
					withdrawMoney();
					state = AgentState.TellerProcessingTransaction;
				return true;
			    }
				}
			if (state == AgentState.AtWindow) {
				if (event == AgentEvent.depositMoney) {
					depositMoney();	
					state = AgentState.TellerProcessingTransaction;
				return true;
			    }
			}
			if (state == AgentState.AtWindow) {
				if (event == AgentEvent.requestOpenAccount){
					openAccount();
					state = AgentState.TellerProcessingTransaction;
				return true;
			    }
			}
			if (state == AgentState.AtWindow) {
				if (event == AgentEvent.requestLoan)	{
				requestLoan();
				state = AgentState.TellerProcessingTransaction;
				return true;
			    }
			}
				
			if (state == AgentState.RequestLoan) {
			    if (event == AgentEvent.loanApproved)	{
				obtainLoan();
				}
				else if (event == AgentEvent.loanUnapproved) {
				doesNotObtainLoan();
				}	
				state = AgentState.TellerProcessingTransaction;
				return true;
			    
			}

			if (state == AgentState.TellerProcessingTransaction) {
			    if (event == AgentEvent.doneTransaction)	{
				leaveBank();
				state = AgentState.DoingNothing;
				return true;
			    }
			}
			System.out.println("No scheduler rule fired, should not happen in FSM, event="+event+" state="+state);

		 return false;
	 }
	 //actions
	 private void goingToBank() {
			//DoGoingToBank();//animation
			bh.msgEnteringBank(this);
	 }    

}
