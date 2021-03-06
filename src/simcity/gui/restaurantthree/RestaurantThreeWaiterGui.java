package simcity.gui.restaurantthree;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.three.RestaurantThreeWaiter;

public class RestaurantThreeWaiterGui extends Gui {
		
	private final int HOST_X = 290;
	private final int HOST_Y = 370;
	
	private final int WAITER_X = 185;
	private final int WAITER_Y = 370;
	
	
	private final int COOK_X = 320;
	private final int COOK_Y = 160;

	public static final int WAITING_X = 85;
	public static final int WAITING_Y = 410;

	public static final int TABLE1_X = 80;
	public static final int TABLE1_Y = 290;
	public static final int TABLE2_X = 150;
	public static final int TABLE2_Y = 290;
	public static final int TABLE3_X = 220;
	public static final int TABLE3_Y = 290;
	ImageIcon ii = new ImageIcon("res/person/tanpersondownbig.png");
	Image img = ii.getImage();


	public RestaurantThreeWaiterGui(RestaurantThreeWaiter b) {
		role = b;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(img,getX(), getY(), null);
	}

	public void DoGoToHost() {
		DoGoToLocation(HOST_X, HOST_Y);	
	}
	public void DoGoToStation() {
		DoGoToLocation(WAITER_X, WAITER_Y);
	}

	public void DoGoToWaitingCustomer() {
		DoGoToLocation(WAITING_X, WAITING_Y);
	}
	public void DoGoToTable(int tableNumber) {
		if (tableNumber == 0) {
			DoGoToLocation(TABLE1_X, TABLE1_Y);
		}
		else if (tableNumber == 1) {
			DoGoToLocation(TABLE2_X, TABLE2_Y);
		}
		else if (tableNumber == 2) {
			DoGoToLocation(TABLE3_X, TABLE3_Y);
		}
	}

	public void DoTakeOrder(int tableNumber) {
		if (tableNumber == 0) {
			DoGoToLocation(TABLE1_X, TABLE1_Y);
		}
		else if (tableNumber == 1) {
			DoGoToLocation(TABLE2_X, TABLE2_Y);
		}
		else if (tableNumber == 2) {
			DoGoToLocation(TABLE3_X, TABLE3_Y);
		}
	}

	public void DoGoToCook() {
		DoGoToLocation(COOK_X, COOK_Y);
	}

	public void DoGoToKitchen() {
		DoGoToLocation(300,80);
	}
	
}