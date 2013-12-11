package simcity.gui.restaurantfive;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.restaurant.five.RestaurantFiveCook;

public class RestaurantFiveCookGui extends Gui {
	private final int HOME_X = 350;
	private final int HOME_Y = 100;
	private final int FRIDGE_X = 240;
	private final int FRIDGE_Y = 75;
	private final int STOVE_X = 380;
	private final int STOVE_Y = 70;
	private final int COUNTER_X = 350;
	private final int COUNTER_Y = 110;
	
	private ImageIcon ii = new ImageIcon("res/person/bluepersondownbig.png");
	private Image cookimage = ii.getImage();
	
	public RestaurantFiveCookGui(RestaurantFiveCook c) {
		role = c;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(cookimage, getX(), getY(), null);
	}
	
	public void DoGoToHome() {
		DoGoToLocation(HOME_X, HOME_Y);
	}
	
	public void DoGoToRefrigerator() {
		DoGoToLocation(FRIDGE_X, FRIDGE_Y);
	}
	
	public void DoMoveToStove() {
		DoGoToLocation(STOVE_X, STOVE_Y);
	}
	
	public void DoGoToCounter() {
		DoGoToLocation(COUNTER_X, COUNTER_Y);
	}

}
