package simcity.gui.restaurantthree;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.three.RestaurantThreeWaiter;

public class RestaurantThreeWaiterGui extends Gui {
		
	private final int HOST_X = 290;
	private final int HOST_Y = 370;
	
	private final int WAITER_X = 195;
	private final int WAITER_Y = 370;
	
	public static final int WAITING_X = 240;
	public static final int WAITING_Y = 370;
	
	
	ImageIcon ii = new ImageIcon("res/person/tanpersondownbig.png");
	Image img = ii.getImage();


	public RestaurantThreeWaiterGui(RestaurantThreeWaiter b) {
		role = b;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(img,getX(), getY(), null);
	}

	public void DoGoToHost() {
		DoGoToLocation(HOST_X, HOST_Y);	
	}
	public void DoGoToStation() {
		DoGoToLocation(WAITER_X, WAITER_Y);
	}

	public void DoGoToWaitingCustomer() {
		DoGoToLocation(WAITING_X, WAITING_Y);
	}
}