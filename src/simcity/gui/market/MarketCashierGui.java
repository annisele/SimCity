package simcity.gui.market;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import simcity.gui.Gui;
import simcity.interfaces.market.MarketCashier;

public class MarketCashierGui extends Gui {

	private final int CASH_REG_X = 60;
	private final int CASH_REG_Y = 95;
	private final int COUNTER_X = 0;
	private final int COUNTER_Y = 100;
	private final int CENTER_X = 115;	
	private final int CENTER_Y = 225;
	private final int LEFT_X = -50;
	private final int ITEM_COUNTER_X = 50;
	private final int ITEM_COUNTER_Y = 160;
	private int numCounterItems = 0;
	private boolean carry = false;
	
	private ImageIcon i = new ImageIcon("res/market/item.png");
	private Image img = i.getImage();
	private Image itemimage = img.getScaledInstance(18, 18, java.awt.Image.SCALE_SMOOTH);
	
	private ImageIcon ii = new ImageIcon("res/person/bluepersondownbig.png");
	private Image cashierimage = ii.getImage();

	public MarketCashierGui(MarketCashier m) {
		role = m;
		size_x = 70;
		size_y = 62;
	}
	
	public void addItemToCounter() {
		numCounterItems++;
	}
	
	public void removeItemFromCounter() {
		numCounterItems--;
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(cashierimage, getX(), getY(), null);
		if(numCounterItems > 0) {
			g.drawImage(itemimage, ITEM_COUNTER_X, ITEM_COUNTER_Y, null);
		}
		if(carry) {
			g.drawImage(itemimage, getX(), getY() + 30, null);
		}
	}
	
	public void DoGoToCashRegister() {
		DoGoToLocation(CASH_REG_X, CASH_REG_Y);
	}
	
	public void DoGoToCenter() {
		DoGoToLocation(CENTER_X, CENTER_Y);
	}
	
	public void DoGoToLeftCenter() {
		DoGoToLocation(LEFT_X, CENTER_Y);
	}
	
	public void DoGoToLeftTop() {
		DoGoToLocation(LEFT_X, CASH_REG_Y);
	}
	
	public void DoGoToCounter() {
		DoGoToLocation(COUNTER_X, COUNTER_Y);
	}
	
	public void carryItem(boolean c) {
		carry = c;
	}
}
