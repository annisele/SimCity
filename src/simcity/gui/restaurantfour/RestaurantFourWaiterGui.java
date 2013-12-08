package simcity.gui.restaurantfour;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.four.RestaurantFourWaiter;

public class RestaurantFourWaiterGui extends Gui {
        
	private ImageIcon ii = new ImageIcon("res/person/bluepersondownbig.png");
	private Image hostimage = ii.getImage();
	
	public static final int HOST_LOCATION_X = 290;
	public static final int HOST_LOCATION_Y = 370;
	
	public RestaurantFourWaiterGui(RestaurantFourWaiter waiter) {
		role = waiter;
	}

	public void draw(Graphics2D g) {
		g.drawImage(hostimage, getX(), getY(), null);
	}
	
	public void DoGoToHostLocation() {
		DoGoToLocation(HOST_LOCATION_X, HOST_LOCATION_Y);
	}
        
}