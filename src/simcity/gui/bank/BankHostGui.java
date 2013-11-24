package simcity.gui.bank;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import simcity.Location;
import simcity.buildings.bank.BankHostRole;
import simcity.gui.Gui;
import simcity.interfaces.bank.BankHost;

public class BankHostGui extends Gui {
	private final int HOST_X = 250;
	private final int HOST_Y = 250;
	
	private String name;
	private BankHostRole role = null;

	private boolean isPresent = false;
	
	public BankHostGui (BankHostRole b) {
		this.role = b;
	}
	
	public void setPresent(boolean p) {
		isPresent = p;
	}
	public void updatePosition() {
		
	}
	public void draw(Graphics2D g) {
		g.setColor(Color.PINK);
		g.fillRect(x, y, SIZE, SIZE);
	}
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean contains(Point point) {
		// TODO Auto-generated method stub
		return false;
	}

	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}
}
