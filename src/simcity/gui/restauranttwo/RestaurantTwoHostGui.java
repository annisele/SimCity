package simcity.gui.restauranttwo;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import simcity.gui.Gui;
import simcity.interfaces.*;

import javax.swing.ImageIcon;

import simcity.interfaces.restaurant.two.RestaurantTwoCustomer;
import simcity.interfaces.restaurant.two.RestaurantTwoHost;
import simcity.interfaces.restaurant.two.RestaurantTwoWaiter;

public class RestaurantTwoHostGui extends Gui{
	private final int HOST_X = 300;
	private final int HOST_Y = 400;
	static final int NTABLES = 3;//a global for the number of tables.
	//Notice that we implement waitingCustomers using ArrayList, but type it
	//with List semantics.

	

	ImageIcon ii = new ImageIcon("res/restaurant/two/hostdown.png");
	Image img = ii.getImage();
	Image image = img.getScaledInstance(30, 35,  java.awt.Image.SCALE_SMOOTH); 

	public RestaurantTwoHostGui (RestaurantTwoHost h) {
		role = h;
		
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, getX(), getY(), null); 
	}

	public void DoGoToHostPosition() {
		System.out.println("host gui goes to position");
		DoGoToLocation(400, 400);
	}
	
	
}