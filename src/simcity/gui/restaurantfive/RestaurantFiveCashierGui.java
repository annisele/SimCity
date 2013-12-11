package simcity.gui.restaurantfive;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.five.RestaurantFiveCashier;

public class RestaurantFiveCashierGui extends Gui {
	private final int HOME_X = 400;
	private final int HOME_Y = 300;
	
	private ImageIcon ii = new ImageIcon("res/person/bluepersondownbig.png");
	private Image cashierimage = ii.getImage();
	
	public RestaurantFiveCashierGui(RestaurantFiveCashier c) {
		role = c;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(cashierimage, getX(), getY(), null);
	}
	
	public void DoGoToHome() {
		DoGoToLocation(HOME_X, HOME_Y);
	}

}
