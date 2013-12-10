package simcity;

import java.util.*;

public class Directory {
	
	private static WorldSystem world;
	public static enum EntryType {Market, Restaurant, Bank, House, Bus, Car};
	private static Map<String, Entry> directory = new HashMap<String, Entry>(); //maps names to entries
	private static Map<Integer, Location>busStopDirectory = new HashMap<Integer, Location>();
	private static Map<Integer, Location> parkingStructureDirectory = new HashMap<Integer, Location>();
	private static Map<Integer, Location> streetDirectory = new HashMap<Integer, Location>();
	
	
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
	
	public void makeStreets() {
		/*
		 * Intersection naming convention
		 * 	- From top to bottom, from left to right, are the intersection numbers
		 * 	- North is 1,
		 * 	- West is 2,
		 *  - East is 3,
		 *  - South is 4,
		 *  
		 *  So if it's the south point of intersection 6 for example, the corresponding integer in the HashMap
		 *  is 64 (eg. Directory.getStreetStop(64));
		 */
		
		// Row 1 street stops
		Location intersection1East = new Location(1,1);
		Location intersection1South = new Location(1,1);
		Location intersection2West = new Location(1,1);
		Location intersection2East = new Location(1,1);
		Location intersection2South = new Location(1,1);
		Location intersection3West = new Location(1,1);
		Location intersection3East = new Location(1,1);
		Location intersection3South = new Location(1,1);
		Location intersection4West = new Location(1,1);
		Location intersection4South = new Location(1,1);
		streetDirectory.put(13, intersection1East);
		streetDirectory.put(14, intersection1South);
		streetDirectory.put(22, intersection2West);
		streetDirectory.put(23, intersection2East);
		streetDirectory.put(24, intersection2South);
		streetDirectory.put(32, intersection3West);
		streetDirectory.put(33, intersection3East);
		streetDirectory.put(34, intersection3South);
		streetDirectory.put(42, intersection4West);
		streetDirectory.put(44, intersection4South);
		
		// Row 2 street stops
		Location intersection5North = new Location(1,1);
		Location intersection5East = new Location(1,1);
		Location intersection5South = new Location(1,1);
		Location intersection6North = new Location(1,1);
		Location intersection6West = new Location(1,1);
		Location intersection6East = new Location(1,1);
		Location intersection6South = new Location(1,1);
		Location intersection7North = new Location(1,1);
		Location intersection7West = new Location(1,1);
		Location intersection7East = new Location(1,1);
		Location intersection7South = new Location(1,1);
		Location intersection8North = new Location(1,1);
		Location intersection8West = new Location(1,1);
		Location intersection8South = new Location(1,1);
		streetDirectory.put(51, intersection5North);
		streetDirectory.put(53, intersection5East);
		streetDirectory.put(54, intersection5South);
		streetDirectory.put(61, intersection6North);
		streetDirectory.put(62, intersection6West);
		streetDirectory.put(63, intersection6East);
		streetDirectory.put(64, intersection6South);
		streetDirectory.put(71, intersection7North);
		streetDirectory.put(72, intersection7West);
		streetDirectory.put(73, intersection7East);
		streetDirectory.put(74, intersection7South);
		streetDirectory.put(81, intersection8North);
		streetDirectory.put(82, intersection8West);
		streetDirectory.put(83, intersection8South);
		
		// Row 3 street stops
		Location intersection9North = new Location(1,1);
		Location intersection9East = new Location(1,1);
		Location intersection10North = new Location(1,1);
		Location intersection10West = new Location(1,1);
		Location intersection10East = new Location(1,1);
		Location intersection11North = new Location(1,1);
		Location intersection11West = new Location(1,1);
		Location intersection11East = new Location(1,1);
		Location intersection12North = new Location(1,1);
		Location intersection12West = new Location(1,1);
		streetDirectory.put(91, intersection9North);
		streetDirectory.put(93, intersection9East);
		streetDirectory.put(101, intersection10North);
		streetDirectory.put(102, intersection10West);
		streetDirectory.put(103, intersection10East);
		streetDirectory.put(111, intersection11North);
		streetDirectory.put(112, intersection11West);
		streetDirectory.put(113, intersection11East);
		streetDirectory.put(121, intersection12North);
		streetDirectory.put(122, intersection12West);
	}
	
	public static Location getBusStop(int bsc) {
		return busStopDirectory.get(bsc);
	}
	
	public static  Location getGarage(int gc) {
		
		return parkingStructureDirectory.get(gc);
	}
	
	public static Location getStreetStop(int ss) {
		return streetDirectory.get(ss);
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
		if(building.equals("MARKET1") || building.equals("MARKET3")) {
			return true;
		}
		return false;
	}
}
