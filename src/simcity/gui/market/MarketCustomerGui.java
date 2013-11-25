package simcity.gui.market;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.market.MarketCustomer;

public class MarketCustomerGui extends Gui {

	private final int CASHIER_X = 169;
	private final int CASHIER_Y = 99;
	private ImageIcon ii = new ImageIcon("res/person/persondownbig.png");
	private Image img = ii.getImage();

	public MarketCustomerGui(MarketCustomer m) {
		role = m;
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img, getX(), getY(), null); 
	}

	public void DoGoToCashier() {
		DoGoToLocation(CASHIER_X, CASHIER_Y);
	}

}
