package simcity.gui.market;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.market.MarketCustomer;

public class MarketCustomerGui extends Gui {

	private final int CASHIER_X = 169;
	private final int CASHIER_Y = 99;
	
	private ImageIcon i = new ImageIcon("res/market/item.png");
	private Image img1 = i.getImage();
	private Image itemimage = img1.getScaledInstance(18, 18, java.awt.Image.SCALE_SMOOTH);
		
	private ImageIcon ii = new ImageIcon("res/person/persondownbig.png");
	private Image img = ii.getImage();
	private boolean carry = false;
	
	public MarketCustomerGui(MarketCustomer m) {
		role = m;
	}

	@Override
	public void draw(Graphics2D g) {
		if(carry) {
			g.drawImage(itemimage, getX(), getY() + 30, null);
		}
		g.drawImage(img, getX(), getY(), null); 
	}

	public void carryItem(boolean c) {
		carry = c;
	}
	
	public void DoGoToCashier() {
		DoGoToLocation(CASHIER_X, CASHIER_Y);
	}

}
