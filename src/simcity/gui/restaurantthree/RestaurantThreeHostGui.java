package simcity.gui.restaurantthree;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.three.RestaurantThreeHost;

public class RestaurantThreeHostGui extends Gui {
	
	private final int HOST_X = 50;
	private final int HOST_Y = 375;
	
	ImageIcon ii = new ImageIcon("res/person/tanpersondownbig.png");
	Image img = ii.getImage();


	public RestaurantThreeHostGui(RestaurantThreeHost b) {
		role = b;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(img,getX(), getY(), null);
	}
	public void DoGoToStand() {
		DoGoToLocation(HOST_X, HOST_Y);
	}

}