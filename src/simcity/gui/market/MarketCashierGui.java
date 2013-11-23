package simcity.gui.market;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import simcity.gui.Gui;

public class MarketCashierGui extends Gui {

	@Override
	public void updatePosition() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.CYAN);
		g.fillRect(10, 10, 20, 20);
	}

	@Override
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean contains(Point point) {
		// TODO Auto-generated method stub
		return false;
	}

}
