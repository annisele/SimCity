package simcity.gui.restaurantfour;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.four.RestaurantFourCustomer;

public class RestaurantFourCustomerGui extends Gui {

	private ImageIcon ii = new ImageIcon("res/person/persondownbig.png");
	private Image hostimage = ii.getImage();
	
	public static final int HOST_LOCATION_X = 290;
	public static final int HOST_LOCATION_Y = 370;
	
	public static final int TABLE1_X = 100;
	public static final int TABLE1_Y = 300;
	public static final int TABLE2_X = 170;
	public static final int TABLE2_Y = 300;
	public static final int TABLE3_X = 240;
	public static final int TABLE3_Y = 300;
	
	public RestaurantFourCustomerGui(RestaurantFourCustomer customer) {
		role = customer;
	}

	public void draw(Graphics2D g) {
		g.drawImage(hostimage, getX(), getY(), null);
	}
	
	public void DoGoToHostLocation() {
		DoGoToLocation(HOST_LOCATION_X, HOST_LOCATION_Y);
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