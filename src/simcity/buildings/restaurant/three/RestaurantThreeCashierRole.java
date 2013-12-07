package simcity.buildings.restaurant.three;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Role;
import simcity.SimSystem;
import simcity.gui.restaurantthree.RestaurantThreeCashierGui;
import simcity.interfaces.restaurant.three.RestaurantThreeCashier;
import simcity.interfaces.restaurant.three.RestaurantThreeCook;
import simcity.interfaces.restaurant.three.RestaurantThreeHost;

public class RestaurantThreeCashierRole extends Role implements RestaurantThreeCashier {
	Timer timer = new Timer();
    private RestaurantThreeCook cook;
    private RestaurantThreeHost host;
    private RestaurantThreeSystem system;
	private Semaphore atDest = new Semaphore(0, true);
	public void atDestination() {
		atDest.release();	
	}
	public RestaurantThreeCashierRole(PersonAgent p) {
		person = p;
		this.gui = new RestaurantThreeCashierGui(this);
	}
	public void msgPleasePay(String marketName, double payment, int orderNum) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void exitBuilding() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void enterBuilding(SimSystem s) {
		// TODO Auto-generated method stub
		
	}

}