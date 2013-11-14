package simcity.buildings.market;

import java.util.*;

public class MarketSystem {

	Map<String, Integer> inventory = Collections.synchronizedMap(new HashMap<String, Integer>());
	
	public MarketSystem() {
		//going to populate inventory in constructor for now so we don't have to worry about it
		inventory.put("chicken", 20);
		inventory.put("cheese", 30);
		inventory.put("pasta", 40);
	}
	
	public void populateInventory(Map<String, Integer> map) {
		inventory = map;
	}

	public Map<String, Integer> fillOrder(Map<String, Integer> orders) {
		Iterator it = orders.entrySet().iterator();
		Map<String, Integer> toReturn = new HashMap<String, Integer>(); //when we implement returning partial orders, use this

		//synchronize so that only one worker can access at a time
		synchronized(orders) {
			//loop through requested list of orders
			while (it.hasNext()) {
				String key = (String)it.next(); //getting the item name
				if(inventory.containsKey(key)) {
					//make sure the inventory has more or the same number of that item
					//.get(key) returns the value, or amount of the key item
					if(inventory.get(key) >= orders.get(key)) {
						toReturn.put(key, orders.get(key)); //if there is enough in inventory, puts the 
						//ordered amount on a toReturn map

						//removes map item and puts a new map item with same key/name, 
						//and the decreased amount/value (essentially removes items from inventory)
						int oldAmount = inventory.remove(key);
						inventory.put(key, oldAmount - orders.get(key));
					}
					else {
						return null; //for now will return null whenever full order can't be filled
					}
				}

				it.remove(); // avoids a ConcurrentModificationException
			}
			return toReturn; //return the completely filled order
		}
	}


}
