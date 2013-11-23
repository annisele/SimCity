package simcity.gui.market;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import simcity.buildings.market.MarketCashierRole;
import simcity.buildings.market.MarketCustomerRole;
import simcity.gui.Gui;

public class MarketCashierGui extends Gui {

	public MarketCashierGui(MarketCashierRole m) {
		role = m;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(x, y, 20, 20);
	}
}
