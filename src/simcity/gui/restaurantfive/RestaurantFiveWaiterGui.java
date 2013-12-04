package simcity.gui.restaurantfive;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.five.RestaurantFiveWaiter;

public class RestaurantFiveWaiterGui extends Gui {

	private final int HOME_X = 100;
	private final int HOME_Y = 100;
	
	private ImageIcon ii = new ImageIcon("res/person/bluepersondownbig.png");
	private Image hostimage = ii.getImage();
	
	public RestaurantFiveWaiterGui(RestaurantFiveWaiter w) {
		role = w;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(hostimage, getX(), getY(), null);
	}
	
	public void DoGoToHome() {
		DoGoToLocation(HOME_X, HOME_Y);
	}
}
