package simcity.gui.restaurantsix;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.five.RestaurantFiveHost;
import simcity.interfaces.restaurant.six.RestaurantSixCustomer;

public class RestaurantSixCustomerGui extends Gui {

	private final int HOST_STAND_X = 100;
	private final int HOST_STAND_Y = 375;
	
	private ImageIcon ii = new ImageIcon("res/person/persondownbig.png");
	private Image hostimage = ii.getImage();
	
	public RestaurantSixCustomerGui(RestaurantSixCustomer c) {
		role = c;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(hostimage, getX(), getY(), null);
	}
	
	public void DoGoToHost() {
		DoGoToLocation(HOST_STAND_X, HOST_STAND_Y);
	}
	
	
}
