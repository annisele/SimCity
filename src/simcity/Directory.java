package simcity;

import java.util.*;

public class Directory {
	
	private static WorldSystem world;
	public static enum EntryType {Market, Restaurant, Bank, House, Bus};
	private static Map<String, Entry> directory = new HashMap<String, Entry>(); //maps names to entries
	private static Map<Integer, Location>busStopDirectory = new HashMap<Integer, Location>();

	
	
	private class Entry {
		String name;
		Location location;
		EntryType type;
		SimSystem system;
		

		Entry(Location l, EntryType t, SimSystem s) {
			//shifting location so people enter through the door
			location = new Location(l.getX() + 22, l.getY() + 50);
			type = t;
			system = s; 
		}
	}
	
	public void makeBusStops1() {
		Location stop0 = new Location(40, 67);
		Location stop1 = new Location(380, 67);
		Location stop2 = new Location(380, 366);
		Location stop3 = new Location(40, 366);
		busStopDirectory.put(0, stop0);
		busStopDirectory.put(1, stop1);
		busStopDirectory.put(2, stop2);
		busStopDirectory.put(3, stop3);	
	}
	
	public void makeBusStops2() {
		Location stop0 = new Location(8, 110);
		Location stop1 = new Location(373, 110);
		Location stop2 = new Location(373, 410);
		Location stop3 = new Location(8, 410);
		busStopDirectory.put(0, stop0);
		busStopDirectory.put(1, stop1);
		busStopDirectory.put(2, stop2);
		busStopDirectory.put(3, stop3);	
	}
	
	public static Location getBusStop(int bsc) {
		return busStopDirectory.get(bsc);
	}
	
	public void setWorld(WorldSystem w) {
		world = w;
	}
	
	public static WorldSystem getWorld() {
		return world;
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
	
	public void clear() {
		directory.clear();
	}
}
