package simcity.gui.restaurantone;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import simcity.buildings.restaurant.one.RestaurantOneCustomerRole;
import simcity.gui.Gui;
import simcity.interfaces.restaurant.one.RestaurantOneCustomer;

public class RestaurantOneCustomerGui extends Gui{


	private final int SIZE = 20;
	
	 private ImageIcon ii = new ImageIcon("res/person/bluepersondownbig.png");
	    private Image hostimage = ii.getImage();
	
	public RestaurantOneCustomerGui(RestaurantOneCustomer cust) {
		role = cust;
		
		
	}
	


	@Override
	public void draw(Graphics2D g) {
    	g.drawImage(hostimage,getX(), getY(), null);

	}



	public void DoGoToFront() {
		DoGoToLocation(200, 200);
	}

	public void DoGoToSeat() {
		// TODO Auto-generated method stub
		
	}

	public void DoExitRestaurant() {
		// TODO Auto-generated method stub
		
	}
	
}