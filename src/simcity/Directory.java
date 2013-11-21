package simcity;

import java.util.*;

public class Directory {
	
	private enum EntryType {market, restaurant, bank};
	private Map<String, Entry> directory = new HashMap<String, Entry>(); //maps names to entries
	
	
	private class Entry {
		String name;
		Location location;
		EntryType type;
		Role contact;
		
		Entry( Location l, EntryType t, Role c) {
			
			location = l;
			type = t;
			contact = c;
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
		Entry temp = new Entry(l,temptype,c);
		directory.put(n, temp);
	}
}
