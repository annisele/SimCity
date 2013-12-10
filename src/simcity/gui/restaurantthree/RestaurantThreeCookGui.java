package simcity.gui.restaurantthree;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.three.RestaurantThreeCook;

public class RestaurantThreeCookGui extends Gui {
	
			
	private final int WINDOW1_X = 305;
	private final int WINDOW1_Y = 95;
	
	
	ImageIcon ii = new ImageIcon("res/person/tanpersondownbig.png");
	Image img = ii.getImage();


	public RestaurantThreeCookGui(RestaurantThreeCook b) {
		role = b;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(img,getX(), getY(), null);
	}
	public void DoGoToStand () {
		DoGoToLocation(WINDOW1_X, WINDOW1_Y);
	}
}