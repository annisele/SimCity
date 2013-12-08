package simcity.gui.restaurantthree;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.three.RestaurantThreeWaiter;

public class RestaurantThreeWaiterGui extends Gui {
		
	private final int CORRIDOR_X = 15;
	private final int CORRIDOR_Y = 85;
			;
	private final int WINDOW1_X = 105;
	private final int WINDOW1_Y = 103;
	
	private final int WINDOW2_X = 200;
	private final int WINDOW2_Y = 103;
		
	private final int WINDOW3_X = 280;
	private final int WINDOW3_Y = 103;

	private final int HOST_X = 250;
	private final int HOST_Y = 400;
	
	ImageIcon ii = new ImageIcon("res/person/tanpersondownbig.png");
	Image img = ii.getImage();


	public RestaurantThreeWaiterGui(RestaurantThreeWaiter b) {
		role = b;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(img,getX(), getY(), null);
	}

	public void DoGoToHome() {
		DoGoToLocation(CORRIDOR_X, CORRIDOR_Y);
	}

}