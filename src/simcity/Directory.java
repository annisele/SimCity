package simcity;

import java.util.*;

public class Directory {
	
	private enum EntryType {market, restaurant, bank};
	private Map<String, Entry> directory = new HashMap<String, Entry>(); //maps names to entries
	
	
	private class Entry {
		Location location;
		EntryType type;
		Role contact;
		
		Entry(Location l, EntryType t, Role c) {
			location = l;
			type = t;
			contact = c;
		}
	}
}
