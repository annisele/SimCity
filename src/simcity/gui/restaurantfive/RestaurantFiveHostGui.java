package simcity.gui.restaurantfive;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.five.RestaurantFiveHost;

public class RestaurantFiveHostGui extends Gui {

	private final int HOST_STAND_X = 50;
	private final int HOST_STAND_Y = 325;
	
	private ImageIcon ii = new ImageIcon("res/person/bluepersondownbig.png");
	private Image hostimage = ii.getImage();
	
	public RestaurantFiveHostGui(RestaurantFiveHost h) {
		role = h;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(hostimage, getX(), getY(), null);
	}
	
	public void DoGoToStand() {
		DoGoToLocation(HOST_STAND_X, HOST_STAND_Y);
	}
	
	
}
