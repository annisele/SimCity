package simcity.gui.restaurantthree;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.three.RestaurantThreeCustomer;

public class RestaurantThreeCustomerGui extends Gui {
		
	
	private final int HOST_OFFSET = 50;
	private final int WINDOW1_X = 100;
	private final int WINDOW1_Y = 325;
	
	private final int WINDOW2_X = 200;
	private final int WINDOW2_Y = 103;
		
	private final int WINDOW3_X = 280;
	private final int WINDOW3_Y = 103;

	private final int HOST_X = 250;
	private final int HOST_Y = 400;
	
	ImageIcon ii = new ImageIcon("res/person/persondownbig.png");
	Image img = ii.getImage();


	public RestaurantThreeCustomerGui(RestaurantThreeCustomer b) {
		role = b;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(img,getX(), getY(), null);
	}
	public void DoGoToHost(int n) {
		DoGoToLocation(WINDOW1_X + (HOST_OFFSET * n), WINDOW1_Y);
	}
}