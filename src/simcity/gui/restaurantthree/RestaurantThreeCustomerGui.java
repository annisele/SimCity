package simcity.gui.restaurantthree;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.three.RestaurantThreeCustomer;

public class RestaurantThreeCustomerGui extends Gui {
		
	
	private final int HOST_OFFSET = 50;
	private final int WINDOW1_X = 55;
	private final int WINDOW1_Y = 375;


	public static final int TABLE1_X = 80;
	public static final int TABLE1_Y = 300;
	public static final int TABLE2_X = 150;
	public static final int TABLE2_Y = 300;
	public static final int TABLE3_X = 220;
	public static final int TABLE3_Y = 300;
	
	ImageIcon ii = new ImageIcon("res/person/persondownbig.png");
	Image img = ii.getImage();


	public RestaurantThreeCustomerGui(RestaurantThreeCustomer b) {
		role = b;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(img,getX(), getY(), null);
	}
	public void DoGoToHost(int n) {
		DoGoToLocation(WINDOW1_X+ (HOST_OFFSET * n), WINDOW1_Y);
	}

	public void DoGoToSeat(int tableNumber) {
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
}