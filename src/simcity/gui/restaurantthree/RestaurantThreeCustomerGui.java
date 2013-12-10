package simcity.gui.restaurantthree;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.three.RestaurantThreeCustomer;

public class RestaurantThreeCustomerGui extends Gui {
		
	
	private final int HOST_OFFSET = 50;
	private final int WINDOW1_X = 100;
	private final int WINDOW1_Y = 325;
	
	
	ImageIcon ii = new ImageIcon("res/person/persondownbig.png");
	Image img = ii.getImage();


	public RestaurantThreeCustomerGui(RestaurantThreeCustomer b) {
		role = b;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(img,getX(), getY(), null);
	}
	public void DoGoToHost(int n) {
		DoGoToLocation(WINDOW1_X+ (HOST_OFFSET * n), WINDOW1_Y);
	}

	public void DoGoToSeat(int tableNumber) {
		// TODO Auto-generated method stub
		
	}
}