package simcity.gui.restaurantfive;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.five.RestaurantFiveCustomer;
import simcity.interfaces.restaurant.five.RestaurantFiveWaiter;

public class RestaurantFiveWaiterGui extends Gui {

	private final int HOME_X = 100;
	private final int HOME_Y = 100;
	private final int HOST_X = 100;
	private final int HOST_Y = 285;
	private final int TABLE1_X = 200;
	private final int TABLE_Y = 200;
	
	private ImageIcon ii = new ImageIcon("res/person/bluepersondownbig.png");
	private Image hostimage = ii.getImage();
	
	public RestaurantFiveWaiterGui(RestaurantFiveWaiter w) {
		role = w;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(hostimage, getX(), getY(), null);
	}
	
	public void DoGoToHome() {
		DoGoToLocation(HOME_X, HOME_Y);
	}
	
	public void DoGoToHost() {
		DoGoToLocation(HOST_X, HOST_Y);
	}
	
	public void DoGoToTable(int table) {
		if(table == 1) {
			DoGoToLocation(TABLE1_X, TABLE_Y);
		}
	}
	
	public void DoSeatCustomer(RestaurantFiveCustomer c, int table) {
		if(table == 1) {
			DoGoToLocation(TABLE1_X, TABLE_Y);
			((RestaurantFiveCustomerGui) c.getGui()).DoGoToSeat(TABLE1_X + size_x, TABLE_Y + (size_y/4));
		}
	}
}
