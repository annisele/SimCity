package simcity.gui.market;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.market.MarketTruck;

public class MarketTruckGui extends Gui {
	private final int MARKET_X;
	private final int MARKET_Y;
	private final int COUNTER_X = 0;
	private final int COUNTER_Y = 100;
	private final int CENTER_X = 115;	
	private final int CENTER_Y = 225;
	private final int LEFT_X = -50;

	ImageIcon ii = new ImageIcon("res/market/cashier.png");
	Image img = ii.getImage();
	Image truckimage = img.getScaledInstance(70, 62,  java.awt.Image.SCALE_SMOOTH); 

	public MarketTruckGui(MarketTruck t, int xLoc, int yLoc) {
		role = t;
		MARKET_X = xLoc;
		MARKET_Y = yLoc;
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(truckimage, getX(), getY(), null);
	}
	
	public void DoGoToMarket() {
		DoGoToLocation(MARKET_X, MARKET_Y);
	}
}
