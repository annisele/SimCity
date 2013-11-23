package simcity.gui.market;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import simcity.buildings.market.MarketCashierRole;
import simcity.buildings.market.MarketCustomerRole;
import simcity.gui.Gui;

public class MarketCashierGui extends Gui {
	
	 ImageIcon ii = new ImageIcon("res/market/cashier.png");
	    Image img = ii.getImage();
	    Image cashierimage = img.getScaledInstance(70, 62,  java.awt.Image.SCALE_SMOOTH); 

	public MarketCashierGui(MarketCashierRole m) {
		role = m;
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(cashierimage, x, y, null); //COMMENT this out to get old Gui Back- Josh

	}
}
