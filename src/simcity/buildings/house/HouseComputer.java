package simcity.buildings.house;

import java.util.*;

import simcity.interfaces.bank.BankCustomer;

public class HouseComputer {

	// variables
	private Map<String, Integer> inventory = Collections.synchronizedMap(new HashMap<String, Integer>());
	

	// constructor
	public HouseComputer() {
			
		
	}

	// functions
	public void setInventory(Map<String, Integer> map) {
		inventory = map;
	}
}	