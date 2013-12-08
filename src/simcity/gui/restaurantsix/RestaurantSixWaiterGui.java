package simcity.gui.restaurantsix;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.six.RestaurantSixCustomer;
import simcity.interfaces.restaurant.six.RestaurantSixWaiter;

public class RestaurantSixWaiterGui extends Gui {

	private final int HOST_STAND_X = 250;
	private final int HOST_STAND_Y = 175;
	
	private ImageIcon ii = new ImageIcon("res/person/bluepersondownbig.png");
	private Image hostimage = ii.getImage();
	
	public RestaurantSixWaiterGui(RestaurantSixWaiter w) {
		role = w;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(hostimage, getX(), getY(), null);
	}
	
	public void DoGoToWaitingArea() {
		DoGoToLocation(HOST_STAND_X, HOST_STAND_Y);
	}
}
