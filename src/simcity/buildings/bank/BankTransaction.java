package simcity.buildings.bank;

import java.util.*;

public class BankTransaction {
	
	 enum transactionType {none, openAccount, depositMoney, withdrawMoney, loanMoney, payLoan, payRent};
	 transactionType type = transactionType.none;
	 int accountID;
	 double amountProcessed;
	 String pinCode;
	 boolean transactionSuccess = true;
	 int landlordAccountID;
	
	 public transactionType getTransactionType() {
		 return type;
	 }
	 public void setTransactionType(transactionType type) {
		 this.type = type;
	 }
	 public int getAccountID() {
		 return accountID;
	 }
	 public void setAccountID(int accountID) {
		 this.accountID = accountID;
	 }
	 public double getAmountProcessed() {
		 return amountProcessed;
	 }
	 public void setAmountProcessed(double amountProcessed) {
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
	 public void setLandlordAccountID(int landlordAccountID) {
		 this.landlordAccountID = landlordAccountID;
	 }
}

