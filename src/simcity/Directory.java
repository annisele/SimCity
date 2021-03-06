package simcity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simcity.buildings.transportation.Intersection;

public class Directory {
	
	private static WorldSystem world;
	public static enum EntryType {Market, Restaurant, Bank, House, Bus, Car};
	private static Map<String, Entry> directory = new HashMap<String, Entry>(); //maps names to entries
	private static Map<Integer, Location>busStopDirectory = new HashMap<Integer, Location>();
	private static Map<Integer, Location> parkingStructureDirectory = new HashMap<Integer, Location>();
	private static Map<Integer, Location> streetDirectory = new HashMap<Integer, Location>();
	private static Map<Integer, Intersection> intersections = new HashMap<Integer, Intersection>();
	
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
	
	public Intersection getIntersection(int i) {
		return intersections.get(i);
	}
	
	public static Location getBusStop(int bsc) {
		return busStopDirectory.get(bsc);
	}
	
	public static Location getGarage(int gc) {
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

	public static List<String> getOpenRestaurants() {
		List<String> restaurants = new ArrayList<String>();
		for (String key : directory.keySet()) {
			if(directory.get(key).type == EntryType.Restaurant) {
				if (directory.get(key).system.isOpen()) {
					restaurants.add(key);
				}
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
	
	public static List<String> getOpenMarkets() {
		List<String> markets = new ArrayList<String>();
		for (String key : directory.keySet()) {
			if(directory.get(key).type == EntryType.Market) {
				if (directory.get(key).system.isOpen()) {
					markets.add(key);
				}
			}
		}
		return markets;
	}

	public static List<String> getOpenBanks() {
		List<String> banks = new ArrayList<String>();
		for (String key : directory.keySet()) {
			if(directory.get(key).type == EntryType.Bank) {
				if (directory.get(key).system.isOpen()) {
					banks.add(key);
				}
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
	
	public void makeParkingStructure1() {
		Location parking1 = new Location(700,182);
		Location parking2 = new Location(280,368);
		Location parking3 = new Location(700,542);
		Location parking4 = new Location(1135,368);
		Location parking5 = new Location(280,728);
		Location parking6 = new Location(1135,728);
		parkingStructureDirectory.put(1, parking1);
		parkingStructureDirectory.put(2, parking2);
		parkingStructureDirectory.put(3, parking3);
		parkingStructureDirectory.put(4, parking4);
		parkingStructureDirectory.put(5, parking5);
		parkingStructureDirectory.put(6, parking6);
	}
	
	public void makeBusStopDirectory() {
		Location stop0 = new Location(150, 180);
		Location stop1 = new Location(590, 150);
		Location stop2 = new Location(1340, 180);
		Location stop3 = new Location(1340, 530);
		Location stop4 = new Location(150, 530);
		busStopDirectory.put(0, stop0);
		busStopDirectory.put(1, stop1);
		busStopDirectory.put(2, stop2);
		busStopDirectory.put(3, stop3);	
		busStopDirectory.put(4, stop4);	
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
	
	public void makeStreetsAndIntersections() {
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
		 *  
		 *  On the other hand, parking structures have code 9+ParkingStructureNo.+NorthOrSouth
		 *  
		 *  So the upper lane of parking structure 6 is 961, lower lane is 964
		 *  
		 *  BusStops are 8+District+NSEW, so bus stop in district 1, east is 813
		 *  The "mega district" has code 886
		 */
		
		// Row 1 street stops
		Location intersection1East = new Location(150,80);
		Location intersection1South = new Location(110,150);
		Location intersection2West = new Location(470,110);
		Location intersection2East = new Location(590,80);
		Location intersection2South = new Location(545,150);
		Location intersection3West = new Location(910,110);
		Location intersection3East = new Location(1020,80);
		Location intersection3South = new Location(975,150);
		Location intersection4West = new Location(1340,110);
		Location intersection4South = new Location(1400,150);
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
		Location intersection5North = new Location(80,400);
		Location intersection5East = new Location(150,435);
		Location intersection5South = new Location(110,490);
		Location intersection6North = new Location(505,400);
		Location intersection6West = new Location(470,470);
		Location intersection6East = new Location(590,435);
		Location intersection6South = new Location(545,490);
		Location intersection7North = new Location(940,400);
		Location intersection7West = new Location(910,470);
		Location intersection7East = new Location(1020,435);
		Location intersection7South = new Location(975,490);
		Location intersection8North = new Location(1370,400);
		Location intersection8West = new Location(1340,470);
		Location intersection8South = new Location(1400,490);
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
		streetDirectory.put(84, intersection8South);
		
		// Row 3 street stops
		Location intersection9North = new Location(80,760);
		Location intersection9East = new Location(150,800);
		Location intersection10North = new Location(505,760);
		Location intersection10West = new Location(470,830);
		Location intersection10East = new Location(590,800);
		Location intersection11North = new Location(940,760);
		Location intersection11West = new Location(910,830);
		Location intersection11East = new Location(1020,800);
		Location intersection12North = new Location(1370,760);
		Location intersection12West = new Location(1340,830);
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
		
		// Parking structure stops
		Location parking1North = new Location(700,80);
		Location parking1South = new Location(700,110);
		Location parking2North = new Location(280,435);
		Location parking2South = new Location(280,470);
		Location parking3North = new Location(700,435);
		Location parking3South = new Location(700,470);
		Location parking4North = new Location(1135,435);
		Location parking4South = new Location(1135,470);
		Location parking5North = new Location(280,800);
		Location parking5South = new Location(280,830);
		Location parking6North = new Location(1135,800);
		Location parking6South = new Location(1135,830);
		streetDirectory.put(911, parking1North);
		streetDirectory.put(914, parking1South);
		streetDirectory.put(921, parking2North);
		streetDirectory.put(924, parking2South);
		streetDirectory.put(931, parking3North);
		streetDirectory.put(934, parking3South);
		streetDirectory.put(941, parking4North);
		streetDirectory.put(944, parking4South);
		streetDirectory.put(951, parking5North);
		streetDirectory.put(954, parking5South);
		streetDirectory.put(961, parking6North);
		streetDirectory.put(964, parking6South);
		
		// Bus stops
		Location busStop1West = new Location(80,180);
		Location busStop1East = new Location(110,180);
		Location busStop2North = new Location(590,80);
		Location busStop2South = new Location(590,110);
		Location busStop3West = new Location(1370,180);
		Location busStop3East = new Location(1400,180);
		Location busStop4West = new Location(80,530);
		Location busStop4East = new Location(110,530);
		Location busStop6West = new Location(1370,530);
		Location busStop6East = new Location(1400,530);
		streetDirectory.put(812, busStop1West);
		streetDirectory.put(813, busStop1East);
		streetDirectory.put(821, busStop2North);
		streetDirectory.put(824, busStop2South);
		streetDirectory.put(832, busStop3West);
		streetDirectory.put(833, busStop3East);
		streetDirectory.put(842, busStop4West);
		streetDirectory.put(843, busStop4East);
		streetDirectory.put(862, busStop6West);
		streetDirectory.put(863, busStop6East);
		
		// Intersections
		intersections.put(1, new Intersection(1));
		intersections.put(2, new Intersection(2));
		intersections.put(3, new Intersection(3));
		intersections.put(4, new Intersection(4));
		intersections.put(5, new Intersection(5));
		intersections.put(6, new Intersection(6));
		intersections.put(7, new Intersection(7));
		intersections.put(8, new Intersection(8));
		intersections.put(9, new Intersection(9));
		intersections.put(10, new Intersection(10));
		intersections.put(11, new Intersection(11));
		intersections.put(12, new Intersection(12));
	}
	
	public static List<Location> findRoute(int start, int end) {
		List<Location> route = Collections.synchronizedList(new ArrayList<Location>());
		List<Intersection> stoplights = Collections.synchronizedList(new ArrayList<Intersection>());
		route.clear();
		stoplights.clear();
		if (start == 1) {
			if (end == 2) {
				route.add(streetDirectory.get(23));
				route.add(streetDirectory.get(61));
				route.add(streetDirectory.get(921));
				route.add(parkingStructureDirectory.get(2));
			} else if (end == 3) {
				route.add(streetDirectory.get(23));
				route.add(streetDirectory.get(61));
				route.add(streetDirectory.get(934));
				route.add(parkingStructureDirectory.get(3));
			} else if (end == 4) {
				route.add(streetDirectory.get(32));
				route.add(streetDirectory.get(71));
				route.add(streetDirectory.get(934));
				route.add(parkingStructureDirectory.get(4));
			} else if (end == 5) {
				route.add(streetDirectory.get(23));
				route.add(streetDirectory.get(61));
				route.add(streetDirectory.get(101));
				route.add(streetDirectory.get(951));
				route.add(parkingStructureDirectory.get(5));
			} else if (end == 6) {
				route.add(streetDirectory.get(32));
				route.add(streetDirectory.get(42));
				route.add(streetDirectory.get(81));
				route.add(streetDirectory.get(121));
				route.add(streetDirectory.get(961));
				route.add(parkingStructureDirectory.get(6));
			}
		}
		else if (start == 2) {
			if (end == 1) {
				route.add(streetDirectory.get(62));
				route.add(streetDirectory.get(24));
				route.add(streetDirectory.get(914));
				route.add(parkingStructureDirectory.get(1));
			} else if (end == 3) {
				route.add(streetDirectory.get(62));
				route.add(streetDirectory.get(934));
				route.add(parkingStructureDirectory.get(3));
			} else if (end == 4) {
				route.add(streetDirectory.get(62));
				route.add(streetDirectory.get(72));
				route.add(streetDirectory.get(944));
				route.add(parkingStructureDirectory.get(4));
			} else if (end == 5) {
				route.add(streetDirectory.get(62));
				route.add(streetDirectory.get(101));
				route.add(streetDirectory.get(951));
				route.add(parkingStructureDirectory.get(5));
			} else if (end == 6) {
				route.add(streetDirectory.get(62));
				route.add(streetDirectory.get(101));
				route.add(streetDirectory.get(112));
				route.add(streetDirectory.get(964));
				route.add(parkingStructureDirectory.get(6));
			}
		}
		else if (start == 3) {
			if (end == 1) {
				route.add(streetDirectory.get(63));
				route.add(streetDirectory.get(24));
				route.add(streetDirectory.get(914));
				route.add(parkingStructureDirectory.get(1));
			} else if (end == 2) {
				route.add(streetDirectory.get(63));
				route.add(streetDirectory.get(921));
				route.add(parkingStructureDirectory.get(2));
			} else if (end == 4) {
				route.add(streetDirectory.get(72));
				route.add(streetDirectory.get(944));
				route.add(parkingStructureDirectory.get(4));
			} else if (end == 5) {
				route.add(streetDirectory.get(63));
				route.add(streetDirectory.get(101));
				route.add(streetDirectory.get(951));
				route.add(parkingStructureDirectory.get(5));
			} else if (end == 6) {
				route.add(streetDirectory.get(72));
				route.add(streetDirectory.get(82));
				route.add(streetDirectory.get(121));
				route.add(streetDirectory.get(961));
				route.add(parkingStructureDirectory.get(6));
				stoplights.add(new Intersection(7));
				stoplights.add(new Intersection(8));
				stoplights.add(new Intersection(12));
			}
		}
		else if (start == 4) {
			if (end == 1) {
				route.add(streetDirectory.get(73));
				route.add(streetDirectory.get(34));
				route.add(streetDirectory.get(911));
				route.add(parkingStructureDirectory.get(1));
			} else if (end == 2) {
				route.add(streetDirectory.get(73));
				route.add(streetDirectory.get(63));
				route.add(streetDirectory.get(921));
				route.add(parkingStructureDirectory.get(2));
			} else if (end == 3) {
				route.add(streetDirectory.get(73));
				route.add(streetDirectory.get(931));
				route.add(parkingStructureDirectory.get(3));
			} else if (end == 5) {
				route.add(streetDirectory.get(73));
				route.add(streetDirectory.get(63));
				route.add(streetDirectory.get(101));
				route.add(streetDirectory.get(951));
				route.add(parkingStructureDirectory.get(5));
			} else if (end == 6) {
				route.add(streetDirectory.get(82));
				route.add(streetDirectory.get(121));
				route.add(streetDirectory.get(961));
				route.add(parkingStructureDirectory.get(6));
			}
		}
		else if (start == 5) {
			if (end == 1) {
				route.add(streetDirectory.get(102));
				route.add(streetDirectory.get(64));
				route.add(streetDirectory.get(24));
				route.add(streetDirectory.get(914));
				route.add(parkingStructureDirectory.get(1));
			} else if (end == 2) {
				route.add(streetDirectory.get(93));
				route.add(streetDirectory.get(54));
				route.add(streetDirectory.get(924));
				route.add(parkingStructureDirectory.get(2));
			} else if (end == 3) {
				route.add(streetDirectory.get(102));
				route.add(streetDirectory.get(64));
				route.add(streetDirectory.get(934));
				route.add(parkingStructureDirectory.get(3));
			} else if (end == 4) {
				route.add(streetDirectory.get(102));
				route.add(streetDirectory.get(64));
				route.add(streetDirectory.get(72));
				route.add(streetDirectory.get(944));
				route.add(parkingStructureDirectory.get(4));
			} else if (end == 6) {
				route.add(streetDirectory.get(102));
				route.add(streetDirectory.get(112));
				route.add(streetDirectory.get(964));
				route.add(parkingStructureDirectory.get(6));
			}
		}
		else if (start == 6) {
			if (end == 1) {
				route.add(streetDirectory.get(113));
				route.add(streetDirectory.get(103));
				route.add(streetDirectory.get(64));
				route.add(streetDirectory.get(24));
				route.add(streetDirectory.get(914));
				route.add(parkingStructureDirectory.get(1));
			} else if (end == 2) {
				route.add(streetDirectory.get(113));
				route.add(streetDirectory.get(103));
				route.add(streetDirectory.get(64));
				route.add(streetDirectory.get(921));
				route.add(parkingStructureDirectory.get(2));
			} else if (end == 3) {
				route.add(streetDirectory.get(122));
				route.add(streetDirectory.get(84));
				route.add(streetDirectory.get(73));
				route.add(streetDirectory.get(931));
				route.add(parkingStructureDirectory.get(3));
			} else if (end == 4) {
				route.add(streetDirectory.get(122));
				route.add(streetDirectory.get(84));
				route.add(streetDirectory.get(941));
				route.add(parkingStructureDirectory.get(4));
			} else if (end == 5) {
				route.add(streetDirectory.get(113));
				route.add(streetDirectory.get(103));
				route.add(streetDirectory.get(951));
				route.add(parkingStructureDirectory.get(5));
			}
		}
		return route;
	}
	
	public static List<Intersection> findStoplights(int start, int end) {
		List<Intersection> stoplights = Collections.synchronizedList(new ArrayList<Intersection>());
		stoplights.clear();
		
		if (start == 1) {
			if (end == 2) {
				stoplights.add(intersections.get(2));
				stoplights.add(intersections.get(6));
			} else if (end == 3) {
				stoplights.add(intersections.get(2));
				stoplights.add(intersections.get(6));
			} else if (end == 4) {
				stoplights.add(intersections.get(3));
				stoplights.add(intersections.get(7));
			} else if (end == 5) {
				stoplights.add(intersections.get(2));
				stoplights.add(intersections.get(6));
				stoplights.add(intersections.get(10));
			} else if (end == 6) {
				stoplights.add(intersections.get(3));
				stoplights.add(intersections.get(4));
				stoplights.add(intersections.get(8));
				stoplights.add(intersections.get(12));
			}
		}
		else if (start == 2) {
			if (end == 1) {
				stoplights.add(intersections.get(6));
				stoplights.add(intersections.get(2));
			} else if (end == 3) {
				stoplights.add(intersections.get(6));
			} else if (end == 4) {
				stoplights.add(intersections.get(6));
				stoplights.add(intersections.get(7));
			} else if (end == 5) {
				stoplights.add(intersections.get(6));
				stoplights.add(intersections.get(10));
			} else if (end == 6) {
				stoplights.add(intersections.get(6));
				stoplights.add(intersections.get(10));
				stoplights.add(intersections.get(11));
			}
		}
		else if (start == 3) {
			if (end == 1) {
				stoplights.add(intersections.get(6));
				stoplights.add(intersections.get(2));
			} else if (end == 2) {
				stoplights.add(intersections.get(6));
			} else if (end == 4) {
				stoplights.add(intersections.get(7));
			} else if (end == 5) {
				stoplights.add(intersections.get(6));
				stoplights.add(intersections.get(10));
			} else if (end == 6) {
				stoplights.add(intersections.get(7));
				stoplights.add(intersections.get(8));
				stoplights.add(intersections.get(12));
			}
		}
		
		else if (start == 4) {
			if (end == 1) {
				stoplights.add(intersections.get(7));
				stoplights.add(intersections.get(3));
			} else if (end == 2) {
				stoplights.add(intersections.get(7));
				stoplights.add(intersections.get(6));
			} else if (end == 3) {
				stoplights.add(intersections.get(7));
			} else if (end == 5) {
				stoplights.add(intersections.get(7));
				stoplights.add(intersections.get(6));
				stoplights.add(intersections.get(10));
			} else if (end == 6) {
				stoplights.add(intersections.get(8));
				stoplights.add(intersections.get(12));
			}
		}
		else if (start == 5) {
			if (end == 1) {
				stoplights.add(intersections.get(10));
				stoplights.add(intersections.get(6));
				stoplights.add(intersections.get(2));
			} else if (end == 2) {
				stoplights.add(intersections.get(9));
				stoplights.add(intersections.get(5));
			} else if (end == 3) {
				stoplights.add(intersections.get(10));
				stoplights.add(intersections.get(6));
			} else if (end == 4) {
				stoplights.add(intersections.get(10));
				stoplights.add(intersections.get(6));
				stoplights.add(intersections.get(7));
			} else if (end == 6) {
				stoplights.add(intersections.get(10));
				stoplights.add(intersections.get(11));
			}
		}
		else if (start == 6) {
			if (end == 1) {
				stoplights.add(intersections.get(11));
				stoplights.add(intersections.get(10));
				stoplights.add(intersections.get(6));
				stoplights.add(intersections.get(2));
			} else if (end == 2) {
				stoplights.add(intersections.get(11));
				stoplights.add(intersections.get(10));
				stoplights.add(intersections.get(6));
			} else if (end == 3) {
				stoplights.add(intersections.get(12));
				stoplights.add(intersections.get(8));
				stoplights.add(intersections.get(7));
			} else if (end == 4) {
				stoplights.add(intersections.get(12));
				stoplights.add(intersections.get(8));
			} else if (end == 5) {
				stoplights.add(intersections.get(11));
				stoplights.add(intersections.get(10));
			}
		}
		return stoplights;
	}
	
	public static List<Location> defineClockwiseBusRoute() {
		List<Location> busRoute = Collections.synchronizedList(new ArrayList<Location>());
		busRoute.add(streetDirectory.get(14));
		busRoute.add(streetDirectory.get(22));
		
		busRoute.add(streetDirectory.get(824));	// 2
		
		busRoute.add(streetDirectory.get(32));
		busRoute.add(streetDirectory.get(42));
		
		busRoute.add(streetDirectory.get(832));	// 5
		
		busRoute.add(streetDirectory.get(81));
		
		busRoute.add(streetDirectory.get(862));	// 7
		
		busRoute.add(streetDirectory.get(121));
		busRoute.add(streetDirectory.get(113));
		busRoute.add(streetDirectory.get(103));
		busRoute.add(streetDirectory.get(93));
		
		busRoute.add(streetDirectory.get(843));	// 12
		
		busRoute.add(streetDirectory.get(54));
		
		busRoute.add(streetDirectory.get(813));	// 14
		return busRoute;
	}
	
	public static List<Location> defineCounterClockwiseBusRoute() {
		List<Location> busRoute = Collections.synchronizedList(new ArrayList<Location>());
		busRoute.add(streetDirectory.get(122));
		
		busRoute.add(streetDirectory.get(863));	// 1
		
		busRoute.add(streetDirectory.get(84));
		
		busRoute.add(streetDirectory.get(833));	// 3
		
		busRoute.add(streetDirectory.get(44));
		busRoute.add(streetDirectory.get(33));
		
		busRoute.add(streetDirectory.get(821));	// 6
		
		busRoute.add(streetDirectory.get(23));
		busRoute.add(streetDirectory.get(13));
		
		busRoute.add(streetDirectory.get(812));	// 9
		
		busRoute.add(streetDirectory.get(51));
		
		busRoute.add(streetDirectory.get(942));	// 11
		
		busRoute.add(streetDirectory.get(91));
		busRoute.add(streetDirectory.get(102));
		busRoute.add(streetDirectory.get(112));
		return busRoute;
	}
	
	public static List<Intersection> defineClockwiseBusStopIntersections() {
		List<Intersection> busIntersections = Collections.synchronizedList(new ArrayList<Intersection>());
		busIntersections.add(intersections.get(1));
		busIntersections.add(intersections.get(2));
		busIntersections.add(intersections.get(3));
		busIntersections.add(intersections.get(4));
		busIntersections.add(intersections.get(8));
		busIntersections.add(intersections.get(12));
		busIntersections.add(intersections.get(11));
		busIntersections.add(intersections.get(10));
		busIntersections.add(intersections.get(9));
		busIntersections.add(intersections.get(5));
		return busIntersections;
	}
	
	public static List<Intersection> defineCounterClockwiseBusStopIntersections() {
		List<Intersection> busIntersections = Collections.synchronizedList(new ArrayList<Intersection>());
		busIntersections.add(intersections.get(12));
		busIntersections.add(intersections.get(8));
		busIntersections.add(intersections.get(4));
		busIntersections.add(intersections.get(3));
		busIntersections.add(intersections.get(2));
		busIntersections.add(intersections.get(1));
		busIntersections.add(intersections.get(5));
		busIntersections.add(intersections.get(9));
		busIntersections.add(intersections.get(10));
		busIntersections.add(intersections.get(11));
		return busIntersections;
	}
	
	
}
