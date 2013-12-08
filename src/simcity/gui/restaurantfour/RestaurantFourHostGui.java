package simcity.gui.restaurantfour;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.four.RestaurantFourHost;

public class RestaurantFourHostGui extends Gui {

	private ImageIcon ii = new ImageIcon("res/person/bluepersondownbig.png");
	private Image hostimage = ii.getImage();
	
	public static final int HOST_STATION_X = 335;
	public static final int HOST_STATION_Y = 370;
	
	public RestaurantFourHostGui(RestaurantFourHost host) {
		role = host;
	}

	public void draw(Graphics2D g) {
		g.drawImage(hostimage, getX(), getY(), null);
	}
	
	public void DoGoToHostStation() {
		DoGoToLocation(HOST_STATION_X, HOST_STATION_Y);
	}
	
}