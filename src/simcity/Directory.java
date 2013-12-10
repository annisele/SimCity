package simcity;

import java.util.*;

public class Directory {
	
	private static WorldSystem world;
	public static enum EntryType {Market, Restaurant, Bank, House, Bus, Car};
	private static Map<String, Entry> directory = new HashMap<String, Entry>(); //maps names to entries
	private static Map<Integer, Location>busStopDirectory = new HashMap<Integer, Location>();
	private static Map<Integer, Location> parkingStructureDirectory = new HashMap<Integer, Location>();
	
	
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
	
	public void makeParkingStructure1() {
		Location parking1 = new Location(720,143);
		Location parking2 = new Location(290,411);
		Location parking3 = new Location(720,503);
		Location parking4 = new Location(1145,411);
		Location parking5 = new Location(290,771);
		Location parking6 = new Location(1145,771);
		parkingStructureDirectory.put(0, parking1);
		parkingStructureDirectory.put(1, parking2);
		parkingStructureDirectory.put(2, parking3);
		parkingStructureDirectory.put(3, parking4);
		parkingStructureDirectory.put(4, parking5);
		parkingStructureDirectory.put(5, parking6);
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
	
	public static  Location getGarage(int gc) {
		
		return parkingStructureDirectory.get(gc);
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
	
	public static boolean hasEarlySchedule(String building) {
		if(building.equals("RESTAURANT2") ||building.equals("MARKET1") || building.equals("MARKET3")) {
			return true;
		}
		return false;
	}
}
