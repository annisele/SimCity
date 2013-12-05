package simcity.gui.restaurantsix;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.six.RestaurantSixCook;
import simcity.interfaces.restaurant.six.RestaurantSixCustomer;

public class RestaurantSixCookGui extends Gui {

	private final int HOST_STAND_X = 350;
	private final int HOST_STAND_Y = 175;
	
	private ImageIcon ii = new ImageIcon("res/person/bluepersondownbig.png");
	private Image hostimage = ii.getImage();
	
	public RestaurantSixCookGui(RestaurantSixCook c) {
		role = c;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(hostimage, getX(), getY(), null);
	}
	
	public void DoGoToKitchen() {
		DoGoToLocation(HOST_STAND_X, HOST_STAND_Y);
	}
}
