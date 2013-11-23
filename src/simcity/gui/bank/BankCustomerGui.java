package simcity.gui.bank;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import simcity.Location;
import simcity.buildings.bank.BankCustomerRole;
import simcity.gui.Gui;

public class BankCustomerGui extends Gui{

	private BankCustomerRole role = null;
	private int x = 0;
	private int y = 0;
	private int xDest = 0;
	private int yDest = 0;
	private final int SIZE = 20;
	
	public BankCustomerGui() {
		
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(x, y, SIZE, SIZE);
	}
	
}