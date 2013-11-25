package simcity.gui.restauranttwo;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import simcity.buildings.restaurant.two.RestaurantTwoHostRole;
import simcity.gui.Gui;

public class RestaurantTwoHostGui extends Gui{
	private final int HOST_X = 300;
	private final int HOST_Y = 400;
	
	ImageIcon ii = new ImageIcon("res/person/persondownbig.png");
	Image img = ii.getImage();

	public RestaurantTwoHostGui (RestaurantTwoHostRole h) {
		role = h;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(img, x, y, null); 
	}

	public void DoGoToHostPosition() {
		DoGoToLocation(HOST_X, HOST_Y);
	}
	
	
}