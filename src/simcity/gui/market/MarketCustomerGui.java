package simcity.gui.market;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.Location;
import simcity.buildings.market.MarketCustomerRole;
import simcity.gui.Gui;
import simcity.interfaces.market.MarketCustomer;

public class MarketCustomerGui extends Gui {
	
	private final int CASHIER_X = 169;
	private final int CASHIER_Y = 99;
	

	 ImageIcon ii = new ImageIcon("res/person/persondownbig.png");
	    Image img = ii.getImage();
	
	
	public MarketCustomerGui(MarketCustomerRole m) {
		role = m;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img, x, y, null); //COMMENT this out to get old Gui Back- Josh
	}
	
	public void DoGoToCashier() {
		DoGoToLocation(CASHIER_X, CASHIER_Y);
	}

}
