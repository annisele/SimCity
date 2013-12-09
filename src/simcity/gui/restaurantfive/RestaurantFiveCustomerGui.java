package simcity.gui.restaurantfive;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.five.RestaurantFiveCustomer;

public class RestaurantFiveCustomerGui extends Gui {

	private final int HOST_OFFSET = 50;
	private final int HOST_X = 100;
	private final int HOST_Y = 325;
	private boolean sit = false;
	
	private ImageIcon ii = new ImageIcon("res/person/persondownbig.png");
	private Image hostimage = ii.getImage();
	
	public RestaurantFiveCustomerGui(RestaurantFiveCustomer c) {
		role = c;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(hostimage, getX(), getY(), null);
	}
	
	public void DoGoToHost(int n) {
		DoGoToLocation(HOST_X + (HOST_OFFSET * n), HOST_Y);
	}
	
	public void DoGoToSeat() {
		sit = true;
	}
	
	public void DoGoToSeat(int x, int y) {
		if(sit) {
			DoGoToLocation(x, y);
			sit = false;
		}
		//HANDLE WHEN SIT IS FALSE?
	}
	
	
}
