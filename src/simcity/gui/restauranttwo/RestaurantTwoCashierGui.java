package simcity.gui.restauranttwo;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.market.MarketCashier;
import simcity.interfaces.restaurant.two.RestaurantTwoCashier;

public class RestaurantTwoCashierGui extends Gui {

	

	ImageIcon ii = new ImageIcon("res/market/cashier.png");
	Image img = ii.getImage();
	Image image = img.getScaledInstance(70, 62,  java.awt.Image.SCALE_SMOOTH); 

	public RestaurantTwoCashierGui(RestaurantTwoCashier m) {
		role = m;
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, getX(), getY(), null);
	}

	public void DoGoToCenter() {
		DoGoToLocation(50, 60);
	}

}
