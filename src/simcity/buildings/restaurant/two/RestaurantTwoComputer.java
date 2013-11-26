package simcity.buildings.restaurant.two;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simcity.buildings.market.MarketSystem;
import simcity.buildings.restaurant.two.RestaurantTwoCashierRole.order;

public class RestaurantTwoComputer {
	protected Inventory inventory; 
	private double money = 400;
	public Map<String,Double> Menu= new HashMap<String, Double>();
	public double balance=0;
	public List<mymarket> markets
	= Collections.synchronizedList(new ArrayList<mymarket>());
	public List<order> owed_markets
	= Collections.synchronizedList(new ArrayList<order>());
	public List<order> bad_orders
	= Collections.synchronizedList(new ArrayList<order>());
	
	
	public RestaurantTwoComputer( int st, int ch, int sa, int pi) {
		inventory= new Inventory(st,ch,sa,pi);
		Menu.put("chicken",10.99);	
		Menu.put("steak",15.99);
		Menu.put("salad",5.99);
		Menu.put("pizza",8.99);
		balance=500;
		//hack
	}
	public class Inventory{
		int steak;
		boolean steak_low=false;
		boolean steak_gone=false;
		int chicken;
		boolean chicken_low=false;
		boolean chicken_gone=false;
		int salad;
		boolean salad_low=false;
		boolean salad_gone=false;
		int pizza;
		boolean pizza_low=false;
		boolean pizza_gone=false;
		
		Inventory( int st, int ch, int sa, int pi ){
		steak=st;
		chicken=ch;
		salad=sa;
		pizza=pi;
		
		}
	}
	protected void setInventory(int st, int ch, int sa, int pi) {
		inventory.steak=st;
		inventory.chicken=ch;
		inventory.salad=sa;
		inventory.pizza=pi;
	}
		public class mymarket{
		public double bill=0;
		MarketSystem m;
		public String name;
		public double debt=0;
		
		mymarket(MarketSystem market){
			m =market;
			name= market.getName();
		}
	}
	protected String checkMarket(){
		synchronized(markets){
			for(mymarket m: markets){
				if(m.debt>0){
					if(balance>m.debt){
						balance-=m.debt;
						System.out.println("$"+m.debt+" has been paid to "+m.name);
						return m.name;
					}
					if(balance<=m.debt){
						return null;
					}
				}
				}
			}
		return null;
	}
	
	protected void removeFromMenu(String choice){
		Menu.remove(choice);
		System.out.println(""+choice+" has been removed from Menu.");
		if(choice.equals("steak")){
			inventory.steak_gone=true;
		}
		if(choice.equals("chicken")){
			inventory.chicken_gone=true;
		}
		if(choice.equals("salad")){
			inventory.salad_gone=true;
		}
		if(choice.equals("pizza")){
			inventory.pizza_gone=true;
		}
	}
	protected void addToMenu(String choice){
		System.out.println(""+choice+" has been added to Menu");
		if(choice.equals("steak")){
			Menu.put("steak",15.99);
			inventory.steak_gone=false;
		}
		if(choice.equals("chicken")){
			Menu.put("chicken",11.99);
			inventory.chicken_gone=false;
		}
		if(choice.equals("salad")){
			Menu.put("salad",5.99);
			inventory.salad_gone=false;
		}
		if(choice.equals("pizza")){
			Menu.put("pizza",8.99);
			inventory.pizza_gone=false;
		}
	}
	protected double getMoney() {
		return balance;
	}
	protected void addMoney(double payment) {
		balance += payment;
	}
	protected void subtractMoney(double payment) {
		balance -= payment;
	}
	public void addMarket(MarketSystem m){
		mymarket temp= new mymarket(m);
		markets.add(temp);
	}
}
