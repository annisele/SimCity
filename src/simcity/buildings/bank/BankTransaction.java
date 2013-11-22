package simcity.buildings.bank;

import java.util.*;

public class BankTransaction {
	
	 enum transactionType {none, openAccount, depositMoney, withdrawMoney, loanMoney, payLon, payRent};
	 int accountID;
	 double amountProcessed;
	 String pinCode;
	 boolean transactionSuccess = true;
	 int landlordAccountID;
	
	 public int getAccountID() {
		 return accountID;
	 }
	 public void setAccountID(int accountID) {
		 this.accountID = accountID;
	 }
	 public double getAmountProcessed() {
		 return amountProcessed;
	 }
	 public void setAmountProcessed(double amountProcesed) {
		 this.amountProcessed = amountProcessed;
	 }
	 public String getPinCode() {
		 return pinCode;
	 }
	 public void setPinCode(String pinCode) {
		 this.pinCode = pinCode;
	 }
	 public int getLandlordAccountID() {
		 return landlordAccountID;
	 }
	 public void setLandlordAccountID(int landlordAccount) {
		 this.landlordAccountID = landlordAccountID;
	 }
}

