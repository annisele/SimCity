package simcity.gui.restaurantthree;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.three.RestaurantThreeWaiter;

public class RestaurantThreeWaiterGui extends Gui {
		
	private final int HOST_X = 290;
	private final int HOST_Y = 370;
	
	private final int WAITER_X = 195;
	private final int WAITER_Y = 370;
	
	public static final int WAITING_X = 240;
	public static final int WAITING_Y = 370;
	

	public static final int TABLE1_X = 80;
	public static final int TABLE1_Y = 300;
	public static final int TABLE2_X = 150;
	public static final int TABLE2_Y = 300;
	public static final int TABLE3_X = 220;
	public static final int TABLE3_Y = 300;
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
		if (tableNumber == 1) {
			DoGoToLocation(TABLE1_X, TABLE1_Y);
		}
		else if (tableNumber == 2) {
			DoGoToLocation(TABLE2_X, TABLE2_Y);
		}
		else if (tableNumber == 3) {
			DoGoToLocation(TABLE3_X, TABLE3_Y);
		}
	}
	
}