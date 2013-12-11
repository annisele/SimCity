package simcity.buildings.market; 

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;

/************************
 * Market System - Holds the inventory and money of the market.
 * @author rebeccahao
 *
 */
public class MarketComputer {

	private Map<String, Integer> inventory = Collections.synchronizedMap(new HashMap<String, Integer>());
	private double money = 400;
	private Map<String, Double> prices = Collections.synchronizedMap(new HashMap<String, Double>());
	private MarketSystem market;
	public MarketComputer() {
		
		inventory.put("chicken", 60);
		inventory.put("steak", 100);
		inventory.put("salad", 40);
		inventory.put("pizza", 50);
		
		prices.put("chicken", 7.0);
		prices.put("steak", 7.0);
		prices.put("salad", 7.0);
		prices.put("pizza", 7.0);
		
	}
	public void setSystem(MarketSystem s){
		this.market=s;
	}
	
	public void setInventory(Map<String, Integer> map) {
		inventory = map;
		market.updateFoodDisplay();
	}
	public Map<String, Integer> getInventory(){
		return inventory;
	}
	
	public Map<String, Double> getPrices() {
		return prices;
	}

	public Map<String, Integer> fillOrder(Map<String, Integer> orders) {
		
		Map<String, Integer> toReturn = new HashMap<String, Integer>(); //when we implement returning partial orders, use this
		
		Set<String> keys = orders.keySet();
		for (String key : keys) {
			if(inventory.containsKey(key)) {
				//make sure the inventory has enough of that item
				if(inventory.get(key) >= orders.get(key)) {
					toReturn.put(key, orders.get(key));
					
					//removes map item and puts a new map item with same key/name, 
					//and the decreased amount/value (essentially removes items from inventory)
					int oldAmount = inventory.remove(key);
					inventory.put(key, oldAmount - orders.get(key));
				}
				else {
					return null; //change this later, returns null when can't complete order
				}
			}
		}
		AlertLog.getInstance().logMessage(AlertTag.MARKET1, "MarketComp:", "ORDER HAS SIZE "+toReturn.size());


		return toReturn;
		
	}

	public void addMoney(double payment) {
		money += payment;
	}
	
	public double getMoney() {
		return money;
	}

}
