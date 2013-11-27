package simcity.gui.market;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.market.MarketTruck;

public class MarketTruckGui extends Gui {
	private final int MARKET_X;
	private final int MARKET_Y;

	ImageIcon ii = new ImageIcon("res/transportation/bus.png");
	Image img = ii.getImage();
	Image truckimage = img.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);  

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
