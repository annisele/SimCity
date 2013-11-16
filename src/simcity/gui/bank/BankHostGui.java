package simcity.gui.bank;

import java.awt.*;
import java.util.Vector;

import simcity.buildings.bank.BankHostRole;
import simcity.gui.Gui;

public class BankHostGui implements Gui {
	private String name;
	private BankHostRole role = null;
	private int xPos = 1, yPos = 1;//default Bank teller position
	public BankHostGui (BankHostRole b) {
		this.role = b;
	}
	private boolean isPresent = false;
	public void setPresent(boolean p) {
		isPresent = p;
	}
	public void updatePosition() {
		
	}
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean contains(Point point) {
		// TODO Auto-generated method stub
		return false;
	}
}
