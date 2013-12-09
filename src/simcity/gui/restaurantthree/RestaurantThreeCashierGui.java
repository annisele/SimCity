package simcity.gui.restaurantthree;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.three.RestaurantThreeCashier;

public class RestaurantThreeCashierGui extends Gui {
	
	private final int CASHIER_X = 350;
	private final int CASHIER_Y = 375;

	
	ImageIcon ii = new ImageIcon("res/person/tanpersondownbig.png");
	Image img = ii.getImage();


	public RestaurantThreeCashierGui(RestaurantThreeCashier b) {
		role = b;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(img,getX(), getY(), null);
	}
	public void DoGoToStand () {
		DoGoToLocation(CASHIER_X, CASHIER_Y);
	}

}