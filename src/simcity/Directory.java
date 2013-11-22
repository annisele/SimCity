package simcity;

import java.util.*;

public class Directory {
	
	private enum EntryType {market, restaurant, bank};
	private Map<String, Contact> directory = new HashMap<String, Contact>(); //maps names to entries
	
	
	private class Contact {
		String name;
		Location location;
		EntryType type;
		Role role;
		
		Contact( Location l, EntryType t, Role r) {
			
			location = l;
			type = t;
			role = r;
		}
	}
	public void Add(String n, String t, Location l, Role c){
		EntryType temptype = null;
		if (t.equals("market")){
			temptype= EntryType.market;
		}
		if (t.equals("bank")){
			temptype= EntryType.bank;
		}
		if (t.equals("restaurant")){
			temptype= EntryType.restaurant;
		}
		Contact temp = new Contact(l,temptype,c);
		directory.put(n, temp);
	}
	public Role getContact(String p){
		Contact temp=null;
		temp=directory.get(p);
		
		return temp.role;
		
	}
	public void updateContact(String p, Role r){
		Contact temp=null;
		
				for(Map.Entry<String,Contact> entry_ : directory.entrySet() ) {
			        if( entry_.getKey().equals(p) ) {
			            temp= entry_.getValue();
			            break;
			        }
			    }
				temp.role=r;
		directory.put(p, temp);
	}
}
