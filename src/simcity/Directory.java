package simcity;

import java.util.*;

public class Directory {

	public static enum EntryType {Market, Restaurant, Bank};
	private static Map<String, Entry> directory = new HashMap<String, Entry>(); //maps names to entries

	private class Entry {
		String name;
		Location location;
		EntryType type;
		SimSystem system;

		Entry(Location l, EntryType t, SimSystem s) {
			location = l;
			type = t;
			system = s;
		}
	}

	public static Location getLocation(String name) {
		return directory.get(name).location;
	}

	public static SimSystem getSystem(String name) {
		return directory.get(name).system;
	}

	public static List<String> getRestaurants() {
		List<String> restaurants = new ArrayList<String>();
		for (String key : directory.keySet()) {
			if(directory.get(key).type == EntryType.Restaurant) {
				restaurants.add(key);
			}
		}
		return restaurants;
	}

	public static List<String> getMarkets() {
		List<String> markets = new ArrayList<String>();
		for (String key : directory.keySet()) {
			if(directory.get(key).type == EntryType.Market) {
				markets.add(key);
			}
		}
		return markets;
	}

	public static List<String> getBanks() {
		List<String> banks = new ArrayList<String>();
		for (String key : directory.keySet()) {
			if(directory.get(key).type == EntryType.Bank) {
				banks.add(key);
			}
		}
		return banks;
	}

	public void add(String n, EntryType t, Location l, SimSystem s){
		Entry temp = new Entry(l, t, s);
		directory.put(n, temp);
	}
}
