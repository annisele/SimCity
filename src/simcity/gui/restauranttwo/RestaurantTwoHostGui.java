package simcity.gui.restauranttwo;

import java.awt.Graphics2D;
import java.awt.Image;

import simcity.gui.Gui;
import simcity.interfaces.*;

import javax.swing.ImageIcon;

import simcity.interfaces.restaurant.two.RestaurantTwoHost;

public class RestaurantTwoHostGui extends Gui{
	private final int HOST_X = 300;
	private final int HOST_Y = 400;
	
	ImageIcon ii = new ImageIcon("res/person/persondownbig.png");
	Image img = ii.getImage();

	public RestaurantTwoHostGui (RestaurantTwoHost h) {
		role = h;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(img, HOST_X, HOST_Y, null); 
	}

	public void DoGoToHostPosition() {
		DoGoToLocation(HOST_X, HOST_Y);
	}
	
	
}