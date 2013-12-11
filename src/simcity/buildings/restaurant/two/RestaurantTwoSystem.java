package simcity.buildings.restaurant.two;

import simcity.Role;
import simcity.SimSystem;
import simcity.buildings.bank.BankHostRole;
import simcity.buildings.market.MarketCashierRole;
import simcity.buildings.market.MarketCashierRole.MarketState;
import simcity.buildings.restaurant.two.RestaurantTwoHostRole.R2State;
import simcity.gui.SimCityGui;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;

import java.awt.*;

import simcity.gui.SimCityGui;
import simcity.gui.bank.BankHostGui;
import simcity.gui.house.HouseControlPanel;
import simcity.gui.restaurantone.RestaurantOneAnimationPanel;
import simcity.gui.restaurantone.RestaurantOneControlPanel;
import simcity.gui.restauranttwo.RestaurantTwoAnimationPanel;
import simcity.gui.restauranttwo.RestaurantTwoControlPanel;
import simcity.gui.restauranttwo.RestaurantTwoHostGui;
import simcity.interfaces.bank.BankHost;
import simcity.interfaces.house.HouseInhabitant;
import simcity.interfaces.market.MarketCashier;
import simcity.interfaces.market.MarketCustomer;
import simcity.interfaces.market.MarketTruck;
import simcity.interfaces.market.MarketWorker;
import simcity.interfaces.restaurant.one.*;
import simcity.interfaces.restaurant.two.RestaurantTwoCashier;
import simcity.interfaces.restaurant.two.RestaurantTwoCook;
import simcity.interfaces.restaurant.two.RestaurantTwoCustomer;
import simcity.interfaces.restaurant.two.RestaurantTwoHost;
import simcity.interfaces.restaurant.two.RestaurantTwoWaiter;

public class RestaurantTwoSystem extends SimSystem {

	private SimCityGui scg;
	// public RestaurantControlPanel controlPanel;
	private List<RestaurantTwoCustomer> customers = new ArrayList<RestaurantTwoCustomer>();
	private List<RestaurantTwoWaiter> waiters = new ArrayList<RestaurantTwoWaiter>();
	private RestaurantTwoOrderWheel owheel;
	private RestaurantTwoHost host;
	private RestaurantTwoCook cook;
	private RestaurantTwoComputer computer;
	private RestaurantTwoCashier cashier;
	private boolean hack;
	private double lowestprice;
	public Map<Integer,Boolean> waitingSpots= new HashMap<Integer, Boolean>();
	public Map<Integer,Boolean> waiterSpots= new HashMap<Integer, Boolean>();


	public RestaurantTwoSystem(SimCityGui scgui,RestaurantTwoComputer c) {
		super(scgui);
		this.computer=c;
		computer.setSystem(this);
		super.setAnimationPanel(new RestaurantTwoAnimationPanel());
		super.setControlPanel(new RestaurantTwoControlPanel(c));
		this.owheel = new RestaurantTwoOrderWheel();
		this.lowestprice=6;
		waitingSpots.put(0,false);
		waitingSpots.put(1,false);
		waitingSpots.put(2,false);
		waitingSpots.put(3,false);
		waiterSpots.put(0,false);
		waiterSpots.put(1,false);
		waiterSpots.put(2,false);
		waiterSpots.put(3,false);
		waiterSpots.put(4,false);
		waiterSpots.put(5,false);
		((RestaurantTwoControlPanel)controlPanel).updateFoodDisplay(computer.getInventory());
		

	}

	public RestaurantTwoHost getR2Host() {
		return host;
	}
	public RestaurantTwoCook getR2Cook() {
		return cook;
	}
	public RestaurantTwoCashier getR2Cashier() {
		return cashier;
	}
	 protected void setLowestPrice(double lp) {
	         this.lowestprice = lp;
	 }
	 protected double getLowestPrice(){
         return lowestprice ;
 }
	/*
	public void setRestaurantTwoHost(RestaurantTwoHostRole h) {
		this.host = h;
		RestaurantTwoHostGui hostGui = new RestaurantTwoHostGui(h);
		animationPanel.addGui(hostGui);
	}
	 */
		public void updateFoodDisplay(RestaurantTwoCook c) {
			((RestaurantTwoControlPanel)controlPanel).updateFoodDisplay(c.getFoodStock());
		}
		public void changeFoodDisplay() {
			((RestaurantTwoControlPanel)controlPanel).updateFoodDisplay(computer.getInventory());
		}
	public boolean msgEnterBuilding(Role role) {
		animationPanel.addGui(role.getGui());
		//System.out.println("gui: "+role.getGui());
		//System.out.println("the role "+role+" has a gui: "+role.getGui());
		
		if(role instanceof RestaurantTwoHost) {
			System.out.println("HOST IZ HERE");
			if (host == null) {
				System.out.println("HOST IZ HERE YO");
				host = (RestaurantTwoHost) role;
				return true;
			}
		}
		else if (host != null&&host.getR2State()==R2State.running) {
				
		if(role instanceof RestaurantTwoCustomer) {
					System.out.println(""+host+"  "+cashier+"   "+cook);
					((RestaurantTwoCustomer) role).setCook(cook);
					((RestaurantTwoCustomer) role).setHost(host);
					((RestaurantTwoCustomer) role).setCashier(cashier);
			
					if (hack==true){
						((RestaurantTwoCustomer) role).hack_chicken();
					}
					customers.add((RestaurantTwoCustomer) role);
				}
				else if(role instanceof RestaurantTwoCashier) {
					cashier = (RestaurantTwoCashier) role;
				}
				else if(role instanceof RestaurantTwoCook) {
					((RestaurantTwoCook)role).setOrderWheel(owheel);
					((RestaurantTwoCook)role).setCashier(cashier);
					cook = (RestaurantTwoCook) role;
				}
				else if(role instanceof RestaurantTwoWaiter) {
					if(role instanceof RestaurantTwoSharedDataWaiterRole){
						//System.out.println("SHARED DATA");
						((RestaurantTwoWaiter) role).setOrderWheel(owheel);
					}
					((RestaurantTwoWaiter) role).setHost(host);
					((RestaurantTwoWaiter) role).setCashier(cashier);
					((RestaurantTwoWaiter) role).setCook(cook);
					if(waiterSpots.containsValue(false)){
						synchronized(waiterSpots){

							for(Entry<Integer, Boolean> entry : waiterSpots.entrySet()){
								if(entry.getValue()==false){
									((RestaurantTwoWaiter) role).setSpot(entry.getKey());
									//g.Start(entry.getKey());
									waiterSpots.put(entry.getKey(), true);
									break;
								}
							}
						}
					}
					host.addWaiter((RestaurantTwoWaiter) role);
					waiters.add((RestaurantTwoWaiter) role);
				}
			
			return true;
		}
		return false;
		
	}
	public void EveryoneLeave(){
		System.out.println("HEY");
		if(customers.size() == 0 ){
			System.out.println("HEY2");
		if(waiters.size() == 0 ||cashier==null||cook==null){
			System.out.println("HEY3");
			host.msgLeaveWork();
		}
		if(cashier!=null){
			cashier.msgLeaveWork();
			if(waiters.size() == 0 |cook==null){
				host.msgLeaveWork();
			}
		}
		if(cook!=null){
			cook.msgLeaveWork();
			if(waiters.size() == 0 |cashier==null){
			host.msgLeaveWork();
		}
		}
		for(RestaurantTwoWaiter w: waiters){
			w.msgLeaveWork();
		}
		}
	}
	public void exitBuilding(Role role) {
		animationPanel.removeGui(role.getGui());
		if(role instanceof RestaurantTwoCustomer) {
			customers.remove((RestaurantTwoCustomer) role);
			if(customers.size() == 0 && host.getR2State() == R2State.closed) {
				cashier.msgLeaveWork();
				cook.msgLeaveWork();
				for(RestaurantTwoWaiter w: waiters){
					w.msgLeaveWork();
				}
			}
			
		}
		else if(role instanceof RestaurantTwoWaiter) {
			waiters.remove((RestaurantTwoWaiter) role);
			if(waiters.size() == 0 && host.getR2State() == R2State.closed) {
				host.msgLeaveWork();
			}
		}
		else if(role instanceof RestaurantTwoCashier) {
			cashier=null;
		}
		else if(role instanceof RestaurantTwoCook) {
			cook=null;
		}
		else if(role instanceof RestaurantTwoHost) {
			System.out.println("HOST LEAVE");
			host=null;
		}

	}

	public void hackr2() {
		hack=true;
		// TODO Auto-generated method stub
		
	}
	
	public boolean isOpen() {
		return (host != null);
	}


}
