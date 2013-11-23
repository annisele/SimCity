package simcity.gui.market;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import simcity.Location;
import simcity.buildings.market.MarketCustomerRole;
import simcity.gui.Gui;
import simcity.interfaces.market.MarketCustomer;

public class MarketCustomerGui extends Gui {
	
	private final int CASHIER_X = 200;
	private final int CASHIER_Y = 200;
	
	
	public MarketCustomerGui(MarketCustomerRole m) {
		role = m;
	} 
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.CYAN);
		g.fillRect(x, y, SIZE, SIZE);
	}
	
	public void DoGoToCashier() {
		DoGoToLocation(CASHIER_X, CASHIER_Y);
	}

}
