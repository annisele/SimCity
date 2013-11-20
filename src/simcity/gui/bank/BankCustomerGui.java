package simcity.gui.bank;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import simcity.buildings.bank.BankCustomerRole;
import simcity.gui.Gui;

public class BankCustomerGui implements Gui{

	private BankCustomerRole role = null;
	private int x = 0;
	private int y = 0;
	private int xDest = 0;
	private int yDest = 0;
	private final int SIZE = 20;
	
	public BankCustomerGui() {
		
	}
	public void updatePosition() {
		if(xDest > x) {
			x++;
		}
		else {
			x--;
		}
		if(yDest > y) {
			y++;
		}
		else {
			y--;
		}
		if(x == xDest && y == yDest) {
			xDest = (int) (Math.random() * 200);
			yDest = (int) (Math.random() * 200);
		}
	}
	public void draw(Graphics2D g) {
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, SIZE, SIZE);
	}

	public boolean isPresent() {
		return true;
	}

	public boolean contains(Point point) {
		if(point.getX() >= x && point.getX() <= x + SIZE) {
			if(point.y >= y && point.y <= y + SIZE) {
				return true;
			}
		}
		return false;
	}

	public void DoGoToLocation(int x, int y) {
		xDest = x;
		yDest = y;
	}
	
}